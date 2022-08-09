package com.sethfagen.sethstradingapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sethfagen.sethstradingapplication.adapters.StockNewsListViewCustomAdapter;
import com.sethfagen.sethstradingapplication.databinding.FragmentStockNewsBinding;
import com.sethfagen.sethstradingapplication.remote_database.RetrofitClient;
import com.sethfagen.sethstradingapplication.remote_database.WebInterface;
import com.sethfagen.sethstradingapplication.remote_database.models.StockNews;
import com.sethfagen.sethstradingapplication.remote_database.retrofit_calls.StockNewsUtility;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockNewsFragment extends Fragment {
    FragmentStockNewsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentStockNewsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        WebInterface webInterface = RetrofitClient.getInstance().getWebInterface();

        String[] search = getArguments().getString(StockActivity.EXTRA_SEARCH_TICKER).split(" ");
        String ticker = search[search.length - 1];

        StockNewsUtility utility = new StockNewsUtility(getActivity(), ticker, binding.listviewStocknews);
        utility.setData();
    }
}