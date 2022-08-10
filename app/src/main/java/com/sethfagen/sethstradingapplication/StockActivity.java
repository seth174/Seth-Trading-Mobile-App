package com.sethfagen.sethstradingapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.sethfagen.sethstradingapplication.databinding.ActivityStockBinding;
import com.sethfagen.sethstradingapplication.remote_database.retrofit_calls.StockInfoUtility;

public class StockActivity extends AppCompatActivity implements StockGraphFragment.OnDatePass {

    private ActivityStockBinding binding;

    public static final String EXTRA_SEARCH_TICKER = "com.sethfagen.sethstradingapplication.EXTRA_SEARCH_TICKER";
    public static final String EXTRA_DAYS = "com.sethfagen.sethstradingapplication.EXTRA_DAYS";

    private final int INFO_TAB = 0;
    private final int GRAPH_TAB = 1;
    private final int NEWS_TAB= 2;

    private View.OnClickListener button_buy_stock_clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            PurchaseStockFragment dialog = new PurchaseStockFragment();
            Bundle bundle = new Bundle();
            bundle.putString(EXTRA_SEARCH_TICKER, getIntent().getStringExtra(SearchFragment.TICKER_EXTRA));
            dialog.setArguments(bundle);
            dialog.showNow(getSupportFragmentManager(), "Fragment");
        }
    };

    private View.OnClickListener button_sell_stock_clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SellStockFragment dialog = new SellStockFragment();
            Bundle bundle = new Bundle();
            bundle.putString(EXTRA_SEARCH_TICKER, getIntent().getStringExtra(SearchFragment.TICKER_EXTRA));
            dialog.setArguments(bundle);
            dialog.showNow(getSupportFragmentManager(), "Fragment");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityStockBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        Intent intent = getIntent();

        binding.buttonBuyStockActivity.setOnClickListener(button_buy_stock_clickListener);
        binding.buttonSellStockActivity.setOnClickListener(button_sell_stock_clickListener);

        String ticker = intent.getStringExtra(SearchFragment.TICKER_EXTRA);

        String[] search = ticker.split(" ");
        String actualTicker = search[search.length - 1];

        binding.textviewMainpageStockName.setText(ticker);



        StockInfoUtility infoUtility = new StockInfoUtility(this, actualTicker, binding.textviewMainpagePrice, binding.textviewMainpagePriceChange);
        infoUtility.setStockInfo();

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
                openFragment(fragment, ticker, null);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        openFragment(new StockInfoFragment(), ticker, null);

    }

    public void openFragment(Fragment fragment, String ticker, String days){

        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_SEARCH_TICKER, ticker);
        bundle.putString(EXTRA_DAYS, days == null ? "30" : days);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_page, fragment).commit();
    }

    @Override
    public void OnDatePass(String days, String ticker) {
        openFragment(new StockGraphFragment(), ticker, days);
    }
}