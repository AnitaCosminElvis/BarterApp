package com.example.barterapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.example.barterapp.data.Product;
import com.example.barterapp.data.ProductsAdapter;
import com.example.barterapp.data.Response;
import com.example.barterapp.view_models.LoginViewModel;
import com.example.barterapp.view_models.ProductsViewModel;
import com.example.barterapp.view_models.RegisterViewModel;
import com.example.barterapp.view_models.ViewModelFactory;
import com.example.barterapp.views.AboutActivity;
import com.example.barterapp.views.AccountActivity;
import com.example.barterapp.views.AddProductActivity;
import com.example.barterapp.views.ContactsActivity;
import com.example.barterapp.views.LoginActivity;
import com.example.barterapp.views.RegisterActivity;
import com.example.barterapp.views.ViewProductActivity;
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
import androidx.recyclerview.widget.RecyclerView;

import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import javax.annotation.Nullable;

import static com.example.barterapp.utility.DefinesUtility.*;

public class MainActivity   extends     AppCompatActivity
                            implements  ProductsAdapter.ItemClickListener,
                                        NavigationView.OnNavigationItemSelectedListener{
    private ProductsViewModel                       mProductsViewModel;
    private LoginViewModel                          mLoginViewModel;
    private RegisterViewModel                       mRegisterViewModel;
    private MutableLiveData<Response>               mLoginResponseLiveData;
    private MutableLiveData<Response>               mRegisterResposneLiveData;
    private MutableLiveData<ArrayList<Product>>     mGadgetsLiveData;
    private MutableLiveData<ArrayList<Product>>     mClothesLiveData;
    private MutableLiveData<ArrayList<Product>>     mToolsLiveData;
    private MutableLiveData<ArrayList<Product>>     mBikesLiveData;
    private ProductsAdapter                         mGadgetsAdapter ;
    private ProductsAdapter                         mClothesAdapter;
    private ProductsAdapter                         mToolsAdapter;
    private ProductsAdapter                         mBikesAdapter;
    private ProductsAdapter                         mOtherProductsAdapter;
    private DrawerLayout                            mDrawer;
    private NavigationView                          mNavigationView;
    private RecyclerView                            mProductsRecyclerView;
    private Toolbar                                 mToolbar                        = null;
    private TabLayout                               mProductsCatTabLayout;
    private int                                     mCurrentTabPosition             = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

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
        mProductsViewModel = ViewModelProviders.of(this, new ViewModelFactory())
                .get(ProductsViewModel.class);

        //create login view model
        mLoginViewModel = ViewModelProviders.of(this, new ViewModelFactory())
                .get(LoginViewModel.class);

        //create register view model
        mRegisterViewModel = ViewModelProviders.of(this, new ViewModelFactory())
                .get(RegisterViewModel.class);

        //getting products live data
        mGadgetsLiveData = mProductsViewModel.getGadgetsLiveData();
        mClothesLiveData = mProductsViewModel.getClothesLiveData();
        mToolsLiveData = mProductsViewModel.getToolsLiveData();
        mBikesLiveData = mProductsViewModel.getBikesLiveData();

        mLoginResponseLiveData = mLoginViewModel.getLoginResponseLiveData();
        mRegisterResposneLiveData = mRegisterViewModel.getRegisterResponseLiveData();

        //initialize the gadgets list
        mProductsViewModel.triggerGetProductsByCategory(CATEGORY_KEY, CAT_GADGETS);

        createObserversForMutableLiveData();

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
                mCurrentTabPosition = tab.getPosition();
                refreshProductsAdapterByCurrentPosition();
            }
            @Override public void onTabUnselected(TabLayout.Tab tab) { }
            @Override public void onTabReselected(TabLayout.Tab tab) { }
        });

        //sign out from any logged account
        mProductsViewModel.signOut();
    }

    private void refreshProductsAdapterByCurrentPosition() {
        switch (mCurrentTabPosition){
            case 0:{//Gadgets
                mProductsViewModel.triggerGetProductsByCategory(CATEGORY_KEY, CAT_GADGETS);
                mProductsRecyclerView.setAdapter(mGadgetsAdapter);
                break;
            }
            case 1:{//Clothes
                mProductsViewModel.triggerGetProductsByCategory(CATEGORY_KEY, CAT_CLOTHES);
                mProductsRecyclerView.setAdapter(mClothesAdapter);
                break;
            }
            case 2:{//Tools
                mProductsViewModel.triggerGetProductsByCategory(CATEGORY_KEY, CAT_TOOLS);
                mProductsRecyclerView.setAdapter(mToolsAdapter);
                break;
            }
            case 3:{//Bikes
                mProductsViewModel.triggerGetProductsByCategory(CATEGORY_KEY, CAT_BIKES);
                mProductsRecyclerView.setAdapter(mBikesAdapter);
                break;
            }
            case 4:{//Other
                mProductsViewModel.triggerGetProductsByCategory(CATEGORY_KEY, CAT_OTHER);
                mProductsRecyclerView.setAdapter(mOtherProductsAdapter);
                break;
            }

            default: return;
        }
    }

    @Override
    public void onResume() {
        refreshProductsAdapterByCurrentPosition();
        super.onResume();
    }

    @Override
    public void onDestroy(){
        //sign out from any logged account
        mProductsViewModel.signOut();
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

    private void createObserversForMutableLiveData() {
        //create observers for the products
        mGadgetsLiveData.observe(this, new Observer<ArrayList<Product>>() {
            @Override public void onChanged(ArrayList<Product> productsList) {
                if (null != productsList) {
                    mGadgetsAdapter.setProductsList(productsList);
                    mProductsRecyclerView.setAdapter(mGadgetsAdapter);
                }
            }
        });

        mClothesLiveData.observe(this, new Observer<ArrayList<Product>>() {
            @Override public void onChanged(ArrayList<Product> productsList) {
                if (null != productsList){
                    mClothesAdapter.setProductsList(productsList);
                    mProductsRecyclerView.setAdapter(mClothesAdapter);
                }
            }
        });

        mToolsLiveData.observe(this, new Observer<ArrayList<Product>>() {
            @Override public void onChanged(ArrayList<Product> productsList) {
                if (null != productsList){
                    mToolsAdapter.setProductsList(productsList);
                    mProductsRecyclerView.setAdapter(mToolsAdapter);
                }
            }
        });

        mBikesLiveData.observe(this, new Observer<ArrayList<Product>>() {
            @Override public void onChanged(ArrayList<Product> productsList) {
                if (null != productsList) {
                    mBikesAdapter.setProductsList(productsList);
                    mProductsRecyclerView.setAdapter(mBikesAdapter);
                }
            }
        });

        //create observer for login response
        mLoginResponseLiveData.observe(this, new Observer<Response>(){
            @Override public void onChanged(@Nullable Response response){
                if (null != response){ setNavViewUserEmail(response);}
            }
        });

        //create observer for login response
        mRegisterResposneLiveData.observe(this, new Observer<Response>(){
            @Override public void onChanged(@Nullable Response response){
                if (null != response){ setNavViewUserEmail(response);}
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_account: {
                startIntentActionWithUserLoggedInCheck(AccountActivity.class);
                break;
            }
            case R.id.nav_add: {
                startIntentActionWithUserLoggedInCheck(AddProductActivity.class);
                break;
            }
            case R.id.nav_signin: {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                break;
            }
            case R.id.nav_signup: {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                break;
            }
            case R.id.nav_signout: {
                mProductsViewModel.signOut();
                setNavViewUserEmail(new Response("", false));
                Toast.makeText(MainActivity.this, "Signed out.", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.nav_about: {
                startActivity(new Intent(MainActivity.this, AboutActivity.class));
                break;
            }
            case R.id.nav_contact: {
                startActivity(new Intent(MainActivity.this, ContactsActivity.class));
                break;
            }
        }

        return true;
    }

    private void setNavViewUserEmail(Response response){
        TextView tvUserEmail = mNavigationView.getHeaderView(0).findViewById(R.id.tv_alias);
        if (null != response) {
            if (response.getmIsSuccessfull()) {
                tvUserEmail.setText(mProductsViewModel.getUserEmail());
            } else {
                tvUserEmail.setText(getString(R.string.nav_header_email));
            }
        }
    }

    private boolean checkUserIsLoggedIn(){
        boolean bIsSignedIn = mProductsViewModel.isUserSignedIn();

        if (!bIsSignedIn) {
            Toast.makeText(MainActivity.this, "Please Sign in." , Toast.LENGTH_SHORT).show();
        }

        return bIsSignedIn;
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
                Intent intent = new Intent(MainActivity.this, ViewProductActivity.class);
                intent.putExtra(getString(R.string.product_info_tag) ,
                        mGadgetsAdapter.getProductByPosition(adapterPosition));
                startActivity(intent);
                break;
            }
            case CAT_CLOTHES:{
                Intent intent = new Intent(MainActivity.this, ViewProductActivity.class);
                intent.putExtra(getString(R.string.product_info_tag) ,
                        mClothesAdapter.getProductByPosition(adapterPosition));
                startActivity(intent);
                break;
            }
            case CAT_TOOLS:{
                Intent intent = new Intent(MainActivity.this, ViewProductActivity.class);
                intent.putExtra(getString(R.string.product_info_tag) ,
                        mToolsAdapter.getProductByPosition(adapterPosition));
                startActivity(intent);
                break;
            }
            case CAT_BIKES:{
                Intent intent = new Intent(MainActivity.this, ViewProductActivity.class);
                intent.putExtra(getString(R.string.product_info_tag) ,
                        mBikesAdapter.getProductByPosition(adapterPosition));
                startActivity(intent);
                break;
            }
            default: return;
        }
    }
}
