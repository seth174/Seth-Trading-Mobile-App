package com.sethfagen.sethstradingapplication.remote_database.retrofit_calls;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.sethfagen.sethstradingapplication.AccountHomeFragment;
import com.sethfagen.sethstradingapplication.R;
import com.sethfagen.sethstradingapplication.remote_database.RetrofitClient;
import com.sethfagen.sethstradingapplication.remote_database.request_models.PurchaseSellRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SellStockUtility {
    private PurchaseSellRequest request;
    private Activity activity;

    private DialogInterface.OnClickListener button_return_home_clickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            ((FragmentActivity)activity).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AccountHomeFragment()).commit();
        }
    };

    public SellStockUtility(String ticker, int quantity, int userId, Activity activity) {
        this.request = new PurchaseSellRequest(ticker, quantity, userId);
        this.activity = activity;
    }

    public void sellStock(){
        Call<Float> call = RetrofitClient.getInstance().getWebInterface().sellStock(request);

        call.enqueue(new Callback<Float>() {
            @Override
            public void onResponse(Call<Float> call, Response<Float> response) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                if(response.isSuccessful()){
                    builder.setTitle("Stock Sold")
                            .setMessage(String.valueOf(request.getQuantity()) + " stock of " +
                                    request.getStock() + " was sold at " + String.valueOf(response.body()))
                            .setPositiveButton("OK", null);
                    builder.show();

                }
                else{
                    builder.setTitle("Error Something went wrong")
                            .setMessage("Either not enough stocks owned or wrong Ticker")
                            .setNeutralButton("Return Home", null)
                            .setPositiveButton("Stay on Page", null);
                    builder.show();
                }
            }

            @Override
            public void onFailure(Call<Float> call, Throwable t) {
                Log.d("CSC", "FAILED BUY STOCK UTILITY");
            }
        });
    }
}
