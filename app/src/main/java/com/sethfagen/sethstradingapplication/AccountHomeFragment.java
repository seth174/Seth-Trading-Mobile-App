package com.sethfagen.sethstradingapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sethfagen.sethstradingapplication.adapters.StockListViewCustomAdapter;
import com.sethfagen.sethstradingapplication.databinding.FragmentAccountHomeBinding;
import com.sethfagen.sethstradingapplication.remote_database.RetrofitClient;
import com.sethfagen.sethstradingapplication.remote_database.WebInterface;
import com.sethfagen.sethstradingapplication.remote_database.models.StockOverviewInfo;
import com.sethfagen.sethstradingapplication.remote_database.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountHomeFragment extends Fragment {

    private FragmentAccountHomeBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAccountHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeData(User.getUser());
    }

    public void initializeData(User u){
        setListView(u);
        setTextViews(u);
    }

    public void setListView(User u){
        WebInterface webInterface = RetrofitClient.getInstance().getWebInterface();
        Call<List<StockOverviewInfo>> call = webInterface.getStockInfo(u.getId());
        call.enqueue(new Callback<List<StockOverviewInfo>>() {
            @Override
            public void onResponse(Call<List<StockOverviewInfo>> call, Response<List<StockOverviewInfo>> response) {
                if(response.isSuccessful()){
                    List<StockOverviewInfo> listInfo = response.body();
                    StockListViewCustomAdapter adapter = new StockListViewCustomAdapter(getActivity(), listInfo);
                    binding.listviewStocks.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<StockOverviewInfo>> call, Throwable t) {

            }
        });
    }

    public void setTextViews(User u){
        binding.textviewName.setText(u.getName());
    }
}
