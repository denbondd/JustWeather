package com.denbondd.justweather.ui.base;

import androidx.lifecycle.ViewModel;

import com.denbondd.justweather.dataflow.ApiHelper;

public abstract class BaseVM extends ViewModel {
    protected ApiHelper apiHelper = new ApiHelper();
}
