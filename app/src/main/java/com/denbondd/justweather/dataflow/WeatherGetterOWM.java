package com.denbondd.justweather.dataflow;

import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.denbondd.justweather.AppApplication;
import com.denbondd.justweather.models.CurrentWeatherOWMModel;
import com.denbondd.justweather.models.FindCityOWMModel;
import com.denbondd.justweather.models.OneCallOWMModel;
import com.denbondd.justweather.models.findowm.Data;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.denbondd.justweather.AppApplication.getContext;

public class WeatherGetterOWM {

    @Inject
    public WeatherGetterOWM() {}

    public MutableLiveData<OneCallOWMModel> oneCall = new MutableLiveData<>();
    public void updateOneCall(ApiHelper apiHelper, double lat, double lon) {
        apiHelper.getOneCallOWM(lat, lon).enqueue(new Callback<OneCallOWMModel>() {
            @Override
            public void onResponse(@NotNull Call<OneCallOWMModel> call, @NotNull Response<OneCallOWMModel> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), response.code(), Toast.LENGTH_SHORT).show();
                    cityName.postValue(null);
                    return;
                }
                oneCall.postValue(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<OneCallOWMModel> call, @NotNull Throwable t) {
                t.printStackTrace();
                Toast.makeText(getContext(), "Error with getting weather", Toast.LENGTH_SHORT).show();
                oneCall.postValue(null);
            }
        });
    }

    public MutableLiveData<String> cityName = new MutableLiveData<>();
    public void updateCityName(ApiHelper apiHelper, double lat, double lon) {
        apiHelper.getCurrentWeatherOWM(lat, lon).enqueue(new Callback<CurrentWeatherOWMModel>() {
            @Override
            public void onResponse(@NotNull Call<CurrentWeatherOWMModel> call, @NotNull Response<CurrentWeatherOWMModel> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(AppApplication.getContext(), response.code(), Toast.LENGTH_SHORT).show();
                    cityName.postValue(null);
                    return;
                }
                CurrentWeatherOWMModel currentWeatherOWMModel = response.body();
                if (currentWeatherOWMModel != null) {
                    cityName.postValue(currentWeatherOWMModel.getName());
                } else {
                    cityName.postValue(null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<CurrentWeatherOWMModel> call, @NotNull Throwable t) {
                t.printStackTrace();
                Toast.makeText(AppApplication.getContext(), "Error with location name", Toast.LENGTH_SHORT).show();
                cityName.postValue(null);
            }
        });
    }

    public MutableLiveData<ArrayList<Data>> findCity = new MutableLiveData<>();
    public void updateFindCity(ApiHelper apiHelper, String name) {
        apiHelper.getFindCityOWM(name).enqueue(new Callback<FindCityOWMModel>() {
            @Override
            public void onResponse(@NotNull Call<FindCityOWMModel> call, @NotNull Response<FindCityOWMModel> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        findCity.postValue((ArrayList<Data>) response.body().getData());
                        return;
                    }
                }
                cityName.postValue(null);
            }

            @Override
            public void onFailure(@NotNull Call<FindCityOWMModel> call, @NotNull Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                cityName.postValue(null);
            }
        });
    }
}
