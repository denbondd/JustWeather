package com.denbondd.justweather.models;

import android.graphics.drawable.Drawable;

public class MoreInfoItemModel {

    private MoreInfoTypeEnum type;
    private Drawable icon;
    private String title;
    private String dataAndMeasure;

    public MoreInfoItemModel(MoreInfoTypeEnum type, Drawable icon, String title, String dataAndMeasure) {
        this.type = type;
        this.icon = icon;
        this.title = title;
        this.dataAndMeasure = dataAndMeasure;
    }

    public MoreInfoTypeEnum getType() {
        return type;
    }

    public void setType(MoreInfoTypeEnum type) {
        this.type = type;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDataAndMeasure() {
        return dataAndMeasure;
    }

    public void setDataAndMeasure(String dataAndMeasure) {
        this.dataAndMeasure = dataAndMeasure;
    }
}
