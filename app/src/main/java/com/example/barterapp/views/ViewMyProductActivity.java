package com.example.barterapp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.barterapp.R;
import com.example.barterapp.data.Product;
import com.example.barterapp.utility.DateUtility;
import com.example.barterapp.view_models.ViewModelFactory;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

/**
 * The type View my product activity.
 */
public class ViewMyProductActivity extends AppCompatActivity {
    /**
     * The M date text view.
     */
    TextView                    mDateTextView;
    /**
     * The M alias text view.
     */
    TextView                    mAliasTextView;
    /**
     * The M title text view.
     */
    TextView                    mTitleTextView;
    /**
     * The M description text view.
     */
    TextView                    mDescriptionTextView;
    /**
     * The M product photo image view.
     */
    ImageView                   mProductPhotoImageView;
    /**
     * The M product vid image view.
     */
    ImageView                   mProductVidImageView;
    /**
     * The M product.
     */
    Product                     mProduct;

    /**
     * initializing class members
     *
     * @param savedInstanceState
     * @return void
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_product);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        mDateTextView = findViewById(R.id.tv_product_view_date);
        mAliasTextView = findViewById(R.id.tv_product_view_user);
        mTitleTextView = findViewById(R.id.tv_view_product_title);
        mDescriptionTextView = findViewById(R.id.tv_view_product_description);
        mProductPhotoImageView  = findViewById(R.id.ib_view_photo);
        mProductVidImageView = findViewById(R.id.ib_view_video);

        mProduct  = getIntent().getParcelableExtra(getText(R.string.product_info_tag).toString());

        mDateTextView.setText(DateUtility.getDateFromTimestampByFormat(mProduct.getmTimeStamp()));
        mAliasTextView.setText(mProduct.getAlias());

        mTitleTextView.setText(mProduct.getmTitle());
        mDescriptionTextView.setText(mProduct.getmDescription());

        if ((null != mProduct.getImgUriPath()) && (false == mProduct.getImgUriPath().isEmpty())) {
            Picasso.get().load(mProduct.getImgUriPath()).fit().centerCrop().
                    tag(this).into(mProductPhotoImageView);
            mProductPhotoImageView.setPadding(1, 1, 1, 1);
        }

        if ((null != mProduct.getVidUriPath()) && (false == mProduct.getVidUriPath().isEmpty())) {
            mProductVidImageView.setImageResource(R.drawable.ic_view_video_violet_100dp);
        }

        mProductPhotoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mProduct.getImgUriPath().isEmpty()) return;

                Intent intent = new Intent(ViewMyProductActivity.this, ViewImageActivity.class);
                intent.putExtra(getString(R.string.view_image_info_tag), mProduct.getImgUriPath());
                startActivity(intent);
            }
        });

        mProductVidImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mProduct.getVidUriPath().isEmpty()) return;

                Intent intent = new Intent(ViewMyProductActivity.this, ViewVideoActivity.class);
                intent.putExtra(getString(R.string.view_video_info_tag), mProduct.getVidUriPath());
                startActivity(intent);
            }
        });
    }
}
