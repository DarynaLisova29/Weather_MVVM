package com.example.weather_mvvm_new.di;

import com.example.weather_mvvm_new.view_model.MyViewModel;

import dagger.Component;

@Component(modules = MyRepoModule.class)
public interface AppComponent {
    void inject(MyViewModel myViewModel);
}
