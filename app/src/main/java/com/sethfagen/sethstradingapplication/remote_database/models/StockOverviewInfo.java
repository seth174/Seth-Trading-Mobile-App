package com.sethfagen.sethstradingapplication.remote_database.models;

import com.google.gson.annotations.SerializedName;
//Not an actual table in my database just comprised of a bunch of different tables thats easier to compute on the website
public class StockOverviewInfo {
    @SerializedName("ticker")
    private String ticker;
    @SerializedName("price_per_share")
    private float pricePerShare;
    @SerializedName("price")
    private float price;
    @SerializedName("quantity")
    private int quantity;
    @SerializedName("current_price")
    private float currentPrice;
    @SerializedName("percent_change")
    private float percentChange;
    @SerializedName("net_gain")
    private float netGain;
    @SerializedName("daily_percent_change")
    private float dailyPercentChange;
    @SerializedName("daily_net_change")
    private float dailyNetChange;

    public StockOverviewInfo(String ticker, float pricePerShare, float price, int quantity, float currentPrice, float percentChange, float netGain, float dailyPercentChange, float dailyNetChange) {
        this.ticker = ticker;
        this.pricePerShare = pricePerShare;
        this.price = price;
        this.quantity = quantity;
        this.currentPrice = currentPrice;
        this.percentChange = percentChange;
        this.netGain = netGain;
        this.dailyPercentChange = dailyPercentChange;
        this.dailyNetChange = dailyNetChange;
    }

    public String getTicker() {
        return ticker;
    }

    public float getPricePerShare() {
        return pricePerShare;
    }

    public float getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getCurrentPrice() {
        return currentPrice;
    }

    public float getPercentChange() {
        return percentChange;
    }

    public float getNetGain() {
        return netGain;
    }

    public float getDailyPercentChange() {
        return dailyPercentChange;
    }

    public float getDailyNetChange() {
        return dailyNetChange;
    }

    @Override
    public String toString() {
        return "StockOverviewInfo{" +
                "ticker='" + ticker + '\'' +
                ", pricePerShare=" + pricePerShare +
                ", price=" + price +
                ", quantity=" + quantity +
                ", currentPrice=" + currentPrice +
                ", percentChange=" + percentChange +
                ", netGain=" + netGain +
                ", dailyPercentChange=" + dailyPercentChange +
                ", dailyNetChange=" + dailyNetChange +
                '}';
    }
}
