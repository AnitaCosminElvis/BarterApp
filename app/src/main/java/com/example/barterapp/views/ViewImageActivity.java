package com.example.barterapp.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.barterapp.R;
import com.squareup.picasso.Picasso;

/**
 * The type View image activity.
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

        mImageUri = getIntent().getStringExtra(getString(R.string.view_image_info_tag));

        mImageView = findViewById(R.id.iv_view_image);

        if ((null != mImageUri) && (!mImageUri.isEmpty()))
            Picasso.get().load(mImageUri).fit().centerCrop().tag(this).into(mImageView);

    }
}
