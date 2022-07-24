package com.sethfagen.sethstradingapplication.remote_database.retrofit_calls;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.sethfagen.sethstradingapplication.remote_database.RetrofitClient;
import com.sethfagen.sethstradingapplication.remote_database.WebInterface;
import com.sethfagen.sethstradingapplication.remote_database.models.Stock;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IndexStockUtility {
    private Context context;
    private AutoCompleteTextView view;

    public IndexStockUtility(Context context, AutoCompleteTextView view) {
        this.context = context;
        this.view = view;
    }

    public void setStockList(){

        WebInterface webInterface = RetrofitClient.getInstance().getWebInterface();
        Call<List<Stock>> call = webInterface.getStocks();
        call.enqueue(new Callback<List<Stock>>() {
            @Override
            public void onResponse(Call<List<Stock>> call, Response<List<Stock>> response) {
                if(response.isSuccessful()){
                    List<Stock> stocks = response.body();

                    List<String> strings = new ArrayList<>();
                    for(Stock s : stocks){
                        strings.add(s.getName() + " " + s.getTicker());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, strings);
                    Toast.makeText(context, "HERE", Toast.LENGTH_SHORT).show();

                    view.setAdapter(adapter);
                }
                else{
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Stock>> call, Throwable t) {
                Log.d("CSC", "Failed");
            }
        });
    }


}
