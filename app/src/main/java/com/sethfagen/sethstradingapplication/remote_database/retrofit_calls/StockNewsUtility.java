package com.sethfagen.sethstradingapplication.remote_database.retrofit_calls;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.sethfagen.sethstradingapplication.adapters.StockNewsListViewCustomAdapter;
import com.sethfagen.sethstradingapplication.remote_database.RetrofitClient;
import com.sethfagen.sethstradingapplication.remote_database.WebInterface;
import com.sethfagen.sethstradingapplication.remote_database.models.StockNews;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockNewsUtility {
    private List<StockNews> list;
    private Context context;
    private String ticker;
    private ListView listview;

    public StockNewsUtility(Context context, String ticker, ListView listView){
        this.context = context;
        this.ticker = ticker;
        this.listview = listView;
    }

    public void setData(){

        WebInterface webInterface = RetrofitClient.getInstance().getWebInterface();
        Call<List<StockNews>> call = webInterface.getCompanyNews(ticker);


        call.enqueue(new Callback<List<StockNews>>() {
            @Override
            public void onResponse(Call<List<StockNews>> call, Response<List<StockNews>> response) {
                if (response.isSuccessful()) {
                    Log.d("CSC", "STOCK NEWS SUCCESS");

                    List<StockNews> list = response.body();
                    StockNewsListViewCustomAdapter adapter = new StockNewsListViewCustomAdapter(context, list);
                    listview.setClickable(true);
                    listview.setOnItemClickListener((adapterView, view1, i, l) -> {
                        Toast.makeText(context, "click", Toast.LENGTH_SHORT).show();
                        StockNews stock = (StockNews) adapterView.getItemAtPosition(i);

                        Intent implicit = new Intent(Intent.ACTION_VIEW,
                                Uri.parse(stock.getUrl()));
                        if (implicit.resolveActivity(context.getPackageManager()) != null) {
                            context.startActivity(implicit);
                        }

                    });
                    listview.setAdapter(adapter);
                } else {
                    Log.d("CSC", "STOCK NEWS FAIL");
                }
            }

            @Override
            public void onFailure(Call<List<StockNews>> call, Throwable t) {

            }

        });
    }


}
