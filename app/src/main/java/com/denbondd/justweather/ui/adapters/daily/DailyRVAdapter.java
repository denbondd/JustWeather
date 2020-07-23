package com.denbondd.justweather.ui.adapters.daily;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.denbondd.justweather.R;
import com.denbondd.justweather.models.onecallowm.Daily;

import java.util.ArrayList;

public class DailyRVAdapter extends RecyclerView.Adapter<DailyRVViewHolder> {
    private ArrayList<Daily> items;

    @NonNull
    @Override
    public DailyRVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DailyRVViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_rvitem, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull DailyRVViewHolder holder, int position) {
        holder.onBind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<Daily> items) {
        this.items = items;
        notifyDataSetChanged();
    }
}
