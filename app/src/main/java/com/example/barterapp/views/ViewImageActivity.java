package com.example.barterapp.views;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

 import  com.example.barterapp.R;
import com.squareup.picasso.Picasso;

/**
 * The View the product's image.
 */
public class ViewImageActivity extends AppCompatActivity {
    String              mImageUri;
    ImageView           mImageView;

    /**
     * initializing class members
     *
     * @param savedInstanceState
     * @return void
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //get image URI string
        mImageUri = getIntent().getStringExtra(getString(R.string.view_image_info_tag));

        //init ImageView
        mImageView = findViewById(R.id.iv_view_image);

        //if image Uri is valid
        if ((null != mImageUri) && (!mImageUri.isEmpty()))
            Picasso.get().load(mImageUri).fit().centerCrop().tag(this).into(mImageView);
    }
}
