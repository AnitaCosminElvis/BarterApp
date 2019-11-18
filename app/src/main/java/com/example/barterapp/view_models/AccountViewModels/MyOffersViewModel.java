package com.example.barterapp.view_models.AccountViewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.barterapp.data.Offer;
import com.example.barterapp.models.OffersModel;

import java.util.ArrayList;

/**
 * The type My offers view model.
 */
public class MyOffersViewModel extends ViewModel {
    private OffersModel mOffersModel;

    /**
     * Instantiates a new My offers view model.
     *
     * @param offersModel the offers model
     */
    public MyOffersViewModel(OffersModel offersModel){
        mOffersModel = offersModel;
    }

    /**
     * Get mutable live data my offers list mutable live data.
     *
     * @return the mutable live data
     */
    public MutableLiveData<ArrayList<Offer>> getMutableLiveDataMyOffersList(){
        return mOffersModel.getMutableLiveDataMyOffersList();
    }

    /**
     * Trigger get my offers.
     */
    public void triggerGetMyOffers(){
        mOffersModel.triggerGetMyOffers();
    }
}
