package com.denbondd.justweather.models.currentweather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rain {
    @SerializedName("1h")
    @Expose
    private Double _1h;

    public Double get_1h() {
        return _1h;
    }
}
