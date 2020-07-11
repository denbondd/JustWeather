package com.denbondd.justweather.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.denbondd.justweather.R;
import com.denbondd.justweather.models.CityModel;

import java.util.ArrayList;

public class NavItemsRVAdapter extends RecyclerView.Adapter<NavItemsRVViewHolder> {
    private ArrayList<CityModel> cityModels;
    private final NavItemsRVInterface navItemsRVInterface;

    public NavItemsRVAdapter(NavItemsRVInterface navItemsRVInterface) {
        this.navItemsRVInterface = navItemsRVInterface;
    }

    @NonNull
    @Override
    public NavItemsRVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NavItemsRVViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_rvitem, parent, false),
                navItemsRVInterface
        );
    }

    @Override
    public void onBindViewHolder(@NonNull NavItemsRVViewHolder holder, int position) {
        holder.onBind(cityModels.get(position));
    }

    @Override
    public int getItemCount() {
        return cityModels.size();
    }

    public void setCityModels(ArrayList<CityModel> cityModels) {
        this.cityModels = cityModels;
        notifyDataSetChanged();
    }

    public interface NavItemsRVInterface {
        void openCityFragment(CityModel city);
    }
}
