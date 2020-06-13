package com.denbondd.justweather;

import android.app.Application;
import android.content.Context;

import java.lang.ref.WeakReference;

public class AppApplication extends Application {
    //for getting context from anywhere
    private static WeakReference<Context> context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = new WeakReference<>(this);
    }

    static {
        System.loadLibrary("keys");
    }

    public static Context getContext() {
        return context.get();
    }
}
