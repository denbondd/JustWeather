package com.denbondd.justweather.ui.activities.splash;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Handler;

import androidx.core.app.ActivityCompat;
import androidx.lifecycle.MutableLiveData;

import com.denbondd.justweather.AppApplication;
import com.denbondd.justweather.models.City;
import com.denbondd.justweather.ui.base.BaseVM;

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
        weatherGetterOWM.updateCityName(apiHelper, geolocationCity.getLat(), geolocationCity.getLon());

        weatherGetterOWM.cityName.observeForever(name -> {
            geolocationCity.setName(name);
            isCityReadyToGo.postValue(true);
        });
    }

    public void insertCity() {
        new Thread(() -> appDatabase.cityDao().insert(geolocationCity)).start();
    }
}
