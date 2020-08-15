package com.denbondd.justweather.dataflow;

import com.denbondd.justweather.AppApplication;
import com.denbondd.justweather.R;
import com.denbondd.justweather.models.CurrentWeatherOWMModel;
import com.denbondd.justweather.models.FindCityOWMModel;
import com.denbondd.justweather.models.OneCallOWMModel;
import com.denbondd.justweather.services.Keys;
import com.denbondd.justweather.services.OpenWeatherMapApi;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiHelper {

    @Inject
    public ApiHelper() {}

    private final Retrofit retrofitOWM = new Retrofit.Builder()
            .baseUrl(AppApplication.getContext().getString(R.string.BASE_URL_OPEN_WEATHER_MAP))
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private final OpenWeatherMapApi openWeatherMapApi = retrofitOWM.create(OpenWeatherMapApi.class);

    public Call<OneCallOWMModel> getOneCallOWM(double lat, double lon) {
        return openWeatherMapApi.getOneCallOWM(lat, lon, Keys.readOWMkey(), "metric");
    }

    public Call<CurrentWeatherOWMModel> getCurrentWeatherOWM(double lat, double lon) {
        return openWeatherMapApi.getCurrentWeatherOWM(lat, lon, Keys.readOWMkey(), "metric");
    }

    public Call<FindCityOWMModel> getFindCityOWM(String name) {
        return openWeatherMapApi.findCities(name, "metric", Keys.readOWMkey());
    }
}
