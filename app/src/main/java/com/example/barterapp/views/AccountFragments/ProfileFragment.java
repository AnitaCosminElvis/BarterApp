package com.example.barterapp.views.AccountFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.barterapp.R;
import com.example.barterapp.data.UserProfile;
import com.example.barterapp.view_models.AccountViewModels.ProfileViewModel;
import com.example.barterapp.view_models.ViewModelFactory;
import com.google.firebase.database.annotations.Nullable;

/**
 * A placeholder fragment containing a simple view.
 */
public class ProfileFragment extends Fragment {
    private ProfileViewModel                mProfileViewModel;
    private MutableLiveData<UserProfile>    mUserProfileLiveData;
    private TextView                        mFirstNameTextView;
    private TextView                        mSurnameTextView;
    private TextView                        mTelNoTextView;
    private TextView                        mAliasTextView;
    private TextView                        mEmailTextView;
    private TextView                        mRatingValueTextView;
    private RatingBar                       mUserRatingBar;

    public ProfileFragment(){}

    public static ProfileFragment newInstance(int index) {
        ProfileFragment fragment = new ProfileFragment();
//        Bundle bundle = new Bundle();
//        bundle.putInt(ARG_SECTION_NUMBER, index);
//        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mProfileViewModel = ViewModelProviders.of(this, new ViewModelFactory())
                .get(ProfileViewModel.class);

        mUserProfileLiveData = mProfileViewModel.getUserProfileLiveData();
        mUserProfileLiveData.observe(this, new Observer<UserProfile>(){
            @Override
            public void onChanged(@Nullable UserProfile userProfile){
                if (null != userProfile){
                    mFirstNameTextView.setText(userProfile.getmFirstName());
                    mSurnameTextView.setText(userProfile.getmSurname());
                    mAliasTextView.setText(userProfile.getmAlias());
                    mTelNoTextView.setText(userProfile.getmTelNo());
                    mEmailTextView.setText(userProfile.getmEmail());
                    mRatingValueTextView.setText("10");
                    mUserRatingBar.setRating(10);
                }
            }
        });
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_account_profile, container, false);

        mAliasTextView = root.findViewById(R.id.tv_alias);
        mFirstNameTextView = root.findViewById(R.id.tv_first_name);
        mSurnameTextView  = root.findViewById(R.id.tv_surname);
        mTelNoTextView  = root.findViewById(R.id.tv_telephone);
        mEmailTextView = root.findViewById(R.id.tv_email);
        mRatingValueTextView = root.findViewById(R.id.tv_rating_value);
        mUserRatingBar = root.findViewById(R.id.rb_user_rating);

        mProfileViewModel.getUserProfile();
        return root;
    }
}