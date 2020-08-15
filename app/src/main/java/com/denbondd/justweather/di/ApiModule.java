package com.denbondd.justweather.di;

import com.denbondd.justweather.dataflow.ApiHelper;
import com.denbondd.justweather.dataflow.WeatherGetterOWM;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApiModule {

    @Singleton
    @Provides
    static ApiHelper provideApiHelper() {
        return new ApiHelper();
    }

    @Singleton
    @Provides
    static WeatherGetterOWM provideWeatherGetterOWM() {
        return new WeatherGetterOWM();
    }
}
