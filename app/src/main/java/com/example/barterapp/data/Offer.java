package com.example.barterapp.data;

public class Offer {
    private String      mUserId;
    private String      mProductId;
    private String      mContactEmail;
    private String      mMessage;

    public Offer(){}

    public Offer(String mUserId, String mProductId, String mContactEmail, String mMessage) {
        this.mUserId = mUserId;
        this.mProductId = mProductId;
        this.mContactEmail = mContactEmail;
        this.mMessage = mMessage;
    }

    public String getmUserId() {
        return mUserId;
    }

    public void setmUserId(String mUserId) {
        this.mUserId = mUserId;
    }

    public String getmProductId() {
        return mProductId;
    }

    public void setmProductId(String mProductId) {
        this.mProductId = mProductId;
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

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }

}
