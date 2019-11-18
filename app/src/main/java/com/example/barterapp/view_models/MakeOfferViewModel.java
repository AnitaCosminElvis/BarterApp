package com.example.barterapp.view_models;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.barterapp.data.Offer;
import com.example.barterapp.data.Response;
import com.example.barterapp.models.OffersModel;

/**
 * The type Make offer view model.
 */
public class MakeOfferViewModel extends ViewModel {
    OffersModel                 mOffersModel;

    /**
     * Instantiates a new Make offer view model.
     *
     * @param offersModel the offers model
     */
    MakeOfferViewModel(OffersModel offersModel){
        mOffersModel = offersModel;
    }

    /**
     * Get mutable live data offer response mutable live data.
     *
     * @return the mutable live data
     */
    public MutableLiveData<Response> getMutableLiveDataOfferResponse(){ return mOffersModel.getMutableLiveDataOfferResponse();}

    /**
     * Make offer.
     *
     * @param offer the offer
     */
    public void makeOffer(Offer offer) { mOffersModel.createOffer(offer); }
}
