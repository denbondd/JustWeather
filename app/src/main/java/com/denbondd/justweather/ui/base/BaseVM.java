package com.denbondd.justweather.ui.base;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.denbondd.justweather.AppApplication;
import com.denbondd.justweather.dataflow.ApiHelper;

public abstract class BaseVM extends ViewModel {
    protected ApiHelper apiHelper = new ApiHelper();
    protected Context getContext() {
        return AppApplication.getContext();
    }
}
