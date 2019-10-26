package com.example.barterapp.view_models;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.barterapp.data.Response;
import com.example.barterapp.data.UserProfile;
import com.example.barterapp.models.AuthentificationModel;

public class RegisterViewModel  extends ViewModel {
    private AuthentificationModel mAuthModel;

    public RegisterViewModel(AuthentificationModel authModel)
    {
        mAuthModel = authModel;
    }

    public MutableLiveData<Response> getRegisterResponseLiveData(){return mAuthModel.getMutableLiveDataRegisterResponse();}

    public void register(UserProfile userProfile, String password) {
        mAuthModel.signUp(userProfile,password);
    }
}
