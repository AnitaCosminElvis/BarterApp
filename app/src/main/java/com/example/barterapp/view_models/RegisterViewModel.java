package com.example.barterapp.view_models;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.barterapp.data.Response;
import com.example.barterapp.data.UserProfile;
import com.example.barterapp.models.AuthentificationModel;

/**
 * The type Register view model.
 */
public class RegisterViewModel  extends ViewModel {
    private AuthentificationModel mAuthModel;

    /**
     * Instantiates a new Register view model.
     *
     * @param authModel the auth model
     */
    public RegisterViewModel(AuthentificationModel authModel)
    {
        mAuthModel = authModel;
    }

    /**
     * Get register response live data mutable live data.
     *
     * @return the mutable live data
     */
    public MutableLiveData<Response> getRegisterResponseLiveData(){return mAuthModel.getMutableLiveDataRegisterResponse();}

    /**
     * Register.
     *
     * @param userProfile the user profile
     * @param password    the password
     */
    public void register(UserProfile userProfile, String password) {
        mAuthModel.signUp(userProfile,password);
    }
}
