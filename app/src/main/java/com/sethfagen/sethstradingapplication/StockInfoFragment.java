package com.sethfagen.sethstradingapplication;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sethfagen.sethstradingapplication.databinding.FragmentStockInfoBinding;
import com.sethfagen.sethstradingapplication.remote_database.retrofit_calls.StockInfoUtility;

public class StockInfoFragment extends Fragment {

    private FragmentStockInfoBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentStockInfoBinding.inflate(inflater, container, false);

        if(getArguments() == null){
            return binding.getRoot();
        }
        String search[] = getArguments().getString(StockActivity.EXTRA_SEARCH_TICKER).split(" ");

        String ticker = search[search.length - 1];
        StockInfoUtility utility = new StockInfoUtility(getActivity(),binding.textviewStockinfoCurrentPrice, binding.textviewStockinfoDailyGain, ticker, binding.textviewStockinfoDailyPercentChange, binding.textviewStockinfoName, binding.textviewStockinfoTicker, binding.textviewStockinfoHighPrice, null, binding.textviewStockinfoOpenPrice, binding.textviewStockinfoPreviousClose);
        utility.setStockInfo();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

    }
}