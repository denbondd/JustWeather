package com.denbondd.justweather.ui.adapters.navitems;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.denbondd.justweather.R;
import com.denbondd.justweather.models.City;

import java.util.ArrayList;

public class NavItemsRVAdapter extends RecyclerView.Adapter<NavItemsRVViewHolder> {
    private ArrayList<City> cities = new ArrayList<>();
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
        holder.onBind(cities.get(position));
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    public void setCities(ArrayList<City> cities) {
        this.cities = cities;
        notifyDataSetChanged();
    }

    public void setCurrentCity(long id) {
        for (City city : cities) {
            city.setCurrent(city.getId() == id);
            notifyItemChanged(cities.indexOf(city));
        }
    }

    public interface NavItemsRVInterface {
        void openCityFragment(City city);
    }
}
