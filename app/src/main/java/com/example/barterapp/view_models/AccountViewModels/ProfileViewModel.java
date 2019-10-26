package com.example.barterapp.view_models.AccountViewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.barterapp.data.UserProfile;
import com.example.barterapp.models.AuthentificationModel;

public class ProfileViewModel extends ViewModel {
    private AuthentificationModel mAuthModel;

    public  ProfileViewModel(AuthentificationModel authModel) { mAuthModel = authModel; }

    public MutableLiveData<UserProfile> getUserProfileLiveData() {
        return mAuthModel.getMutableLiveDataUserProfile();
    }

    public boolean getUserProfile() { return mAuthModel.getUserProfile();}

}
