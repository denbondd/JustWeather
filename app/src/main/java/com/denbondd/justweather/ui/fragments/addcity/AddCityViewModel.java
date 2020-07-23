package com.denbondd.justweather.ui.fragments.addcity;

import androidx.lifecycle.MutableLiveData;

import com.denbondd.justweather.models.FindCityOWMModel;
import com.denbondd.justweather.ui.base.BaseVM;

import retrofit2.Call;

public class AddCityViewModel extends BaseVM {

    protected MutableLiveData<Call<FindCityOWMModel>> findCity = new MutableLiveData<>();

    protected void updateFindCity(String message) {
        findCity.postValue(apiHelper.getFindCityOWM(message));
    }
}