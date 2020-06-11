package com.denbondd.justweather.services;

import com.denbondd.justweather.models.OneCallOWM;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherMapApi {

    @GET("data/2.5/onecall")
    Call<OneCallOWM> getCurrentWeather(
            @Query("lat") double lat,
            @Query("lon") double lon,
            @Query("appid") String appid,
            @Query("units") String units
    );
}
