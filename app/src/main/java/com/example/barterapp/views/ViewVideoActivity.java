package com.example.barterapp.views;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.barterapp.R;

public class ViewVideoActivity extends AppCompatActivity {
    String              mVideoUri;
    VideoView           mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_video);

        mVideoUri = getIntent().getStringExtra(getString(R.string.view_video_info_tag));

        mVideoView = findViewById(R.id.vv_view_video);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(mVideoView);
        mVideoView.setMediaController(mediaController);

        if ((null != mVideoUri) && (!mVideoUri.isEmpty())) {
            Uri uri = Uri.parse(mVideoUri);
            mVideoView.setVideoURI(uri);
            mVideoView.requestFocus();
        }


    }
}
