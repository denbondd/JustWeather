package com.denbondd.justweather.ui.adapters.hourly;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.denbondd.justweather.AppApplication;
import com.denbondd.justweather.databinding.HourlyRvitemBinding;
import com.denbondd.justweather.models.onecallowm.Hourly;

public class HourlyRVViewHolder extends RecyclerView.ViewHolder {
    private final HourlyRvitemBinding binding;

    public HourlyRVViewHolder(@NonNull View itemView) {
        super(itemView);
        binding = HourlyRvitemBinding.bind(itemView);
    }

    public void onBind(Hourly hourly) {
        binding.setHourly(hourly);
        binding.setPref(AppApplication.getSharedPreferences());
    }
}
