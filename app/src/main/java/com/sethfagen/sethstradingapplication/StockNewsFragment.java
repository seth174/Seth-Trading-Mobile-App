package com.sethfagen.sethstradingapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sethfagen.sethstradingapplication.adapters.StockNewsListViewCustomAdapter;
import com.sethfagen.sethstradingapplication.databinding.FragmentStockNewsBinding;
import com.sethfagen.sethstradingapplication.remote_database.RetrofitClient;
import com.sethfagen.sethstradingapplication.remote_database.WebInterface;
import com.sethfagen.sethstradingapplication.remote_database.models.StockNews;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockNewsFragment extends Fragment {
    FragmentStockNewsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentStockNewsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        WebInterface webInterface = RetrofitClient.getInstance().getWebInterface();

        String[] search = getArguments().getString(StockActivity.EXTRA_SEARCH_TICKER).split(" ");
        String ticker = search[search.length - 1];
        Call<List<StockNews>> call = webInterface.getCompanyNews(ticker);

        call.enqueue(new Callback<List<StockNews>>() {
            @Override
            public void onResponse(Call<List<StockNews>> call, Response<List<StockNews>> response) {
                if(response.isSuccessful()){
                    Log.d("CSC", "STOCK NEWS SUCCESS");

                    List<StockNews> list = response.body();
                    StockNewsListViewCustomAdapter adapter = new StockNewsListViewCustomAdapter(getActivity(), list);
                    binding.listviewStocknews.setAdapter(adapter);
                }
                else{
                    Log.d("CSC", "STOCK NEWS FAIL");
                }
            }

            @Override
            public void onFailure(Call<List<StockNews>> call, Throwable t) {
                Log.d("CSC", "STOCK NEWS FAIL 2");
            }
        });
    }
}