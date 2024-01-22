package com.example.weather_mvvm_new.model.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.weather_mvvm_new.model.MyModel;

import java.util.List;

@Dao
public interface WeatherDao {
//    додати елемент в таблицю
    @Insert(onConflict = OnConflictStrategy.REPLACE)
public void addInfoWeather(MyModel myModel);
//    отримати весь список
    @Query("SELECT * FROM weather")
    LiveData<List<MyModel>>getInfoAllWeather();
//    List<MyModel>getInfoAllWeather();










//    видалити
    @Delete
    void delete(MyModel myModel);


}
