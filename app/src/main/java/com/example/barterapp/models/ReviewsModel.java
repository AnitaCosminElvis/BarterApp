package com.example.barterapp.models;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.barterapp.data.Response;
import com.example.barterapp.data.UserReview;
import com.example.barterapp.data.UserReviewAggregationData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static com.example.barterapp.utility.DefinesUtility.*;

/**
 * The type Reviews model.
 */
public class ReviewsModel {
    private static volatile ReviewsModel                mInstance;
    private final CollectionReference                   mDbReviewsCollection;
    private final FirebaseAuth                          mAuth;
    private MutableLiveData<Response>                   mSetReviewResponseLiveData = new MutableLiveData<>();
    private MutableLiveData<UserReview>                 mUserReviewsLiveData = new MutableLiveData<>();
    private MutableLiveData<UserReviewAggregationData>  mUserReviewsAggregationData = new MutableLiveData<>();

    private ReviewsModel() {
        mAuth = FirebaseAuth.getInstance();
        mDbReviewsCollection = FirebaseFirestore.getInstance().collection(REVIEWS_COLLECTION);
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static synchronized ReviewsModel getInstance() {
        if (mInstance == null) {
            mInstance = new ReviewsModel();
        }
        return mInstance;
    }

    /**
     * Get mutable live set review response mutable live data.
     *
     * @return the mutable live data
     */
    public MutableLiveData<Response> getMutableLiveSetReviewResponse(){ return mSetReviewResponseLiveData; }

    /**
     * Gets mutable live data user reviews.
     *
     * @return the mutable live data user reviews
     */
    public MutableLiveData<UserReview> getMutableLiveDataUserReviews() { return mUserReviewsLiveData; }

    /**
     * Gets mutable live data user review aggregation data.
     *
     * @return the mutable live data user review aggregation data
     */
    public MutableLiveData<UserReviewAggregationData> getMutableLiveDataUserReviewAggregationData() {
        return mUserReviewsAggregationData;
    }

    /**
     * Trigger get review by user id and product id.
     *
     * @param userId    the user id
     * @param productId the product id
     */
    public void triggerGetReviewByUserIdAndProductId(String userId, String productId){
        mDbReviewsCollection.document(userId).collection(USER_REVIEWS_COLLECTION)
        .whereEqualTo(PRODUCT_ID,productId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    UserReview userReviews = null;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        userReviews = document.toObject(UserReview.class);
                    }

                    mUserReviewsLiveData.setValue(userReviews);
                } else {
                    String s = task.getException().getMessage();
                }
            }
        });
    }

    /**
     * Sets user review by user id and product id.
     *
     * @param userRev the user rev
     * @param userId  the user id
     * @param prodId  the prod id
     */
    public void setUserReviewByUserIdAndProductId(UserReview userRev, String userId, String prodId) {
        userRev.setmFromAlias(mAuth.getCurrentUser().getDisplayName());
        mDbReviewsCollection.document(userId).collection(USER_REVIEWS_COLLECTION).document(prodId)
        .set(userRev).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mSetReviewResponseLiveData.setValue(new Response("",true));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mSetReviewResponseLiveData.setValue(new Response(e.getMessage(),true));

            }
        });
    }

    /**
     * Trigger get user review data.
     *
     * @param userId the user id
     */
    public void triggerGetUserReviewData(String userId) {
        mDbReviewsCollection.document(userId).collection(USER_REVIEWS_COLLECTION)
        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<UserReview> userReviewsList = new ArrayList<>();
                    int sumOfFlags = 0;
                    float sumOfRatings = 0;
                    float countedRatings = 0;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        UserReview userReview = document.toObject(UserReview.class);
                        if (userReview.ismIsFlagged()) ++sumOfFlags;
                        ++countedRatings;
                        sumOfRatings += userReview.getmRatingValue();
                        userReviewsList.add(userReview);
                    }
                    UserReviewAggregationData aggregationData = new UserReviewAggregationData();
                    float ratingsAvg = sumOfRatings/countedRatings;
                    aggregationData.setmNoOfFlaggs(sumOfFlags);
                    aggregationData.setmUserRatingAvg(ratingsAvg);
                    aggregationData.setmUserReviewsList(userReviewsList);
                    mUserReviewsAggregationData.setValue(aggregationData);
                } else {
                    String s = task.getException().getMessage();
                }
            }
        });
    }
}
