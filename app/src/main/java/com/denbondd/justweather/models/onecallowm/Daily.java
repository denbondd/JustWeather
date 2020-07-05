package com.denbondd.justweather.models.onecallowm;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Daily implements Parcelable {

    @SerializedName("dt")
    @Expose
    private Integer dt;
    @SerializedName("sunrise")
    @Expose
    private Integer sunrise;
    @SerializedName("sunset")
    @Expose
    private Integer sunset;
    @SerializedName("temp")
    @Expose
    private Temp temp;
    @SerializedName("feels_like")
    @Expose
    private FeelsLike feelsLike;
    @SerializedName("pressure")
    @Expose
    private Integer pressure;
    @SerializedName("humidity")
    @Expose
    private Integer humidity;
    @SerializedName("dew_point")
    @Expose
    private Double dewPoint;
    @SerializedName("wind_speed")
    @Expose
    private Double windSpeed;
    @SerializedName("wind_deg")
    @Expose
    private Integer windDeg;
    @SerializedName("weather")
    @Expose
    private List<Weather> weather;
    @SerializedName("clouds")
    @Expose
    private Integer clouds;
    @SerializedName("uvi")
    @Expose
    private Double uvi;
    @SerializedName("rain")
    @Expose
    private Double rain;

    public Integer getDt() {
        return dt;
    }

    public Integer getSunrise() {
        return sunrise;
    }

    public Integer getSunset() {
        return sunset;
    }

    public Temp getTemp() {
        return temp;
    }

    public FeelsLike getFeelsLike() {
        return feelsLike;
    }

    public Integer getPressure() {
        return pressure;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public Double getDewPoint() {
        return dewPoint;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public Integer getWindDeg() {
        return windDeg;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public Integer getClouds() {
        return clouds;
    }

    public Double getUvi() {
        return uvi;
    }

    public Double getRain() {
        return rain;
    }

    protected Daily(Parcel in) {
        if (in.readByte() == 0) {
            dt = null;
        } else {
            dt = in.readInt();
        }
        if (in.readByte() == 0) {
            sunrise = null;
        } else {
            sunrise = in.readInt();
        }
        if (in.readByte() == 0) {
            sunset = null;
        } else {
            sunset = in.readInt();
        }
        if (in.readByte() == 0) {
            pressure = null;
        } else {
            pressure = in.readInt();
        }
        if (in.readByte() == 0) {
            humidity = null;
        } else {
            humidity = in.readInt();
        }
        if (in.readByte() == 0) {
            dewPoint = null;
        } else {
            dewPoint = in.readDouble();
        }
        if (in.readByte() == 0) {
            windSpeed = null;
        } else {
            windSpeed = in.readDouble();
        }
        if (in.readByte() == 0) {
            windDeg = null;
        } else {
            windDeg = in.readInt();
        }
        if (in.readByte() == 0) {
            clouds = null;
        } else {
            clouds = in.readInt();
        }
        if (in.readByte() == 0) {
            uvi = null;
        } else {
            uvi = in.readDouble();
        }
        if (in.readByte() == 0) {
            rain = null;
        } else {
            rain = in.readDouble();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (dt == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(dt);
        }
        if (sunrise == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(sunrise);
        }
        if (sunset == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(sunset);
        }
        if (pressure == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(pressure);
        }
        if (humidity == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(humidity);
        }
        if (dewPoint == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(dewPoint);
        }
        if (windSpeed == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(windSpeed);
        }
        if (windDeg == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(windDeg);
        }
        if (clouds == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(clouds);
        }
        if (uvi == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(uvi);
        }
        if (rain == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(rain);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Daily> CREATOR = new Creator<Daily>() {
        @Override
        public Daily createFromParcel(Parcel in) {
            return new Daily(in);
        }

        @Override
        public Daily[] newArray(int size) {
            return new Daily[size];
        }
    };
}
