package com.denbondd.justweather;

import android.app.Application;
import android.content.Context;

import com.denbondd.justweather.di.AppComponent;
import com.denbondd.justweather.di.DaggerAppComponent;

import java.lang.ref.WeakReference;

public class AppApplication extends Application {

    //for getting context from anywhere
    private static WeakReference<Context> context;
    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        context = new WeakReference<>(this);
        appComponent = DaggerAppComponent.builder().context(getApplicationContext()).build();
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
}
