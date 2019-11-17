package com.example.barterapp.view_models.AccountViewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.barterapp.data.Offer;
import com.example.barterapp.models.OffersModel;

import java.util.ArrayList;

/**
 * The type My reviews view model.
 */
public class MyReviewsViewModel extends ViewModel {
    /**
     * The M offers model.
     */
    OffersModel mOffersModel;

    /**
     * Instantiates a new My reviews view model.
     *
     * @param offersModel the offers model
     */
    public MyReviewsViewModel(OffersModel offersModel){
        mOffersModel = offersModel;
    }

    /**
     * Get mutable live data my offers history list mutable live data.
     *
     * @return the mutable live data
     */
    public MutableLiveData<ArrayList<Offer>> getMutableLiveDataMyOffersHistoryList(){
        return mOffersModel.getMutableLiveDataMyOffersHistoryList();
    }

    /**
     * Trigger get my offers hystory.
     */
    public void triggerGetMyOffersHystory(){ mOffersModel.triggerGetMyOffersHistory();}

}
