package com.example.barterapp.views;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

 import  com.example.barterapp.R;
import com.example.barterapp.data.Offer;
import com.example.barterapp.data.Product;
import com.example.barterapp.data.UserReview;
import com.example.barterapp.views.AccountFragments.MyProductsFragment;
import com.example.barterapp.views.AccountFragments.OffersFragment;
import com.example.barterapp.views.AccountFragments.ProfileFragment;
import com.example.barterapp.views.AccountFragments.ReviewsFragment;
import com.example.barterapp.views.AccountFragments.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

/**
 * The Account activity manages the fragments and listens for events
 */
public class AccountActivity extends    AppCompatActivity
                             implements MyProductsFragment.OnMyProductInteractionListener,
                                        OffersFragment.OnOfferInteractionListener,
                                        ReviewsFragment.OnHistoryInteractionListener,
                                        ProfileFragment.OnProfileInteractionListener {
    private SectionsPagerAdapter    mSectionsPageAdapter;
    private ViewPager               mViewPager;

    /**
     * initializing class members
     *
     * @param savedInstanceState
     * @return void
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //set section pager for managing fragments into the tab layout
        mSectionsPageAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        mViewPager = findViewById(R.id.view_pager);
        mViewPager.setAdapter(mSectionsPageAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(mViewPager);
    }

    @Override
    public void OnMyProductInteractionListener(Product item) {
        Intent intent = new Intent(AccountActivity.this, ViewMyProductActivity.class);
        intent.putExtra(getText(R.string.product_info_tag).toString(),item);
        startActivity(intent);
    }

    @Override
    public void OnOfferInteractionListener(Offer item) {
        Intent intent = new Intent(AccountActivity.this, ViewOfferActivity.class);
        intent.putExtra(getString(R.string.view_offer_info_tag),item);
        startActivity(intent);
    }

    @Override
    public void OnHistoryInteractionListener(Offer item) {
        Intent intent = new Intent(AccountActivity.this, ViewReviewActivity.class);
        intent.putExtra(getString(R.string.view_review_info_tag),item);
        startActivity(intent);
    }

    @Override
    public void OnProfileInteractionListener(ArrayList<UserReview> reviews) {
        Intent intent = new Intent(AccountActivity.this, ReviewsActivity.class);
        intent.putParcelableArrayListExtra(getString(R.string.view_reviews_info_tag),reviews);
        startActivity(intent);
    }
}