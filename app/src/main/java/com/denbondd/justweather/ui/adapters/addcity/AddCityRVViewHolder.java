package com.denbondd.justweather.ui.adapters.addcity;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.denbondd.justweather.databinding.AddCityRvitemBinding;
import com.denbondd.justweather.models.City;
import com.denbondd.justweather.models.findowm.Data;

public class AddCityRVViewHolder extends RecyclerView.ViewHolder {
    private final AddCityRvitemBinding binding;
    private final AddCityRVAdapter.OnItemClick listener;

    public AddCityRVViewHolder(@NonNull View itemView, AddCityRVAdapter.OnItemClick listener) {
        super(itemView);
        binding = AddCityRvitemBinding.bind(itemView);
        this.listener = listener;
    }

    public void onBind(Data city) {
        binding.setCity(city);
        itemView.setOnClickListener(v -> listener.onItemClick(new City(
                city.getName(),
                false,
                city.getCoord().getLat(),
                city.getCoord().getLon(),
                false)
        ));
    }
}
