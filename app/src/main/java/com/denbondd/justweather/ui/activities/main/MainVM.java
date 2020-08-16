package com.denbondd.justweather.ui.activities.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.denbondd.justweather.R;
import com.denbondd.justweather.models.City;
import com.denbondd.justweather.ui.base.BaseVM;

import java.util.ArrayList;
import java.util.List;

public class MainVM extends BaseVM {
    public MutableLiveData<String> currentPage = new MutableLiveData<>();

    public LiveData<List<City>> getAllLD() {
        return appDatabase.cityDao().getAll();
    }

    public long getCurrentId() {
        return appDatabase.cityDao().getCurrent().getId();
    }
}
