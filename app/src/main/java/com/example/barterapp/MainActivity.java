package com.example.barterapp;

import static com.example.barterapp.utility.DefinesUtility.CATEGORY_KEY;
import static com.example.barterapp.utility.DefinesUtility.CAT_BIKES;
import static com.example.barterapp.utility.DefinesUtility.CAT_CLOTHES;
import static com.example.barterapp.utility.DefinesUtility.CAT_GADGETS;
import static com.example.barterapp.utility.DefinesUtility.CAT_OTHER;
import static com.example.barterapp.utility.DefinesUtility.CAT_TOOLS;

import com.example.barterapp.R;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import javax.annotation.Nullable;

import com.example.barterapp.data.Product;
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
import com.example.barterapp.views.ProductsAdapter;
import com.example.barterapp.views.RegisterActivity;
import com.example.barterapp.views.ViewProductActivity;


/**
 * Main activity is the launcher activity, and it exposes the products categories
 * through a Tab Layout in a two columns grid Recycler View
 */
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
    private MutableLiveData<ArrayList<Product>>     mOtherLiveData;
    private ProductsAdapter                         mGadgetsAdapter ;
    private ProductsAdapter                         mClothesAdapter;
    private ProductsAdapter                         mToolsAdapter;
    private ProductsAdapter                         mBikesAdapter;
    private ProductsAdapter                         mOtherProductsAdapter;
    private ArrayList<Product>                      mGadgetsList = new ArrayList<>();
    private ArrayList<Product>                      mClothesList = new ArrayList<>();
    private ArrayList<Product>                      mToolsList = new ArrayList<>();
    private ArrayList<Product>                      mBikesList = new ArrayList<>();
    private ArrayList<Product>                      mOtherList = new ArrayList<>();
    private DrawerLayout                            mDrawer;
    private NavigationView                          mNavigationView;
    private RecyclerView                            mProductsRecyclerView;
    private Toolbar                                 mToolbar                        = null;
    private TabLayout                               mProductsCatTabLayout;
    private SearchView                              mSearchView;
    private int                                     mCurrentTabPosition             = 0;

    /**
     * initializing class members
     *
     * @param savedInstanceState
     * @return void
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initProductAdaptersPerCategory();
        initRecyclerView();
        intiViewModels();
        initLiveData();
        createObserversForMutableLiveData();
        setAddProductFloatingActionButtonOnClickListener();
        initMainMenu();
        setProductsTabLayoutListener();

        //initialize the gadgets list
        mProductsViewModel.triggerGetProductsByCategory(CATEGORY_KEY, CAT_GADGETS);

        //sign out from any logged account
        mProductsViewModel.signOut();
    }

    /**
     * Sets Products Tab Layout Listener
     *
     * @return void
     */
    private void setProductsTabLayoutListener() {
        mProductsCatTabLayout = findViewById(R.id.tab_product_category);

        mProductsCatTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override public void onTabSelected(TabLayout.Tab tab) {
                mCurrentTabPosition = tab.getPosition();
                mSearchView.setQuery("", false);
                refreshProductsAdapterByCurrentPosition();
            }
            @Override public void onTabUnselected(TabLayout.Tab tab) { }
            @Override public void onTabReselected(TabLayout.Tab tab) { }
        });
    }

    /**
     * Initializes the Main Menu
     *
     * @return void
     */
    private void initMainMenu() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mDrawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);
        mDrawer.setDrawerListener(toogle);
        toogle.syncState();

        mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        mSearchView = findViewById(R.id.sv_find_products);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String arg0) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean onQueryTextChange(String arg0) {
                if(arg0.length() > 50){
                    mSearchView.setQuery(arg0.substring(0,49), false);
                }

                switch (mCurrentTabPosition){
                    case 0:{//Gadgets
                        mProductsViewModel.triggerFilterProductsByKeyAndCategory(
                                mGadgetsList,
                                String.valueOf(mSearchView.getQuery()),
                                CAT_GADGETS
                        );
                        mProductsRecyclerView.setAdapter(mGadgetsAdapter);
                        break;
                    }
                    case 1:{//Clothes
                        mProductsViewModel.triggerFilterProductsByKeyAndCategory(
                                mClothesList,
                                String.valueOf(mSearchView.getQuery()),
                                CAT_CLOTHES
                        );
                        mProductsRecyclerView.setAdapter(mClothesAdapter);
                        break;
                    }
                    case 2:{//Tools
                        mProductsViewModel.triggerFilterProductsByKeyAndCategory(
                                mToolsList,
                                String.valueOf(mSearchView.getQuery()),
                                CAT_TOOLS
                        );
                        mProductsRecyclerView.setAdapter(mToolsAdapter);
                        break;
                    }
                    case 3:{//Bikes
                        mProductsViewModel.triggerFilterProductsByKeyAndCategory(
                                mBikesList,
                                String.valueOf(mSearchView.getQuery()),
                                CAT_BIKES
                        );
                        mProductsRecyclerView.setAdapter(mBikesAdapter);
                        break;
                    }
                    case 4:{//Other
                        mProductsViewModel.triggerFilterProductsByKeyAndCategory(
                                mOtherList,
                                String.valueOf(mSearchView.getQuery()),
                                CAT_OTHER
                        );
                        mProductsRecyclerView.setAdapter(mOtherProductsAdapter);
                        break;
                    }
                    default: break;
                }

                return false;
            }
        });
    }

    /**
     * Set the Add Product Floating Action Button On Click Listener
     *
     * @return void
     */
    private void setAddProductFloatingActionButtonOnClickListener() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                startIntentActionWithUserLoggedInCheck(AddProductActivity.class);
            }
        });
    }

    /**
     * Initializes the Live Data class members
     *
     * @return void
     */
    private void initLiveData() {
        //getting products live data
        mGadgetsLiveData = mProductsViewModel.getGadgetsLiveData();
        mClothesLiveData = mProductsViewModel.getClothesLiveData();
        mToolsLiveData = mProductsViewModel.getToolsLiveData();
        mBikesLiveData = mProductsViewModel.getBikesLiveData();
        mOtherLiveData = mProductsViewModel.getOtherProductsLiveData();

        //get responses live data
        mLoginResponseLiveData = mLoginViewModel.getLoginResponseLiveData();
        mRegisterResposneLiveData = mRegisterViewModel.getRegisterResponseLiveData();
    }

    /**
     * Initializes the view models
     *
     * @return void
     */
    private void intiViewModels() {
        //create the view model
        mProductsViewModel = ViewModelProviders.of(this, new ViewModelFactory())
                .get(ProductsViewModel.class);

        //create login view model
        mLoginViewModel = ViewModelProviders.of(this, new ViewModelFactory())
                .get(LoginViewModel.class);

        //create register view model
        mRegisterViewModel = ViewModelProviders.of(this, new ViewModelFactory())
                .get(RegisterViewModel.class);
    }

    /**
     * Initializes the Recycler View
     *
     * @return void
     */
    private void initRecyclerView() {
        mProductsRecyclerView = findViewById(R.id.rv_products);
        mProductsRecyclerView.setHasFixedSize(true);
        mProductsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        //set the default adapter
        mProductsRecyclerView.setAdapter(mGadgetsAdapter);
    }

    /**
     * Initializes the Product Adapters Per Category
     *
     * @return void
     */
    private void initProductAdaptersPerCategory() {
        //allocate the adapters
        mGadgetsAdapter = new ProductsAdapter(this, new ArrayList<Product>(), CAT_GADGETS);
        mClothesAdapter = new ProductsAdapter(this, new ArrayList<Product>(), CAT_CLOTHES);
        mToolsAdapter = new ProductsAdapter(this, new ArrayList<Product>(), CAT_TOOLS);
        mBikesAdapter = new ProductsAdapter(this, new ArrayList<Product>(), CAT_BIKES);
        mOtherProductsAdapter = new ProductsAdapter(this, new ArrayList<Product>(), CAT_OTHER);

        //set the listener to the adapters
        mGadgetsAdapter.setClickListener(this);
        mToolsAdapter.setClickListener(this);
        mClothesAdapter.setClickListener(this);
        mBikesAdapter.setClickListener(this);
        mOtherProductsAdapter.setClickListener(this);
    }

    /**
     * Refreshes Products Adapter By Current Position
     *
     * @return void
     */
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

    /**
     * On Resume triggers a refresh
     *
     * @return void
     */
    @Override
    public void onResume() {
        refreshProductsAdapterByCurrentPosition();
        super.onResume();
    }

    /**
     * On Destroy automatically signs out
     *
     * @return void
     */
    @Override
    public void onDestroy(){
        //sign out from any logged account
        mProductsViewModel.signOut();
        super.onDestroy();
    }

    /**
     * On Back Pressed callback
     *
     * @return void
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Creates Observers For MutableLiveData class members
     *
     * @return void
     */
    private void createObserversForMutableLiveData() {

        //create observers for the products
        mGadgetsLiveData.observe(this, new Observer<ArrayList<Product>>() {
            @Override public void onChanged(ArrayList<Product> productsList) {
                if (null != productsList) {
                    if (mSearchView.getQuery().toString().isEmpty()) {
                        mGadgetsList = productsList;
                    }
                    mGadgetsAdapter.setProductsList(productsList);
                    mProductsRecyclerView.setAdapter(mGadgetsAdapter);
                }
            }
        });
        mClothesLiveData.observe(this, new Observer<ArrayList<Product>>() {
            @Override public void onChanged(ArrayList<Product> productsList) {
                if (null != productsList){
                    if (mSearchView.getQuery().toString().isEmpty()) {
                        mClothesList = productsList;
                    }
                    mClothesAdapter.setProductsList(productsList);
                    mProductsRecyclerView.setAdapter(mClothesAdapter);
                }
            }
        });
        mToolsLiveData.observe(this, new Observer<ArrayList<Product>>() {
            @Override public void onChanged(ArrayList<Product> productsList) {
                if (null != productsList){
                    if (mSearchView.getQuery().toString().isEmpty()) {
                        mToolsList = productsList;
                    }
                    mToolsAdapter.setProductsList(productsList);
                    mProductsRecyclerView.setAdapter(mToolsAdapter);
                }
            }
        });
        mBikesLiveData.observe(this, new Observer<ArrayList<Product>>() {
            @Override public void onChanged(ArrayList<Product> productsList) {
                if (null != productsList) {
                    if (mSearchView.getQuery().toString().isEmpty()) {
                        mBikesList = productsList;
                    }
                    mBikesAdapter.setProductsList(productsList);
                    mProductsRecyclerView.setAdapter(mBikesAdapter);
                }
            }
        });
        mOtherLiveData.observe(this, new Observer<ArrayList<Product>>() {
            @Override public void onChanged(ArrayList<Product> productsList) {
                if (null != productsList) {
                    if (mSearchView.getQuery().toString().isEmpty()) {
                        mOtherList = productsList;
                    }
                    mOtherProductsAdapter.setProductsList(productsList);
                    mProductsRecyclerView.setAdapter(mOtherProductsAdapter);
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

    /**
     * The main menu item selection triggers
     *
     * @return void
     */
    @SuppressLint("NonConstantResourceId")
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
                Toast.makeText(MainActivity.this, getString(R.string.signed_out)
                        , Toast.LENGTH_SHORT).show();
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
            default:
                throw new IllegalStateException("Unexpected value: " + menuItem.getItemId());
        }

        return true;
    }


    /**
     * Sets the Navigation View tag with the User's Email
     *
     * @param response
     * @return void
     */
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

    /**
     * Checks if the User Is Logged In
     *
     * @return boolean
     */
    private boolean checkUserIsLoggedIn(){
        boolean bIsSignedIn = mProductsViewModel.isUserSignedIn();

        if (!bIsSignedIn) {
            Toast.makeText(MainActivity.this, getString(R.string.sign_in) , Toast.LENGTH_SHORT).show();
        }

        return bIsSignedIn;
    }

    /**
     * Starts Intent Action With User Logged In Check
     *
     * @param destClass
     * @return void
     */
    private void startIntentActionWithUserLoggedInCheck(Class destClass){
        if (false == checkUserIsLoggedIn()) return;

        startActivity(new Intent(MainActivity.this, destClass));
    }

    /**
     * On Item click Listener for starting a new intent action to view the selected product
     *
     * @param view, adapterPosition, category
     * @return void
     */
    @Override
    public void onItemClick(View view, int adapterPosition, String category) {
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
            case CAT_OTHER:{
                Intent intent = new Intent(MainActivity.this, ViewProductActivity.class);
                intent.putExtra(getString(R.string.product_info_tag) ,
                        mOtherProductsAdapter.getProductByPosition(adapterPosition));
                startActivity(intent);
                break;
            }
            default: return;
        }
    }
}
