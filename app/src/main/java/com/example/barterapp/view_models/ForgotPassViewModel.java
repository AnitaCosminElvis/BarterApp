package com.example.barterapp.view_models;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.barterapp.data.Response;
import com.example.barterapp.models.AuthentificationModel;

public class ForgotPassViewModel extends ViewModel {
    private AuthentificationModel           mAuthModel;

    public ForgotPassViewModel(AuthentificationModel authModel)
    {
        mAuthModel = authModel;
    }

    public MutableLiveData<Response> getForgotPassResponseLiveData(){return mAuthModel.getMutableLiveDataResetPass();}

    public void resetPass(String email) {
        mAuthModel.resetPassword(email);
    }
}
