package com.sethfagen.sethstradingapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;
import com.sethfagen.sethstradingapplication.databinding.ActivityStockBinding;

public class StockActivity extends AppCompatActivity {

    private ActivityStockBinding binding;

    private final int INFO_TAB = 0;
    private final int GRAPH_TAB = 1;
    private final int NEWS_TAB= 2;


    public static final String EXTRA_SEARCH_TICKER = "com.sethfagen.sethstradingapplication.EXTRA_SEARCH_TICKER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStockBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        Intent intent = getIntent();

        String ticker = intent.getStringExtra(SearchFragment.TICKER_EXTRA);

        binding.textviewMainpageStockName.setText(ticker);

        binding.tablayoutStocks.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment = null;
                switch (tab.getPosition()){
                    case GRAPH_TAB:{
                        fragment = new StockGraphFragment();
                        break;
                    }
                    case NEWS_TAB:{
                        fragment = new StockNewsFragment();
                        break;
                    }
                    case INFO_TAB:{
                        fragment = new StockInfoFragment();
                        break;
                    }
                }
                openFragment(fragment, ticker);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        openFragment(new StockInfoFragment(), ticker);

    }

    public void openFragment(Fragment fragment, String ticker){

        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_SEARCH_TICKER, ticker);
        fragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_page, fragment).commit();
    }
}