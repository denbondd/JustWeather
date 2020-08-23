package com.denbondd.justweather.ui.activities.splash;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Handler;

import androidx.core.app.ActivityCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.preference.PreferenceManager;

import com.denbondd.justweather.AppApplication;
import com.denbondd.justweather.R;
import com.denbondd.justweather.models.City;
import com.denbondd.justweather.ui.base.BaseVM;

import java.util.Locale;

public class SplashVM extends BaseVM {

    City geolocationCity;
    MutableLiveData<Boolean> isCityReadyToGo = new MutableLiveData<>();

    public void addGeolocationCity() {
        geolocationCity = new City(true);
        geolocationCity.setCurrent(true);
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

    public void firstStart() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        preferences.edit().putString(getContext().getString(R.string.temperature_key), "c").apply();
        preferences.edit().putString(getContext().getString(R.string.speed_key), "ms").apply();
        preferences.edit().putString(getContext().getString(R.string.pressure_key), "mbar").apply();
        preferences.edit().putString(getContext().getString(R.string.language_key), getLanguageTag()).apply();
    }

    private String getLanguageTag() {
        String deviceLang = Locale.getDefault().getLanguage();
        if (deviceLang.equals("ua") || deviceLang.equals("ru")) {
            return deviceLang;
        } else {
            return "en";
        }
    }
}
