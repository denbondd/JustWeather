package com.denbondd.justweather.models;

import com.denbondd.justweather.models.findowm.Data;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FindCityOWMModel {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("cod")
    @Expose
    private String cod;
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("list")
    @Expose
    private List<Data> data;

    public String getMessage() {
        return message;
    }

    public String getCod() {
        return cod;
    }

    public Integer getCount() {
        return count;
    }

    public List<Data> getData() {
        return data;
    }
}
