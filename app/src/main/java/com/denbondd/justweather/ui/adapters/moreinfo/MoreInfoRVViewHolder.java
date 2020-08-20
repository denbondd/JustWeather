package com.denbondd.justweather.ui.adapters.moreinfo;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.denbondd.justweather.AppApplication;
import com.denbondd.justweather.databinding.MoreInfoRvitemBinding;
import com.denbondd.justweather.models.MoreInfoItemModel;

public class MoreInfoRVViewHolder extends RecyclerView.ViewHolder {

    public MoreInfoRVViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void onBind(MoreInfoItemModel item) {
        MoreInfoRvitemBinding binding = MoreInfoRvitemBinding.bind(itemView);
        binding.setItem(item);
    }
}
