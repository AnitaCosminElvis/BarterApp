package com.example.barterapp.view_models;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.barterapp.data.Offer;
import com.example.barterapp.data.Response;
import com.example.barterapp.models.OffersModel;
import com.example.barterapp.models.ProductsModel;

/**
 * The type View offer view model.
 */
public class ViewOfferViewModel extends ViewModel {
    OffersModel                 mOfferModel;

    /**
     * Instantiates a new View offer view model.
     *
     * @param offerModel the offer model
     */
    public ViewOfferViewModel(OffersModel offerModel){
        mOfferModel = offerModel;
    }

    /**
     * Get offer state response live data mutable live data.
     *
     * @return the mutable live data
     */
    public MutableLiveData<Response> getOfferStateResponseLiveData(){ return mOfferModel.getOfferStateResponseLiveData(); }

    /**
     * Set offer state.
     *
     * @param offer      the offer
     * @param isAccepted the is accepted
     */
    public void setOfferState(Offer offer, boolean isAccepted){
        mOfferModel.setOfferState(offer,isAccepted);
    }
}
