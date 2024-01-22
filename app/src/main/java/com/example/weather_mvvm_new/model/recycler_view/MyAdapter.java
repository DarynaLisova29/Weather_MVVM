package com.example.weather_mvvm_new.model.recycler_view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weather_mvvm_new.R;
import com.example.weather_mvvm_new.model.MyModel;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    List<MyModel> item;
//    List<MyModel> item;

    public MyAdapter(List<MyModel> item) {
        this.item = item;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout
                .history, parent, false));
    }//за прокрутку

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.date.setText(item.get(position).getLdt());
        holder.city.setText(item.get(position).getCity());
        holder.tempAndPress.setText(item.get(position).getMain().toString());
        holder.cond.setText(item.get(position).getWeather().get(0).getMain());
    }//за розміщення елементів

    @Override
    public int getItemCount() {
        return item.size();
    }//за кількість
}
