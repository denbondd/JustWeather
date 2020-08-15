package com.denbondd.justweather.ui.base;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.denbondd.justweather.AppApplication;
import com.denbondd.justweather.dataflow.ApiHelper;
import com.denbondd.justweather.dataflow.WeatherGetterOWM;
import com.denbondd.justweather.db.AppDatabase;

import javax.inject.Inject;

public abstract class BaseVM extends ViewModel {
    @Inject
    protected ApiHelper apiHelper;

    @Inject
    protected WeatherGetterOWM weatherGetterOWM;

    protected Context getContext() {
        return AppApplication.getContext();
    }

    public BaseVM() {
        AppApplication.getAppComponent().inject(this);
    }

    @Inject
    protected AppDatabase appDatabase;
}
