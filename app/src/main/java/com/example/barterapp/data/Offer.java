package com.example.barterapp.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * The type Offer.
 */
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

    /**
     * Instantiates a new Offer.
     */
    public Offer(){}

    /**
     * Instantiates a new Offer.
     *
     * @param offerId        the offer id
     * @param mToUserId      the m to user id
     * @param mToAlias       the m to alias
     * @param mFromUserId    the m from user id
     * @param mFromAlias     the m from alias
     * @param mProductId     the m product id
     * @param mProductImgUri the m product img uri
     * @param mContactEmail  the m contact email
     * @param mMessage       the m message
     * @param mIsPending     the m is pending
     * @param mIsAccepted    the m is accepted
     */
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

    /**
     * Instantiates a new Offer.
     *
     * @param in the in
     */
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

    /**
     * The constant CREATOR.
     */
// this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Creator<Offer> CREATOR = new Creator<Offer>() {
        @Override
        public Offer createFromParcel(Parcel in) { return new Offer(in); }
        @Override
        public Offer[] newArray(int size) { return new Offer[size]; }
    };

    /**
     * Gets offer id.
     *
     * @return the offer id
     */
    public String getOfferId() {
        return mOfferId;
    }

    /**
     * Sets offer id.
     *
     * @param offerId the offer id
     */
    public void setOfferId(String offerId) {
        this.mOfferId = offerId;
    }

    /**
     * Gets to user id.
     *
     * @return the to user id
     */
    public String getmToUserId() {
        return mToUserId;
    }

    /**
     * Sets to user id.
     *
     * @param mToUserId the m to user id
     */
    public void setmToUserId(String mToUserId) {
        this.mToUserId = mToUserId;
    }

    /**
     * Gets to alias.
     *
     * @return the to alias
     */
    public String getmToAlias() {
        return mToAlias;
    }

    /**
     * Sets to alias.
     *
     * @param mToAlias the m to alias
     */
    public void setmToAlias(String mToAlias) {
        this.mToAlias = mToAlias;
    }

    /**
     * Gets from user id.
     *
     * @return the from user id
     */
    public String getmFromUserId() {
        return mFromUserId;
    }

    /**
     * Sets from user id.
     *
     * @param mFromUserId the m from user id
     */
    public void setmFromUserId(String mFromUserId) {
        this.mFromUserId = mFromUserId;
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
     * Gets contact email.
     *
     * @return the contact email
     */
    public String getmContactEmail() {
        return mContactEmail;
    }

    /**
     * Sets contact email.
     *
     * @param mContactEmail the m contact email
     */
    public void setmContactEmail(String mContactEmail) {
        this.mContactEmail = mContactEmail;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getmMessage() {
        return mMessage;
    }

    /**
     * Sets message.
     *
     * @param mMessage the m message
     */
    public void setmMessage(String mMessage) { this.mMessage = mMessage; }

    /**
     * Ism is pending boolean.
     *
     * @return the boolean
     */
    public boolean ismIsPending() { return mIsPending; }

    /**
     * Sets is pending.
     *
     * @param mIsPending the m is pending
     */
    public void setmIsPending(boolean mIsPending) {
        this.mIsPending = mIsPending;
    }

    /**
     * Ism is accepted boolean.
     *
     * @return the boolean
     */
    public boolean ismIsAccepted() {
        return mIsAccepted;
    }

    /**
     * Sets is accepted.
     *
     * @param mIsAccepted the m is accepted
     */
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
