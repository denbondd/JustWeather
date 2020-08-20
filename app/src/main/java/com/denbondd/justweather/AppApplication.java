package com.denbondd.justweather;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.preference.PreferenceManager;

import com.denbondd.justweather.di.AppComponent;
import com.denbondd.justweather.di.DaggerAppComponent;

import java.lang.ref.WeakReference;

import static com.denbondd.justweather.util.Constants.PREFERENCES_NAME;

public class AppApplication extends Application {

    //for getting context from anywhere
    private static WeakReference<Context> context;

    private static AppComponent appComponent;
    private static SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        context = new WeakReference<>(this);
        appComponent = DaggerAppComponent.builder().context(getApplicationContext()).build();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
    }

    static {
        System.loadLibrary("keys");
    }

    public static Context getContext() {
        return context.get();
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    public static SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public static boolean checkLocationPermissions() {
        return ActivityCompat.checkSelfPermission(AppApplication.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(AppApplication.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }
}
