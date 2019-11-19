package com.example.barterapp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barterapp.R;
import com.example.barterapp.data.Offer;
import com.example.barterapp.data.Response;
import com.example.barterapp.view_models.ViewModelFactory;
import com.example.barterapp.view_models.ViewOfferViewModel;
import com.squareup.picasso.Picasso;

/**
 * The type View offer activity.
 */
public class ViewOfferActivity extends AppCompatActivity {
    private MutableLiveData<Response>   mSetOfferStateResponseLiveData;
    private ViewOfferViewModel          mViewOfferViewModel;
    private Offer                       mOffer;
    private TextView                    mMessageTextView;
    private TextView                    mAliasTextView;
    private TextView                    mContactMailTextView;
    private ImageView                   mProductPhotoImageView;
    private Button                      mRejectBtn;
    private Button                      mAcceptBtn;
    private boolean                     mCanFinish                            = false;

    /**
     * initializing class members
     *
     * @param savedInstanceState
     * @return void
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_offer);

        //create the view model
        mViewOfferViewModel = ViewModelProviders.of(this, new ViewModelFactory())
                .get(ViewOfferViewModel.class);
        //get live data reference
        mSetOfferStateResponseLiveData = mViewOfferViewModel.getOfferStateResponseLiveData();

        //get object from intent
        mOffer = getIntent().getParcelableExtra(getString(R.string.view_offer_info_tag));

        //init ui elements
        mMessageTextView = findViewById(R.id.tv_view_offer_message);
        mAliasTextView = findViewById(R.id.tv_view_offer_alias);
        mContactMailTextView = findViewById(R.id.tv_view_offer_contact_email);
        mProductPhotoImageView = findViewById(R.id.iv_view_offer_img);
        mRejectBtn = findViewById(R.id.btn_view_offer_reject);
        mAcceptBtn = findViewById(R.id.btn_view_offer_accept);

        mMessageTextView.setText(mOffer.getmMessage());
        mAliasTextView.setText(mOffer.getmFromAlias());
        mContactMailTextView.setText(mOffer.getmContactEmail());

        //sets the product's image from URI
        String uri = mOffer.getmProductImgUri();
        if (!uri.isEmpty())
            Picasso.get().load(uri).fit().centerCrop().tag(this).into(mProductPhotoImageView);

        //creates observer
        mSetOfferStateResponseLiveData.observe(this, new Observer<Response>() {
            @Override
            public void onChanged(Response setOfferStateresponse) {
                if (null != setOfferStateresponse){
                    if (!setOfferStateresponse.getmIsSuccessfull()) {
                        Toast.makeText(ViewOfferActivity.this,
                                setOfferStateresponse.getmResponseText(), Toast.LENGTH_SHORT).show();
                        setButtonsEnabled(true);
                    }else{
                        if (mCanFinish) finish();
                    }
                }
            }
        });

        //sets listeners
        mRejectBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                mCanFinish = true;
                mViewOfferViewModel.setOfferState(mOffer, false);
                setButtonsEnabled(false);
            }
        });

        mAcceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                mCanFinish = true;
                mViewOfferViewModel.setOfferState(mOffer, true);
                setButtonsEnabled(false);
            }
        });
    }

    /**
     * sets the buttons state as enabled/disabled for preventing click floods
     *
     * @param bValue
     * @return void
     */
    private void setButtonsEnabled(boolean bValue){
        mRejectBtn.setEnabled(bValue);
        mAcceptBtn.setEnabled(bValue);
    }
}
