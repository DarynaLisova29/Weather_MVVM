package com.example.weather_mvvm_new.view_model;

import com.example.weather_mvvm_new.MainActivity;

import dagger.Component;

@Component
public interface MyViewModelComponent {
    MyViewModel getMyViewModel();
    void inject(MainActivity mainActivity);
}
