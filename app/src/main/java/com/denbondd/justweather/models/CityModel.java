package com.denbondd.justweather.models;

public class CityModel {

    private String name;
    private boolean isGeolocation;
    private double lat;
    private double lon;

    public CityModel(String name, boolean isGeolocation, double lat, double lon) {
        this.name = name;
        this.isGeolocation = isGeolocation;
        this.lat = lat;
        this.lon = lon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isGeolocation() {
        return isGeolocation;
    }

    public void setGeolocation(boolean geolocation) {
        isGeolocation = geolocation;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
