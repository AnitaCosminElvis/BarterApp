package com.example.barterapp.view_models;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.barterapp.models.AuthentificationModel;

public class MainViewModel extends ViewModel {
    private AuthentificationModel mAuthModel;

    public MainViewModel(AuthentificationModel authModel)
    {
        mAuthModel = authModel;
    }

    public MutableLiveData getLoginLiveData() { return mAuthModel.getMutableLiveDataLoginResponse(); }

    public void signOut() { mAuthModel.signOut(); }

    public String getUserEmail() { return mAuthModel.getUserEmail(); }

    public boolean isUserSignedIn() { return mAuthModel.isUserSignedIn();}

}
