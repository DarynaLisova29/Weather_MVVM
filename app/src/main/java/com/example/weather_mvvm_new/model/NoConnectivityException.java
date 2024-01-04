package com.example.weather_mvvm_new.model;

public class NoConnectivityException extends Throwable{
    @Override
    public String getMessage() {
        return "No Internet Connection";
        // You can send any message whatever you want from here.
    }
}
