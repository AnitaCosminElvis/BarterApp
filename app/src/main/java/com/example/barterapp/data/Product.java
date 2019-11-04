package com.example.barterapp.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Product  implements Parcelable {
    private String      mUserId;
    private String      mAlias;
    private String      mTitle;
    private String      mDescription;
    private String      mCategory;
    private String      mImgUri;
    private String      mVidUri;
    private long        mTimeStamp;

    public Product(){}

    public Product(String mUserId, String alias, String mTitle, String mDescription, String mCategory,
                   String imgUri, String vidUri, long timeStamp) {
        this.mUserId = mUserId;
        this.mAlias = alias;
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.mCategory = mCategory;
        this.mImgUri = imgUri;
        this.mVidUri = vidUri;
        this.mTimeStamp = timeStamp;
    }

    public Product(Parcel in) {
        mUserId = in.readString();
        mAlias = in.readString();
        mTitle = in.readString();
        mDescription = in.readString();
        mCategory = in.readString();
        mImgUri = in.readString();
        mVidUri = in.readString();
        mTimeStamp = in.readLong();
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public String getmUserId() {
        return mUserId;
    }

    public void setmUserId(String userId) { this.mUserId = userId; }

    public String getAlias() {
        return mAlias;
    }

    public void setAlias(String alias) { this.mAlias = alias; }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) { this.mDescription = mDescription; }

    public String getmCategory() { return mCategory; }

    public void setmCategory(String mCategory) { this.mCategory = mCategory; }

    public String getImgUriPath() { return mImgUri; }

    public void setImgUriPath(String uriPath) { this.mImgUri = uriPath; }

    public String getVidUriPath() { return mVidUri; }

    public void setVidUriPath(String uriPath) { this.mVidUri = uriPath; }

    public long getmTimeStamp() { return mTimeStamp; }

    public void setmTimeStamp(long mTimeStamp) { this.mTimeStamp = mTimeStamp; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mUserId);
        parcel.writeString(mAlias);
        parcel.writeString(mTitle);
        parcel.writeString(mDescription);
        parcel.writeString(mCategory);
        parcel.writeString(mImgUri);
        parcel.writeString(mVidUri);
        parcel.writeLong(mTimeStamp);
    }
}
