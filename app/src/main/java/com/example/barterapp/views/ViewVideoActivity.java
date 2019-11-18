package com.example.barterapp.views;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.example.barterapp.R;

/**
 * The type View video activity.
 */
public class ViewVideoActivity extends AppCompatActivity {
    /**
     * The M video uri.
     */
    String              mVideoUri;
    /**
     * The M video view.
     */
    VideoView           mVideoView;
    /**
     * The M progress bar.
     */
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

        mVideoUri = getIntent().getStringExtra(getString(R.string.view_video_info_tag));

        mVideoView = findViewById(R.id.vv_view_video);
        mProgressBar = findViewById(R.id.pb_view_video);

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(mVideoView);
        mVideoView.setMediaController(mediaController);

        if ((null != mVideoUri) && (!mVideoUri.isEmpty())) {
            Uri uri = Uri.parse(mVideoUri);
            mVideoView.setVideoURI(uri);
            mVideoView.start();

            mProgressBar.setVisibility(View.VISIBLE);

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
