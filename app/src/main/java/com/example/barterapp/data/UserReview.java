package com.example.barterapp.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * The entity User review.
 */
public class UserReview implements Parcelable {
    private String      mOfferId;
    private String      mFromAlias;
    private String      mProductId;
    private String      mProductImgUri;
    private float       mRatingValue;
    private String      mReviewText;
    private boolean     mIsFlagged;

    /**
     * Instantiates a new User review.
     */
    public UserReview(){}

    /**
     * Instantiates a new User review.
     * @param mOfferId       the m from offerId
     * @param mFromAlias     the m from alias
     * @param mProductId     the m product id
     * @param mProductImgUri the m product img uri
     * @param mRatingValue   the m rating value
     * @param mReviewText    the m review text
     * @param mIsFlagged     the m is flagged
     */
    public UserReview(String mOfferId, String mFromAlias, String mProductId, String mProductImgUri,
                      float mRatingValue, String mReviewText, boolean mIsFlagged) {
        this.mOfferId = mOfferId;
        this.mFromAlias = mFromAlias;
        this.mProductId = mProductId;
        this.mProductImgUri = mProductImgUri;
        this.mRatingValue = mRatingValue;
        this.mReviewText = mReviewText;
        this.mIsFlagged = mIsFlagged;
    }

    /**
     * Instantiates a new User review.
     *
     * @param in the in
     */
    public UserReview(Parcel in) {
        mOfferId = in.readString();
        mFromAlias = in.readString();
        mProductId = in.readString();
        mProductImgUri = in.readString();
        mRatingValue = in.readFloat();
        mReviewText = in.readString();
        mIsFlagged = in.readByte() != 0;
    }

    /**
     * The constant CREATOR.
     */
// this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Creator<UserReview> CREATOR = new Creator<UserReview>() {
        @Override
        public UserReview createFromParcel(Parcel in) { return new UserReview(in); }
        @Override
        public UserReview[] newArray(int size) { return new UserReview[size]; }
    };


    /**
     * Gets offerId.
     *
     * @return the offerId
     */
    public String getmOfferId() {
        return mOfferId;
    }

    /**
     * Sets offerId.
     *
     * @param mOfferId the offerId
     */
    public void setmOfferId(String mOfferId) {
        this.mOfferId = mOfferId;
    }


    /**
     * Gets from alias.
     *
     * @return the from alias
     */
    public String getmFromAlias() {
        return mFromAlias;
    }

    /**
     * Sets from alias.
     *
     * @param mFromAlias the m from alias
     */
    public void setmFromAlias(String mFromAlias) {
        this.mFromAlias = mFromAlias;
    }

    /**
     * Gets product id.
     *
     * @return the product id
     */
    public String getmProductId() {
        return mProductId;
    }

    /**
     * Sets product id.
     *
     * @param mProductId the m product id
     */
    public void setmProductId(String mProductId) {
        this.mProductId = mProductId;
    }

    /**
     * Gets product img uri.
     *
     * @return the product img uri
     */
    public String getmProductImgUri() {
        return mProductImgUri;
    }

    /**
     * Sets product img uri.
     *
     * @param mProductImgUri the m product img uri
     */
    public void setmProductImgUri(String mProductImgUri) {
        this.mProductImgUri = mProductImgUri;
    }

    /**
     * Gets rating value.
     *
     * @return the rating value
     */
    public float getmRatingValue() {
        return mRatingValue;
    }

    /**
     * Sets rating value.
     *
     * @param mRatingValue the m rating value
     */
    public void setmRatingValue(float mRatingValue) {
        this.mRatingValue = mRatingValue;
    }

    /**
     * Gets review text.
     *
     * @return the review text
     */
    public String getmReviewText() {
        return mReviewText;
    }

    /**
     * Sets review text.
     *
     * @param mReviewText the m review text
     */
    public void setmReviewText(String mReviewText) {
        this.mReviewText = mReviewText;
    }

    /**
     * Ism is flagged boolean.
     *
     * @return the boolean
     */
    public boolean ismIsFlagged() {
        return mIsFlagged;
    }

    /**
     * Sets is flagged.
     *
     * @param mIsFlagged the m is flagged
     */
    public void setmIsFlagged(boolean mIsFlagged) {
        this.mIsFlagged = mIsFlagged;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mOfferId);
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
