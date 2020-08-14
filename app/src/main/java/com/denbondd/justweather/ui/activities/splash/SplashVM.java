package com.denbondd.justweather.ui.activities.splash;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Handler;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.lifecycle.MutableLiveData;

import com.denbondd.justweather.AppApplication;
import com.denbondd.justweather.models.City;
import com.denbondd.justweather.models.CurrentWeatherOWMModel;
import com.denbondd.justweather.ui.base.BaseVM;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashVM extends BaseVM {

    City geolocationCity;
    MutableLiveData<Boolean> isCityReadyToGo = new MutableLiveData<>();

    public void addGeolocationCity() {
        geolocationCity = new City(true);
        geolocationCity.setCurrent(true);
    }

    public boolean checkLocationPermissions() {
        return ActivityCompat.checkSelfPermission(AppApplication.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(AppApplication.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    public void setCityLocation(Location location) {
        geolocationCity.setLon(location.getLongitude());
        geolocationCity.setLat(location.getLatitude());
        setCityName();
    }

    public void setCityName() {
        apiHelper.getCurrentWeatherOWM(geolocationCity.getLat(), geolocationCity.getLon()).enqueue(new Callback<CurrentWeatherOWMModel>() {
            @Override
            public void onResponse(@NotNull Call<CurrentWeatherOWMModel> call, @NotNull Response<CurrentWeatherOWMModel> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(AppApplication.getContext(), response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                CurrentWeatherOWMModel currentWeatherOWMModel = response.body();
                if (currentWeatherOWMModel != null) {
                    geolocationCity.setName(currentWeatherOWMModel.getName());
                    new Thread(() -> appDatabase.cityDao().insert(geolocationCity)).start();
                    new Handler().postDelayed(() -> isCityReadyToGo.postValue(true), 2000);
                }
            }

            @Override
            public void onFailure(@NotNull Call<CurrentWeatherOWMModel> call, @NotNull Throwable t) {
                t.printStackTrace();
                Toast.makeText(AppApplication.getContext(), "Error with location name", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
