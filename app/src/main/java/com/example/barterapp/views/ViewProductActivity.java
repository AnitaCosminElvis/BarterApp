package com.example.barterapp.views;

import static com.example.barterapp.utility.DefinesUtility.USER_MAX_NO_OF_FLAGS;
import static com.example.barterapp.utility.DefinesUtility.USER_MIN_RATING_VALUE;
import static com.example.barterapp.utility.OperationsUtility.getFormatedFloatText;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

 import  com.example.barterapp.R;
import com.example.barterapp.data.Product;
import com.example.barterapp.data.UserReviewAggregationData;
import com.example.barterapp.utility.DateUtility;
import com.example.barterapp.utility.OperationsUtility;
import com.example.barterapp.view_models.ProductsViewModel;
import com.example.barterapp.view_models.ViewModelFactory;
import com.example.barterapp.view_models.ViewReviewViewModel;
import com.squareup.picasso.Picasso;

/**
 * The type View product activity.
 */
public class ViewProductActivity extends AppCompatActivity {
    private ProductsViewModel                           mProductsViewModel;
    private ViewReviewViewModel                         mReviewsModel;
    private MutableLiveData<UserReviewAggregationData>  mReviewAggregationLiveData;
    private TextView                                    mDateTextView;
    private TextView                                    mAliasTextView;
    private TextView                                    mTitleTextView;
    private TextView                                    mDescriptionTextView;
    private TextView                                    mUserReviewValue;
    private TextView                                    mFlagValueTextView;
    private ImageView                                   mProductPhotoImageView;
    private ImageView                                   mProductVidImageView;
    private RatingBar                                   mNegativeRatingBar;
    private RatingBar                                   mPozitiveRatingBar;
    private LinearLayout                                mUserRatingLinearLayout;
    private Button                                      mBarterButton;
    private Button                                      mViewUsersProductsButton;
    private Product                                     mProduct;
    private float                                       mAvgRatingValue;
    private int                                         mNoOfFlags;
    private boolean                                     mIsUserRestricted       = false;

