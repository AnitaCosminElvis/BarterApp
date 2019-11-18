package com.example.barterapp.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Offer implements Parcelable {
    private String      mOfferId;
    private String      mToUserId;
    private String      mToAlias;
    private String      mFromUserId;
    private String      mFromAlias;
    private String      mProductId;
    private String      mProductImgUri;
    private String      mContactEmail;
    private String      mMessage;
    private boolean     mIsPending;
    private boolean     mIsAccepted;

    public Offer(){}

    public Offer(String offerId, String mToUserId, String mToAlias, String mFromUserId, String mFromAlias,
                 String mProductId, String mProductImgUri, String mContactEmail, String mMessage,
                 boolean mIsPending, boolean mIsAccepted) {
        this.mOfferId = offerId;
        this.mToUserId = mToUserId;
        this.mToAlias = mToAlias;
        this.mFromUserId = mFromUserId;
        this.mFromAlias = mFromAlias;
        this.mProductId = mProductId;
        this.mProductImgUri = mProductImgUri;
        this.mContactEmail = mContactEmail;
        this.mMessage = mMessage;
        this.mIsPending = mIsPending;
        this.mIsAccepted = mIsAccepted;
    }

    protected Offer(Parcel in) {
        mOfferId = in.readString();
        mToUserId = in.readString();
        mToAlias = in.readString();
        mFromUserId = in.readString();
        mFromAlias = in.readString();
        mProductId = in.readString();
        mProductImgUri = in.readString();
        mContactEmail = in.readString();
        mMessage = in.readString();
        //converting from byte to boolean
        mIsPending = in.readByte() != 0;
        mIsAccepted = in.readByte() != 0;
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Creator<Offer> CREATOR = new Creator<Offer>() {
        @Override
        public Offer createFromParcel(Parcel in) { return new Offer(in); }
        @Override
        public Offer[] newArray(int size) { return new Offer[size]; }
    };

    public String getOfferId() {
        return mOfferId;
    }

    public void setOfferId(String offerId) {
        this.mOfferId = offerId;
    }

    public String getmToUserId() {
        return mToUserId;
    }

    public void setmToUserId(String mToUserId) {
        this.mToUserId = mToUserId;
    }

    public String getmToAlias() {
        return mToAlias;
    }

    public void setmToAlias(String mToAlias) {
        this.mToAlias = mToAlias;
    }

    public String getmFromUserId() {
        return mFromUserId;
    }

    public void setmFromUserId(String mFromUserId) {
        this.mFromUserId = mFromUserId;
    }

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

    public String getmContactEmail() {
        return mContactEmail;
    }

    public void setmContactEmail(String mContactEmail) {
        this.mContactEmail = mContactEmail;
    }

    public String getmMessage() {
        return mMessage;
    }

    public void setmMessage(String mMessage) { this.mMessage = mMessage; }

    public boolean ismIsPending() { return mIsPending; }

    public void setmIsPending(boolean mIsPending) {
        this.mIsPending = mIsPending;
    }

    public boolean ismIsAccepted() {
        return mIsAccepted;
    }

    public void setmIsAccepted(boolean mIsAccepted) {
        this.mIsAccepted = mIsAccepted;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mOfferId);
        parcel.writeString(mToUserId);
        parcel.writeString(mToAlias);
        parcel.writeString(mFromUserId);
        parcel.writeString(mFromAlias);
        parcel.writeString(mProductId);
        parcel.writeString(mProductImgUri);
        parcel.writeString(mContactEmail);
        parcel.writeString(mMessage);
        //because writeBoolean minimum api version is at least API level Q
        // we are using conversion from boolean to byte values
        parcel.writeByte((byte)(mIsPending?1:0));
        parcel.writeByte((byte)(mIsAccepted?1:0));
    }
}
