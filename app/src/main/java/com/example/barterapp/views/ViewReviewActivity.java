package com.example.barterapp.views;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.barterapp.R;
import com.example.barterapp.data.Offer;
import com.example.barterapp.data.Response;
import com.example.barterapp.data.UserReview;
import com.example.barterapp.utility.OperationsUtility;
import com.example.barterapp.view_models.ViewModelFactory;
import com.example.barterapp.view_models.ViewReviewViewModel;

import java.text.DecimalFormat;

import static com.example.barterapp.utility.OperationsUtility.*;


/**
 * The type View review activity.
 */
public class ViewReviewActivity extends AppCompatActivity {
    private ViewReviewViewModel         mViewReviewViewModel;
    private MutableLiveData<Response>   mSetReviewResponseLiveData;
    private MutableLiveData<UserReview> mUserReviewLiveData;
    private RatingBar                   mNegativeRatingBar;
    private RatingBar                   mPositiveRatingBar;
    private TextView                    mRatingValueTextView;
    private EditText                    mTextReviewEditText;
    private TextView                    mAliasTextView;
    private Switch                      mSetFlagSwitch;
    private ImageView                   mFlagStateImageView;
    private Button                      mApplyButton;
    private float                       mRatingValue                            = 0;
    private boolean                     mIsInitialState                         = true;
    private Offer                       mOffer;
    private String                      mReviewedUserId;
<<<<<<< HEAD

=======
    private DecimalFormat               mDecFormat                              = new DecimalFormat("#.##");

    /**
     * initializing class members
     *
     * @param savedInstanceState
     * @return void
     */
>>>>>>> parent of ffd48f8... Revert "Merge branch 'master' of https://github.com/AnitaCosminElvis/BarterApp"
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_review);

        mViewReviewViewModel = ViewModelProviders.of(this, new ViewModelFactory())
                .get(ViewReviewViewModel.class);
        mSetReviewResponseLiveData = mViewReviewViewModel.getMutableLiveSetReviewResponse();
        mUserReviewLiveData = mViewReviewViewModel.getMutableLiveDataUserReviews();

        mNegativeRatingBar = findViewById(R.id.rb_view_review_negative_val);
        mPositiveRatingBar = findViewById(R.id.rb_view_review_pozitive_val);
        mRatingValueTextView = findViewById(R.id.tv_view_review_value);
        mTextReviewEditText = findViewById(R.id.et_view_review_text);
        mAliasTextView = findViewById(R.id.tv_view_review_alias);
        mSetFlagSwitch = findViewById(R.id.sw_view_review_flag);
        mFlagStateImageView = findViewById(R.id.iv_view_review_flag);
        mApplyButton = findViewById(R.id.btn_view_review_apply);

        mOffer = getIntent().getParcelableExtra(getString(R.string.view_review_info_tag));

        String userId = mViewReviewViewModel.getCurrentUserId();

        if (userId.equals(mOffer.getmToUserId())){
            mAliasTextView.setText(mOffer.getmFromAlias());
            mReviewedUserId = mOffer.getmFromUserId();
        }
        else {
            mAliasTextView.setText(mOffer.getmToAlias());
            mReviewedUserId = mOffer.getmToUserId();
        }

        mViewReviewViewModel.triggerGetUserReviewByUserIdAndProductId(mReviewedUserId,mOffer.getmProductId());

        mUserReviewLiveData.observe(this, new Observer<UserReview>() {
            @Override
            public void onChanged(UserReview userReview) {
                if (null != userReview){
                    if (!mOffer.getmProductId().equals(userReview.getmProductId())) return;

                    mRatingValue = userReview.getmRatingValue();
                    mRatingValueTextView.setText(getFormatedFloatText(mRatingValue));

                    if (0 > mRatingValue) {
                        mPositiveRatingBar.setRating(0);
                        mNegativeRatingBar.setRating((inverseFloatValueSign(mRatingValue)));
                    }else{
                        mNegativeRatingBar.setRating(0);
                        mPositiveRatingBar.setRating(mRatingValue);
                    }

                    mTextReviewEditText.setText(userReview.getmReviewText());

                    mSetFlagSwitch.setChecked(userReview.ismIsFlagged());
                    if (userReview.ismIsFlagged()) {
                        mFlagStateImageView.setImageResource(R.drawable.ic_flag_enabled_50dp);
                    }else{
                        mFlagStateImageView.setImageResource(R.drawable.ic_flag_disabled_50dp);
                    }
                }
            }
        });

        mSetReviewResponseLiveData.observe(this, new Observer<Response>() {
            @Override
            public void onChanged(Response setReviewResponse) {
                if (null != setReviewResponse){
                    if (!setReviewResponse.getmIsSuccessfull()) {
                        Toast.makeText(ViewReviewActivity.this,
                                setReviewResponse.getmResponseText(), Toast.LENGTH_SHORT).show();
                    }else{
                        if (!mIsInitialState) finish();
                    }
                }
            }
        });

        mNegativeRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                mPositiveRatingBar.setRating(0);
                mRatingValue = 0 - v;
                mRatingValueTextView.setText(String.valueOf(mRatingValue));
                mNegativeRatingBar.setRating(v);
            }
        });

        mPositiveRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                mNegativeRatingBar.setRating(0);
                mRatingValue = v;
                mRatingValueTextView.setText(String.valueOf(mRatingValue));
                mPositiveRatingBar.setRating(v);
            }
        });

        mSetFlagSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) mFlagStateImageView.setImageResource(R.drawable.ic_flag_enabled_50dp);
                else mFlagStateImageView.setImageResource(R.drawable.ic_flag_disabled_50dp);
            }
        });

        mApplyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserReview userRev = new UserReview("", mOffer.getmProductId(),mOffer.getmProductImgUri(),
                                                    mRatingValue, mTextReviewEditText.getText().toString(),
                                                    mSetFlagSwitch.isChecked());
                mViewReviewViewModel.setUserReviewByUserIdAndProductId(userRev,mReviewedUserId,
                                                                        mOffer.getmProductId());
                mIsInitialState = false;
            }
        });
    }
}