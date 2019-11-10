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

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel              mLoginViewModel;
    private MutableLiveData<Response>   mLoginResponseLiveData;
    private EditText                    mEmailEditText;
    private EditText                    mPasswordEditText;
    private Button                      mLoginButton;
    private ProgressBar                 mLoadingProgressBar;
    private Button                      mResetPassButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mLoginViewModel = ViewModelProviders.of(this, new ViewModelFactory())
                .get(LoginViewModel.class);

        mLoginResponseLiveData = mLoginViewModel.getLoginResponseLiveData();
        mLoginResponseLiveData.observe(this, new Observer<Response>(){
            @Override
            public void onChanged(@Nullable Response loginResponse){
                mLoadingProgressBar.setVisibility(View.INVISIBLE);
                if (null != loginResponse){
                    Toast.makeText(LoginActivity.this,
                            loginResponse.getmResponseText() , Toast.LENGTH_SHORT).show();
                }
            }
        });

        mEmailEditText = findViewById(R.id.username);
        mPasswordEditText = findViewById(R.id.password);

        mLoginButton = findViewById(R.id.btn_sign_in);
        mLoadingProgressBar = findViewById(R.id.loading);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sEmail = mEmailEditText.getText().toString();
                String sPass= mPasswordEditText.getText().toString();


                if (sEmail.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Empty email." , Toast.LENGTH_SHORT).show();
                    return;
                }

                if (false == AuthentificationUtility.isEmailValid(sEmail)){
                    Toast.makeText(LoginActivity.this, "Invalid email." , Toast.LENGTH_SHORT).show();
                    return;
                }

                if (sPass.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Empty password." , Toast.LENGTH_SHORT).show();
                    return;
                }

                if (false == AuthentificationUtility.isPasswordValid(sPass)){
                    Toast.makeText(LoginActivity.this, "The password must have at least 6 characters." ,
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    mLoadingProgressBar.setVisibility(View.VISIBLE);
                    mLoginViewModel.login(sEmail, sPass);
                }catch (Exception ex){
                    Toast.makeText(LoginActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        mResetPassButton = findViewById(R.id.btn_forgot_pass);

        mResetPassButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgotPassActivity.class);
                startActivity(intent);
            }
        });
    }
}
