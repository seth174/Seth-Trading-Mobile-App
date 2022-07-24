package com.sethfagen.sethstradingapplication.remote_database.request_models;

public class StockGraphRequest {
    private String ticker;
    private String days;

    public StockGraphRequest(String ticker, String days) {
        this.ticker = ticker;
        this.days = days;
    }
}
