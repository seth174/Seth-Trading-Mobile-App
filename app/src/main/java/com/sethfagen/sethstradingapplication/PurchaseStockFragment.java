package com.sethfagen.sethstradingapplication;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sethfagen.sethstradingapplication.databinding.FragmentPurchaseStockBinding;
import com.sethfagen.sethstradingapplication.remote_database.RetrofitClient;
import com.sethfagen.sethstradingapplication.remote_database.WebInterface;
import com.sethfagen.sethstradingapplication.remote_database.models.Stock;
import com.sethfagen.sethstradingapplication.remote_database.models.User;
import com.sethfagen.sethstradingapplication.remote_database.retrofit_calls.BuyStockUtility;
import com.sethfagen.sethstradingapplication.remote_database.retrofit_calls.IndexStockUtility;
import com.sethfagen.sethstradingapplication.remote_database.retrofit_calls.StockInfoUtility;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PurchaseStockFragment extends Fragment {

    private FragmentPurchaseStockBinding binding;

    private View.OnClickListener button_submit_purchase_clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String tickerInput = binding.editTextStocks.getText().toString();
            String[] sentence = tickerInput.split(" ");
            String ticker = sentence[sentence.length - 1];
            String quantity = binding.editTextPurchaseQuantity.getText().toString();
            if(!checkInput(quantity, ticker)){
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("User Input Error")
                        .setMessage("Please make sure quantity and stock ticker are both filled out");
                builder.show();
                return;
            }
            BuyStockUtility buyStockUtility = new BuyStockUtility(ticker, Integer.valueOf(quantity), User.getUser().getId(), getActivity());
            buyStockUtility.buyStock();
        }
    };

    private View.OnFocusChangeListener edit_text_stock_picked_focusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            if(!hasFocus){
                String sentence = binding.editTextStocks.getText().toString();
                String[] arr = sentence.split(" ");
                String ticker = arr[arr.length - 1];

                StockInfoUtility helper = new StockInfoUtility(getActivity(), binding.textviewPurchasePrice, binding.texttextviewDayGainPurchase, ticker, binding.textViewNamePurchase);
                helper.setStockInfo();
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPurchaseStockBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.editTextStocks.setOnFocusChangeListener(edit_text_stock_picked_focusChangeListener);

        binding.buttonOrderPurchase.setOnClickListener(button_submit_purchase_clickListener);

        IndexStockUtility indexStockUtility = new IndexStockUtility(getActivity(), binding.editTextStocks);
        indexStockUtility.setStockList();
    }

    public boolean checkInput(String quantity, String ticker){
        return quantity.length() != 0 && ticker.length() != 0;
    }
}
