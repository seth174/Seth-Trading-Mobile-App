package com.sethfagen.sethstradingapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.sethfagen.sethstradingapplication.databinding.ActivityHomePageBinding;
import com.sethfagen.sethstradingapplication.remote_database.RetrofitClient;
import com.sethfagen.sethstradingapplication.remote_database.WebInterface;
import com.sethfagen.sethstradingapplication.remote_database.models.Stock;
import com.sethfagen.sethstradingapplication.remote_database.models.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityHomePageBinding binding;
    private DrawerLayout drawer;
    private Fragment lastSeen;

    public static final String BUNDLE_LAST_FRAGMENT = "com.sethfagen.sethstradingapplication.BUNDLE_LAST_FRAGMENT";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        User.setUser(createUser(getIntent()));

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);

        drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AccountHomeFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Stocks");

        searchView.setOnQueryTextFocusChangeListener((view, b) -> {
            if(b){

                SearchFragment searchFragment = new SearchFragment();

                searchView.clearFocus();

                Log.d("CSC", "HEREE");

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, searchFragment).addToBackStack(null).commit();

            }
            else{
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, lastSeen).commit();
                Log.d("CSC", "FALSE");
                searchView.setIconified(true);
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_buy:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PurchaseStockFragment()).commit();
                break;
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AccountHomeFragment()).commit();
                break;
            case R.id.nav_sell:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SellStockFragment()).commit();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public User createUser(Intent intent){
        int id = intent.getIntExtra(LoginActivity.BUNDLE_USER_ID, -1);
        String name = intent.getStringExtra(LoginActivity.BUNDLE_NAME);
        String email = intent.getStringExtra(LoginActivity.BUNDLE_EMAIL);
        boolean admin = intent.getBooleanExtra(LoginActivity.BUNDLE_ADMIN, false);
        String password_digest = intent.getStringExtra(LoginActivity.BUNDLE_PASSWORD_DIGEST);
        return new User(id, name, email, password_digest, admin);
    }

    public Fragment getVisibleFragment(){
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if(fragments != null){
            for(Fragment fragment : fragments){
                if(fragment != null && fragment.isVisible())
                    return fragment;
            }
        }
        return null;
    }

}