package com.example.barterapp.view_models;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.barterapp.data.Product;
import com.example.barterapp.data.Response;
import com.example.barterapp.models.AuthentificationModel;
import com.example.barterapp.models.ProductsModel;

import java.util.ArrayList;

public class MainViewModel extends ViewModel {
    private AuthentificationModel   mAuthModel;
    private ProductsModel           mProdModel;

    public MainViewModel(AuthentificationModel authModel, ProductsModel prodModel) {
        mAuthModel = authModel;
        mProdModel = prodModel;
    }

    public MutableLiveData<Response> getLoginResponseLiveData() { return mAuthModel.getMutableLiveDataLoginResponse(); }
    public MutableLiveData<Response> getProductsResponseLiveData() { return mProdModel.getMutableLiveDataListProductsResponse(); }
    public MutableLiveData<ArrayList<Product>> getGadgetsLiveData() { return mProdModel.getMutableLiveDataGadgetsChanged(); }
    public MutableLiveData<ArrayList<Product>> getClothesLiveData() { return mProdModel.getMutableLiveDataClothesChanged(); }
    public MutableLiveData<ArrayList<Product>> getToolsLiveData() { return mProdModel.getMutableLiveDataToolsChanged(); }
    public MutableLiveData<ArrayList<Product>> getBikesLiveData() { return mProdModel.getMutableLiveDataBikesChanged(); }

    public void signOut() { mAuthModel.signOut(); }

    public String getUserEmail() { return mAuthModel.getUserEmail(); }

    public boolean isUserSignedIn() { return mAuthModel.isUserSignedIn(); }

    public void setSingleEventByProductCategory(String category) { mProdModel.setSingleEventByProductCategory(category); }

}