    /**
     * initializing class members
     *
     * @param savedInstanceState
     * @return void
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //create the view models
        mProductsViewModel = ViewModelProviders.of(this, new ViewModelFactory())
                .get(ProductsViewModel.class);
        mReviewsModel = ViewModelProviders.of(this, new ViewModelFactory())
                .get(ViewReviewViewModel.class);
        //get live data reference
        mReviewAggregationLiveData = mReviewsModel.getMutableLiveDataReviewAggregationData();

        //inti ui elements
        mDateTextView = findViewById(R.id.tv_product_view_date);
        mAliasTextView = findViewById(R.id.tv_product_view_user);
        mTitleTextView = findViewById(R.id.tv_view_product_title);
        mDescriptionTextView = findViewById(R.id.tv_view_product_description);
        mUserReviewValue = findViewById(R.id.tv_prod_view_user_value);
        mFlagValueTextView = findViewById(R.id.tv_view_product_flag_value);
        mProductPhotoImageView = findViewById(R.id.ib_view_photo);
        mProductVidImageView = findViewById(R.id.ib_view_video);
        mNegativeRatingBar = findViewById(R.id.rb_prod_negative_val);
        mPozitiveRatingBar = findViewById(R.id.rb_prod_view_pozitive_val);
        mUserRatingLinearLayout = findViewById(R.id.ll_prod_view_user_rating);
        mBarterButton = findViewById(R.id.btn_barter);
        mViewUsersProductsButton = findViewById(R.id.btn_other_products);

        //get the product object from the intent
        mProduct = getIntent().getParcelableExtra(getText(R.string.product_info_tag).toString());

        mDateTextView.setText(DateUtility.getDateFromTimestampByFormat(mProduct.getmTimeStamp()));
        mAliasTextView.setText(mProduct.getAlias());

        mTitleTextView.setText(mProduct.getmTitle());
        mDescriptionTextView.setText(mProduct.getmDescription());

        //triggers event
        mReviewsModel.triggerGetReviewDataByUserId(mProduct.getmUserId());

        //if image URI is valid loads the image
        if ((null != mProduct.getImgUriPath()) && (false == mProduct.getImgUriPath().isEmpty())) {
            Picasso.get().load(mProduct.getImgUriPath()).fit().centerCrop().
                    tag(this).into(mProductPhotoImageView);
            mProductPhotoImageView.setPadding(1, 1, 1, 1);
        }

        //if video URI is valid loads the image
        if ((null != mProduct.getVidUriPath()) && (false == mProduct.getVidUriPath().isEmpty())) {
            mProductVidImageView.setImageResource(R.drawable.ic_view_video_violet_100dp);
        }

        //create observer for live data
        mReviewAggregationLiveData.observe(this, new Observer<UserReviewAggregationData>() {
            @Override
            public void onChanged(UserReviewAggregationData aggregationData) {
                if (null != aggregationData) {
                    mAvgRatingValue = aggregationData.getmUserRatingAvg();
                    mNoOfFlags = aggregationData.getmNoOfFlaggs();
                    mUserReviewValue.setText(getFormatedFloatText(mAvgRatingValue));
                    mFlagValueTextView.setText(String.valueOf(mNoOfFlags));

                    //sets the rating bars
                    if (0 > mAvgRatingValue) {
                        mPozitiveRatingBar.setRating(0);
                        mNegativeRatingBar.setRating(OperationsUtility.
                                inverseFloatValueSign(mAvgRatingValue));
                    } else {
                        mNegativeRatingBar.setRating(0);
                        mPozitiveRatingBar.setRating(mAvgRatingValue);
                    }

                    if ((USER_MIN_RATING_VALUE > mAvgRatingValue) || (USER_MAX_NO_OF_FLAGS < mNoOfFlags)){
                        mIsUserRestricted = true;
                        Toast.makeText(ViewProductActivity.this,
                                getString(R.string.user_restricted_access) , Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        //set listeners
        mUserRatingLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if user isn't signed in return
                if (!mProductsViewModel.isUserSignedIn()) {
                    Toast.makeText(ViewProductActivity.this,getString(R.string.sign_in),
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(ViewProductActivity.this, ReviewsActivity.class);
                intent.putParcelableArrayListExtra(getString(R.string.view_reviews_info_tag),
                        mReviewAggregationLiveData.getValue().getmUserReviewsList());
                startActivity(intent);
            }
        });

        mViewUsersProductsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if user isn't signed in return
                if (!mProductsViewModel.isUserSignedIn()) {
                    Toast.makeText(ViewProductActivity.this, getString(R.string.sign_in), Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(ViewProductActivity.this, UserProductsActivity.class);
                intent.putExtra(getString(R.string.view_products_info_tag), mProduct.getmUserId());
                startActivity(intent);
            }
        });

        mBarterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if user isn't signed in return
                if (!mProductsViewModel.isUserSignedIn()) {
                    Toast.makeText(ViewProductActivity.this,getString(R.string.sign_in),
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                //if same user, deny barter access
                if (mProduct.getmUserId().equals(mProductsViewModel.getCurrentUserId())){
                    Toast.makeText(ViewProductActivity.this,
                            getString(R.string.barter_same_user), Toast.LENGTH_LONG).show();
                    return;
                }

                //if user is restricted return
                if (mIsUserRestricted) {
                    Toast.makeText(ViewProductActivity.this,
                            getString(R.string.user_restricted_access), Toast.LENGTH_LONG).show();
                    return;
                }

                //go to Offer Activity
                Intent intent = new Intent(ViewProductActivity.this, OfferActivity.class);
                intent.putExtra(getString(R.string.offer_user_id_tag), mProduct.getmUserId());
                intent.putExtra(getString(R.string.offer_product_id_tag), mProduct.getProductId());
                intent.putExtra(getString(R.string.offer_product_img__uri_tag), mProduct.getImgUriPath());
                startActivity(intent);
            }
        });

        mProductPhotoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if user isn't signed in return
                if (!mProductsViewModel.isUserSignedIn()) {
                    Toast.makeText(ViewProductActivity.this,getString(R.string.sign_in),
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if (mProduct.getImgUriPath().isEmpty()) return;

                Intent intent = new Intent(ViewProductActivity.this, ViewImageActivity.class);
                intent.putExtra(getString(R.string.view_image_info_tag), mProduct.getImgUriPath());
                startActivity(intent);
            }
        });

        mProductVidImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if user isn't signed in return
                if (!mProductsViewModel.isUserSignedIn()) {
                    Toast.makeText(ViewProductActivity.this,getString(R.string.sign_in),
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if (mProduct.getVidUriPath().isEmpty()) return;

                Intent intent = new Intent(ViewProductActivity.this, ViewVideoActivity.class);
                intent.putExtra(getString(R.string.view_video_info_tag), mProduct.getVidUriPath());
                startActivity(intent);
            }
        });
    }
}
