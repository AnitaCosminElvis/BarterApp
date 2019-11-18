package com.example.barterapp.data;

import android.os.Parcel;
import android.os.Parcelable;

public class UserReview implements Parcelable {
    private String      mFromAlias;
    private String      mProductId;
    private String      mProductImgUri;
    private float       mRatingValue;
    private String      mReviewText;
    private boolean     mIsFlagged;

    public UserReview(){}

    public UserReview(String mFromAlias, String mProductId, String mProductImgUri,
                      float mRatingValue, String mReviewText, boolean mIsFlagged) {
        this.mFromAlias = mFromAlias;
        this.mProductId = mProductId;
        this.mProductImgUri = mProductImgUri;
        this.mRatingValue = mRatingValue;
        this.mReviewText = mReviewText;
        this.mIsFlagged = mIsFlagged;
    }

    public UserReview(Parcel in) {
        mFromAlias = in.readString();
        mProductId = in.readString();
        mProductImgUri = in.readString();
        mRatingValue = in.readFloat();
        mReviewText = in.readString();
        mIsFlagged = in.readByte() != 0;
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Creator<UserReview> CREATOR = new Creator<UserReview>() {
        @Override
        public UserReview createFromParcel(Parcel in) { return new UserReview(in); }
        @Override
        public UserReview[] newArray(int size) { return new UserReview[size]; }
    };

    public String getmFromAlias() {
        return mFromAlias;
    }

    public void setmFromAlias(String mFromAlias) {
        this.mFromAlias = mFromAlias;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mFromAlias);
        parcel.writeString(mProductId);
        parcel.writeString(mProductImgUri);
        parcel.writeFloat(mRatingValue);
        parcel.writeString(mReviewText);
        //because writeBoolean minimum api version is at least API level Q
        // we are using conversion from boolean to byte values
        parcel.writeByte((byte)(mIsFlagged?1:0));
    }
}
