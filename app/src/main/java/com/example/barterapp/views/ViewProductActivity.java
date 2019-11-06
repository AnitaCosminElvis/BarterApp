package com.example.barterapp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barterapp.R;
import com.example.barterapp.data.Product;
import com.example.barterapp.utility.DateUtility;
import com.example.barterapp.view_models.ProductInfoViewModel;
import com.example.barterapp.view_models.ViewModelFactory;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class ViewProductActivity extends AppCompatActivity {
    final String                DATE_FORMAT                     = "EEE MMM dd hh:mm:ss yyyy ";
    ProductInfoViewModel        mProductInfoViewModel;
    TextView                    mDateTextView;
    TextView                    mAliasTextView;
    TextView                    mTitleTextView;
    TextView                    mDescriptionTextView;
    TextView                    mUserReviewValue;
    ImageView                   mProductPhotoImageView;
    ImageView                   mProductVidImageView;
    RatingBar                   mUserReviewRatingBar;
    Button                      mBarterButton;
    Button                      mViewUsersProductsButton;
    Product                     mProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mProductInfoViewModel = ViewModelProviders.of(this, new ViewModelFactory())
                .get(ProductInfoViewModel.class);

        mDateTextView = findViewById(R.id.tv_product_view_date);
        mAliasTextView = findViewById(R.id.tv_product_view_user);
        mTitleTextView = findViewById(R.id.tv_view_product_title);
        mDescriptionTextView = findViewById(R.id.tv_view_product_description);
        mUserReviewValue = findViewById(R.id.tv_prod_view_user_value);
        mProductPhotoImageView  = findViewById(R.id.ib_view_photo);
        mProductVidImageView = findViewById(R.id.ib_view_video);
        mUserReviewRatingBar = findViewById(R.id.rb_prod_view_user_rating);
        mBarterButton = findViewById(R.id.btn_barter);
        mViewUsersProductsButton = findViewById(R.id.btn_other_products);

        mProduct  = getIntent().getParcelableExtra(getText(R.string.product_info_tag).toString());

        mDateTextView.setText(DateUtility.getDateFromTimestampByFormat(mProduct.getmTimeStamp(),DATE_FORMAT));
        mAliasTextView.setText(mProduct.getAlias());

        mTitleTextView.setText(mProduct.getmTitle());
        mDescriptionTextView.setText(mProduct.getmDescription());

        if ((null != mProduct.getImgUriPath()) && (false == mProduct.getImgUriPath().isEmpty())) {
            Picasso.get().load(mProduct.getImgUriPath()).fit().centerCrop().
                    tag(this).into(mProductPhotoImageView);
            mProductPhotoImageView.setPadding(1, 1, 1, 1);
        }

        if ((null != mProduct.getVidUriPath()) && (false == mProduct.getVidUriPath().isEmpty())) {
            Uri vidUri = Uri.parse(mProduct.getVidUriPath());
            MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
            mediaMetadataRetriever.setDataSource(mProduct.getVidUriPath(), new HashMap<String, String>());
            mProductVidImageView.setImageBitmap(
                    mediaMetadataRetriever.getFrameAtTime(1, MediaMetadataRetriever.OPTION_CLOSEST));
        }

        mBarterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!mProductInfoViewModel.isSignedIn()){
                    Toast.makeText(ViewProductActivity.this, "Please Sign in." , Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(ViewProductActivity.this, OfferActivity.class);
                intent.putExtra(getString(R.string.offer_user_id_tag), mProduct.getmUserId());
                intent.putExtra(getString(R.string.offer_product_id_tag), mProduct.getProductId());
                intent.putExtra(getString(R.string.offer_product_img__uri_tag), mProduct.getImgUriPath());
                startActivity(intent);
            }
        });
    }
}
