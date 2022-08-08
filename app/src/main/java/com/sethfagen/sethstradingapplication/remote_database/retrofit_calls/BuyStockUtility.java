package com.sethfagen.sethstradingapplication.remote_database.retrofit_calls;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.sethfagen.sethstradingapplication.AccountHomeFragment;
import com.sethfagen.sethstradingapplication.HomePageActivity;
import com.sethfagen.sethstradingapplication.R;
import com.sethfagen.sethstradingapplication.remote_database.RetrofitClient;
import com.sethfagen.sethstradingapplication.remote_database.request_models.PurchaseSellRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuyStockUtility{
    private PurchaseSellRequest request;
    private Activity activity;

    private DialogInterface.OnClickListener button_return_home_clickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
//            ((FragmentActivity)activity).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AccountHomeFragment()).commit();
        }
    };

    public BuyStockUtility(String ticker, int quantity, int userId, Activity activity){
        this.activity = activity;
        request = new PurchaseSellRequest(ticker, quantity, userId);
    }

    public void buyStock(){
        Call<Float> call = RetrofitClient.getInstance().getWebInterface().buyStock(request);

        call.enqueue(new Callback<Float>() {
            @Override
            public void onResponse(Call<Float> call, Response<Float> response) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                if(response.isSuccessful()){
                    builder.setTitle("Stock Purchased")
                            .setMessage(String.valueOf(request.getQuantity()) + " stock of " +
                            request.getStock() + " was purchased at " + String.valueOf(response.body()))
                            .setPositiveButton("OK", button_return_home_clickListener);
                    builder.show();

                }
                else{
                    builder.setTitle("Error Something went wrong")
                            .setMessage("Either insufficient funds or wrong Ticker")
                            .setNeutralButton("Return Home", button_return_home_clickListener)
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
