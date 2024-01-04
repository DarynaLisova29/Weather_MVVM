package com.example.weather_mvvm_new.model.room;

import androidx.room.TypeConverter;

import com.example.weather_mvvm_new.model.item.Weather;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class WeatherConvectors {
    @TypeConverter
    public static ArrayList<Weather> fromStringToWeatherList(String value) {
        Type listType = new TypeToken<ArrayList<Weather>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromWeatherList(ArrayList<Weather> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}
