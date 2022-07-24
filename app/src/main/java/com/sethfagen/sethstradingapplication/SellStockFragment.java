package com.sethfagen.sethstradingapplication;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sethfagen.sethstradingapplication.databinding.FragmentSellStockBinding;
import com.sethfagen.sethstradingapplication.remote_database.models.User;
import com.sethfagen.sethstradingapplication.remote_database.retrofit_calls.BuyStockUtility;
import com.sethfagen.sethstradingapplication.remote_database.retrofit_calls.IndexStockUtility;
import com.sethfagen.sethstradingapplication.remote_database.retrofit_calls.SellStockUtility;
import com.sethfagen.sethstradingapplication.remote_database.retrofit_calls.StockInfoUtility;

public class SellStockFragment extends Fragment {
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

    private View.OnFocusChangeListener edit_text_stock_picked_focusChangeListener = new View.OnFocusChangeListener() {
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

        binding.editTextStocks.setOnFocusChangeListener(edit_text_stock_picked_focusChangeListener);

        binding.buttonOrderSale.setOnClickListener(button_submit_sale_clickListener);

        IndexStockUtility indexStockUtility = new IndexStockUtility(getActivity(), binding.editTextStocks);
        indexStockUtility.setStockList();
    }

    public boolean checkInput(String quantity, String ticker){
        return quantity.length() != 0 && ticker.length() != 0;
    }
}
