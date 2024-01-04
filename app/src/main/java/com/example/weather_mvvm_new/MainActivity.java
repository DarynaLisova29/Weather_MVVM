package com.example.weather_mvvm_new;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weather_mvvm_new.model.App;
import com.example.weather_mvvm_new.model.MyModel;
import com.example.weather_mvvm_new.model.NoConnectivityException;
import com.example.weather_mvvm_new.model.recycler_view.MyAdapter;
import com.example.weather_mvvm_new.model.room.WeatherDatabase;
import com.example.weather_mvvm_new.view_model.MyViewModel;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    TextView temp, cond,press;
    Button getWeather;
    EditText inputCity;
    String city, date;
    MyViewModel myViewModel;
//    for database Room
    WeatherDatabase weatherDatabase;
    List<MyModel>list=new ArrayList<>();
//    for RecyclerView
    RecyclerView recyclerView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);
        //познаходили поля й присвоєли по айді значення
        temp=findViewById(R.id.temp_value);
        cond=findViewById(R.id.cond_value);
        press=findViewById(R.id.press_value);
        getWeather=findViewById(R.id.getWeather);
        inputCity=findViewById(R.id.inputCity);
        myViewModel=new ViewModelProvider(this).get(MyViewModel.class);
//       визначаємо recyclerView
        recyclerView=findViewById(R.id.recycler);

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

        weatherDatabase= Room.databaseBuilder(getApplicationContext(),
                WeatherDatabase.class,"WeatherDB").addCallback(myCallBack).build();

        //вішаємо на кнопку подію
        getWeather.setOnClickListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        getWeatherListInBackGround();
        myViewModel.getModel().observe(this, new Observer<MyModel>() {
            @Override
            public void onChanged(MyModel myModel) {
                cond.setText(myModel.getWeather().get(0).getMain());
                temp.setText(String.valueOf(myModel.getMain().getTemp()));
                press.setText(String.valueOf(myModel.getMain().getPressure()));
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.getWeather) {
            city = inputCity.getText().toString();
            Observable<MyModel>observable= App.getMyApi().getWeather(city,App.apiKey,"metric");
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new io.reactivex.rxjava3.core.Observer<MyModel>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull MyModel myModel) {
                            if (myModel.getCod() == 200){
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                    LocalDateTime ldt=LocalDateTime.now(ZoneId.of("Europe/Kyiv"));
                                    date=ldt.format(DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy"));
                                    myModel.setLdt(date);
                                }

                                myViewModel.setModel(myModel);
                                addWeatherInformInBackGround(myModel);
                                getWeatherListInBackGround();
                                Log.d("My Tag",list.size()+" size");
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            if (e instanceof NoConnectivityException){
                                Toast.makeText(MainActivity.this,e.getMessage()
                                        +" No INTERNET",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(MainActivity.this,e.getMessage()
                                        +" Some problems",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
//            Observable<Model> observable = App.getMyApi().getWeather(city, apiKey, "metric");
//            observable.subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
        }
    }
    public void addWeatherInformInBackGround(MyModel w){
        ExecutorService executorService= Executors.newSingleThreadExecutor();
        Handler handler=new Handler(Looper.getMainLooper());
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                // Check if the object with the same id already exists in the database

                Log.d("My Tag", "Before insertion - ID: " + w.getId());
                weatherDatabase.getWeatherDao().addInfoWeather(w);
                Log.d("My Tag", "After insertion - ID: " + w.getId());

//                //background task
//                weatherDatabase.getWeatherDao().addInfoWeather(w);
//                Log.d("My tag",w.getId()+"  l");
                //on finishing task
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("My Tag","Add inform");
                    }
                });
            }
        });
    }
    public void getWeatherListInBackGround(){
        ExecutorService executorService= Executors.newSingleThreadExecutor();
        Handler handler=new Handler(Looper.getMainLooper());
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                //background task
                list=weatherDatabase.getWeatherDao().getInfoAllWeather();
                Collections.reverse(list);
                //on finishing task
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("My Tag", "Show all inform");
                        recyclerView.setAdapter(new MyAdapter(list));
//                        Log.d("My Tag",wheatherList.toString());
                    }
                });
            }
        });
    }
}
