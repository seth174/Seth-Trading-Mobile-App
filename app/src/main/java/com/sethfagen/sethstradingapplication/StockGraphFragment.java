package com.sethfagen.sethstradingapplication;

import android.os.Bundle;

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

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockGraphFragment extends Fragment {

    public interface OnDataPass{
        public void onDataPass(int days);

    }

    private FragmentStockGraphBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentStockGraphBinding.inflate(inflater, container, false);

        GraphView graphStock = binding.graphStock;

        graphStock.setTitle("TEST");

        WebInterface webInterface = RetrofitClient.getInstance().getWebInterface();

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
                    final java.text.DateFormat dateTimeFormatter = DateFormat.getDateFormat(getActivity());
                    double min = dateTimeFormatter.getCalendar().getTime().getTime();
                    for(Date s : stocks.getDatePriceMap().keySet()){
                        min = min > s.getTime() ? s.getTime() : min;
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
                    graphStock.getViewport().setMinX(Instant.now().minus(Duration.ofDays(30)).toEpochMilli());
//
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
}