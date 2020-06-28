package com.denbondd.justweather.ui.adapters;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.denbondd.justweather.databinding.HourlyRvitemBinding;
import com.denbondd.justweather.models.onecallowm.Hourly;

public class HourlyRVViewHolder extends RecyclerView.ViewHolder {
    public HourlyRVViewHolder(@NonNull View itemView) {
        super(itemView);
        binding = HourlyRvitemBinding.bind(itemView);
    }

    private HourlyRvitemBinding binding;

    public void onBind(Hourly hourly) {
        binding.setHourly(hourly);
    }
}
