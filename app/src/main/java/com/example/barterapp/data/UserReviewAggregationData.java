package com.example.barterapp.data;

import java.util.ArrayList;

public class UserReviewAggregationData {
    float                   mUserRatingAvg;
    int                     mNoOfFlaggs;
    ArrayList<UserReview>   mUserReviewsList;

    public UserReviewAggregationData(){}

    public UserReviewAggregationData(float mUserRatingAvg, int mNoOfFlaggs, ArrayList<UserReview> mUserReviewsList) {
        this.mUserRatingAvg = mUserRatingAvg;
        this.mNoOfFlaggs = mNoOfFlaggs;
        this.mUserReviewsList = mUserReviewsList;
    }

    public float getmUserRatingAvg() {
        return mUserRatingAvg;
    }

    public void setmUserRatingAvg(float mUserRatingAvg) {
        this.mUserRatingAvg = mUserRatingAvg;
    }

    public int getmNoOfFlaggs() {
        return mNoOfFlaggs;
    }

    public void setmNoOfFlaggs(int mNoOfFlaggs) {
        this.mNoOfFlaggs = mNoOfFlaggs;
    }

    public ArrayList<UserReview> getmUserReviewsList() {
        return mUserReviewsList;
    }

    public void setmUserReviewsList(ArrayList<UserReview> mUserReviewsList) {
        this.mUserReviewsList = mUserReviewsList;
    }
}
