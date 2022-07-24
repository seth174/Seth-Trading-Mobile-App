package com.sethfagen.sethstradingapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.sethfagen.sethstradingapplication.databinding.FragmentStockGraphBinding;
import com.sethfagen.sethstradingapplication.remote_database.RetrofitClient;
import com.sethfagen.sethstradingapplication.remote_database.WebInterface;
import com.sethfagen.sethstradingapplication.remote_database.models.StockGraph;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockGraphFragment extends Fragment {

    private FragmentStockGraphBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentStockGraphBinding.inflate(inflater, container, false);

        GraphView graphStock = binding.graphStock;

        graphStock.setTitle("TEST");

        WebInterface webInterface = RetrofitClient.getInstance().getWebInterface();
        //CHANGE TO ONLY GERT TICKER
        String[] words = getArguments().getString(StockActivity.EXTRA_SEARCH_TICKER).split(" ");
        String ticker = words[words.length - 1];
        Call<StockGraph> call = webInterface.getStockGraph(ticker, "30");
        call.enqueue(new Callback<StockGraph>() {
            @Override
            public void onResponse(Call<StockGraph> call, Response<StockGraph> response) {
                if(response.isSuccessful()){

                    StockGraph stocks = response.body();
                    DataPoint[] dataPoints = new DataPoint[stocks.getDatePriceMap().keySet().size()];

                    int count = 0;
                    for(Date s : stocks.getDatePriceMap().keySet()){
                        //dataPoints[count] = new DataPoint(s, stocks.getDatePriceMap().get(s));
                        dataPoints[count] = new DataPoint(count, stocks.getDatePriceMap().get(s));
                        count += 1;
                        Log.d("CSC", String.valueOf(stocks.getDatePriceMap().size()));
                    }
                    Log.d("CSC", "Success");

                    LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(dataPoints);
                    graphStock.addSeries(series);

                    graphStock.getViewport().setYAxisBoundsManual(true);
                    graphStock.getViewport().setMinY(stocks.getMin());
                    graphStock.getViewport().setMaxY(stocks.getMax());
                }
                else{
                    Log.d("CSC", "FAILED");
                }
            }

            @Override
            public void onFailure(Call<StockGraph> call, Throwable t) {
                Log.d("CSC", "FAILJRE");
            }

        });




        return binding.getRoot();
    }
}