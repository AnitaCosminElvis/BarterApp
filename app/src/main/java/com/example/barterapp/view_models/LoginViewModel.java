package com.example.barterapp.view_models;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.barterapp.data.Response;
import com.example.barterapp.models.AuthentificationModel;

public class LoginViewModel extends ViewModel {
    private AuthentificationModel           mAuthModel;

    public LoginViewModel(AuthentificationModel authModel)
    {
        mAuthModel = authModel;
    }

    public MutableLiveData<Response> getLoginResponseLiveData() { return mAuthModel.getMutableLiveDataLoginResponse();}

    public void login(String email, String password) {
        mAuthModel.signIn(email,password);
    }

}
