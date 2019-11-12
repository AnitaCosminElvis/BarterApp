package com.example.barterapp.views.AccountFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.barterapp.R;
import com.example.barterapp.data.UserProfile;
import com.example.barterapp.data.UserReview;
import com.example.barterapp.data.UserReviewAggregationData;
import com.example.barterapp.view_models.AccountViewModels.ProfileViewModel;
import com.example.barterapp.view_models.ViewModelFactory;
import com.example.barterapp.view_models.ViewReviewViewModel;
import com.google.firebase.database.annotations.Nullable;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class ProfileFragment extends Fragment {
    private OnProfileInteractionListener                        mListener;
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
    private LinearLayout                                        mViewReviewsLinearLayout;
    private DecimalFormat                                       mDecFormat = new DecimalFormat("#.##");

    private static volatile ProfileFragment     mInstance;

    private ProfileFragment() {
    }

    @SuppressWarnings("unused")
    public static synchronized ProfileFragment getInstance() {
        if (mInstance == null) {
            mInstance = new ProfileFragment();
        }
        return mInstance;
    }

    @Override
    public void onResume() {
        mReviewViewModel.triggerGetReviewDataByUserId(mProfileViewModel.getUserID());
        super.onResume();
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

        mReviewViewModel.triggerGetReviewDataByUserId(mProfileViewModel.getUserID());

        mReviewAggregationLiveData.observe(this, new Observer<UserReviewAggregationData>() {
            @Override
            public void onChanged(UserReviewAggregationData userReviewAggregationData) {
                if (null != userReviewAggregationData){
                    mFlagValueTextView.setText(String.valueOf(userReviewAggregationData.getmNoOfFlaggs()));
                    float ratingAvg = userReviewAggregationData.getmUserRatingAvg();
                    mRatingValueTextView.setText(mDecFormat.format(ratingAvg));
                    if (0 > ratingAvg) {
                        mPozitiveRatingBar.setRating(0);
                        mNegativeRatingBar.setRating(2 + ratingAvg);
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
        mViewReviewsLinearLayout = root.findViewById(R.id.ll_profile_user_rating);

        mViewReviewsLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.OnProfileInteractionListener(mReviewAggregationLiveData.getValue().getmUserReviewsList());
            }
        });

        mProfileViewModel.getUserProfile();
        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnProfileInteractionListener) {
            mListener = (OnProfileInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnProfileInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnProfileInteractionListener {
        void OnProfileInteractionListener(ArrayList<UserReview> reviews);
    }
}