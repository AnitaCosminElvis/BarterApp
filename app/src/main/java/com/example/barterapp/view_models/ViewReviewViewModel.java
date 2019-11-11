package com.example.barterapp.view_models;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.barterapp.data.Response;
import com.example.barterapp.data.UserReview;
import com.example.barterapp.data.UserReviewAggregationData;
import com.example.barterapp.models.AuthentificationModel;
import com.example.barterapp.models.ReviewsModel;

public class ViewReviewViewModel extends ViewModel {
    AuthentificationModel           mAuth;
    ReviewsModel                    mReviewsModel;

    public ViewReviewViewModel( ReviewsModel revModel, AuthentificationModel auth ) {
        mAuth = auth;
        mReviewsModel = revModel;
    }

    public MutableLiveData<Response> getMutableLiveSetReviewResponse(){ return mReviewsModel.getMutableLiveSetReviewResponse(); }
    public MutableLiveData<UserReview> getMutableLiveDataUserReviews(){ return mReviewsModel.getMutableLiveDataUserReviews(); }
    public MutableLiveData<UserReviewAggregationData> getMutableLiveDataReviewAggregationData(){
        return mReviewsModel.getMutableLiveDataUserReviewAggregationData();
    }

    public String getCurrentUserId(){ return mAuth.getCurrentUserId();}

    public void triggerGetUserReviewByUserIdAndProductId(String userID, String prodId) {
        mReviewsModel.triggerGetReviewByUserIdAndProductId(userID,prodId);
    }

    public void setUserReviewByUserIdAndProductId(UserReview userRev, String userId, String prodId) {
        mReviewsModel.setUserReviewByUserIdAndProductId(userRev,userId,prodId);
    }

    public void triggerGetReviewDataByUserId(String userID) { mReviewsModel.triggerGetUserReviewData(userID);}
}
