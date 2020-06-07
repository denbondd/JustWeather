package com.denbondd.justweather.services;

import com.denbondd.justweather.models.CurrentWeatherModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherMapApi {

    @GET("data/2.5/weather")
    Call<CurrentWeatherModel> getCurrentWeather(
            @Query("lat") double lat,
            @Query("lon") double lon,
            @Query("appid") String appid
    );
}
