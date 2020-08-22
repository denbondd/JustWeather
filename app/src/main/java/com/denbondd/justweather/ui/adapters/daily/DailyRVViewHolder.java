package com.denbondd.justweather.ui.adapters.daily;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.denbondd.justweather.AppApplication;
import com.denbondd.justweather.databinding.DailyRvitemBinding;
import com.denbondd.justweather.models.onecallowm.Daily;

public class DailyRVViewHolder extends RecyclerView.ViewHolder {
    private final DailyRvitemBinding binding;

    public DailyRVViewHolder(@NonNull View itemView) {
        super(itemView);
        binding = DailyRvitemBinding.bind(itemView);
    }

    public void onBind(Daily item) {
        binding.setDaily(item);
    }
}
