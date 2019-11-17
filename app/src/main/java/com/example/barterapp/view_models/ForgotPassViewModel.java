package com.example.barterapp.view_models;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.barterapp.data.Response;
import com.example.barterapp.models.AuthentificationModel;

/**
 * The type Forgot pass view model.
 */
public class ForgotPassViewModel extends ViewModel {
    private AuthentificationModel           mAuthModel;

    /**
     * Instantiates a new Forgot pass view model.
     *
     * @param authModel the auth model
     */
    public ForgotPassViewModel(AuthentificationModel authModel)
    {
        mAuthModel = authModel;
    }

    /**
     * Get forgot pass response live data mutable live data.
     *
     * @return the mutable live data
     */
    public MutableLiveData<Response> getForgotPassResponseLiveData(){return mAuthModel.getMutableLiveDataResetPass();}

    /**
     * Reset pass.
     *
     * @param email the email
     */
    public void resetPass(String email) {
        mAuthModel.resetPassword(email);
    }
}
