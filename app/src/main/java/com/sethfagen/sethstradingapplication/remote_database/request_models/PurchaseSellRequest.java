package com.sethfagen.sethstradingapplication.remote_database.request_models;

public class PurchaseSellRequest {
    private String stock;
    private int quantity;
    private int user_id;

    public PurchaseSellRequest(String stock, int quantity, int user_id) {
        this.stock = stock;
        this.quantity = quantity;
        this.user_id = user_id;
    }

    public String getStock() {
        return stock;
    }

    public int getQuantity() {
        return quantity;
    }
}
