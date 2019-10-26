package com.example.barterapp;

import android.content.Intent;
import android.os.Bundle;

import com.example.barterapp.data.Response;
import com.example.barterapp.view_models.MainViewModel;
import com.example.barterapp.view_models.ViewModelFactory;
import com.example.barterapp.views.AccountActivity;
import com.example.barterapp.views.LoginActivity;
import com.example.barterapp.views.RegisterActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private MainViewModel                   mMainViewModel;
    private MutableLiveData<Response>       mLoginLiveData;
    private DrawerLayout                    mDrawer;
    private NavigationView                  mNavigationView;
    private Toolbar                         mToolbar                = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainViewModel = ViewModelProviders.of(this, new ViewModelFactory())
                .get(MainViewModel.class);

        mLoginLiveData = mMainViewModel.getLoginLiveData();

        mLoginLiveData.observe(this, new Observer<Response>(){
            @Override
            public void onChanged(@Nullable Response response){
                if (null != response){ setNavViewUserEmail();}
            }
        });
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mDrawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);
        mDrawer.setDrawerListener(toogle);
        toogle.syncState();

        mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
//                || super.onSupportNavigateUp();
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        Intent intent;
        switch (id){
            case R.id.nav_account:
                if (!mMainViewModel.isUserSignedIn()) {
                    Toast.makeText(MainActivity.this, "Please Sign in." , Toast.LENGTH_SHORT).show();
                    break;
                }

                intent = new Intent(MainActivity.this, AccountActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_signin:
                intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_signup:
                intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
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
}
