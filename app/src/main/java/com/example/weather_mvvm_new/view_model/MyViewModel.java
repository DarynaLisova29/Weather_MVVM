package com.example.weather_mvvm_new.view_model;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weather_mvvm_new.model.App;
import com.example.weather_mvvm_new.model.MyModel;
import com.example.weather_mvvm_new.model.recycler_view.MyAdapter;
import com.example.weather_mvvm_new.repositories.MyRepo;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class MyViewModel extends ViewModel {

    public MyViewModel() {
        App.appComponent.inject(this);
    }
    private MutableLiveData<MyModel>model=new MutableLiveData<>();
    public MutableLiveData<Boolean>errorHandler=new MutableLiveData<>();
    @Inject
    public MyRepo myRepo;

    public void insert(MyModel model){myRepo.insert(model);}

    public MutableLiveData<MyModel> getModel() {
        return model;
    }

    public void setModel(MyModel item) {
        model.setValue(item);
    }

    public MutableLiveData<Boolean> getErrorHandler() {
        return errorHandler;
    }

    public void setErrorHandler(Boolean errorHandler) {
        this.errorHandler.setValue(errorHandler);
    }

    public void getWeather(String city, String metric){
        myRepo.getWeather(city,metric).subscribe(new Observer<MyModel>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull MyModel myModel) {
                String date;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    LocalDateTime ldt=LocalDateTime.now(ZoneId.of("Europe/Kyiv"));
                    date=ldt.format(DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy"));
                    myModel.setLdt(date);
                }

                setModel(myModel);

            }

            @Override
            public void onError(@NonNull Throwable e) {
                errorHandler.setValue(true);
            }

            @Override
            public void onComplete() {

            }
        });
    }
    public LiveData<List<MyModel>> getAllWeatherLifeData() {
        return myRepo.getAllWeather();
    }

}
