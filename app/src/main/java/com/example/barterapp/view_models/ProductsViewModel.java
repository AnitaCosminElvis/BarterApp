package com.example.barterapp.view_models;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.barterapp.data.Product;
import com.example.barterapp.data.Response;
import com.example.barterapp.models.AuthentificationModel;
import com.example.barterapp.models.ProductsModel;

import java.util.ArrayList;

public class ProductsViewModel extends ViewModel {
    private AuthentificationModel   mAuthModel;
    private ProductsModel           mProdModel;

    public ProductsViewModel(AuthentificationModel authModel, ProductsModel prodModel) {
        mAuthModel = authModel;
        mProdModel = prodModel;
    }

    public MutableLiveData<Response> getLoginResponseLiveData() { return mAuthModel.getMutableLiveDataLoginResponse(); }
    public MutableLiveData<Response> getProductsResponseLiveData() { return mProdModel.getMutableLiveDataListProductsResponse(); }
    public MutableLiveData<ArrayList<Product>> getGadgetsLiveData() { return mProdModel.getMutableLiveDataGadgetsChanged(); }
    public MutableLiveData<ArrayList<Product>> getClothesLiveData() { return mProdModel.getMutableLiveDataClothesChanged(); }
    public MutableLiveData<ArrayList<Product>> getToolsLiveData() { return mProdModel.getMutableLiveDataToolsChanged(); }
    public MutableLiveData<ArrayList<Product>> getBikesLiveData() { return mProdModel.getMutableLiveDataBikesChanged(); }
    public MutableLiveData<ArrayList<Product>> getOtherProductsLiveData() { return mProdModel.getMutableLiveDataOtherChanged(); }
    public MutableLiveData<ArrayList<Product>> getUserProductsLiveData() { return mProdModel.getMutableLiveDataUserProducts(); }

    public void signOut() { mAuthModel.signOut(); }

    public String getCurrentUserId() { return mAuthModel.getCurrentUserId(); }

    public String getUserEmail() { return mAuthModel.getUserEmail(); }

    public String getUserAlias() { return mAuthModel.getUserAlias(); }

    public boolean isUserSignedIn() { return mAuthModel.isUserSignedIn(); }

    public void triggerGetProductsByUserId(String userIdKey, String userId) {
        mProdModel.triggerGetProductsByUserId(userIdKey,userId);
    }

    public void triggerGetProductsByCategory(String categoryKey, String category) {
        mProdModel.triggerGetProductsByCategory(categoryKey,category);
    }

}
