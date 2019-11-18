package com.example.barterapp.view_models;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.barterapp.data.Offer;
import com.example.barterapp.data.Response;
import com.example.barterapp.models.OffersModel;

public class MakeOfferViewModel extends ViewModel {
    OffersModel                 mOffersModel;

    MakeOfferViewModel(OffersModel offersModel){
        mOffersModel = offersModel;
    }

    public MutableLiveData<Response> getMutableLiveDataOfferResponse(){ return mOffersModel.getMutableLiveDataOfferResponse();}

    public void makeOffer(Offer offer) { mOffersModel.createOffer(offer); }
}
