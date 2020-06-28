package com.denbondd.justweather.ui.fragments.hourly;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denbondd.justweather.R;
import com.denbondd.justweather.models.onecallowm.Hourly;
import com.denbondd.justweather.ui.adapters.HourlyRVAdapter;
import com.denbondd.justweather.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class HourlyFragment extends BaseFragment<HourlyViewModel> {
    private static final String HOURLY_LIST_KEY = "HOURLY_LIST_KEY";

    @Override
    public int getLayoutId() {
        return R.layout.hourly_fragment;
    }

    @Override
    public Class<HourlyViewModel> getViewModelClass() {
        return HourlyViewModel.class;
    }

    public static HourlyFragment newInstance(ArrayList<Hourly> hourlyArrayList) {
        HourlyFragment fragment = new HourlyFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(HOURLY_LIST_KEY, hourlyArrayList);
        fragment.setArguments(bundle);
        return fragment;
    }

    private ArrayList<Hourly> hourlyArrayList;
    private RecyclerView rvHourly;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        hourlyArrayList = getArguments().getParcelableArrayList(HOURLY_LIST_KEY);

        rvHourly = view.findViewById(R.id.rvHourly);
        makeRecycler();
    }

    private void makeRecycler() {
        HourlyRVAdapter adapter = new HourlyRVAdapter();
        adapter.setItems(hourlyArrayList);
        rvHourly.setAdapter(adapter);
    }
}