package com.denbondd.justweather.ui.fragments.addcity;

import androidx.lifecycle.MutableLiveData;

import com.denbondd.justweather.models.FindCityOWMModel;
import com.denbondd.justweather.models.findowm.Data;
import com.denbondd.justweather.ui.base.BaseVM;

import java.util.ArrayList;

import retrofit2.Call;

public class AddCityViewModel extends BaseVM {

    protected MutableLiveData<ArrayList<Data>> findCity = new MutableLiveData<>();

    protected void updateFindCity(String message) {
        weatherGetterOWM.updateFindCity(apiHelper, message);
        weatherGetterOWM.findCity.observeForever(data -> findCity.postValue(data));
    }
}