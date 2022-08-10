package com.sethfagen.sethstradingapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LabelFormatter;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;
import com.sethfagen.sethstradingapplication.databinding.FragmentStockGraphBinding;
import com.sethfagen.sethstradingapplication.remote_database.RetrofitClient;
import com.sethfagen.sethstradingapplication.remote_database.WebInterface;
import com.sethfagen.sethstradingapplication.remote_database.models.StockGraph;

import java.io.Console;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockGraphFragment extends Fragment implements View.OnClickListener {

    private OnDatePass dataPasser;

    public interface OnDatePass{
        public void OnDatePass(String days, String ticker);
    }

    private FragmentStockGraphBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentStockGraphBinding.inflate(inflater, container, false);

        GraphView graphStock = binding.graphStock;

        binding.buttonOneDay.setOnClickListener(this);
        binding.buttonOneWeek.setOnClickListener(this);
        binding.buttonOneMonth.setOnClickListener(this);
        binding.buttonThreeMoth.setOnClickListener(this);
        binding.buttonSixMonth.setOnClickListener(this);
        binding.buttonOneYear.setOnClickListener(this);

        graphStock.setTitle("TEST");

        WebInterface webInterface = RetrofitClient.getInstance().getWebInterface();

        String[] words = getArguments().getString(StockActivity.EXTRA_SEARCH_TICKER).split(" ");
        String ticker = words[words.length - 1];
        String days = getArguments().getString(StockActivity.EXTRA_DAYS, "30");
        Call<StockGraph> call = webInterface.getStockGraph(ticker, days);
        call.enqueue(new Callback<StockGraph>() {
            @Override
            public void onResponse(Call<StockGraph> call, Response<StockGraph> response) {
                if(response.isSuccessful()){

                    StockGraph stocks = response.body();
                    DataPoint[] dataPoints = new DataPoint[stocks.getDatePriceMap().keySet().size()];

                    int count = 0;
                    for(Date s : stocks.getDatePriceMap().keySet()){
                        dataPoints[count] = new DataPoint((double)s.getTime(), stocks.getDatePriceMap().get(s));
                        //dataPoints[count] = new DataPoint(count, stocks.getDatePriceMap().get(s));
                        count += 1;
                        Log.d("CSC", String.valueOf(s));
                        Log.d("CSC", String.valueOf(stocks.getDatePriceMap().get(s)));
                    }
                    Log.d("CSC", "Success");

                    LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);
                    series.setDrawDataPoints(true);


                    graphStock.getViewport().setMinY(stocks.getMin());
                    graphStock.getViewport().setMaxY(stocks.getMax());
                    graphStock.getViewport().setMaxX(Instant.now().toEpochMilli());
                    graphStock.getViewport().setMinX(Instant.now().minus(Duration.ofDays(Integer.parseInt(days))).toEpochMilli());
                    graphStock.getViewport().setYAxisBoundsManual(true);
                    graphStock.getViewport().setXAxisBoundsManual(true);

                   graphStock.getGridLabelRenderer().setNumHorizontalLabels(4);


                    graphStock.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
                        @Override
                        public String formatLabel(double value, boolean isValueX) {
                            if(isValueX){
                                Log.d("CSC", "CHECK");
                                Log.d("CSC", String.valueOf((long)value));
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd");
                                return simpleDateFormat.format(new Date((long)value));

                            }
                            else{
                                return super.formatLabel(value, isValueX);
                            }
                        }
                    });

                    series.setOnDataPointTapListener(new OnDataPointTapListener() {
                        @Override
                        public void onTap(Series series, DataPointInterface dataPoint) {
                            Log.d("SAS", String.valueOf(dataPoint.getY()));
                        }
                    });

                    graphStock.addSeries(series);


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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dataPasser = (OnDatePass) context;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_one_day:{
                Log.d("CSC", "HERERERERER");
                dataPasser.OnDatePass("1", getArguments().getString(StockActivity.EXTRA_SEARCH_TICKER));
                break;
            }
            case R.id.button_one_week:{
                dataPasser.OnDatePass("7", getArguments().getString(StockActivity.EXTRA_SEARCH_TICKER));
                break;
            }
            case R.id.button_one_month:{
                dataPasser.OnDatePass("30", getArguments().getString(StockActivity.EXTRA_SEARCH_TICKER));
                break;
            }
            case R.id.button_three_moth:{
                dataPasser.OnDatePass("90", getArguments().getString(StockActivity.EXTRA_SEARCH_TICKER));
                break;
            }
            case R.id.button_six_month:{
                dataPasser.OnDatePass("180", getArguments().getString(StockActivity.EXTRA_SEARCH_TICKER));
                break;
            }
            case R.id.button_one_year:{
                dataPasser.OnDatePass("365", getArguments().getString(StockActivity.EXTRA_SEARCH_TICKER));
                break;
            }
        }
    }

}