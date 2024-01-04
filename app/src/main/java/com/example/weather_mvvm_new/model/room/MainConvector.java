package com.example.weather_mvvm_new.model.room;

import androidx.room.TypeConverter;

import com.example.weather_mvvm_new.model.item.Main;
import com.google.gson.Gson;

public class MainConvector {
    @TypeConverter
    public static Main fromStringToMain(String value) {
        return new Gson().fromJson(value, Main.class);
    }

    @TypeConverter
    public static String fromMain(Main main) {
        Gson gson = new Gson();
        return gson.toJson(main);
    }
}
