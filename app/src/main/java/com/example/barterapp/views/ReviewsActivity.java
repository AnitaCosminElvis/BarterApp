package com.example.barterapp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import com.example.barterapp.R;
import com.example.barterapp.data.UserReview;

import java.util.ArrayList;

/**
 * The type Reviews activity.
 */
public class ReviewsActivity    extends AppCompatActivity
                                implements  ReviewsAdapter.ItemClickListener{
    RecyclerView                    mReviewsRecyclerView;
    ReviewsAdapter                  mReviewsAdapter;
    ArrayList<UserReview>           mReviews;

    /**
     * initializing class members
     *
     * @param savedInstanceState
     * @return void
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //get objects from intent
        mReviews = getIntent().getParcelableArrayListExtra(getString(R.string.view_reviews_info_tag));

        mReviewsRecyclerView = findViewById(R.id.rv_reviews_list);
        mReviewsRecyclerView.setHasFixedSize(true);
        mReviewsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                                                            false));
        mReviewsAdapter = new ReviewsAdapter(this,mReviews);
        //set the default adapter
        mReviewsRecyclerView.setAdapter(mReviewsAdapter);

    }

    @Override
    public void onItemClick(View view, int adapterPosition) {

    }
}
