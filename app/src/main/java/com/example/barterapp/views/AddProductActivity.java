package com.example.barterapp.views;

import static com.example.barterapp.utility.DefinesUtility.*;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

 import  com.example.barterapp.R;
import com.example.barterapp.data.Product;
import com.example.barterapp.data.Response;
import com.example.barterapp.data.UserReviewAggregationData;
import com.example.barterapp.view_models.AddProductViewModel;
import com.example.barterapp.view_models.ViewModelFactory;
import com.example.barterapp.view_models.ViewReviewViewModel;

/**
 * Used for Adding products
 */
public class AddProductActivity extends AppCompatActivity {
    private Uri                                                 mImgUri                 = null;
    private Uri                                                 mVideoUri               = null;
    private AddProductViewModel                                 mProductViewModel;
    private ViewReviewViewModel                                 mReviewViewModel;
    private MutableLiveData<UserReviewAggregationData>          mRatingAggregationLiveData;
    private MutableLiveData<Response>                           mAddProductResponseLiveData;
    private EditText                                            mTitleEdtText;
    private EditText                                            mDescriptionText;
    private Button                                              mContinueBtn;
    private ImageView                                           mImageView;
    private ImageView                                           mVideoView;
    private Spinner                                             mCategorySpinner;
    private float                                               mAvgRatingValue;
    private int                                                 mNoOfFlags;
    private boolean                                             mIsUserRestricted       = false;
    private boolean                                             mIsInitialState         = true;

