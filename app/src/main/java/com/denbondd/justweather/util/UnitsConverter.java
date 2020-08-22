package com.denbondd.justweather.util;

public class UnitsConverter {

    public static double celsiusToFahrenheit(double celsius) {
        return getRounded(celsius * 9 / 5 + 32);
    }

    public static double celsiusToKelvin(double celsius) {
        return getRounded(celsius + 273.3);
    }

    public static double meterPerSecToMph(double meterPerSec) {
        return getRounded(meterPerSec * 3600 / 1600);
    }

    public static double meterPerSecToKmPerHour(double meterPerSec) {
        return getRounded(meterPerSec * 3600 / 1000);
    }

    public static double mBarToKPa(double mBar) {
        return getRounded(mBar / 10);
    }

    public static double mBarToHhHg(double mBar) {
        return getRounded(mBar * 1.333);
    }

    public static double getRounded(double data) {
        if (data % 0.1 != 0) data = Math.round(data * 10.0) / 10.0;
        return data % 1 == 0 ? (int) data : data;
    }

}
