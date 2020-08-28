package com.denbondd.justweather.util;

public class UnitsConverter {

    public static String celsiusToFahrenheit(double celsius) {
        return getRounded(celsius * 9 / 5 + 32);
    }

    public static String celsiusToKelvin(double celsius) {
        return getRounded(celsius + 273.3);
    }

    public static String meterPerSecToMph(double meterPerSec) {
        return getRounded(meterPerSec * 3600 / 1600);
    }

    public static String meterPerSecToKmPerHour(double meterPerSec) {
        return getRounded(meterPerSec * 3600 / 1000);
    }

    public static String mBarToKPa(double mBar) {
        return getRounded(mBar / 10);
    }

    public static String mBarToHhHg(double mBar) {
        return getRounded(mBar * 1.333);
    }

    public static String getRounded(double data) {
        if (data % 1 != 0) return "" + Math.round(data * 10.0) / 10.0;
        return "" + (int) data;
    }

}
