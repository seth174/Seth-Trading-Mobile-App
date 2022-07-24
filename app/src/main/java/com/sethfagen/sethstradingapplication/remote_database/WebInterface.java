package com.sethfagen.sethstradingapplication.remote_database;

import com.sethfagen.sethstradingapplication.remote_database.models.Stock;
import com.sethfagen.sethstradingapplication.remote_database.models.StockGraph;
import com.sethfagen.sethstradingapplication.remote_database.models.StockOverviewInfo;
import com.sethfagen.sethstradingapplication.remote_database.models.User;
import com.sethfagen.sethstradingapplication.remote_database.request_models.PurchaseSellRequest;
import com.sethfagen.sethstradingapplication.remote_database.request_models.StockGraphRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface WebInterface {
    @GET("users/show/")
    Call<User> getUser(@Query("email") String email,
                       @Query("password")String password);

    @GET("users")
    Call<List<User>> getUsers();

    @GET("users/get_stocks_owned_info")
    Call<List<StockOverviewInfo>> getStockInfo(@Query("user_id") int user_id);

    @GET("stocks/index")
    Call<List<Stock>> getStocks();

    @GET("stocks/show")
    Call<Stock> getStock(@Query("ticker") String ticker);

    @GET("stocks/stock_graph")
    Call<StockGraph> getStockGraph(@Query("ticker") String ticker,
                                   @Query("days")String days);

    @POST("stocks_purchased_per_people/create")
    Call<Float> buyStock(@Body PurchaseSellRequest request);

    @POST("stocks_sold_per_people/create")
    Call<Float> sellStock(@Body PurchaseSellRequest request);

}
