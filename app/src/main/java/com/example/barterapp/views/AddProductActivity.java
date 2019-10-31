package com.example.barterapp.views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.barterapp.R;
import com.example.barterapp.data.Product;
import com.example.barterapp.data.Response;
import com.example.barterapp.view_models.AddProductViewModel;
import com.example.barterapp.view_models.LoginViewModel;
import com.example.barterapp.view_models.ViewModelFactory;

import java.security.Provider;

public class AddProductActivity extends AppCompatActivity {
    private static final int            PICK_IMG_REQUEST        = 1000;
    private static final int            PICK_VIDEO_REQUEST      = 2000;
    private Uri                         mImgUri                 = null;
    private Uri                         mVideoUri               = null;
    private AddProductViewModel         mProductViewModel;
    private MutableLiveData<Response>   mAddProductResponseLiveData;
    private EditText                    mTitleEdtText;
    private EditText                    mDescriptionText;
    private Button                      mContinueBtn;
    private ImageView                   mImageView;
    private ImageView                   mVideoView;
    private Spinner                     mCategorySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        mProductViewModel = ViewModelProviders.of(this, new ViewModelFactory()).get(AddProductViewModel.class);
        mAddProductResponseLiveData = mProductViewModel.getAddProductResponseLiveData();
        mAddProductResponseLiveData.observe(this, new Observer<Response>() {
            @Override
            public void onChanged(Response response) {
                if (null != response){
                    Toast.makeText(AddProductActivity.this,
                            response.getmResponseText() , Toast.LENGTH_LONG).show();
                }
            }
        });

        mTitleEdtText = findViewById(R.id.et_title);
        mDescriptionText = findViewById(R.id.ed_description);
        mImageView = findViewById(R.id.ib_load_photo);
        mVideoView = findViewById(R.id.ib_load_video);
        mContinueBtn = findViewById(R.id.btn_continue);
        mCategorySpinner = findViewById(R.id.spinner_category);

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent imgIntent = new Intent();
                imgIntent.setType("image/*");
                imgIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(imgIntent,PICK_IMG_REQUEST);
            }
        });

        mVideoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent vidIntent = new Intent();
                vidIntent.setType("video/*");
                vidIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(vidIntent,PICK_VIDEO_REQUEST);
            }
        });

        mContinueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sTitle = mTitleEdtText.getText().toString();

                if (sTitle.isEmpty()) {
                    Toast.makeText(AddProductActivity.this,
                            "Empty title.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String sDescription = mDescriptionText.getText().toString();

                if (sDescription.isEmpty()){
                    Toast.makeText(AddProductActivity.this,
                            "Empty description.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (null == mImgUri) {
                    Toast.makeText(AddProductActivity.this,
                            "Please select a image for your product.", Toast.LENGTH_SHORT).show();
                    return;
                }

                mProductViewModel.addProduct(new Product("",sTitle,sDescription,
                        mCategorySpinner.getSelectedItem().toString(),"","",
                        System.currentTimeMillis()), mImgUri, mVideoUri);
            }
        });
    }

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
                        Bitmap bitmap;
                        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                        mediaMetadataRetriever.setDataSource(this,mVideoUri);
                        bitmap = mediaMetadataRetriever.getFrameAtTime(1, MediaMetadataRetriever.OPTION_CLOSEST);
                        mVideoView.setImageBitmap(bitmap);
                    }
                }
                break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
