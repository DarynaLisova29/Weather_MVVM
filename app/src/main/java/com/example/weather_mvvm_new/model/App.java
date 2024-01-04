package com.example.weather_mvvm_new.model;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

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
    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

    //    logging.setLevel(HttpLoggingInterceptor.Level.BODY);
    private String url="https://api.openweathermap.org/data/2.5/";
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
    }
    public static MyApi getMyApi(){
        return myApi;
    }

}
