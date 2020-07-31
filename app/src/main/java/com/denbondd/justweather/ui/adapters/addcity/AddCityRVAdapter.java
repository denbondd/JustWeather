package com.denbondd.justweather.ui.adapters.addcity;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.denbondd.justweather.R;
import com.denbondd.justweather.models.City;
import com.denbondd.justweather.models.findowm.Data;

import java.util.ArrayList;

public class AddCityRVAdapter extends RecyclerView.Adapter<AddCityRVViewHolder> {
    private ArrayList<Data> cities = new ArrayList<>();
    private final OnItemClick listener;

    public AddCityRVAdapter(OnItemClick listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public AddCityRVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AddCityRVViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.add_city_rvitem, parent, false),
                listener
        );
    }

    @Override
    public void onBindViewHolder(@NonNull AddCityRVViewHolder holder, int position) {
        holder.onBind(cities.get(position));
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    public void setCities(ArrayList<Data> cities) {
        this.cities = cities;
        notifyDataSetChanged();
    }

    public interface OnItemClick {
        void onItemClick(City city);
    }
}
