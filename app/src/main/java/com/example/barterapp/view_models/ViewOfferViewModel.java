package com.example.barterapp.view_models;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.barterapp.data.Offer;
import com.example.barterapp.data.Response;
import com.example.barterapp.models.OffersModel;
import com.example.barterapp.models.ProductsModel;

public class ViewOfferViewModel extends ViewModel {
    OffersModel                 mOfferModel;

    public ViewOfferViewModel(OffersModel offerModel){
        mOfferModel = offerModel;
    }

    public MutableLiveData<Response> getOfferStateResponseLiveData(){ return mOfferModel.getOfferStateResponseLiveData(); }

    public void setOfferState(Offer offer, boolean isAccepted){
        mOfferModel.setOfferState(offer,isAccepted);
    }
}
