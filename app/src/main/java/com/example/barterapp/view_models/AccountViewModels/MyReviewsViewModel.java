package com.example.barterapp.view_models.AccountViewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.barterapp.data.Offer;
import com.example.barterapp.models.OffersModel;

import java.util.ArrayList;

public class MyReviewsViewModel extends ViewModel {
    OffersModel mOffersModel;

    public MyReviewsViewModel(OffersModel offersModel){
        mOffersModel = offersModel;
    }

    public MutableLiveData<ArrayList<Offer>> getMutableLiveDataMyOffersHistoryList(){
        return mOffersModel.getMutableLiveDataMyOffersHistoryList();
    }

    public void triggerGetMyOffersHystory(){ mOffersModel.triggerGetMyOffersHistory();}

}
