package com.sethfagen.sethstradingapplication.remote_database.models;

import com.google.gson.annotations.SerializedName;

public class StockNews {
    @SerializedName("source")
    private String source;
    @SerializedName("url")
    private String url;
    @SerializedName("datetime")
    private long date;
    @SerializedName("headline")
    private String headline;

    public StockNews(String source, String url, long date, String headline) {
        this.source = source;
        this.url = url;
        this.date = date;
        this.headline = headline;
    }

    public String getSource() {
        return source;
    }

    public String getUrl() {
        return url;
    }

    public long getDate() {
        return date;
    }

    public String getHeadline() {
        return headline;
    }
}
