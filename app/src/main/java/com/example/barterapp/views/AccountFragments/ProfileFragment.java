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
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.barterapp.R;
import com.example.barterapp.data.UserProfile;
import com.example.barterapp.data.UserReviewAggregationData;
import com.example.barterapp.view_models.AccountViewModels.ProfileViewModel;
import com.example.barterapp.view_models.ViewModelFactory;
import com.example.barterapp.view_models.ViewReviewViewModel;
import com.google.firebase.database.annotations.Nullable;

/**
 * A placeholder fragment containing a simple view.
 */
public class ProfileFragment extends Fragment {
    private ProfileViewModel                                    mProfileViewModel;
    private ViewReviewViewModel                                 mReviewViewModel;
    private MutableLiveData<UserProfile>                        mUserProfileLiveData;
    private MutableLiveData<UserReviewAggregationData>          mReviewAggregationLiveData;
    private TextView                                            mFirstNameTextView;
    private TextView                                            mSurnameTextView;
    private TextView                                            mTelNoTextView;
    private TextView                                            mAliasTextView;
    private TextView                                            mEmailTextView;
    private TextView                                            mFlagValueTextView;
    private TextView                                            mRatingValueTextView;
    private RatingBar                                           mNegativeRatingBar;
    private RatingBar                                           mPozitiveRatingBar;

    private static volatile ProfileFragment     mInstance;

    private ProfileFragment() {
    }

    @SuppressWarnings("unused")
    public static synchronized ProfileFragment getInstance() {
        if (mInstance == null) {
            mInstance = new ProfileFragment();
        }
        return mInstance;
        // TODO: Customize parameter initialization
//        Bundle args = new Bundle();
//        args.putInt(ARG_COLUMN_COUNT, columnCount);
//        fragment.setArguments(args);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mProfileViewModel = ViewModelProviders.of(this, new ViewModelFactory())
                .get(ProfileViewModel.class);
        mReviewViewModel = ViewModelProviders.of(this, new ViewModelFactory())
                .get(ViewReviewViewModel.class);

        mUserProfileLiveData = mProfileViewModel.getUserProfileLiveData();
        mReviewAggregationLiveData = mReviewViewModel.getMutableLiveDataReviewAggregationData();
        mReviewViewModel.triggerGetMyReviewData(mProfileViewModel.getUserID());

        mReviewAggregationLiveData.observe(this, new Observer<UserReviewAggregationData>() {
            @Override
            public void onChanged(UserReviewAggregationData userReviewAggregationData) {
                if (null != userReviewAggregationData){
                    mFlagValueTextView.setText(String.valueOf(userReviewAggregationData.getmNoOfFlaggs()));
                    float ratingAvg = userReviewAggregationData.getmUserRatingAvg();
                    mRatingValueTextView.setText(String.valueOf(ratingAvg));
                    if (0 > ratingAvg) {
                        mPozitiveRatingBar.setRating(0);
                        mNegativeRatingBar.setRating(1 + ratingAvg);
                    }else{
                        mNegativeRatingBar.setRating(0);
                        mPozitiveRatingBar.setRating(ratingAvg);
                    }
                }
            }
        });

        mUserProfileLiveData.observe(this, new Observer<UserProfile>(){
            @Override
            public void onChanged(@Nullable UserProfile userProfile){
                if (null != userProfile){
                    mFirstNameTextView.setText(userProfile.getmFirstName());
                    mSurnameTextView.setText(userProfile.getmSurname());
                    mAliasTextView.setText(userProfile.getmAlias());
                    mTelNoTextView.setText(userProfile.getmTelNo());
                    mEmailTextView.setText(userProfile.getmEmail());
                    //ToDo : populate profile
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
        mRatingValueTextView = root.findViewById(R.id.tv_profile_value);
        mNegativeRatingBar = root.findViewById(R.id.rb_profile_negative_val);
        mPozitiveRatingBar = root.findViewById(R.id.rb_profile_pozitive_val);
        mFlagValueTextView = root.findViewById(R.id.tv_profile_flag_value);

        mProfileViewModel.getUserProfile();
        return root;
    }
}