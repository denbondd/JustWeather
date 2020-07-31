package com.denbondd.justweather.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class City implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private boolean isGeolocation;
    private double lat;
    private double lon;
    private boolean isCurrent;

    @Ignore
    public City(boolean isGeolocation) {
        this.isGeolocation = isGeolocation;
    }

    public City(String name, boolean isGeolocation, double lat, double lon, boolean isCurrent) {
        this.name = name;
        this.isGeolocation = isGeolocation;
        this.lat = lat;
        this.lon = lon;
        this.isCurrent = isCurrent;
    }

    protected City(Parcel in) {
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

    public static final Creator<City> CREATOR = new Creator<City>() {
        @Override
        public City createFromParcel(Parcel in) {
            return new City(in);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public boolean isCurrent() {
        return isCurrent;
    }

    public void setCurrent(boolean current) {
        isCurrent = current;
    }

    public static Creator<City> getCREATOR() {
        return CREATOR;
    }
}
