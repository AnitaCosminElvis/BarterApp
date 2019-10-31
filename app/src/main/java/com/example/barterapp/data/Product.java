package com.example.barterapp.data;

import android.net.Uri;

public class Product {
    private String      mUserId;
    private String      mTitle;
    private String      mDescription;
    private String      mCategory;
    private String      mImgUri;
    private String      mVidUri;
    private long        mTimeStamp;

    public Product(){}

    public Product(String mUserId, String mTitle, String mDescription, String mCategory,
                   String imgUri, String vidUri, long timeStamp) {
        this.mUserId = mUserId;
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.mCategory = mCategory;
        this.mImgUri = imgUri;
        this.mVidUri = vidUri;
        this.mTimeStamp = timeStamp;
    }

    public String getmUserId() {
        return mUserId;
    }

    public void setmUserId(String userId) { this.mUserId = userId; }

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
}
