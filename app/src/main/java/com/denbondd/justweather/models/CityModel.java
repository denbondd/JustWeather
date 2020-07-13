package com.denbondd.justweather.models;

import android.os.Parcel;
import android.os.Parcelable;

public class CityModel implements Parcelable {

    private String name;
    private boolean isGeolocation;
    private double lat;
    private double lon;
    private boolean isCurrent;

    public CityModel(String name, boolean isGeolocation, double lat, double lon) {
        this.name = name;
        this.isGeolocation = isGeolocation;
        this.lat = lat;
        this.lon = lon;
    }

    public CityModel(boolean isGeolocation) {
        this.isGeolocation = isGeolocation;
    }

    protected CityModel(Parcel in) {
        name = in.readString();
        isGeolocation = in.readByte() != 0;
        lat = in.readDouble();
        lon = in.readDouble();
        isCurrent = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeByte((byte) (isGeolocation ? 1 : 0));
        dest.writeDouble(lat);
        dest.writeDouble(lon);
        dest.writeByte((byte) (isCurrent ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CityModel> CREATOR = new Creator<CityModel>() {
        @Override
        public CityModel createFromParcel(Parcel in) {
            return new CityModel(in);
        }

        @Override
        public CityModel[] newArray(int size) {
            return new CityModel[size];
        }
    };

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

    public boolean isCurrent() {
        return isCurrent;
    }

    public void setCurrent(boolean current) {
        isCurrent = current;
    }
}
