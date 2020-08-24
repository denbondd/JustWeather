package com.denbondd.justweather;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import com.denbondd.justweather.di.AppComponent;
import com.denbondd.justweather.di.DaggerAppComponent;

import java.lang.ref.WeakReference;

public class AppApplication extends Application {

    //for getting context from anywhere
    private static WeakReference<Context> context;

    private static AppComponent appComponent;
    private static String languageTag;

    @Override
    public void onCreate() {
        super.onCreate();
        context = new WeakReference<>(this);
        appComponent = DaggerAppComponent.builder().context(getApplicationContext()).build();
    }

    static {
        System.loadLibrary("keys");
    }

    public static boolean checkLocationPermissions() {
        return ActivityCompat.checkSelfPermission(AppApplication.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(AppApplication.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    public static Context getContext() {
        return context.get();
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    public static String getLanguageTag() {
        return languageTag;
    }

    public static void setLanguageTag(String languageTag) {
        AppApplication.languageTag = languageTag;
    }
}
