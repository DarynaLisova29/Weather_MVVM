package com.example.weather_mvvm_new.model;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MyApi {
    @GET("weather")
    Observable<MyModel> getWeather(@Query("q") String city, @Query("appid") String apiKey, @Query("units") String units);

}
