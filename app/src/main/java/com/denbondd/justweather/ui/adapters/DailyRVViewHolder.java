package com.denbondd.justweather.ui.adapters;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.denbondd.justweather.R;
import com.denbondd.justweather.databinding.DailyRvitemBinding;
import com.denbondd.justweather.models.onecallowm.Daily;
import com.denbondd.justweather.util.OWMExtensions;

public class DailyRVViewHolder extends RecyclerView.ViewHolder {
    private final ImageView ivWeatherIcon;
    private final DailyRvitemBinding binding;

    public DailyRVViewHolder(@NonNull View itemView) {
        super(itemView);

        ivWeatherIcon = itemView.findViewById(R.id.ivWeatherIcon);
        binding = DailyRvitemBinding.bind(itemView);
    }

    public void onBind(Daily item) {
        binding.setDaily(item);

        Glide.with(itemView.getContext())
                .load(OWMExtensions.getIconById(item.getWeather().get(0).getId()))
                .into(ivWeatherIcon);
    }
}
