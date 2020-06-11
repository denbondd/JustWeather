package com.denbondd.justweather.ui.activities.main;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.LocationManager;

import androidx.core.app.ActivityCompat;

import com.denbondd.justweather.AppApplication;
import com.denbondd.justweather.models.OneCallOWM;
import com.denbondd.justweather.ui.base.BaseVM;

import retrofit2.Call;

public class MainVM extends BaseVM {
    public Call<OneCallOWM> getCurrentWeatherOWM(double lat, double lon) {
        return apiHelper.getCurrentWeather(lat, lon);
    }

    public boolean checkLocationPermissions() {
        return ActivityCompat.checkSelfPermission(AppApplication.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(AppApplication.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }
}
