package com.sethfagen.sethstradingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.sethfagen.sethstradingapplication.databinding.ActivityStockBinding;
import com.sethfagen.sethstradingapplication.remote_database.RetrofitClient;
import com.sethfagen.sethstradingapplication.remote_database.WebInterface;
import com.sethfagen.sethstradingapplication.remote_database.models.Stock;
import com.sethfagen.sethstradingapplication.remote_database.models.StockGraph;
import com.sethfagen.sethstradingapplication.remote_database.request_models.StockGraphRequest;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockActivity extends AppCompatActivity {

    private ActivityStockBinding binding;

    public static final String EXTRA_SEARCH_TICKER = "com.sethfagen.sethstradingapplication.EXTRA_SEARCH_TICKER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStockBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        Intent intent = getIntent();

        String ticker = intent.getStringExtra(SearchFragment.TICKER_EXTRA);

        binding.textviewMainpageStockName.setText(ticker);

        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_SEARCH_TICKER, ticker);
        StockGraphFragment fragment = new StockGraphFragment();
        fragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_graph, fragment).commit();
    }
}