package com.example.weather_mvvm_new.di;

import androidx.lifecycle.LiveData;

import com.example.weather_mvvm_new.model.App;
import com.example.weather_mvvm_new.model.MyModel;
import com.example.weather_mvvm_new.model.room.WeatherDao;
import com.example.weather_mvvm_new.repositories.MyRepo;

import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class MyRepoModule {
    @Provides
    public WeatherDao provideWeatherDao(){
        return App.getDatabase().getWeatherDao();
    }
    @Provides
    public LiveData<List<MyModel>> provideAllWeather(WeatherDao weatherDao){
        return weatherDao.getInfoAllWeather();
    }
    @Provides
    public MyRepo provideMyRepo(WeatherDao weatherDao, LiveData<List<MyModel>>allWeather){
        return new MyRepo(weatherDao,allWeather);
    }
}
