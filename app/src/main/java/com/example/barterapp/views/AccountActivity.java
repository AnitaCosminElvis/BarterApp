package com.example.barterapp.views;

import android.os.Bundle;

import com.example.barterapp.R;
import com.example.barterapp.views.AccountFragments.HistoryFragment;
import com.example.barterapp.views.AccountFragments.MyProductsFragment;
import com.example.barterapp.views.AccountFragments.OffersFragment;
import com.example.barterapp.views.AccountFragments.SectionsPagerAdapter;
import com.example.barterapp.views.AccountFragments.dummy.DummyContent;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;


public class AccountActivity extends    AppCompatActivity
                             implements MyProductsFragment.OnListFragmentInteractionListener,
                                        OffersFragment.OnListFragmentInteractionListener,
                                        HistoryFragment.OnListFragmentInteractionListener{
    private SectionsPagerAdapter    mSectionsPageAdapter;
    private ViewPager               mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        mSectionsPageAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        mViewPager = findViewById(R.id.view_pager);
        mViewPager.setAdapter(mSectionsPageAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(mViewPager);
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }
}