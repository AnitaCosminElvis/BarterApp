package com.example.barterapp.utility;

public class DefinesUtility {

    //Images and Videos paths
    public static final String            MULTIMEDIA_PATH           = "ProductsMultimedia/";

    //Firestore collection names
    public static final String            OFFERS_COLLECTION         = "Offers";
    public static final String            PRODUCTS_COLLECTION       = "Products";
    public static final String            REVIEWS_COLLECTION        = "Reviews";
    public static final String            USER_REVIEWS_COLLECTION   = "UserReviews";


    //Offers/History keys
    public static final String            TO_USER_ID_KEY            = "mToUserId";
    public static final String            FROM_USER_ID_KEY          = "mFromUserId";
    public static final String            IS_PENDING_KEY            = "mIsPending";
    public static final String            IS_ACCEPTED_KEY           = "mIsAccepted";
    public static final String            PRODUCT_ID                = "mProductId";

    //Products category keys
    public static final String            CAT_GADGETS               = "Gadgets";
    public static final String            CAT_CLOTHES               = "Clothes";
    public static final String            CAT_TOOLS                 = "Tools";
    public static final String            CAT_BIKES                 = "Bicycles";
    public static final String            CAT_OTHER                 = "Other";
    public static final String            CATEGORY_KEY              = "mCategory";
    public static final String            USER_ID_KEY               = "mUserId";

    public static final float             USER_MIN_RATING_VALUE     = 0;
    public static final int               USER_MAX_NO_OF_FLAGS      = 10;

}
