package com.denbondd.justweather.ui.activities.main;

import com.denbondd.justweather.models.CurrentWeatherModel;
import com.denbondd.justweather.ui.base.BaseVM;

import retrofit2.Call;

public class MainVM extends BaseVM {
    public Call<CurrentWeatherModel> getCurrentWeatherOWM(double lat, double lon) {
        return apiHelper.getCurrentWeather(lat, lon);
    }
}