    /**
     * initializing class members
     *
     * @params savedInstanceState
     * @return void
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //create the view models
        mProductViewModel = ViewModelProviders.of(this, new ViewModelFactory()).
                get(AddProductViewModel.class);
        mReviewViewModel = ViewModelProviders.of(this, new ViewModelFactory()).
                get(ViewReviewViewModel.class);

        //get live data references
        mRatingAggregationLiveData = mReviewViewModel.getMutableLiveDataReviewAggregationData();
        mAddProductResponseLiveData = mProductViewModel.getAddProductResponseLiveData();

        //add observers for the live data
        mRatingAggregationLiveData.observe(this, new Observer<UserReviewAggregationData>() {
            @Override
            public void onChanged(UserReviewAggregationData userReviewAggregationData) {
                if (null != userReviewAggregationData){
                    mNoOfFlags = userReviewAggregationData.getmNoOfFlaggs();
                    mAvgRatingValue = userReviewAggregationData.getmUserRatingAvg();
                    if ((USER_MAX_NO_OF_FLAGS < mNoOfFlags) || (USER_MIN_RATING_VALUE > mAvgRatingValue)){
                        mIsUserRestricted = true;
                        Toast.makeText(AddProductActivity.this,
                                getString(R.string.user_restricted_access) , Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        mAddProductResponseLiveData.observe(this, new Observer<Response>() {
            @Override
            public void onChanged(Response response) {
                if (null != response && !mIsInitialState){
                    Toast.makeText(AddProductActivity.this,
                            response.getmResponseText() , Toast.LENGTH_LONG).show();

                    mContinueBtn.setEnabled(true);
                    if (response.getmIsSuccessfull()) finish();
                }
            }
        });

        //init UI elements
        mTitleEdtText = findViewById(R.id.et_title);
        mDescriptionText = findViewById(R.id.ed_description);
        mImageView = findViewById(R.id.ib_load_photo);
        mVideoView = findViewById(R.id.ib_load_video);
        mContinueBtn = findViewById(R.id.btn_add_product);
        mCategorySpinner = findViewById(R.id.spinner_category);

        addEditorFieldsFilters();

        //set on click listeners
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent imgIntent = new Intent();
                imgIntent.setType(getString(R.string.type_img));
                imgIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(imgIntent, PICK_IMG_REQUEST);
            }
        });

        mVideoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent vidIntent = new Intent();
                vidIntent.setType(getString(R.string.type_vid));
                vidIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(vidIntent,PICK_VIDEO_REQUEST);
            }
        });

        mContinueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mIsUserRestricted){
                    Toast.makeText(AddProductActivity.this,
                            getString(R.string.user_restricted_access) , Toast.LENGTH_LONG).show();
                    return;
                }

                String sTitle = mTitleEdtText.getText().toString();

                if (sTitle.isEmpty()) {
                    Toast.makeText(AddProductActivity.this,
                            getString(R.string.empty_title), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (sTitle.length() < 3) {
                    Toast.makeText(AddProductActivity.this,
                            getString(R.string.min_title), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!sTitle.matches(".*[a-zA-Z]+.*")){
                    Toast.makeText(AddProductActivity.this,
                            getString(R.string.miss_title), Toast.LENGTH_SHORT).show();
                    return;
                }

                String sDescription = mDescriptionText.getText().toString();

                if (sDescription.isEmpty()){
                    Toast.makeText(AddProductActivity.this,
                            getString(R.string.empty_desc), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (sDescription.length() < 10) {
                    Toast.makeText(AddProductActivity.this,
                            getString(R.string.min_desc), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!sDescription.matches(".*[a-zA-Z]+.*")){
                    Toast.makeText(AddProductActivity.this,
                            getString(R.string.miss_desc), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (null == mImgUri) {
                    Toast.makeText(AddProductActivity.this,
                            getString(R.string.select_img), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (mCategorySpinner.getSelectedItem().toString()
                                    .equals(getString(R.string.cat_select))){
                    Toast.makeText(AddProductActivity.this,
                            getString(R.string.select_cat), Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    mIsInitialState = false;
                    mContinueBtn.setEnabled(false);
                    mProductViewModel.addProduct(new Product("","","",
                            sTitle,sDescription, mCategorySpinner.getSelectedItem().toString(),
                            "","", System.currentTimeMillis()), mImgUri, mVideoUri);
                }catch(Exception ex){
                    mContinueBtn.setEnabled(true);
                    Toast.makeText(AddProductActivity.this, ex.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void addEditorFieldsFilters() {
        InputFilter maxTitleFilter = new InputFilter.LengthFilter(50);
        InputFilter maxDescriptionFilter = new InputFilter.LengthFilter(250);

        InputFilter lettersAndNoFilter = (source, start, end, dest, dstart, dend) -> {
            if (dest.toString().isEmpty()) {
                if (source.toString().matches("^[a-zA-Z0-9]$")) return source;
            } else if(source.toString().matches("^[a-zA-Z0-9 '-]$")) {
                if (source.toString().matches("^[ '-]$")) {
                    if (dest.toString().endsWith(source.toString()) ||
                        dest.toString().matches(".*[^a-zA-Z0-9]"))
                        return "";
                }

                return source;
            }

            return "";
        };

        mTitleEdtText.setFilters(new InputFilter[]{maxTitleFilter, lettersAndNoFilter});
        mDescriptionText.setFilters(new InputFilter[]{maxDescriptionFilter, lettersAndNoFilter});
    }

    /**
     * handles the requests id's after picking a image or a video and sets them as thumbnails
     *
     * @params requestCode, resultCode, data
     * @return void
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        switch (requestCode){
            case PICK_IMG_REQUEST:{
                if (RESULT_OK == resultCode){
                    if ((null != data) && (null != data.getData())){
                        mImgUri = data.getData();
                        mImageView.setImageURI(mImgUri);
                        mImageView.setPadding(1,1,1,1);
                    }
                }
                break;
            }
            case PICK_VIDEO_REQUEST:{
                if (RESULT_OK == resultCode){
                    if ((null != data) && (null != data.getData())){
                        mVideoUri = data.getData();
                        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                        mediaMetadataRetriever.setDataSource(this,mVideoUri);
                        mVideoView.setImageBitmap(
                                mediaMetadataRetriever.getFrameAtTime(1,
                                        MediaMetadataRetriever.OPTION_CLOSEST));
                    }
                }
                break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
