package com.example.weather_mvvm_new.model.recycler_view;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weather_mvvm_new.R;

public class MyViewHolder extends RecyclerView.ViewHolder {
    TextView date, city, cond, tempAndPress;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        date=itemView.findViewById(R.id.date);
        city=itemView.findViewById(R.id.city);
        cond=itemView.findViewById(R.id.condition);
        tempAndPress=itemView.findViewById(R.id.tempAndPressure);


    }
}
