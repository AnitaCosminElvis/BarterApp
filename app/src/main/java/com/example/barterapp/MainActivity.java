package com.example.barterapp;

import android.content.Intent;
import android.os.Bundle;

import com.example.barterapp.data.Product;
import com.example.barterapp.data.ProductsAdapter;
import com.example.barterapp.data.Response;
import com.example.barterapp.utility.DefinesUtility;
import com.example.barterapp.view_models.MainViewModel;
import com.example.barterapp.view_models.ViewModelFactory;
import com.example.barterapp.views.AccountActivity;
import com.example.barterapp.views.AddProductActivity;
import com.example.barterapp.views.LoginActivity;
import com.example.barterapp.views.RegisterActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import javax.annotation.Nullable;

import static com.example.barterapp.utility.DefinesUtility.*;

public class MainActivity   extends     AppCompatActivity
                            implements  ProductsAdapter.ItemClickListener,
                                        NavigationView.OnNavigationItemSelectedListener{
    private MainViewModel                           mMainViewModel;
    private MutableLiveData<Response>               mLoginLiveData;
    private MutableLiveData<ArrayList<Product>>     mGadgetsLiveData;
    private MutableLiveData<ArrayList<Product>>     mClothesLiveData;
    private MutableLiveData<ArrayList<Product>>     mToolsLiveData;
    private MutableLiveData<ArrayList<Product>>     mBikesLiveData;
    private ProductsAdapter                         mGadgetsAdapter ;
    private ProductsAdapter                         mClothesAdapter;
    private ProductsAdapter                         mToolsAdapter;
    private ProductsAdapter                         mBikesAdapter;
    private DrawerLayout                            mDrawer;
    private NavigationView                          mNavigationView;
    private RecyclerView                            mProductsRecyclerView;
    private Toolbar                                 mToolbar                = null;
    private TabLayout                               mProductsCatTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGadgetsAdapter = new ProductsAdapter(this, new ArrayList<Product>(), CAT_GADGETS);
        mClothesAdapter = new ProductsAdapter(this, new ArrayList<Product>(), CAT_CLOTHES);
        mToolsAdapter = new ProductsAdapter(this, new ArrayList<Product>(), CAT_TOOLS);
        mBikesAdapter = new ProductsAdapter(this, new ArrayList<Product>(), CAT_BIKES);
        mGadgetsAdapter.setClickListener(this);
        mToolsAdapter.setClickListener(this);
        mClothesAdapter.setClickListener(this);
        mBikesAdapter.setClickListener(this);

        mProductsRecyclerView = findViewById(R.id.rv_products);
        mProductsRecyclerView.setHasFixedSize(true);
        mProductsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        //set the default adapter
        mProductsRecyclerView.setAdapter(mGadgetsAdapter);


        //create the view model
        mMainViewModel = ViewModelProviders.of(this, new ViewModelFactory())
                .get(MainViewModel.class);

        //getting products live data
        mGadgetsLiveData = mMainViewModel.getGadgetsLiveData();
        mClothesLiveData = mMainViewModel.getClothesLiveData();
        mToolsLiveData = mMainViewModel.getToolsLiveData();
        mBikesLiveData = mMainViewModel.getBikesLiveData();
        mLoginLiveData = mMainViewModel.getLoginResponseLiveData();

        //create observers for the products
        mGadgetsLiveData.observe(this, new Observer<ArrayList<Product>>() {
            @Override public void onChanged(ArrayList<Product> productsList) {
                if (null != productsList) setAdapterByCategory(CAT_GADGETS, productsList);
            }
        });

        mClothesLiveData.observe(this, new Observer<ArrayList<Product>>() {
            @Override public void onChanged(ArrayList<Product> productsList) {
                if (null != productsList) setAdapterByCategory(CAT_CLOTHES, productsList);
            }
        });

        mToolsLiveData.observe(this, new Observer<ArrayList<Product>>() {
            @Override public void onChanged(ArrayList<Product> productsList) {
                if (null != productsList) setAdapterByCategory(CAT_TOOLS, productsList);
            }
        });

        mBikesLiveData.observe(this, new Observer<ArrayList<Product>>() {
            @Override public void onChanged(ArrayList<Product> productsList) {
                if (null != productsList) setAdapterByCategory(CAT_BIKES, productsList);
            }
        });

        //create observer for login response
        mLoginLiveData.observe(this, new Observer<Response>(){
            @Override public void onChanged(@Nullable Response response){
                if (null != response){ setNavViewUserEmail();}
            }
        });

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                startIntentActionWithUserLoggedInCheck(AddProductActivity.class);
            }
        });

        mDrawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);
        mDrawer.setDrawerListener(toogle);
        toogle.syncState();

        mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        mProductsCatTabLayout = findViewById(R.id.tab_product_category);

        mProductsCatTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:{
                        mProductsRecyclerView.setAdapter(mGadgetsAdapter);
                        break;
                    }
                    case 1:{
                        mProductsRecyclerView.setAdapter(mClothesAdapter);
                        break;
                    }
                    case 2:{
                        mProductsRecyclerView.setAdapter(mToolsAdapter);
                        break;
                    }
                    case 3:{
                        mProductsRecyclerView.setAdapter(mBikesAdapter);
                        break;
                    }
                    default: return;
                }
            }
            @Override public void onTabUnselected(TabLayout.Tab tab) { }
            @Override public void onTabReselected(TabLayout.Tab tab) { }
        });
        //sign out from any logged account
        mMainViewModel.signOut();
    }


    @Override
    public void onDestroy(){
        //sign out from any logged account
        mMainViewModel.signOut();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_account:
                startIntentActionWithUserLoggedInCheck(AccountActivity.class);
                break;
            case R.id.nav_add:
                startIntentActionWithUserLoggedInCheck(AddProductActivity.class);
                break;
            case R.id.nav_signin:
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                break;
            case R.id.nav_signup:
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                break;
            case R.id.nav_signout:
                mMainViewModel.signOut();
                setNavViewUserEmail();
                break;
        }

        return true;
    }

    private void setNavViewUserEmail(){
        TextView tvUserEmail = mNavigationView.getHeaderView(0).findViewById(R.id.tv_alias);
        tvUserEmail.setText(mMainViewModel.getUserEmail());
    }

    private boolean checkUserIsLoggedIn(){
        boolean bIsSignedIn = mMainViewModel.isUserSignedIn();

        if (!bIsSignedIn) {
            Toast.makeText(MainActivity.this, "Please Sign in." , Toast.LENGTH_SHORT).show();
        }

        return bIsSignedIn;
    }

    private void setAdapterByCategory(String cat, ArrayList<Product> productsList){
        if (cat.equals(CAT_GADGETS)) mGadgetsAdapter = new ProductsAdapter(this,productsList,CAT_GADGETS);
        else if (cat.equals(CAT_CLOTHES)) mClothesAdapter = new ProductsAdapter(this,productsList, CAT_CLOTHES);
        else if (cat.equals(CAT_TOOLS)) mToolsAdapter = new ProductsAdapter(this,productsList,CAT_TOOLS);
        else if (cat.equals(CAT_BIKES)) mBikesAdapter = new ProductsAdapter(this,productsList,CAT_BIKES);
    }

    private void startIntentActionWithUserLoggedInCheck(Class destClass){
        if (false == checkUserIsLoggedIn()) return;

        startActivity(new Intent(MainActivity.this, destClass));
    }

    @Override
    public void onItemClick(View view, int adapterPosition, String category) {
        //ToDo: inflate product description by category
        switch(category){
            case CAT_GADGETS:{
                break;
            }
            case CAT_CLOTHES:{
                int i = 0;
                break;
            }
            case CAT_TOOLS:{
                int j = 1;
                break;
            }
            case CAT_BIKES:{
                int k = 2;
                break;
            }
            default: return;
        }
    }
}
