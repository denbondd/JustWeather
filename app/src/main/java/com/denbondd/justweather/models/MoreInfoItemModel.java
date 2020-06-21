package com.denbondd.justweather.models;

import android.graphics.drawable.Drawable;

public class MoreInfoItemModel {

    private MoreInfoTypeEnum type;
    private Drawable icon;
    private String title;
    private double data;
    private String measure;

    public MoreInfoItemModel(MoreInfoTypeEnum type, String title, double data, Drawable icon, String measure) {
        this.title = title;
        this.type = type;
        this.data = data;
        this.icon = icon;
        this.measure = measure;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MoreInfoTypeEnum getType() {
        return type;
    }

    public void setType(MoreInfoTypeEnum type) {
        this.type = type;
    }

    public double getData() {
        return data;
    }

    public void setData(double data) {
        this.data = data;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }
}
