package com.example.barterapp.views;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.example.barterapp.R;
import com.example.barterapp.data.Offer;
import com.example.barterapp.data.Product;
import com.example.barterapp.views.AccountFragments.HistoryFragment;
import com.example.barterapp.views.AccountFragments.MyProductsFragment;
import com.example.barterapp.views.AccountFragments.OffersFragment;
import com.example.barterapp.views.AccountFragments.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

public class AccountActivity extends    AppCompatActivity
                             implements MyProductsFragment.OnMyProductInteractionListener,
                                        OffersFragment.OnOfferInteractionListener,
                                        HistoryFragment.OnHistoryInteractionListener{
    private SectionsPagerAdapter    mSectionsPageAdapter;
    private ViewPager               mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mSectionsPageAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        mViewPager = findViewById(R.id.view_pager);
        mViewPager.setAdapter(mSectionsPageAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(mViewPager);
    }

    @Override
    public void OnMyProductInteractionListener(Product item) {

    }

    @Override
    public void OnOfferInteractionListener(Offer item) {
        Intent intent = new Intent(AccountActivity.this, ViewOfferActivity.class);
        intent.putExtra(getString(R.string.view_offer_info_tag),item);
        startActivity(intent);
    }

    @Override
    public void OnHistoryInteractionListener(Offer item) {

    }
}