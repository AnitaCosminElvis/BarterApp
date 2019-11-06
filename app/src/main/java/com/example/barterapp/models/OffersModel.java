package com.example.barterapp.models;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.barterapp.data.Offer;
import com.example.barterapp.data.Response;
import com.example.barterapp.utility.DefinesUtility;
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

public class OffersModel {
    private static volatile OffersModel         mInstance;
    private CollectionReference                 mDbOffersCollection;
    private FirebaseAuth                        mAuth;
    private MutableLiveData<Response>           mOfferResponseLiveData          = new MutableLiveData<>();
    private MutableLiveData<Response>           mSetOfferStateResponseLiveData  = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Offer>>   mMyOffersListLiveData           = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Offer>>   mMyHistoryListLiveData          = new MutableLiveData<>();

    private OffersModel() {
        mAuth = FirebaseAuth.getInstance();
        mDbOffersCollection = FirebaseFirestore.getInstance().collection(OFFERS_COLLECTION);
    }

    public static synchronized OffersModel getInstance() {
        if (mInstance == null) {
            mInstance = new OffersModel();
        }
        return mInstance;
    }

    public MutableLiveData<Response> getMutableLiveDataOfferResponse(){ return mOfferResponseLiveData; }
    public MutableLiveData<ArrayList<Offer>> getMutableLiveDataMyOffersList(){ return mMyOffersListLiveData; }
    public MutableLiveData<ArrayList<Offer>> getMutableLiveDataMyHistoryList(){ return mMyHistoryListLiveData; }
    public MutableLiveData<Response> getOfferStateResponseLiveData() { return mSetOfferStateResponseLiveData; }

    public void createOffer(Offer offer) {
        offer.setmFromUserId(mAuth.getCurrentUser().getUid());
        offer.setmFromAlias(mAuth.getCurrentUser().getDisplayName());

        mDbOffersCollection.document().set(offer)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        mOfferResponseLiveData.setValue(new Response("Offer sent successfully.",true));
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mOfferResponseLiveData.setValue(new Response(e.getMessage(),false));
            }
        });
    }

    public void setOfferState(Offer offer, boolean isOfferAccepted){

    }

    public void triggerGetMyOffers(){
        triggerGetOffersByKeyFilter(TO_USER_ID_KEY,mAuth.getCurrentUser().getUid());
    }

    private void triggerGetOffersByKeyFilter(String key, String filterVal){
        mDbOffersCollection.whereEqualTo(key, filterVal)
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
}
