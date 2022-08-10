package com.sethfagen.sethstradingapplication;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.sethfagen.sethstradingapplication.databinding.FragmentSellStockBinding;
import com.sethfagen.sethstradingapplication.remote_database.models.User;
import com.sethfagen.sethstradingapplication.remote_database.retrofit_calls.BuyStockUtility;
import com.sethfagen.sethstradingapplication.remote_database.retrofit_calls.IndexStockUtility;
import com.sethfagen.sethstradingapplication.remote_database.retrofit_calls.SellStockUtility;
import com.sethfagen.sethstradingapplication.remote_database.retrofit_calls.StockInfoUtility;

public class SellStockFragment extends DialogFragment implements View.OnFocusChangeListener {
    private FragmentSellStockBinding binding;

    private View.OnClickListener button_submit_sale_clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String tickerInput = binding.editTextStocks.getText().toString();
            String[] sentence = tickerInput.split(" ");
            String ticker = sentence[sentence.length - 1];
            String quantity = binding.editTextSaleQuantity.getText().toString();
            if(!checkInput(quantity, ticker)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("User Input Error")
                        .setMessage("Please make sure quantity and stock ticker are both filled out");
                builder.show();
                return;
            }
            SellStockUtility sellStockUtility = new SellStockUtility(ticker, Integer.valueOf(quantity), User.getUser().getId(), getActivity());
            sellStockUtility.sellStock();

        }
    };

    private View.OnClickListener button_cancel_purchase_clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(getDialog() == null){

            }
            else{
                getDialog().dismiss();
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSellStockBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.editTextStocks.setOnFocusChangeListener(this);

        binding.buttonOrderSale.setOnClickListener(button_submit_sale_clickListener);

        IndexStockUtility indexStockUtility = new IndexStockUtility(getActivity(), binding.editTextStocks);
        indexStockUtility.setStockList();

        binding.buttonCancelSale.setOnClickListener(button_cancel_purchase_clickListener);


        if(getArguments() != null){
            String ticker = getArguments().getString(StockActivity.EXTRA_SEARCH_TICKER);
            binding.editTextStocks.setText(ticker);
            onFocusChange(null, false);
        }
    }

    public boolean checkInput(String quantity, String ticker){
        return quantity.length() != 0 && ticker.length() != 0;
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if(!hasFocus){
            String sentence = binding.editTextStocks.getText().toString();
            String[] arr = sentence.split(" ");
            String ticker = arr[arr.length - 1];

            StockInfoUtility helper = new StockInfoUtility(getActivity(), binding.textviewSalePrice, binding.textviewDayGainSale, ticker, null);
            helper.setStockInfo();
        }
    }
}
