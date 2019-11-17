package com.example.barterapp.view_models;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.barterapp.data.Product;
import com.example.barterapp.data.Response;
import com.example.barterapp.models.AuthentificationModel;
import com.example.barterapp.models.ProductsModel;

import java.util.ArrayList;

/**
 * The type Products view model.
 */
public class ProductsViewModel extends ViewModel {
    private AuthentificationModel   mAuthModel;
    private ProductsModel           mProdModel;

    /**
     * Instantiates a new Products view model.
     *
     * @param authModel the auth model
     * @param prodModel the prod model
     */
    public ProductsViewModel(AuthentificationModel authModel, ProductsModel prodModel) {
        mAuthModel = authModel;
        mProdModel = prodModel;
    }

    /**
     * Gets login response live data.
     *
     * @return the login response live data
     */
    public MutableLiveData<Response> getLoginResponseLiveData() { return mAuthModel.getMutableLiveDataLoginResponse(); }

    /**
     * Gets products response live data.
     *
     * @return the products response live data
     */
    public MutableLiveData<Response> getProductsResponseLiveData() { return mProdModel.getMutableLiveDataListProductsResponse(); }

    /**
     * Gets gadgets live data.
     *
     * @return the gadgets live data
     */
    public MutableLiveData<ArrayList<Product>> getGadgetsLiveData() { return mProdModel.getMutableLiveDataGadgetsChanged(); }

    /**
     * Gets clothes live data.
     *
     * @return the clothes live data
     */
    public MutableLiveData<ArrayList<Product>> getClothesLiveData() { return mProdModel.getMutableLiveDataClothesChanged(); }

    /**
     * Gets tools live data.
     *
     * @return the tools live data
     */
    public MutableLiveData<ArrayList<Product>> getToolsLiveData() { return mProdModel.getMutableLiveDataToolsChanged(); }

    /**
     * Gets bikes live data.
     *
     * @return the bikes live data
     */
    public MutableLiveData<ArrayList<Product>> getBikesLiveData() { return mProdModel.getMutableLiveDataBikesChanged(); }

    /**
     * Gets other products live data.
     *
     * @return the other products live data
     */
    public MutableLiveData<ArrayList<Product>> getOtherProductsLiveData() { return mProdModel.getMutableLiveDataOtherChanged(); }

    /**
     * Gets user products live data.
     *
     * @return the user products live data
     */
    public MutableLiveData<ArrayList<Product>> getUserProductsLiveData() { return mProdModel.getMutableLiveDataUserProducts(); }

    /**
     * Sign out.
     */
    public void signOut() { mAuthModel.signOut(); }

    /**
     * Gets current user id.
     *
     * @return the current user id
     */
    public String getCurrentUserId() { return mAuthModel.getCurrentUserId(); }

    /**
     * Gets user email.
     *
     * @return the user email
     */
    public String getUserEmail() { return mAuthModel.getUserEmail(); }

    /**
     * Gets user alias.
     *
     * @return the user alias
     */
    public String getUserAlias() { return mAuthModel.getUserAlias(); }

    /**
     * Is user signed in boolean.
     *
     * @return the boolean
     */
    public boolean isUserSignedIn() { return mAuthModel.isUserSignedIn(); }

    /**
     * Trigger get products by user id.
     *
     * @param userIdKey the user id key
     * @param userId    the user id
     */
    public void triggerGetProductsByUserId(String userIdKey, String userId) {
        mProdModel.triggerGetProductsByUserId(userIdKey,userId);
    }

    /**
     * Trigger get products by category.
     *
     * @param categoryKey the category key
     * @param category    the category
     */
    public void triggerGetProductsByCategory(String categoryKey, String category) {
        mProdModel.triggerGetProductsByCategory(categoryKey,category);
    }

}
