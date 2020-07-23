package com.denbondd.justweather.ui.fragments.daily;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.denbondd.justweather.R;
import com.denbondd.justweather.models.onecallowm.Daily;
import com.denbondd.justweather.ui.adapters.DailyRVAdapter;
import com.denbondd.justweather.ui.base.BaseFragment;

import java.util.ArrayList;

public class DailyFragment extends BaseFragment<DailyViewModel> {
    private static final String DAILY_KEY = "DAILY_KEY";

    public static DailyFragment newInstance(ArrayList<Daily> dailies) {
        DailyFragment fragment = new DailyFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(DAILY_KEY, dailies);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.daily_fragment;
    }

    @Override
    public Class<DailyViewModel> getViewModelClass() {
        return DailyViewModel.class;
    }

    private RecyclerView rvDaily;
    private ArrayList<Daily> dailies;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dailies = requireArguments().getParcelableArrayList(DAILY_KEY);
        rvDaily = view.findViewById(R.id.rvDaily);

        makeRecycler();
    }

    private void makeRecycler() {
        DailyRVAdapter adapter = new DailyRVAdapter();
        adapter.setItems(dailies);
        rvDaily.setAdapter(adapter);
    }
}