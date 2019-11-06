package com.example.barterapp.view_models.AccountViewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.barterapp.data.Offer;
import com.example.barterapp.data.Product;
import com.example.barterapp.models.OffersModel;
import com.example.barterapp.models.ProductsModel;

import java.util.ArrayList;

public class MyProductsViewModel extends ViewModel {
    ProductsModel mProductModel;

    public MyProductsViewModel(ProductsModel offersModel){
        mProductModel = offersModel;
    }

    public MutableLiveData<ArrayList<Product>> getMutableLiveDataMyProductsList(){
        return mProductModel.getMutableLiveDataUserProductsChanged();
    }

    public void triggerGetProductsByKeyFilter(String key, String filterVal){
        mProductModel.triggerGetProductsByKeyFilter(key, filterVal);
    }
}
