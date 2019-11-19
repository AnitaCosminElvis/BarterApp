package com.example.barterapp.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.example.barterapp.R;

/**
 * Used to View the product's video.
 */
public class ViewVideoActivity extends AppCompatActivity {
    String              mVideoUri;
    VideoView           mVideoView;
    ProgressBar         mProgressBar;

    /**
     * initializing class members
     *
     * @param savedInstanceState
     * @return void
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_video);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //gets the video uri as a string
        mVideoUri = getIntent().getStringExtra(getString(R.string.view_video_info_tag));

        //init ui elements
        mVideoView = findViewById(R.id.vv_view_video);
        mProgressBar = findViewById(R.id.pb_view_video);

        //set a media controller
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(mVideoView);
        mVideoView.setMediaController(mediaController);

        //if URI is valid
        if ((null != mVideoUri) && (!mVideoUri.isEmpty())) {
            //load uri into the video view and start it
            Uri uri = Uri.parse(mVideoUri);
            mVideoView.setVideoURI(uri);
            mVideoView.start();

            mProgressBar.setVisibility(View.VISIBLE);

            //add listener to stop the progress bar
            mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                    mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                        @Override
                        public void onVideoSizeChanged(MediaPlayer mp, int arg1,
                                                       int arg2) {
                            mProgressBar.setVisibility(View.GONE);
                            mp.start();
                        }
                    });
                }
            });
        }
    }
}
