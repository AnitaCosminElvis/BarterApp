package com.example.barterapp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barterapp.R;
import com.example.barterapp.data.Offer;
import com.example.barterapp.data.Response;
import com.example.barterapp.view_models.RegisterViewModel;
import com.example.barterapp.view_models.ViewModelFactory;
import com.example.barterapp.view_models.ViewOfferViewModel;
import com.squareup.picasso.Picasso;

public class ViewOfferActivity extends AppCompatActivity {
    private MutableLiveData<Response>   mSetOfferStateResponseLiveData;
    private ViewOfferViewModel          mViewOfferViewModel;
    private Offer                       mOffer;
    private TextView                    mMessageTectView;
    private TextView                    mAliasTextView;
    private ImageView                   mProductPhotoImageView;
    private Button                      mRejectBtn;
    private Button                      mAcceptBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_offer);

        mViewOfferViewModel = ViewModelProviders.of(this, new ViewModelFactory())
                .get(ViewOfferViewModel.class);
        mSetOfferStateResponseLiveData = mViewOfferViewModel.getOfferStateResponseLiveData();

        mOffer = getIntent().getParcelableExtra(getString(R.string.view_offer_info_tag));

        mMessageTectView = findViewById(R.id.tv_view_offer_message);
        mAliasTextView = findViewById(R.id.tv_view_offer_alias);
        mProductPhotoImageView = findViewById(R.id.iv_view_offer_img);
        mRejectBtn = findViewById(R.id.btn_view_offer_reject);
        mAcceptBtn = findViewById(R.id.btn_view_offer_accept);

        mMessageTectView.setText(mOffer.getmMessage());
        mAliasTextView.setText(mOffer.getmFromAlias());
        String uri = mOffer.getmProductImgUri();
        if (!uri.isEmpty())
            Picasso.get().load(uri).fit().centerCrop().tag(this).into(mProductPhotoImageView);

        mSetOfferStateResponseLiveData.observe(this, new Observer<Response>() {
            @Override
            public void onChanged(Response setOfferStateresponse) {
                if (null != setOfferStateresponse){
                    if (!setOfferStateresponse.getmIsSuccessfull())
                        Toast.makeText(ViewOfferActivity.this,
                                setOfferStateresponse.getmResponseText() , Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(ViewOfferActivity.this, AccountActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
}
