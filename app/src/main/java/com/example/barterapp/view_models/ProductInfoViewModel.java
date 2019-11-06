package com.example.barterapp.view_models;

import androidx.lifecycle.ViewModel;

import com.example.barterapp.models.AuthentificationModel;

public class ProductInfoViewModel extends ViewModel {
    private AuthentificationModel mAuthModel;

    public ProductInfoViewModel(AuthentificationModel authModel)
    {
        mAuthModel = authModel;
    }

    public String getUserAlias() { return mAuthModel.getUserAlias(); }

    public boolean isSignedIn(){ return mAuthModel.isUserSignedIn();}


}
