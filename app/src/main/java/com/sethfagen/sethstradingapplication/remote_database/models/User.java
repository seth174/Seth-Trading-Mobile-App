package com.sethfagen.sethstradingapplication.remote_database.models;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("password_digest")
    private String password_digest;
    @SerializedName("admin")
    private boolean admin;
    private static User user;
    public User(int id, String name, String email, String password_digest, boolean admin) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password_digest = password_digest;
        this.admin = admin;
    }

    public User(String name, String email, String password_digest, boolean admin) {
        this.name = name;
        this.email = email;
        this.password_digest = password_digest;
        this.admin = admin;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword_digest() {
        return password_digest;
    }

    public boolean isAdmin() {
        return admin;
    }

    public static User getUser(){
        return user;
    }

    public static void setUser(User u){
        user = u;
    }
}
