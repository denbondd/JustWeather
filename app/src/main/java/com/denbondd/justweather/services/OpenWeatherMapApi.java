package com.denbondd.justweather.services;

import com.denbondd.justweather.models.CurrentWeatherOWMModel;
import com.denbondd.justweather.models.FindCityOWMModel;
import com.denbondd.justweather.models.OneCallOWMModel;
import com.denbondd.justweather.models.findowm.Data;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherMapApi {

    @GET("data/2.5/onecall")
    Call<OneCallOWMModel> getOneCallOWM(
            @Query("lat") double lat,
            @Query("lon") double lon,
            @Query("appid") String appid,
            @Query("units") String units,
            @Query("lang") String lang
    );

    @GET("data/2.5/weather")
    Call<CurrentWeatherOWMModel> getCurrentWeatherOWM(
            @Query("lat") double lat,
            @Query("lon") double lon,
            @Query("appid") String appid,
            @Query("units") String units,
            @Query("lang") String lang
    );

    @GET("data/2.5/find")
    Call<FindCityOWMModel> findCities(
            @Query("q") String name,
            @Query("units") String units,
            @Query("appid") String appid,
            @Query("lang") String lang
    );
}
