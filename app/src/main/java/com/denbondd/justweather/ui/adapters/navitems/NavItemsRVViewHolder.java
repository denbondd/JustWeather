
package com.denbondd.justweather.ui.adapters.navitems;

import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.denbondd.justweather.R;
import com.denbondd.justweather.databinding.NavRvitemBinding;
import com.denbondd.justweather.models.CityModel;
import com.denbondd.justweather.ui.adapters.navitems.NavItemsRVAdapter;

public class NavItemsRVViewHolder extends RecyclerView.ViewHolder {
    private final Button navItem = itemView.findViewById(R.id.btnNavItem);
    private final NavRvitemBinding binding = NavRvitemBinding.bind(itemView);

    private final NavItemsRVAdapter.NavItemsRVInterface navItemsRVInterface;

    public NavItemsRVViewHolder(@NonNull View itemView, NavItemsRVAdapter.NavItemsRVInterface navItemsRVInterface) {
        super(itemView);
        this.navItemsRVInterface = navItemsRVInterface;
    }

    public void onBind(CityModel city) {
        binding.setCity(city);

        navItem.setOnClickListener(v -> navItemsRVInterface.openCityFragment(city));
    }
}
