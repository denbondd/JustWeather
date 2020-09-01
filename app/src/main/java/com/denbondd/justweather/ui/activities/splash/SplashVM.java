package com.denbondd.justweather.ui.activities.splash;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Handler;

import androidx.appcompat.app.AppCompatDelegate;
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

    public void notFirstStart(Context baseContext) {
        AppApplication.setLanguageTag(changeLanguage(PreferenceManager.getDefaultSharedPreferences(getContext()), baseContext));
        AppApplication.setAppTheme();
    }

    public void firstStart(Context baseContext) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        preferences.edit().putString(getContext().getString(R.string.temperature_key), "c").apply();
        preferences.edit().putString(getContext().getString(R.string.speed_key), "ms").apply();
        preferences.edit().putString(getContext().getString(R.string.pressure_key), "mbar").apply();
        preferences.edit().putString(getContext().getString(R.string.language_key), getLanguageTag()).apply();
        preferences.edit().putString(getContext().getString(R.string.theme_key), "system").apply();
        changeLanguage(preferences, baseContext);
    }

    public String changeLanguage(SharedPreferences preferences, Context baseContext) {
        String languageTag = preferences.getString(getContext().getString(R.string.language_key), "en");
        AppApplication.setLanguageTag(languageTag);
        Locale locale = new Locale(languageTag);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.setLocale(locale);

        //setting language to the app in two ways, because they working not fully by their own
        getContext().getApplicationContext().getResources()
                .updateConfiguration(configuration, getContext().getResources().getDisplayMetrics());
        baseContext.getResources().updateConfiguration(configuration, baseContext.getResources().getDisplayMetrics());

        return languageTag;
    }

    String getLanguageTag() {
        String deviceLang = Locale.getDefault().getLanguage(); //was rus
        if (deviceLang.equals("uk") || deviceLang.equals("ru")) {
            return deviceLang;
        } else {
            return "en";
        }
    }

    public boolean isThereCurrentCity() {
        return appDatabase.cityDao().getCurrent() != null;
    }
}
