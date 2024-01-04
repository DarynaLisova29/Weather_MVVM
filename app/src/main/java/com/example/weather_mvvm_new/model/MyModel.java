package com.example.weather_mvvm_new.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.weather_mvvm_new.model.item.Main;
import com.example.weather_mvvm_new.model.item.Weather;
import com.example.weather_mvvm_new.model.room.MainConvector;
import com.example.weather_mvvm_new.model.room.WeatherConvectors;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
@Entity(tableName = "weather")
@TypeConverters({WeatherConvectors.class, MainConvector.class})
public class MyModel {

//    @ColumnInfo(name="id_city")
    @PrimaryKey(autoGenerate = true)
    private int id;//айді щоб розуміти кількість записів
    @ColumnInfo(name="city")
    @SerializedName("name")
    @NonNull
    public String city;
    @ColumnInfo(name="condition")
    @TypeConverters(WeatherConvectors.class)
    @SerializedName("weather")
    @Expose
    public ArrayList<Weather> weather;//отримаємо стан

    @ColumnInfo(name="tempAndPress")
    @SerializedName("main")
    @TypeConverters(MainConvector.class)
    @Expose
    public Main main;//отримаємо темп й тиск
    @SerializedName("cod")
    @Expose
    public int cod;//код помилки дляч того щоб розуміти чи все ок, якщо 200-все нормально,
    // якщо 404-то місто ненайдено
    @ColumnInfo(name="dateTime")
    private String ldt;

    public MyModel(String city, ArrayList<Weather> weather, Main main, int cod, String ldt) {
        this.city = city;
        this.weather = weather;
        this.main = main;
        this.cod = cod;
        this.ldt = ldt;
    }

    public ArrayList<Weather> getWeather() {
        return weather;
    }

    public void setWeather(ArrayList<Weather> weather) {
        this.weather = weather;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLdt() {
        return ldt;
    }

    public void setLdt(String ldt) {
        this.ldt = ldt;
    }

}
