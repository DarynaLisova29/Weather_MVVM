package com.example.weather_mvvm_new.repositories;

import androidx.lifecycle.LiveData;

import com.example.weather_mvvm_new.model.App;
import com.example.weather_mvvm_new.model.MyModel;
import com.example.weather_mvvm_new.model.room.WeatherDao;

import java.util.List;

import dagger.Module;
import dagger.Provides;

@Module
public class MyRepoModule {
    @Provides
    public WeatherDao weatherDaoProvides(){
        return App.getDatabase().getWeatherDao();
    }
    @Provides
    public LiveData<List<MyModel>> allWeatherProvides(WeatherDao weatherDao){
        return weatherDao.getInfoAllWeather();
    }
    @Provides
    public MyRepo meRepoProvides(WeatherDao weatherDao, LiveData<List<MyModel>>allWeather){
        return new MyRepo(weatherDao,allWeather);
    }
}
