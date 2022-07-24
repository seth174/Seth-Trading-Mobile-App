package com.sethfagen.sethstradingapplication.remote_database.models;

import com.google.gson.annotations.SerializedName;

public class Stock {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("ticker")
    private String ticker;
    @SerializedName("most_recent_price")
    private float mostRecentPrice;
    @SerializedName("day_change")
    private float dayChange;
    @SerializedName("day_percent_change")
    private float dayPercentChange;
    @SerializedName("day_high_price")
    private float dayHighPrice;
    @SerializedName("day_low_price")
    private float dayLowPrice;
    @SerializedName("day_open_price")
    private float dayOpenPrice;
    @SerializedName("day_previous_close_price")
    private float dayPreviousClosePrice;

    @Override
    public String toString() {
        return "Stock{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ticker='" + ticker + '\'' +
                ", mostRecentPrice=" + mostRecentPrice +
                ", dayChange=" + dayChange +
                ", dayPercentChange=" + dayPercentChange +
                ", dayHighPrice=" + dayHighPrice +
                ", dayLowPrice=" + dayLowPrice +
                ", dayOpenPrice=" + dayOpenPrice +
                ", dayPreviousClosePrice=" + dayPreviousClosePrice +
                '}';
    }

    public Stock(int id, String name, String ticker, float mostRecentPrice, float dayChange, float dayPercentChange, float dayHighPrice, float dayLowPrice, float dayOpenPrice, float dayPreviousClosePrice) {
        this.id = id;
        this.name = name;
        this.ticker = ticker;
        this.mostRecentPrice = mostRecentPrice;
        this.dayChange = dayChange;
        this.dayPercentChange = dayPercentChange;
        this.dayHighPrice = dayHighPrice;
        this.dayLowPrice = dayLowPrice;
        this.dayOpenPrice = dayOpenPrice;
        this.dayPreviousClosePrice = dayPreviousClosePrice;
    }

    public Stock(String name, String ticker, float mostRecentPrice, float dayChange, float dayPercentChange, float dayHighPrice, float dayLowPrice, float dayOpenPrice, float dayPreviousClosePrice) {
        this.name = name;
        this.ticker = ticker;
        this.mostRecentPrice = mostRecentPrice;
        this.dayChange = dayChange;
        this.dayPercentChange = dayPercentChange;
        this.dayHighPrice = dayHighPrice;
        this.dayLowPrice = dayLowPrice;
        this.dayOpenPrice = dayOpenPrice;
        this.dayPreviousClosePrice = dayPreviousClosePrice;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTicker() {
        return ticker;
    }

    public float getMostRecentPrice() {
        return mostRecentPrice;
    }

    public float getDayChange() {
        return dayChange;
    }

    public float getDayPercentChange() {
        return dayPercentChange;
    }

    public float getDayHighPrice() {
        return dayHighPrice;
    }

    public float getDayLowPrice() {
        return dayLowPrice;
    }

    public float getDayOpenPrice() {
        return dayOpenPrice;
    }

    public float getDayPreviousClosePrice() {
        return dayPreviousClosePrice;
    }
}
