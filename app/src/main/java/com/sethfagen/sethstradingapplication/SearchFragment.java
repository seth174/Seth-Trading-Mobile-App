package com.sethfagen.sethstradingapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.sethfagen.sethstradingapplication.databinding.FragmentSearchBinding;
import com.sethfagen.sethstradingapplication.remote_database.RetrofitClient;
import com.sethfagen.sethstradingapplication.remote_database.WebInterface;
import com.sethfagen.sethstradingapplication.remote_database.models.Stock;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {
    private FragmentSearchBinding binding;

    public static final String TICKER_EXTRA = "com.sethfagen.sethstradingapplication.TICKER_EXTRA";

    private AdapterView.OnItemClickListener listview_stock_itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            String ticker = (String)adapterView.getItemAtPosition(i);
            Log.d("CSC", ticker);
            Intent intent = new Intent(getActivity(), StockActivity.class);
            intent.putExtra(TICKER_EXTRA, ticker);
            startActivity(intent);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding =  FragmentSearchBinding.inflate(inflater, container, false);

        binding.editTextTextPersonName.requestFocus();

        binding.listviewSearchStocks.setOnItemClickListener(listview_stock_itemClickListener);

        binding.buttonBack.setOnClickListener((View v) -> {
            getActivity().getSupportFragmentManager().popBackStackImmediate();
              //int frag = getArguments().getInt(HomePageActivity.BUNDLE_LAST_FRAGMENT);
//            int frag = getArguments().getInt(HomePageActivity.BUNDLE_LAST_FRAGMENT);
//            Log.d("CSC", String.valueOf(frag));
//            Fragment fragment = FragmentName.getFragment(frag);
//            assert fragment != null;
        });


        WebInterface webInterface = RetrofitClient.getInstance().getWebInterface();

        Call<List<Stock>> call = webInterface.getStocks();

        call.enqueue(new Callback<List<Stock>>() {
            @Override
            public void onResponse(Call<List<Stock>> call, Response<List<Stock>> response) {
                if (response.isSuccessful()){
                    List<String> stocks = new ArrayList<>();
                    assert response.body() != null;
                    for(Stock s : response.body()){
                        stocks.add(s.getName() + " " + s.getTicker());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, stocks);

                    binding.editTextTextPersonName.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            adapter.getFilter().filter(charSequence);
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });

                    binding.listviewSearchStocks.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(Call<List<Stock>> call, Throwable t) {

            }
        });


        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        menu.close();
//        inflater.inflate(R.menu.search_menu, menu);
//        MenuItem item = menu.findItem(R.id.action_search);
//
//
//        SearchView searchView = (SearchView) item.getActionView();
//        searchView.clearFocus();
//        searchView.setIconified(false);
//        searchView.setFocusable(true);
//
//        searchView.requestFocusFromTouch();
//        searchView.requestFocus(View.FOCUS_UP);
//
//        WebInterface webInterface = RetrofitClient.getInstance().getWebInterface();
//
//        Call<List<Stock>> call = webInterface.getStocks();
//
//        call.enqueue(new Callback<List<Stock>>() {
//            @Override
//            public void onResponse(Call<List<Stock>> call, Response<List<Stock>> response) {
//                if (response.isSuccessful()){
//                    List<String> stocks = new ArrayList<>();
//                    for(Stock s : response.body()){
//                        stocks.add(s.getName() + " " + s.getTicker());
//                    }
//                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, stocks);
//                    binding.listviewSearchStocks.setAdapter(adapter);
//
//                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//                        @Override
//                        public boolean onQueryTextSubmit(String s) {
//
//                            return false;
//                        }
//
//                        @Override
//                        public boolean onQueryTextChange(String s) {
//                            adapter.getFilter().filter(s);
//                            return false;
//                        }
//                    });
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Stock>> call, Throwable t) {
//
//            }
//        });


//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                return false;
//            }
//        }); // Fragment implements Sear
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }
}