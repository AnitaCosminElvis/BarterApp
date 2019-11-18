package com.example.barterapp.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * The type Product.
 */
public class Product  implements Parcelable {
    private String      mUserId;
    private String      mProductId;
    private String      mAlias;
    private String      mTitle;
    private String      mDescription;
    private String      mCategory;
    private String      mImgUri;
    private String      mVidUri;
    private long        mTimeStamp;

    /**
     * Instantiates a new Product.
     */
    public Product(){}

    /**
     * Instantiates a new Product.
     *
     * @param mUserId      the m user id
     * @param productId    the product id
     * @param alias        the alias
     * @param mTitle       the m title
     * @param mDescription the m description
     * @param mCategory    the m category
     * @param imgUri       the img uri
     * @param vidUri       the vid uri
     * @param timeStamp    the time stamp
     */
    public Product(String mUserId, String productId, String alias, String mTitle, String mDescription, String mCategory,
                   String imgUri, String vidUri, long timeStamp) {
        this.mUserId = mUserId;
        this.mProductId = productId;
        this.mAlias = alias;
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.mCategory = mCategory;
        this.mImgUri = imgUri;
        this.mVidUri = vidUri;
        this.mTimeStamp = timeStamp;
    }

    /**
     * Instantiates a new Product.
     *
     * @param in the in
     */
    public Product(Parcel in) {
        mUserId = in.readString();
        mProductId = in.readString();
        mAlias = in.readString();
        mTitle = in.readString();
        mDescription = in.readString();
        mCategory = in.readString();
        mImgUri = in.readString();
        mVidUri = in.readString();
        mTimeStamp = in.readLong();
    }

    /**
     * The constant CREATOR.
     */
// this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public String getmUserId() { return mUserId; }

    /**
     * Sets user id.
     *
     * @param userId the user id
     */
    public void setmUserId(String userId) { this.mUserId = userId; }

    /**
     * Gets product id.
     *
     * @return the product id
     */
    public String getProductId() { return mProductId; }

    /**
     * Sets product id.
     *
     * @param productId the product id
     */
    public void setProductId(String productId) { this.mProductId = productId; }

    /**
     * Gets alias.
     *
     * @return the alias
     */
    public String getAlias() {
        return mAlias;
    }

    /**
     * Sets alias.
     *
     * @param alias the alias
     */
    public void setAlias(String alias) { this.mAlias = alias; }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getmTitle() {
        return mTitle;
    }

    /**
     * Sets title.
     *
     * @param mTitle the m title
     */
    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getmDescription() {
        return mDescription;
    }

    /**
     * Sets description.
     *
     * @param mDescription the m description
     */
    public void setmDescription(String mDescription) { this.mDescription = mDescription; }

    /**
     * Gets category.
     *
     * @return the category
     */
    public String getmCategory() { return mCategory; }

    /**
     * Sets category.
     *
     * @param mCategory the m category
     */
    public void setmCategory(String mCategory) { this.mCategory = mCategory; }

    /**
     * Gets img uri path.
     *
     * @return the img uri path
     */
    public String getImgUriPath() { return mImgUri; }

    /**
     * Sets img uri path.
     *
     * @param uriPath the uri path
     */
    public void setImgUriPath(String uriPath) { this.mImgUri = uriPath; }

    /**
     * Gets vid uri path.
     *
     * @return the vid uri path
     */
    public String getVidUriPath() { return mVidUri; }

    /**
     * Sets vid uri path.
     *
     * @param uriPath the uri path
     */
    public void setVidUriPath(String uriPath) { this.mVidUri = uriPath; }

    /**
     * Gets time stamp.
     *
     * @return the time stamp
     */
    public long getmTimeStamp() { return mTimeStamp; }

    /**
     * Sets time stamp.
     *
     * @param mTimeStamp the m time stamp
     */
    public void setmTimeStamp(long mTimeStamp) { this.mTimeStamp = mTimeStamp; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mUserId);
        parcel.writeString(mProductId);
        parcel.writeString(mAlias);
        parcel.writeString(mTitle);
        parcel.writeString(mDescription);
        parcel.writeString(mCategory);
        parcel.writeString(mImgUri);
        parcel.writeString(mVidUri);
        parcel.writeLong(mTimeStamp);
    }
}
