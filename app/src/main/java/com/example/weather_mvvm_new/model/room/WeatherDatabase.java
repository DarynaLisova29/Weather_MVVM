package com.example.weather_mvvm_new.model.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.weather_mvvm_new.model.MyModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {MyModel.class},version = 1)
@TypeConverters({MainConvector.class, WeatherConvectors.class})
public abstract class WeatherDatabase extends RoomDatabase {
//    визначаємо скільки будемо мати потоків
    private static final int NUMBER_OF_THREADS = 4;
     public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public abstract WeatherDao getWeatherDao();
}


//@Database(entities = {Wheather.class},version = 1)
//public abstract class WheatheDatabase extends RoomDatabase {
//    public abstract WheatherDAO getWheatherDAO();
//}
