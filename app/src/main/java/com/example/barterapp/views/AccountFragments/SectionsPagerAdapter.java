package com.example.barterapp.views.AccountFragments;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

 import  com.example.barterapp.R;


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[]      TAB_TITLES = new int[]{R.string.tab_text_profile,
                                                    R.string.tab_text_products,
                                                    R.string.tab_text_offers,
                                                    R.string.tab_text_review};
    private static final int        TAB_COUNT = 4;
    private final Context           mContext;

    /**
     * initializing class members
     *
     * @param context the context
     * @param fm      the fm
     * @return null
     */
    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to get the fragment instance for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (position){
            case 0:
                return ProfileFragment.getInstance();
            case 1:
                return MyProductsFragment.getInstance();
            case 2:
                return OffersFragment.getInstance();
            case 3:
                return ReviewsFragment.getInstance();
        }

        return null;

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show total pages.
        return TAB_COUNT;
    }
}