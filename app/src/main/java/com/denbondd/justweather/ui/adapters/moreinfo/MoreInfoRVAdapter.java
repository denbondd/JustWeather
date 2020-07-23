package com.denbondd.justweather.ui.adapters.moreinfo;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.denbondd.justweather.R;
import com.denbondd.justweather.models.MoreInfoItemModel;

import java.util.ArrayList;

public class MoreInfoRVAdapter extends RecyclerView.Adapter<MoreInfoRVViewHolder> {
    private ArrayList<MoreInfoItemModel> items = new ArrayList<>();

    @NonNull
    @Override
    public MoreInfoRVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MoreInfoRVViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.more_info_rvitem, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull MoreInfoRVViewHolder holder, int position) {
        holder.onBind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<MoreInfoItemModel> items) {
        this.items = items;
        notifyDataSetChanged();
    }
}
