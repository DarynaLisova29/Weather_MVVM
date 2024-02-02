package com.example.weather_mvvm_new;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.weather_mvvm_new.databinding.ActivityMainBinding;
import com.example.weather_mvvm_new.model.MyModel;
import com.example.weather_mvvm_new.view_model.DaggerMyViewModelComponent;
import com.example.weather_mvvm_new.view_model.MyViewModel;
import com.example.weather_mvvm_new.view_model.MyViewModelComponent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    String city;
    @Inject
    MyViewModel myViewModel;

    List<MyModel>list=new ArrayList<>();
//    for RecyclerView
    RecyclerView recyclerView;
    ActivityMainBinding binding;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        //viewBinding
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);

        MyViewModelComponent myViewModelComponent= DaggerMyViewModelComponent.create();
        myViewModelComponent.inject(this);
//       TODO Для чого це!!!!!!!!!
//        myViewModel=new ViewModelProvider(this).get(MyViewModel.class);


//       визначаємо recyclerView
        recyclerView=findViewById(R.id.recycler);

        //вішаємо на кнопку подію
        binding.getWeather.setOnClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        myViewModel.getInfo(MainActivity.this,recyclerView);

        myViewModel.getModel().observe(this, new Observer<MyModel>() {
            @Override
            public void onChanged(MyModel myModel) {
                binding.tempValue.setText(String.valueOf(myModel.getMain().getTemp()));
                binding.condValue.setText(myModel.getWeather().get(0).getMain());
                binding.pressValue.setText(String.valueOf(myModel.getMain().getPressure()));
                //метод який відповідає за добавлення
                myViewModel.insert(myModel);
                //метод який відповідає за отримання інформації
                myViewModel.getInfo(MainActivity.this,recyclerView);

            }
        });
        myViewModel.errorHandler.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Toast.makeText(MainActivity.this,
                                        " Some problems ",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.getWeather) {
//            city = inputCity.getText().toString();
            city = binding.inputCity.getText().toString();
            Log.d("MyTag", binding.inputCity.getText().toString() + "");
            myViewModel.getWeather(city, "metric");


        }
    }
}
