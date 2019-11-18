package com.example.barterapp.view_models.AccountViewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.barterapp.data.Offer;
import com.example.barterapp.models.OffersModel;

import java.util.ArrayList;

public class MyOffersViewModel extends ViewModel {
    private OffersModel mOffersModel;

    public MyOffersViewModel(OffersModel offersModel){
        mOffersModel = offersModel;
    }

    public MutableLiveData<ArrayList<Offer>> getMutableLiveDataMyOffersList(){
        return mOffersModel.getMutableLiveDataMyOffersList();
    }

    public void triggerGetMyOffers(){
        mOffersModel.triggerGetMyOffers();
    }
}
