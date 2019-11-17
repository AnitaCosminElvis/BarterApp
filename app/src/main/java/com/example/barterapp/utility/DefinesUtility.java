package com.example.barterapp.utility;

/**
 * The type Defines utility.
 */
public class DefinesUtility {

    /**
     * The constant MULTIMEDIA_PATH.
     */
//Images and Videos paths
    public static final String            MULTIMEDIA_PATH           = "ProductsMultimedia/";

    /**
     * The constant OFFERS_COLLECTION.
     */
//Firestore collection names
    public static final String            OFFERS_COLLECTION         = "Offers";
    /**
     * The constant PRODUCTS_COLLECTION.
     */
    public static final String            PRODUCTS_COLLECTION       = "Products";
    /**
     * The constant REVIEWS_COLLECTION.
     */
    public static final String            REVIEWS_COLLECTION        = "Reviews";
    /**
     * The constant USER_REVIEWS_COLLECTION.
     */
    public static final String            USER_REVIEWS_COLLECTION   = "UserReviews";


    /**
     * The constant TO_USER_ID_KEY.
     */
//Offers/History keys
    public static final String            TO_USER_ID_KEY            = "mToUserId";
    /**
     * The constant FROM_USER_ID_KEY.
     */
    public static final String            FROM_USER_ID_KEY          = "mFromUserId";
    /**
     * The constant IS_PENDING_KEY.
     */
    public static final String            IS_PENDING_KEY            = "mIsPending";
    /**
     * The constant IS_ACCEPTED_KEY.
     */
    public static final String            IS_ACCEPTED_KEY           = "mIsAccepted";
    /**
     * The constant PRODUCT_ID.
     */
    public static final String            PRODUCT_ID                = "mProductId";

    /**
     * The constant CAT_GADGETS.
     */
//Products category keys
    public static final String            CAT_GADGETS               = "Gadgets";
    /**
     * The constant CAT_CLOTHES.
     */
    public static final String            CAT_CLOTHES               = "Clothes";
    /**
     * The constant CAT_TOOLS.
     */
    public static final String            CAT_TOOLS                 = "Tools";
    /**
     * The constant CAT_BIKES.
     */
    public static final String            CAT_BIKES                 = "Bicycles";
    /**
     * The constant CAT_OTHER.
     */
    public static final String            CAT_OTHER                 = "Other";
    /**
     * The constant CATEGORY_KEY.
     */
    public static final String            CATEGORY_KEY              = "mCategory";
    /**
     * The constant USER_ID_KEY.
     */
    public static final String            USER_ID_KEY               = "mUserId";

    /**
     * The constant USER_MIN_RATING_VALUE.
     */
//Restricted user conditional checks
    public static final float             USER_MIN_RATING_VALUE     = 0;
    /**
     * The constant USER_MAX_NO_OF_FLAGS.
     */
    public static final int               USER_MAX_NO_OF_FLAGS      = 5;

    /**
     * The constant PICK_IMG_REQUEST.
     */
//request id's for picking multimedia
    public static final int               PICK_IMG_REQUEST        = 1000;
    /**
     * The constant PICK_VIDEO_REQUEST.
     */
    public static final int               PICK_VIDEO_REQUEST      = 2000;

}
