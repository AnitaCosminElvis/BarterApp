package com.example.barterapp.view_models.AccountViewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.barterapp.data.Offer;
import com.example.barterapp.models.OffersModel;

import java.util.ArrayList;

public class MyHistoryViewModel extends ViewModel {
    OffersModel mOffersModel;

    public MyHistoryViewModel(OffersModel offersModel){
        mOffersModel = offersModel;
    }

    public MutableLiveData<ArrayList<Offer>> getMutableLiveDataMyHistoryList(){
        return mOffersModel.getMutableLiveDataMyHistoryList();
    }
}
