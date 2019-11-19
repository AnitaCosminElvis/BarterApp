package com.example.barterapp.data;

import java.util.ArrayList;

/**
 * The entity User review aggregation data.
 */
public class UserReviewAggregationData {
    float                   mUserRatingAvg;
    int                     mNoOfFlaggs;
    ArrayList<UserReview>   mUserReviewsList;
    public UserReviewAggregationData(){}

    /**
     * Instantiates a new User review aggregation data.
     *
     * @param mUserRatingAvg   the m user rating avg
     * @param mNoOfFlaggs      the m no of flaggs
     * @param mUserReviewsList the m user reviews list
     */
    public UserReviewAggregationData(float mUserRatingAvg, int mNoOfFlaggs, ArrayList<UserReview> mUserReviewsList) {
        this.mUserRatingAvg = mUserRatingAvg;
        this.mNoOfFlaggs = mNoOfFlaggs;
        this.mUserReviewsList = mUserReviewsList;
    }

    /**
     * Gets user rating avg.
     *
     * @return the user rating avg
     */
    public float getmUserRatingAvg() {
        return mUserRatingAvg;
    }

    /**
     * Sets user rating avg.
     *
     * @param mUserRatingAvg the m user rating avg
     */
    public void setmUserRatingAvg(float mUserRatingAvg) {
        this.mUserRatingAvg = mUserRatingAvg;
    }

    /**
     * Gets no of flaggs.
     *
     * @return the no of flaggs
     */
    public int getmNoOfFlaggs() {
        return mNoOfFlaggs;
    }

    /**
     * Sets no of flaggs.
     *
     * @param mNoOfFlaggs the m no of flaggs
     */
    public void setmNoOfFlaggs(int mNoOfFlaggs) {
        this.mNoOfFlaggs = mNoOfFlaggs;
    }

    /**
     * Gets user reviews list.
     *
     * @return the user reviews list
     */
    public ArrayList<UserReview> getmUserReviewsList() {
        return mUserReviewsList;
    }

    /**
     * Sets user reviews list.
     *
     * @param mUserReviewsList the m user reviews list
     */
    public void setmUserReviewsList(ArrayList<UserReview> mUserReviewsList) {
        this.mUserReviewsList = mUserReviewsList;
    }
}
