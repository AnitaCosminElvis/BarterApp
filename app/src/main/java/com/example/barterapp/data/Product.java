package com.example.barterapp.data;

public class Product {
    private String      mAlias;
    private String      mTitle;
    private String      mDescription;
    private String      mPhotoPath;
    private String      mVideoPath;

    public Product(){}

    public Product(String mAlias, String mTitle, String mDescription, String mPhotoPath, String mVideoPath) {
        this.mAlias = mAlias;
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.mPhotoPath = mPhotoPath;
        this.mVideoPath = mVideoPath;
    }

    public String getmAlias() {
        return mAlias;
    }

    public void setmAlias(String mAlias) {
        this.mAlias = mAlias;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmPhotoPath() {
        return mPhotoPath;
    }

    public void setmPhotoPath(String mPhotoPath) {
        this.mPhotoPath = mPhotoPath;
    }

    public String getmVideoPath() {
        return mVideoPath;
    }

    public void setmVideoPath(String mVideoPath) {
        this.mVideoPath = mVideoPath;
    }
}
