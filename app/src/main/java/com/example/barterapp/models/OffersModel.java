package com.example.barterapp.models;

import static com.example.barterapp.utility.DefinesUtility.*;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.barterapp.data.Offer;
import com.example.barterapp.data.Response;
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

/**
 * The type Offers model.
 */
public class OffersModel {
    private static volatile OffersModel         mInstance;
    private CollectionReference                 mDbOffersCollection;
    private CollectionReference                 mDbProductsCollection;
    private FirebaseAuth                        mAuth;
    private MutableLiveData<Response>           mOfferResponseLiveData                  = new MutableLiveData<>();
    private MutableLiveData<Response>           mSetOfferStateResponseLiveData          = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Offer>>   mMyOffersListLiveData                   = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Offer>>   mMyOffersHistoryListLiveData            = new MutableLiveData<>();
    private ArrayList<Offer>                    mMyOffersHistoryList                    = new ArrayList<>();
    private Task<QuerySnapshot>                 mGetMyOffersTask;
    private Task<QuerySnapshot>                 mGetOffersHistoryTask;


    private OffersModel() {
        mAuth = FirebaseAuth.getInstance();
        mDbOffersCollection = FirebaseFirestore.getInstance().collection(OFFERS_COLLECTION);
        mDbProductsCollection = FirebaseFirestore.getInstance().collection(PRODUCTS_COLLECTION);
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static synchronized OffersModel getInstance() {
        if (mInstance == null) {
            mInstance = new OffersModel();
        }
        return mInstance;
    }

    /**
     * Get mutable live data offer response mutable live data.
     *
     * @return the mutable live data
     */
    public MutableLiveData<Response> getMutableLiveDataOfferResponse(){
        return mOfferResponseLiveData;
    }

    /**
     * Get mutable live data my offers list mutable live data.
     *
     * @return the mutable live data
     */
    public MutableLiveData<ArrayList<Offer>> getMutableLiveDataMyOffersList(){
        return mMyOffersListLiveData;
    }

    /**
     * Get mutable live data my offers history list mutable live data.
     *
     * @return the mutable live data
     */
    public MutableLiveData<ArrayList<Offer>> getMutableLiveDataMyOffersHistoryList(){
        return mMyOffersHistoryListLiveData;
    }

    /**
     * Gets offer state response live data.
     *
     * @return the offer state response live data
     */
    public MutableLiveData<Response> getOfferStateResponseLiveData() {
        return mSetOfferStateResponseLiveData;
    }

    /**
     * Create offer.
     *
     * @param offer the offer
     */
    public void createOffer(Offer offer) {
        offer.setmFromUserId(mAuth.getCurrentUser().getUid());
        offer.setmFromAlias(mAuth.getCurrentUser().getDisplayName());
        offer.setOfferId(mDbOffersCollection.document().getId());
        mDbOffersCollection.document(offer.getOfferId()).set(offer)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        mOfferResponseLiveData.setValue(
                                new Response(SUCC_SENT_OFFER,true));
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mOfferResponseLiveData.setValue(
                        new Response(e.getMessage(),false));
            }
        });
    }

    /**
     * Set offer state.
     *
     * @param offer           the offer
     * @param isOfferAccepted the is offer accepted
     */
    public void setOfferState(Offer offer, boolean isOfferAccepted){
        offer.setmIsPending(false);
        offer.setmIsAccepted(isOfferAccepted);
        offer.setmToAlias(mAuth.getCurrentUser().getDisplayName());

        mDbOffersCollection.document(offer.getOfferId())
                .set(offer)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        if (isOfferAccepted){
                            mDbProductsCollection.document(offer.getmProductId()).delete();
                        }
                        mSetOfferStateResponseLiveData.setValue(
                                new Response("",true));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        mSetOfferStateResponseLiveData.setValue(
                                new Response(e.getMessage(),false));
                    }
                });
    }

    /**
     * Trigger get my offers.
     */
    public void triggerGetMyOffers(){
        if (null != mGetMyOffersTask && !mGetMyOffersTask.isComplete()) return;

        mGetMyOffersTask = mDbOffersCollection.whereEqualTo(TO_USER_ID_KEY,
                mAuth.getCurrentUser().getUid())
        .whereEqualTo(IS_PENDING_KEY, true)
        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<Offer> offers = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        offers.add(document.toObject(Offer.class));
                    }
                    mMyOffersListLiveData.setValue(offers);
                }
            }
        });
    }

    /**
     * Trigger get my offers history.
     */
    synchronized public void triggerGetMyOffersHistory(){
        if (null != mGetOffersHistoryTask && !mGetOffersHistoryTask.isComplete()) return;

        mGetOffersHistoryTask = mDbOffersCollection.whereEqualTo(TO_USER_ID_KEY,
                mAuth.getCurrentUser().getUid())
        .whereEqualTo(IS_PENDING_KEY, false)
        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    mMyOffersHistoryList.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        mMyOffersHistoryList.add(document.toObject(Offer.class));
                    }

                    mDbOffersCollection.whereEqualTo(FROM_USER_ID_KEY, mAuth.getCurrentUser().getUid())
                            .whereEqualTo(IS_PENDING_KEY, false)
                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    mMyOffersHistoryList.add(document.toObject(Offer.class));
                                }
                                mMyOffersHistoryListLiveData.setValue(mMyOffersHistoryList);
                            }
                        }
                    });
                }
            }
        });
    }
}
