package com.example.weather_mvvm_new.view_model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weather_mvvm_new.model.MyModel;

public class MyViewModel extends ViewModel {
private MutableLiveData<MyModel>model=new MutableLiveData<>();

    public MutableLiveData<MyModel> getModel() {
        return model;
    }

    public void setModel(MyModel item) {
        model.setValue(item);
    }

}
