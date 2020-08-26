package com.denbondd.justweather.util;

import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.denbondd.justweather.AppApplication;
import com.denbondd.justweather.R;

import java.util.Locale;

import static com.denbondd.justweather.util.UnitsConverter.celsiusToFahrenheit;
import static com.denbondd.justweather.util.UnitsConverter.celsiusToKelvin;
import static com.denbondd.justweather.util.UnitsConverter.getRounded;
import static com.denbondd.justweather.util.UnitsConverter.mBarToHhHg;
import static com.denbondd.justweather.util.UnitsConverter.mBarToKPa;
import static com.denbondd.justweather.util.UnitsConverter.meterPerSecToKmPerHour;
import static com.denbondd.justweather.util.UnitsConverter.meterPerSecToMph;

public class WeatherExtensions {

    public static String getTemp(double temp) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AppApplication.getContext());
        String key = sharedPreferences.getString(getString(R.string.temperature_key), "c");
        switch (key) {
            case "c":
                return Math.round(temp) + getString(R.string.celsiusSign);
            case "f":
                return Math.round(celsiusToFahrenheit(temp)) + getString(R.string.fahrenheitSign);
            case "k":
                return Math.round(celsiusToKelvin(temp)) + getString(R.string.kelvinSign);
            default:
                return null;
        }
    }

    public static String getMaxMinTempStr(double max, double min) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AppApplication.getContext());
        String key = sharedPreferences.getString(getString(R.string.temperature_key), "c");
        switch (key) {
            case "c":
                return Math.round(max) + getString(R.string.celsiusSign) + " / " + Math.round(min) + getString(R.string.celsiusSign);
            case "f":
                return Math.round(celsiusToFahrenheit(max)) + getString(R.string.fahrenheitSign) + " / " + Math.round(celsiusToFahrenheit(min)) + getString(R.string.fahrenheitSign);
            case "k":
                return Math.round(celsiusToKelvin(max)) + getString(R.string.kelvinSign) + " / " + Math.round(celsiusToKelvin(min)) + getString(R.string.kelvinSign);
            default:
                return null;
        }
    }

    public static String getMiddleFeelsLikeTempStr(double middle, double feelsLike) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AppApplication.getContext());
        String key = sharedPreferences.getString(getString(R.string.temperature_key), "c");
        switch (key) {
            case "c":
                return Math.round(middle) + getString(R.string.celsiusSign) + "  " + getString(R.string.feels_like) + "  " + Math.round(feelsLike) + getString(R.string.celsiusSign);
            case "f":
                return Math.round(celsiusToFahrenheit(middle)) + getString(R.string.fahrenheitSign) + "  " + getString(R.string.feels_like) + "  " + Math.round(celsiusToFahrenheit(feelsLike)) + getString(R.string.fahrenheitSign);
            case "k":
                return Math.round(celsiusToKelvin(middle)) + getString(R.string.kelvinSign) + "  " + getString(R.string.feels_like) + "  " + Math.round(celsiusToKelvin(feelsLike)) + getString(R.string.kelvinSign);
            default:
                return null;
        }
    }

    public static String getWindStr(double speed, double degrees, boolean longVersion) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AppApplication.getContext());
        switch (sharedPreferences.getString("speed", "kmh")) {
            case "kmh":
                if (longVersion)
                    return getString(R.string.wind) + "  " + meterPerSecToKmPerHour(speed)
                            + getElementInStrArray(R.array.speed_entries, 0)
                            + "  " + getWindDirection(degrees);
                else
                    return meterPerSecToKmPerHour(speed) + getElementInStrArray(R.array.speed_entries, 0);
            case "mph":
                if (longVersion)
                    return getString(R.string.wind) + "  " + meterPerSecToMph(speed) + getElementInStrArray(R.array.speed_entries, 1) + "  " + getWindDirection(degrees);
                else
                    return meterPerSecToMph(speed) + getElementInStrArray(R.array.speed_entries, 1);
            case "ms":
                if (longVersion)
                    return getString(R.string.wind) + "  " + getRounded(speed) + getElementInStrArray(R.array.speed_entries, 2) + "  " + getWindDirection(degrees);
                else
                    return getRounded(speed) + getElementInStrArray(R.array.speed_entries, 2);
            default:
                return null;
        }
    }

    public static String getPressureStr(double pressure) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AppApplication.getContext());
        switch (sharedPreferences.getString("pressure", "mBar")) {
            case "mbar":
                return "" + getRounded(pressure) + getElementInStrArray(R.array.pressure_entries, 0);
            case "kpa":
                return "" + mBarToKPa(pressure) + getElementInStrArray(R.array.pressure_entries, 1);
            case "mmhg":
                return "" + mBarToHhHg(pressure) + getElementInStrArray(R.array.pressure_entries, 2);
            default:
                return null;
        }
    }

    public static String getHumidityStr(int percents) {
        return getString(R.string.humidity) + "  " + percents + '%';
    }

    public static String getCityStr(String city, String countryCode) {
        Locale locale = new Locale("", countryCode);
        String country = locale.getDisplayCountry(Locale.US);
        return city + ", " + country;
    }

    private static String getWindDirection(double degrees) {
        if (degrees >= 337.5 && degrees <= 360 || degrees < 23.5) {
            return getString(R.string.n);
        } else if (degrees >= 23.5 && degrees < 62.5) {
            return getString(R.string.ne);
        } else if (degrees >= 62.5 && degrees < 112.5) {
            return getString(R.string.e);
        } else if (degrees >= 112.5 && degrees < 152.5) {
            return getString(R.string.se);
        } else if (degrees >= 152.5 && degrees < 202.5) {
            return getString(R.string.s);
        } else if (degrees >= 202.5 && degrees < 242.5) {
            return getString(R.string.sw);
        } else if (degrees >= 242.5 && degrees < 292.5) {
            return getString(R.string.w);
        } else if (degrees >= 292.5 && degrees < 337.5) {
            return getString(R.string.nw);
        } else {
            return null;
        }
    }

    private static String getElementInStrArray(int arrayId, int position) {
        return AppApplication.getContext().getResources().getStringArray(arrayId)[position];
    }

    private static String getString(int id) {
        return AppApplication.getContext().getString(id);
    }
}
