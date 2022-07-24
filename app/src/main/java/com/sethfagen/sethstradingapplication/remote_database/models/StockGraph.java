package com.sethfagen.sethstradingapplication.remote_database.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.Map;

public class StockGraph {
    @SerializedName("min")
    private float min;
    @SerializedName("max")
    private float max;
    @SerializedName("new_graph")
    private Map<Date, Float> datePriceMap;

    public StockGraph(float min, float max, Map<Date, Float> datePriceMap) {
        this.min = min;
        this.max = max;
        this.datePriceMap = datePriceMap;
    }

    public float getMin() {
        return min;
    }

    public float getMax() {
        return max;
    }

    public Map<Date, Float> getDatePriceMap() {
        return datePriceMap;
    }
}
