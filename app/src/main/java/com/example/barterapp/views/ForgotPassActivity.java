package com.example.barterapp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.barterapp.R;
import com.example.barterapp.data.Response;
import com.example.barterapp.utility.AuthentificationUtility;
import com.example.barterapp.view_models.ForgotPassViewModel;
import com.example.barterapp.view_models.ViewModelFactory;
import com.google.firebase.database.annotations.Nullable;

public class ForgotPassActivity extends AppCompatActivity {
    private ForgotPassViewModel         mForgotPassViewModel;
    private MutableLiveData<Response>   mResetPassResponseLiveData;
    private EditText                    mEmailEdtTxt;
    private Button                      mResetPassBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mForgotPassViewModel = ViewModelProviders.of(this, new ViewModelFactory())
                .get(ForgotPassViewModel.class);

        mResetPassResponseLiveData = mForgotPassViewModel.getForgotPassResponseLiveData();
        mResetPassResponseLiveData.observe(this, new Observer<Response>(){
            @Override
            public void onChanged(@Nullable Response loginResponse){
                if (null != loginResponse){
                    Toast.makeText(ForgotPassActivity.this,
                            loginResponse.getmResponseText() , Toast.LENGTH_SHORT).show();
                }
            }
        });

        mEmailEdtTxt = findViewById(R.id.et_email);
        mResetPassBtn = findViewById(R.id.btn_reset_password);

        mResetPassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sEmail = mEmailEdtTxt.getText().toString();

                if (sEmail.isEmpty()){
                    Toast.makeText(ForgotPassActivity.this, "Empty email." , Toast.LENGTH_SHORT).show();
                    return;
                }

                if (false == AuthentificationUtility.isEmailValid(sEmail)){
                    Toast.makeText(ForgotPassActivity.this, "Invalid email." , Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    mForgotPassViewModel.resetPass(sEmail);
                }catch (Exception ex){
                    Toast.makeText(ForgotPassActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
