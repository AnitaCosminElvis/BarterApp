package com.example.barterapp.views;

import static com.example.barterapp.utility.DefinesUtility.*;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

 import  com.example.barterapp.R;
import com.example.barterapp.data.Product;
import com.example.barterapp.view_models.ProductsViewModel;
import com.example.barterapp.view_models.ViewModelFactory;

import java.util.ArrayList;

/**
 * The type User products activity.
 */
public class UserProductsActivity   extends AppCompatActivity
                                    implements UserProductsAdapter.ItemClickListener{
    ProductsViewModel                               mProductsViewModel;
    MutableLiveData<ArrayList<Product>>             mUserProductsLiveData;
    RecyclerView                                    mUserProductsRecyclerView;
    UserProductsAdapter                             mUserProductsAdapter;
    ArrayList<Product>                              mUserProducts           = new ArrayList<>();
    String                                          mUserId;

    /**
     * initializing class members
     *
     * @param savedInstanceState
     * @return void
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_products);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mUserId = getIntent().getStringExtra(getString(R.string.view_products_info_tag));

        mUserProductsRecyclerView = findViewById(R.id.rv_user_products);
        mUserProductsRecyclerView.setHasFixedSize(true);
        mUserProductsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false));
        mUserProductsAdapter = new UserProductsAdapter(this,mUserProducts);
        mUserProductsAdapter.setClickListener(this);
        //set the default adapter
        mUserProductsRecyclerView.setAdapter(mUserProductsAdapter);

        //create the view model
        mProductsViewModel = ViewModelProviders.of(this, new ViewModelFactory())
                .get(ProductsViewModel.class);

        mUserProductsLiveData = mProductsViewModel.getUserProductsLiveData();

        mProductsViewModel.triggerGetProductsByUserId(USER_ID_KEY,mUserId);

        mUserProductsLiveData.observe(this, new Observer<ArrayList<Product>>() {
            @Override
            public void onChanged(ArrayList<Product> products) {
                if (null != products){
                    mUserProducts = products;
                    mUserProductsAdapter.setProductsList(mUserProducts);
                    mUserProductsRecyclerView.setAdapter(mUserProductsAdapter);
                }
            }
        });
    }

    @Override
    public void onItemClick(View view, Product product) {
        Intent intent = new Intent(UserProductsActivity.this, ViewMyProductActivity.class);
        intent.putExtra(getText(R.string.product_info_tag).toString(),product);
        startActivity(intent);
    }
}
