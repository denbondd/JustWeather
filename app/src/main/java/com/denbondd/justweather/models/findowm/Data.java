package com.denbondd.justweather.models.findowm;

import com.denbondd.justweather.models.currentweatherowm.Clouds;
import com.denbondd.justweather.models.currentweatherowm.Coord;
import com.denbondd.justweather.models.currentweatherowm.Main;
import com.denbondd.justweather.models.currentweatherowm.Sys;
import com.denbondd.justweather.models.currentweatherowm.Wind;
import com.denbondd.justweather.models.onecallowm.Weather;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("coord")
    @Expose
    private Coord coord;
    @SerializedName("main")
    @Expose
    private Main main;
    @SerializedName("dt")
    @Expose
    private Integer dt;
    @SerializedName("wind")
    @Expose
    private Wind wind;
    @SerializedName("sys")
    @Expose
    private Sys sys;
    @SerializedName("rain")
    @Expose
    private Object rain;
    @SerializedName("snow")
    @Expose
    private Object snow;
    @SerializedName("clouds")
    @Expose
    private Clouds clouds;
    @SerializedName("weather")
    @Expose
    private List<Weather> weather;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coord getCoord() {
        return coord;
    }

    public Main getMain() {
        return main;
    }

    public Integer getDt() {
        return dt;
    }

    public Wind getWind() {
        return wind;
    }

    public Sys getSys() {
        return sys;
    }

    public Object getRain() {
        return rain;
    }

    public Object getSnow() {
        return snow;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public List<Weather> getWeather() {
        return weather;
    }

}
