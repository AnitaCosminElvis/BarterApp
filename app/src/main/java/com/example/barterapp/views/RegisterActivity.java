package com.example.barterapp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.barterapp.R;
import com.example.barterapp.data.Response;
import com.example.barterapp.data.UserProfile;
import com.example.barterapp.utility.AuthentificationUtility;
import com.example.barterapp.view_models.RegisterViewModel;
import com.example.barterapp.view_models.ViewModelFactory;

import javax.annotation.Nullable;


public class RegisterActivity extends AppCompatActivity {
    private RegisterViewModel   mRegisterViewModel;
    private MutableLiveData     mRegisterResponseLiveData;
    private Button              mRegisterBtn;
    private EditText            mFirstNameEdtTxt;
    private EditText            mSurnameEdtTxt;
    private EditText            mTelNoEdtTxt;
    private EditText            mAliasEdtTxt;
    private EditText            mEmailEdtTxt;
    private EditText            mPassEdtTxt;
    private EditText            mPassRetypeEdtTxt;
    private CheckBox            mAgreeCkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mRegisterViewModel = ViewModelProviders.of(this, new ViewModelFactory())
                .get(RegisterViewModel.class);
        mRegisterResponseLiveData = mRegisterViewModel.getRegisterResponseLiveData();
        mRegisterResponseLiveData.observe(this, new Observer<Response>(){
            @Override
            public void onChanged(@Nullable Response registerResponse){
                if (null != registerResponse){
                    Toast.makeText(RegisterActivity.this,
                            registerResponse.getmResponseText() , Toast.LENGTH_SHORT).show();
                }
            }
        });

        mRegisterBtn = findViewById(R.id.btn_register);
        mFirstNameEdtTxt = findViewById(R.id.edt_txt_first_name);
        mSurnameEdtTxt = findViewById(R.id.edt_txt_surname);
        mTelNoEdtTxt = findViewById(R.id.edt_txt_telephone);
        mAliasEdtTxt = findViewById(R.id.edt_txt_alias);
        mEmailEdtTxt = findViewById(R.id.edt_txt_email);
        mPassEdtTxt = findViewById(R.id.edt_txt_password);
        mPassRetypeEdtTxt = findViewById(R.id.edt_txt_password1);
        mAgreeCkBox = findViewById(R.id.chk_box_agree);

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRegisterBtnClicked(v);
            }
        });
    }

    /**
     * method is used for registering.
     *
     * @param v
     * @return void
     */
    public void onRegisterBtnClicked(View v) {
        String sFirstName = mFirstNameEdtTxt.getText().toString();

        if (sFirstName.isEmpty()){
            Toast.makeText(this, "First Name Empty!", Toast.LENGTH_LONG).show();
            return;
        }

        String sSurname = mSurnameEdtTxt.getText().toString();

        if (sSurname.isEmpty()){
            Toast.makeText(this, "Surname Empty!", Toast.LENGTH_LONG).show();
            return;
        }

        String sTelNo = mTelNoEdtTxt.getText().toString();

        if (sTelNo.isEmpty()){
            Toast.makeText(this, "Telephone No Empty!", Toast.LENGTH_LONG).show();
            return;
        }

        String sAlias = mAliasEdtTxt.getText().toString();

        if (sAlias.isEmpty()){
            Toast.makeText(this, "Alias Empty!", Toast.LENGTH_LONG).show();
            return;
        }

        String sEmail = mEmailEdtTxt.getText().toString();

        if (sEmail.isEmpty()){
            Toast.makeText(this, "Email Empty!", Toast.LENGTH_LONG).show();
            return;
        }else{
            if (false == AuthentificationUtility.isEmailValid(sEmail)){
                Toast.makeText(this, "Invalid Email!", Toast.LENGTH_LONG).show();
                return;
            }
        }

        String sPass = mPassEdtTxt.getText().toString();
        String sPassRe = mPassRetypeEdtTxt.getText().toString();

        if (true == sPass.isEmpty()){
            Toast.makeText(this, "First Pass Empty!", Toast.LENGTH_LONG).show();
            return;
        }else{
            if (true == sPassRe.isEmpty()){
                Toast.makeText(this, "Retyped Pass Empty!", Toast.LENGTH_LONG).show();
                return;
            }else {
                if (false == sPass.equals(sPassRe)){
                    Toast.makeText(this, "Your retyped pass is different from the password!", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }

        if (false == AuthentificationUtility.isPasswordValid(sPass)){
            Toast.makeText(this, "The password must have at least 6 characters." ,
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (!mAgreeCkBox.isChecked()){
            Toast.makeText(this, "Please check the agree to terms and conditions!", Toast.LENGTH_LONG).show();
            return;
        }

        UserProfile userProfile = new UserProfile(sFirstName,sSurname,sTelNo,sAlias,sEmail);

        try {
            mRegisterViewModel.register(userProfile, sPass);
        }catch (Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
