package com.example.weather_mvvm_new.model;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.weather_mvvm_new.model.room.WeatherDatabase;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {
    public static final String apiKey = "fe3dea7f68cfa24794c7c5329fb08b55";//API Key
    private static MyApi myApi;
    private Retrofit retrofit;
    ConnectivityManager connectivityManager;
    public static NetworkInfo networkInfo;
    private static WeatherDatabase weatherDatabase;
    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

    //    logging.setLevel(HttpLoggingInterceptor.Level.BODY);
    private String url="https://api.openweathermap.org/data/2.5/";

    //       визначаємо базу даних
    RoomDatabase.Callback myCallBack =new RoomDatabase.Callback() {
        @Override
        public void onCreate(@androidx.annotation.NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }

        @Override
        public void onDestructiveMigration(@androidx.annotation.NonNull SupportSQLiteDatabase db) {
            super.onDestructiveMigration(db);
        }

        @Override
        public void onOpen(@androidx.annotation.NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        connectivityManager=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo=connectivityManager.getActiveNetworkInfo();

        httpClient.addInterceptor(new NetworkConnectionInterceptor());
        retrofit=new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        myApi=retrofit.create(MyApi.class);
        weatherDatabase=Room.databaseBuilder(getApplicationContext(),
                WeatherDatabase.class,"WeatherDB").fallbackToDestructiveMigration().build();
    }
    public static MyApi getMyApi(){
        return myApi;
    }
    public static WeatherDatabase getDatabase(){
        return weatherDatabase;
    }

}
