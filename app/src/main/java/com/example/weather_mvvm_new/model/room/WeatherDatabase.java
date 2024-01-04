package com.example.weather_mvvm_new.model.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.weather_mvvm_new.model.MyModel;

@Database(entities = {MyModel.class},version = 1)
@TypeConverters({MainConvector.class, WeatherConvectors.class})
public abstract class WeatherDatabase extends RoomDatabase {
    public abstract WeatherDao getWeatherDao();
}


//@Database(entities = {Wheather.class},version = 1)
//public abstract class WheatheDatabase extends RoomDatabase {
//    public abstract WheatherDAO getWheatherDAO();
//}
