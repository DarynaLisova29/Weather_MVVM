package com.example.weather_mvvm_new.model;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkConnectionInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);

        int responseCode = response.code();
        if (responseCode >= 200 && responseCode < 300) {
            Log.d("My Tag","Internet is good");
        } else {
            Log.d("My Tag","Internet is BAD");
            try {
                throw new NoConnectivityException();
            } catch (NoConnectivityException e) {
                throw new RuntimeException(e);
            }
        }

        return response;
    }
}
