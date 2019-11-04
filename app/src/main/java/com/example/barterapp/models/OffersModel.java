package com.example.barterapp.models;

import androidx.lifecycle.MutableLiveData;

import com.example.barterapp.data.Offer;
import com.example.barterapp.data.Response;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class OffersModel {
    private static volatile OffersModel         mInstance;
    private CollectionReference                 mDbOffersCollection;
    private MutableLiveData<Response>           mOfferResponseLiveData         = new MutableLiveData<>();

    private OffersModel() {
        mDbOffersCollection = FirebaseFirestore.getInstance().collection("Offers");
    }

    public static synchronized OffersModel getInstance() {
        if (mInstance == null) {
            mInstance = new OffersModel();
        }
        return mInstance;
    }

    public MutableLiveData<Response> getMutableLiveDataOfferResponse(){ return mOfferResponseLiveData; }


    public void createOffer(Offer offer) {
    }
}
