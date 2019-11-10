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

    public static synchronized ReviewsModel getInstance() {
        if (mInstance == null) {
            mInstance = new ReviewsModel();
        }
        return mInstance;
    }

    public MutableLiveData<Response> getMutableLiveSetReviewResponse(){ return mSetReviewResponseLiveData; }
    public MutableLiveData<UserReview> getMutableLiveDataUserReviews() { return mUserReviewsLiveData; }
    public MutableLiveData<UserReviewAggregationData> getMutableLiveDataUserReviewAggregationData() {
        return mUserReviewsAggregationData;
    }

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

    public void setUserReviewByUserIdAndProductId(UserReview userRev, String userId, String prodId) {
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
