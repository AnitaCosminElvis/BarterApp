package com.example.barterapp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.InputFilter;
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


/**
 * Used for getting user's information and create a new account
 */
public class RegisterActivity extends AppCompatActivity {
    private RegisterViewModel   mRegisterViewModel;
    private MutableLiveData     mRegisterResponseLiveData;
    private Button              mRegisterBtn;
    private Button              mTermsAndConditions;
    private EditText            mFirstNameEdtTxt;
    private EditText            mSurnameEdtTxt;
    private EditText            mTelNoEdtTxt;
    private EditText            mAliasEdtTxt;
    private EditText            mEmailEdtTxt;
    private EditText            mPassEdtTxt;
    private EditText            mPassRetypeEdtTxt;
    private CheckBox            mAgreeCkBox;
    private boolean             mIsInitialState                 = true;

    /**
     * initializing class members
     *
     * @param savedInstanceState
     * @return void
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //create view model
        mRegisterViewModel = ViewModelProviders.of(this, new ViewModelFactory())
                .get(RegisterViewModel.class);
        //get live data
        mRegisterResponseLiveData = mRegisterViewModel.getRegisterResponseLiveData();

        //add observer to the live data
        mRegisterResponseLiveData.observe(this, new Observer<Response>(){
            @Override
            public void onChanged(@Nullable Response registerResponse){
                if (null != registerResponse && !mIsInitialState){
                    Toast.makeText(RegisterActivity.this,
                            registerResponse.getmResponseText() , Toast.LENGTH_SHORT).show();
                    mRegisterBtn.setEnabled(true);
                    if (registerResponse.getmIsSuccessfull()) finish();
                }
            }
        });

        //init UI elements
        mRegisterBtn = findViewById(R.id.btn_register);
        mFirstNameEdtTxt = findViewById(R.id.edt_txt_first_name);
        mSurnameEdtTxt = findViewById(R.id.edt_txt_surname);
        mTelNoEdtTxt = findViewById(R.id.edt_txt_telephone);
        mAliasEdtTxt = findViewById(R.id.edt_txt_alias);
        mEmailEdtTxt = findViewById(R.id.edt_txt_email);
        mPassEdtTxt = findViewById(R.id.edt_txt_password);
        mPassRetypeEdtTxt = findViewById(R.id.edt_txt_password1);
        mAgreeCkBox = findViewById(R.id.chk_box_agree);
        mTermsAndConditions = findViewById(R.id.btn_terms);

        addEditorFieldsFilters();

        //set on click listeners for the buttons
        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRegisterBtnClicked(v);
            }
        });

        mTermsAndConditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, TermsAndConditionsActivity.class));
            }
        });
    }

    private void addEditorFieldsFilters() {
        InputFilter maxInputFilter = new InputFilter.LengthFilter(50);
        InputFilter onlyLettersfilter = (source, start, end, dest, dstart, dend) -> {
            if(source.toString().matches("[\\p{Alpha}]*'*-*")) {
                return source;
            }

            return "";
        };

        mFirstNameEdtTxt.setFilters(new InputFilter[]{onlyLettersfilter, maxInputFilter});
        mSurnameEdtTxt.setFilters(new InputFilter[]{onlyLettersfilter, maxInputFilter});
    }

    /**
     * method is used for registering.
     *
     * @param v the v
     * @return void
     */
    public void onRegisterBtnClicked(View v) {
        String sFirstName = mFirstNameEdtTxt.getText().toString();

        if (sFirstName.isEmpty()){
            Toast.makeText(this, getString(R.string.empty_firstN), Toast.LENGTH_LONG).show();
            return;
        }

        String sSurname = mSurnameEdtTxt.getText().toString();

        if (sSurname.isEmpty()){
            Toast.makeText(this, getString(R.string.empty_surname), Toast.LENGTH_LONG).show();
            return;
        }

        String sTelNo = mTelNoEdtTxt.getText().toString();

        if (sTelNo.isEmpty()){
            Toast.makeText(this, getString(R.string.empty_tel), Toast.LENGTH_LONG).show();
            return;
        }

        if (sTelNo.length() < 4){
            Toast.makeText(this, getString(R.string.min_tel), Toast.LENGTH_LONG).show();
            return;
        }

        String sAlias = mAliasEdtTxt.getText().toString();

        if (sAlias.isEmpty()){
            Toast.makeText(this, getString(R.string.empty_alias), Toast.LENGTH_LONG).show();
            return;
        }

        String sEmail = mEmailEdtTxt.getText().toString();

        if (sEmail.isEmpty()){
            Toast.makeText(this, getString(R.string.empty_email), Toast.LENGTH_LONG).show();
            return;
        }else{
            if (false == AuthentificationUtility.isEmailValid(sEmail)){
                Toast.makeText(this, getString(R.string.invalid_email), Toast.LENGTH_LONG).show();
                return;
            }
        }

        String sPass = mPassEdtTxt.getText().toString();
        String sPassRe = mPassRetypeEdtTxt.getText().toString();

        if (true == sPass.isEmpty()){
            Toast.makeText(this, getString(R.string.empty_pass1), Toast.LENGTH_LONG).show();
            return;
        }else{
            if (true == sPassRe.isEmpty()){
                Toast.makeText(this, getString(R.string.empty_pass2), Toast.LENGTH_LONG).show();
                return;
            }else {
                if (false == sPass.equals(sPassRe)){
                    Toast.makeText(this, getString(R.string.diff_pass), Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }

        if (false == AuthentificationUtility.isPasswordValid(sPass)){
            Toast.makeText(this, getString(R.string.short_pass) ,
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (!mAgreeCkBox.isChecked()){
            Toast.makeText(this, getString(R.string.check_agree), Toast.LENGTH_LONG).show();
            return;
        }

        //create new user profile entity with the data gathered from the form
        UserProfile userProfile = new UserProfile(sFirstName,sSurname,sTelNo,sAlias,sEmail);

        //register the new user
        try {
            mIsInitialState = false;
            mRegisterBtn.setEnabled(false);
            mRegisterViewModel.register(userProfile, sPass);
        }catch (Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
