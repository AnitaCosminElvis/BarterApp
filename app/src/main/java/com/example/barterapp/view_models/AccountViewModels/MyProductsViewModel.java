package com.example.barterapp.view_models.AccountViewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.barterapp.data.Product;
import com.example.barterapp.models.ProductsModel;

import java.util.ArrayList;

/**
 * The type My products view model.
 */
public class MyProductsViewModel extends ViewModel {
    ProductsModel               mProductModel;

    /**
     * Instantiates a new My products view model.
     *
     * @param offersModel the offers model
     */
    public MyProductsViewModel(ProductsModel offersModel){
        mProductModel = offersModel;
    }

    /**
     * Get mutable live data my products list mutable live data.
     *
     * @return the mutable live data
     */
    public MutableLiveData<ArrayList<Product>> getMutableLiveDataMyProductsList(){
        return mProductModel.getMutableLiveDataMyProducts();
    }

    /**
     * Trigger get my products.
     */
    public void triggerGetMyProducts(){
        mProductModel.triggerGetMyProducts();
    }
}
