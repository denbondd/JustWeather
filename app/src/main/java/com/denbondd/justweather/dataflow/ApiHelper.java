package com.denbondd.justweather.dataflow;

import com.denbondd.justweather.AppApplication;
import com.denbondd.justweather.R;
import com.denbondd.justweather.models.OneCallOWM;
import com.denbondd.justweather.services.Keys;
import com.denbondd.justweather.services.OpenWeatherMapApi;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiHelper {
    private final Retrofit retrofitOWM = new Retrofit.Builder()
            .baseUrl(AppApplication.getContext().getString(R.string.BASE_URL_OPEN_WEATHER_MAP))
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public Call<OneCallOWM> getCurrentWeather(double lat, double lon) {
        OpenWeatherMapApi openWeatherMapApi = retrofitOWM.create(OpenWeatherMapApi.class);
        return openWeatherMapApi.getCurrentWeather(lat, lon, Keys.readOWMkey(), "metric");
    }
}
