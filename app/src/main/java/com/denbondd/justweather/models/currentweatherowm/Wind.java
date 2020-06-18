package com.denbondd.justweather.models.currentweatherowm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Wind {
    @SerializedName("speed")
    @Expose
    private Integer speed;
    @SerializedName("deg")
    @Expose
    private Integer deg;

    public Integer getSpeed() {
        return speed;
    }

    public Integer getDeg() {
        return deg;
    }
}
