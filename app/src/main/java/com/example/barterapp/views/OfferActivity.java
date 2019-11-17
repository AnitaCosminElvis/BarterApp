package com.example.barterapp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.barterapp.R;
import com.example.barterapp.data.Offer;
import com.example.barterapp.data.Response;
import com.example.barterapp.utility.AuthentificationUtility;
import com.example.barterapp.view_models.MakeOfferViewModel;
import com.example.barterapp.view_models.ViewModelFactory;

import javax.annotation.Nullable;

/**
 * The type Offer activity.
 */
public class OfferActivity extends AppCompatActivity {
    private EditText                            mEmailEditText;
    private EditText                            mMessageEditText;
    private Button                              mMakeOfferBtn;
    private MakeOfferViewModel                  mMakeOfferViewModel;
    private MutableLiveData<Response>           mMakeOfferResponseLiveData;
    private String                              mUserId;
    private String                              mProductId;
    private String                              mProductImgUri;
    private boolean                             mInitialState               = true;

    /**
     * initializing class members
     *
     * @param savedInstanceState
     * @return void
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Intent intent = getIntent();
        mUserId = intent.getStringExtra(getString(R.string.offer_user_id_tag));
        mProductId = intent.getStringExtra(getString(R.string.offer_product_id_tag));
        mProductImgUri = intent.getStringExtra(getString(R.string.offer_product_img__uri_tag));

        mEmailEditText = findViewById(R.id.et_offer_email);
        mMessageEditText = findViewById(R.id.et_offer_message);
        mMakeOfferBtn = findViewById(R.id.btn_make_offer);

        mMakeOfferViewModel = ViewModelProviders.of(this, new ViewModelFactory())
                .get(MakeOfferViewModel.class);

        mMakeOfferResponseLiveData = mMakeOfferViewModel.getMutableLiveDataOfferResponse();

        mMakeOfferResponseLiveData.observe(this, new Observer<Response>(){
            @Override
            public void onChanged(@Nullable Response offerResponse){
                if (null != offerResponse && !mInitialState){
                    Toast.makeText(OfferActivity.this,
                            offerResponse.getmResponseText() , Toast.LENGTH_SHORT).show();
                    mMakeOfferBtn.setEnabled(true);
                    if (offerResponse.getmIsSuccessfull()) finish();
                }
            }
        });

        mMakeOfferBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sEmail = mEmailEditText.getText().toString();
                String sMessage = mMessageEditText.getText().toString();

                if (sEmail.isEmpty()){
                    Toast.makeText(OfferActivity.this, "Empty email." , Toast.LENGTH_SHORT).show();
                    return;
                }

                if (false == AuthentificationUtility.isEmailValid(sEmail)){
                    Toast.makeText(OfferActivity.this, "Invalid email." , Toast.LENGTH_SHORT).show();
                    return;
                }

                if (sMessage.isEmpty()){
                    Toast.makeText(OfferActivity.this, "Empty message." , Toast.LENGTH_SHORT).show();
                    return;
                }

                Offer offer = new Offer("",mUserId,"","","",
                                        mProductId,mProductImgUri,sEmail,sMessage,
                                        true,false);

                mInitialState = false;
                mMakeOfferBtn.setEnabled(false);
                mMakeOfferViewModel.makeOffer(offer);
            }
        });
    }
}
