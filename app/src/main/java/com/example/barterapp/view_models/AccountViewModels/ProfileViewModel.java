package com.example.barterapp.view_models.AccountViewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.barterapp.data.UserProfile;
import com.example.barterapp.models.AuthentificationModel;

/**
 * The type Profile view model.
 */
public class ProfileViewModel extends ViewModel {
    private AuthentificationModel mAuthModel;

    /**
     * Instantiates a new Profile view model.
     *
     * @param authModel the auth model
     */
    public  ProfileViewModel(AuthentificationModel authModel) { mAuthModel = authModel; }

    /**
     * Gets user profile live data.
     *
     * @return the user profile live data
     */
    public MutableLiveData<UserProfile> getUserProfileLiveData() {
        return mAuthModel.getMutableLiveDataUserProfile();
    }

    /**
     * Gets user profile.
     *
     * @return the user profile
     */
    public boolean getUserProfile() { return mAuthModel.getUserProfile();}

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public String getUserID() { return mAuthModel.getCurrentUserId(); }
}
