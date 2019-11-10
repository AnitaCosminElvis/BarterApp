package com.example.barterapp.data;

public class UserReview {
    private String      mProductId;
    private String      mProductImgUri;
    private float       mRatingValue;
    private String      mReviewText;
    private boolean     mIsFlagged;

    public UserReview(){}

    public UserReview(String mProductId, String mProductImgUri, float mRatingValue, String mReviewText, boolean mIsFlagged) {
        this.mProductId = mProductId;
        this.mProductImgUri = mProductImgUri;
        this.mRatingValue = mRatingValue;
        this.mReviewText = mReviewText;
        this.mIsFlagged = mIsFlagged;
    }

    public String getmProductId() {
        return mProductId;
    }

    public void setmProductId(String mProductId) {
        this.mProductId = mProductId;
    }

    public String getmProductImgUri() {
        return mProductImgUri;
    }

    public void setmProductImgUri(String mProductImgUri) {
        this.mProductImgUri = mProductImgUri;
    }

    public float getmRatingValue() {
        return mRatingValue;
    }

    public void setmRatingValue(float mRatingValue) {
        this.mRatingValue = mRatingValue;
    }

    public String getmReviewText() {
        return mReviewText;
    }

    public void setmReviewText(String mReviewText) {
        this.mReviewText = mReviewText;
    }

    public boolean ismIsFlagged() {
        return mIsFlagged;
    }

    public void setmIsFlagged(boolean mIsFlagged) {
        this.mIsFlagged = mIsFlagged;
    }
}
