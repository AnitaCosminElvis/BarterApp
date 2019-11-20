package com.example.barterapp.views;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.barterapp.R;

import com.example.barterapp.data.Response;
import com.example.barterapp.utility.AuthentificationUtility;
import com.example.barterapp.view_models.*;

import javax.annotation.Nullable;

/**
 * This class is used to login as a registered user with email and password,
 * or to recuperate the user’s password.
 */
public class LoginActivity extends AppCompatActivity {

    private LoginViewModel              mLoginViewModel;
    private MutableLiveData<Response>   mLoginResponseLiveData;
    private EditText                    mEmailEditText;
    private EditText                    mPasswordEditText;
    private Button                      mLoginButton;
    private ProgressBar                 mLoadingProgressBar;
    private Button                      mResetPassButton;
    private Button                      mGoogleAccountLoginButton;
    private boolean                     mIsInitialState                  = true;

    /**
     * initializing class members
     *
     * @param savedInstanceState
     * @return void
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //create the view models
        mLoginViewModel = ViewModelProviders.of(this, new ViewModelFactory())
                .get(LoginViewModel.class);

        //get live data
        mLoginResponseLiveData = mLoginViewModel.getLoginResponseLiveData();

        //create observer for the livedata
        mLoginResponseLiveData.observe(this, new Observer<Response>(){
            @Override
            public void onChanged(@Nullable Response loginResponse){
                mLoadingProgressBar.setVisibility(View.INVISIBLE);
                if (null != loginResponse && !mIsInitialState){
                    Toast.makeText(LoginActivity.this,
                            loginResponse.getmResponseText() , Toast.LENGTH_SHORT).show();
                    mLoginButton.setEnabled(true);
                    if (loginResponse.getmIsSuccessfull()) finish();
                }
            }
        });

        //init ui elements
        mEmailEditText = findViewById(R.id.username);
        mPasswordEditText = findViewById(R.id.password);
        mLoginButton = findViewById(R.id.btn_sign_in);
        mLoadingProgressBar = findViewById(R.id.loading);
        mGoogleAccountLoginButton = findViewById(R.id.btn_gmail);
        mResetPassButton = findViewById(R.id.btn_forgot_pass);

        //set on click listeners for the buttons
        mGoogleAccountLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mIsInitialState = false;
                    mLoadingProgressBar.setVisibility(View.INVISIBLE);
                    //ToDO : implement the Google authentification
                    Toast.makeText(LoginActivity.this, getString(R.string.not_available),
                            Toast.LENGTH_SHORT).show();
                }catch (Exception ex){
                    Toast.makeText(LoginActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sEmail = mEmailEditText.getText().toString();
                String sPass= mPasswordEditText.getText().toString();

                if (sEmail.isEmpty()){
                    Toast.makeText(LoginActivity.this, getString(R.string.empty_email) ,
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if (false == AuthentificationUtility.isEmailValid(sEmail)){
                    Toast.makeText(LoginActivity.this, getString(R.string.invalid_email) ,
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if (sPass.isEmpty()){
                    Toast.makeText(LoginActivity.this, getString(R.string.empty_pass) ,
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if (false == AuthentificationUtility.isPasswordValid(sPass)){
                    Toast.makeText(LoginActivity.this, getString(R.string.short_pass) ,
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    mIsInitialState = false;
                    mLoadingProgressBar.setVisibility(View.VISIBLE);
                    mLoginButton.setEnabled(false);
                    mLoginViewModel.login(sEmail, sPass);
                }catch (Exception ex){
                    Toast.makeText(LoginActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        mResetPassButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mIsInitialState = false;
                Intent intent = new Intent(LoginActivity.this, ForgotPassActivity.class);
                startActivity(intent);
            }
        });
    }
}
