package com.denbondd.justweather.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.denbondd.justweather.R;
import com.denbondd.justweather.models.onecallowm.Hourly;

import java.util.ArrayList;

public class HourlyRVAdapter extends RecyclerView.Adapter<HourlyRVViewHolder> {
    private ArrayList<Hourly> items;

    @NonNull
    @Override
    public HourlyRVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HourlyRVViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.hourly_rvitem, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull HourlyRVViewHolder holder, int position) {
        holder.onBind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<Hourly> items) {
        this.items = items;
        notifyDataSetChanged();
    }
}
