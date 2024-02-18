package com.example.weather_mvvm_new.repositories;

import com.example.weather_mvvm_new.view_model.MyViewModel;

import dagger.Component;

@Component(modules= MyRepoModule.class)
public interface MyRepoComponent {
    void inject(MyViewModel myViewModel);
}
