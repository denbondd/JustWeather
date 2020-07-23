package com.denbondd.justweather.ui.adapters.addcity;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.denbondd.justweather.databinding.AddCityRvitemBinding;
import com.denbondd.justweather.models.findowm.Data;

public class AddCityRVViewHolder extends RecyclerView.ViewHolder {
    private final AddCityRvitemBinding binding;

    public AddCityRVViewHolder(@NonNull View itemView) {
        super(itemView);
        binding = AddCityRvitemBinding.bind(itemView);
    }

    public void onBind(Data city) {
        binding.setCity(city);
    }
}
