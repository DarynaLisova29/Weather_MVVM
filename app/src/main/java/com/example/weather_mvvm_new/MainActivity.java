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
import com.example.weather_mvvm_new.model.recycler_view.MyAdapter;
import com.example.weather_mvvm_new.view_model.MyViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    MyViewModel myViewModel;
    ActivityMainBinding binding;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //viewBinding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initViewModel();
        setLayoutManager();
        setListeners();
        observable();
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.getWeather) {
            String city = binding.inputCity.getText().toString();
            myViewModel.getWeather(city, Const.METRIC);
        }
    }

    private void observable() {
        myViewModel.getAllWeatherLifeData().observe(this,
                weathers -> binding.recycler.setAdapter(new MyAdapter(weathers))
        );

        myViewModel.getModel().observe(this, myModel -> {
            binding.tempValue.setText(String.valueOf(myModel.getMain().getTemp()));
            binding.condValue.setText(myModel.getWeather().get(0).getMain());
            binding.pressValue.setText(String.valueOf(myModel.getMain().getPressure()));
            //метод який відповідає за добавлення
            myViewModel.insert(myModel);
        });
        myViewModel.errorHandler.observe(this, aBoolean
                -> Toast.makeText(MainActivity.this,
                " Some problems ", Toast.LENGTH_SHORT).show()
        );
    }

    private void setListeners() {
        binding.getWeather.setOnClickListener(this);
    }

    private void setLayoutManager() {
        //вішаємо на кнопку подію й ресайклView
        binding.recycler.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    private void initViewModel() {
        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);
    }
}
