package com.example.barterapp.data;

public class Response {
    private String      mResponseText;
    private boolean     mIsSuccessfull;

    public Response(String responseText, boolean bIsSuccessfull) {
        this.mResponseText = responseText;
        this.mIsSuccessfull = bIsSuccessfull;
    }
    public boolean getmIsSuccessfull() {
        return mIsSuccessfull;
    }

    public void setmIsSuccessfull(boolean mIsSuccessfull) {
        this.mIsSuccessfull = mIsSuccessfull;
    }

    public String getmResponseText() {
        return mResponseText;
    }

    public void setmResponseText(String responseText) {
        this.mResponseText = responseText;
    }
}
