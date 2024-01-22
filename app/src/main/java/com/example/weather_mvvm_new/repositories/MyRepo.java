package com.example.weather_mvvm_new.repositories;

import android.graphics.ColorSpace;

import androidx.lifecycle.LiveData;

import com.example.weather_mvvm_new.model.App;
import com.example.weather_mvvm_new.model.MyModel;
import com.example.weather_mvvm_new.model.room.WeatherDao;
import com.example.weather_mvvm_new.model.room.WeatherDatabase;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MyRepo {
    //Retrofit and room
    private WeatherDao weatherDao;
    private LiveData<List<MyModel>> allWeather;

    public MyRepo() {
        weatherDao=App.getDatabase().getWeatherDao();
        allWeather=weatherDao.getInfoAllWeather();
    }

    public Observable<MyModel>getWeather(String city, String metric){

        return App.getMyApi().getWeather(city,App.apiKey,metric).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread());

    }

    public LiveData<List<MyModel>> getAllWeather() {
        return allWeather;
    }
    public void insert(MyModel model){
        WeatherDatabase.databaseWriteExecutor.execute(() -> {
            weatherDao.addInfoWeather(model);
        });
    }
}
