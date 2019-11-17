package com.example.barterapp.view_models;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.barterapp.data.Response;
import com.example.barterapp.models.AuthentificationModel;

/**
 * The type Login view model.
 */
public class LoginViewModel extends ViewModel {
    private AuthentificationModel           mAuthModel;

    /**
     * Instantiates a new Login view model.
     *
     * @param authModel the auth model
     */
    public LoginViewModel(AuthentificationModel authModel)
    {
        mAuthModel = authModel;
    }

    /**
     * Gets login response live data.
     *
     * @return the login response live data
     */
    public MutableLiveData<Response> getLoginResponseLiveData() { return mAuthModel.getMutableLiveDataLoginResponse();}

    /**
     * Login.
     *
     * @param email    the email
     * @param password the password
     */
    public void login(String email, String password) {
        mAuthModel.signIn(email,password);
    }

}
