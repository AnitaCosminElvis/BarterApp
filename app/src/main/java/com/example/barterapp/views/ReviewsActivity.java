package com.example.barterapp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import com.example.barterapp.R;
import com.example.barterapp.data.ProductsAdapter;
import com.example.barterapp.data.ReviewsAdapter;
import com.example.barterapp.data.UserReview;
import com.example.barterapp.utility.DefinesUtility;

import java.util.ArrayList;

public class ReviewsActivity    extends AppCompatActivity
                                implements  ReviewsAdapter.ItemClickListener{
    RecyclerView                    mReviewsRecyclerView;
    ReviewsAdapter                  mReviewsAdapter;
    ArrayList<UserReview>           mReviews;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

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
