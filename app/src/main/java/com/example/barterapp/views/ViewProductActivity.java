package com.example.barterapp.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.barterapp.R;
import com.example.barterapp.data.Product;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class ViewProductActivity extends AppCompatActivity {
    TextView                    mTitleTextView;
    TextView                    mDescriptionTextView;
    TextView                    mUserReviewValue;
    ImageView                   mProductPhotoImageView;
    ImageView                   mProductVidImageView;
    RatingBar                   mUserReviewRatingBar;
    Button                      mBarterButton;
    Button                      mViewUsersProductsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);

        mTitleTextView = findViewById(R.id.tv_view_product_title);
        mDescriptionTextView = findViewById(R.id.tv_view_product_description);
        mUserReviewValue = findViewById(R.id.tv_prod_view_user_value);
        mProductPhotoImageView  = findViewById(R.id.ib_view_photo);
        mProductVidImageView = findViewById(R.id.ib_view_video);
        mUserReviewRatingBar = findViewById(R.id.rb_prod_view_user_rating);
        mBarterButton = findViewById(R.id.btn_barter);
        mViewUsersProductsButton = findViewById(R.id.btn_other_products);

        Product product  = getIntent().getParcelableExtra(getText(R.string.product_info_tag).toString());

        mTitleTextView.setText(product.getmTitle());
        mDescriptionTextView.setText(product.getmDescription());

        Picasso.get().load(product.getImgUriPath()).fit().centerCrop().
                tag(this).into(mProductPhotoImageView);
        mProductPhotoImageView.setPadding(1,1,1,1);

        Uri vidUri = Uri.parse(product.getVidUriPath());
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(product.getVidUriPath(), new HashMap<String,String>());
        mProductVidImageView.setImageBitmap(
                mediaMetadataRetriever.getFrameAtTime(1, MediaMetadataRetriever.OPTION_CLOSEST));


    }
}
