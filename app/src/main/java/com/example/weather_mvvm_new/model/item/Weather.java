package com.example.weather_mvvm_new.model.item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Weather {
    @SerializedName("main")
    @Expose
    public String main;//стан погоди

    public Weather(String main) {
        this.main = main;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }
}
