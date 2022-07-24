package com.sethfagen.sethstradingapplication.remote_database.retrofit_calls;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.sethfagen.sethstradingapplication.R;
import com.sethfagen.sethstradingapplication.remote_database.RetrofitClient;
import com.sethfagen.sethstradingapplication.remote_database.WebInterface;
import com.sethfagen.sethstradingapplication.remote_database.models.Stock;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockInfoUtility {
    private Context context;
    private TextView price;
    private TextView priceChange;
    private String ticker;
    private TextView priceChangePercent;
    private TextView name;
    private TextView tickerTextView;
    private TextView dayHighPrice;
    private TextView dayLowPrice;
    private TextView dayOpenPrice;
    private TextView dayPreviousClosePrice;


    public StockInfoUtility(Context context, TextView price, TextView priceChange, String ticker, TextView name){
        this.context = context;
        this.price = price;
        this.priceChange = priceChange;
        this.ticker = ticker;
        this.name = name;
    }

    public StockInfoUtility(Context context, TextView price, TextView priceChange, String ticker, TextView priceChangePercent, TextView name, TextView tickerTextView, TextView dayHighPrice, TextView dayLowPrice, TextView dayOpenPrice, TextView dayPreviousClosePrice) {
        this.context = context;
        this.price = price;
        this.priceChange = priceChange;
        this.ticker = ticker;
        this.priceChangePercent = priceChangePercent;
        this.name = name;
        this.tickerTextView = tickerTextView;
        this.dayHighPrice = dayHighPrice;
        this.dayLowPrice = dayLowPrice;
        this.dayOpenPrice = dayOpenPrice;
        this.dayPreviousClosePrice = dayPreviousClosePrice;
    }

    public void setStockInfo(){
        WebInterface webInterface = RetrofitClient.getInstance().getWebInterface();
        Call<Stock> call = webInterface.getStock(this.ticker);

        call.enqueue(new Callback<Stock>() {
            @Override
            public void onResponse(Call<Stock> call, Response<Stock> response) {
                if(response.isSuccessful()){
                    Stock stock = response.body();
                    Log.d("CSC", "The name " + String.valueOf(stock.getId()));
                    price.setText(String.valueOf(stock.getMostRecentPrice()));
                    priceChange.setText(String.valueOf(stock.getDayChange()));
                    if(stock.getDayChange() < 0) priceChange.setTextColor(context.getResources().getColor(R.color.warning, context.getTheme()));
                    else{
                        priceChange.setTextColor(context.getResources().getColor(R.color.success, context.getTheme()));
                    }

                    if(priceChangePercent != null) priceChange.setText(String.valueOf(stock.getDayPercentChange()));
                    if(name != null) {

                        name.setText(stock.getName());
                    }

                    if(tickerTextView != null) tickerTextView.setText(stock.getTicker());
                    if(dayHighPrice != null) dayHighPrice.setText(String.valueOf(stock.getDayHighPrice()));
                    if(dayLowPrice != null) dayLowPrice.setText(String.valueOf(stock.getDayLowPrice()));
                    if(dayOpenPrice != null) dayOpenPrice.setText(String.valueOf(stock.getDayOpenPrice()));
                    if(dayPreviousClosePrice != null) dayPreviousClosePrice.setText(String.valueOf(stock.getDayPreviousClosePrice()));

                }
                else {
                    price.setText(String.valueOf(0));
                    priceChange.setText(String.valueOf(0));
                }
            }

            @Override
            public void onFailure(Call<Stock> call, Throwable t) {

            }
        });
    }
}
