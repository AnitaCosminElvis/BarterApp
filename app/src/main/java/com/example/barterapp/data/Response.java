package com.example.barterapp.data;

/**
 * The entity Response.
 */
public class Response {
    private String      mResponseText;
    private boolean     mIsSuccessfull;

    /**
     * Instantiates a new Response.
     *
     * @param responseText   the response text
     * @param bIsSuccessfull the b is successfull
     */
    public Response(String responseText, boolean bIsSuccessfull) {
        this.mResponseText = responseText;
        this.mIsSuccessfull = bIsSuccessfull;
    }

    /**
     * Gets is successfull.
     *
     * @return the is successfull
     */
    public boolean getmIsSuccessfull() {
        return mIsSuccessfull;
    }

    /**
     * Sets is successfull.
     *
     * @param mIsSuccessfull the m is successfull
     */
    public void setmIsSuccessfull(boolean mIsSuccessfull) {
        this.mIsSuccessfull = mIsSuccessfull;
    }

    /**
     * Gets response text.
     *
     * @return the response text
     */
    public String getmResponseText() {
        return mResponseText;
    }

    /**
     * Sets response text.
     *
     * @param responseText the response text
     */
    public void setmResponseText(String responseText) {
        this.mResponseText = responseText;
    }
}
