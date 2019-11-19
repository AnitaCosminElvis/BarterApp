package com.example.barterapp.view_models;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.barterapp.data.Response;
import com.example.barterapp.data.UserReview;
import com.example.barterapp.data.UserReviewAggregationData;
import com.example.barterapp.models.AuthentificationModel;
import com.example.barterapp.models.ReviewsModel;

/**
 * The type View review view model.
 */
public class ViewReviewViewModel extends ViewModel {
    AuthentificationModel           mAuth;
    ReviewsModel                    mReviewsModel;

    /**
     * Instantiates a new View review view model.
     *
     * @param revModel the rev model
     * @param auth     the auth
     */
    public ViewReviewViewModel( ReviewsModel revModel, AuthentificationModel auth ) {
        mAuth = auth;
        mReviewsModel = revModel;
    }

    /**
     * Get mutable live set review response mutable live data.
     *
     * @return the mutable live data
     */
    public MutableLiveData<Response> getMutableLiveSetReviewResponse(){ return mReviewsModel.getMutableLiveSetReviewResponse(); }

    /**
     * Get mutable live data user reviews mutable live data.
     *
     * @return the mutable live data
     */
    public MutableLiveData<UserReview> getMutableLiveDataUserReviews(){ return mReviewsModel.getMutableLiveDataUserReviews(); }

    /**
     * Get mutable live data review aggregation data mutable live data.
     *
     * @return the mutable live data
     */
    public MutableLiveData<UserReviewAggregationData> getMutableLiveDataReviewAggregationData(){
        return mReviewsModel.getMutableLiveDataUserReviewAggregationData();
    }

    /**
     * Get current user id string.
     *
     * @return the string
     */
    public String getCurrentUserId(){ return mAuth.getCurrentUserId();}

    /**
     * Trigger get user review by user id and product id.
     *
     * @param userID the user id
     * @param offerId the offer id
     */
    public void triggerGetUserReviewByUserIdAndOfferId(String userID, String offerId) {
        mReviewsModel.triggerGetUserReviewByUserIdAndOfferId(userID,offerId);
    }

    /**
     * Sets user review by user id and product id.
     *
     * @param userRev the user rev
     * @param userId  the user id
     */
    public void setUserReviewByUserIdAndOfferId(UserReview userRev, String userId) {
        mReviewsModel.setUserReviewByUserIdAndOfferId(userRev,userId);
    }

    /**
     * Trigger get review data by user id.
     *
     * @param userID the user id
     */
    public void triggerGetReviewDataByUserId(String userID) { mReviewsModel.triggerGetUserReviewData(userID);}
}
