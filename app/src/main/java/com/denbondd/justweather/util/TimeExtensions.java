package com.denbondd.justweather.util;

import android.text.format.DateFormat;

import com.denbondd.justweather.AppApplication;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TimeExtensions {
    public static String convertMillisToDate(long millis, String pattern) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.US);
        return simpleDateFormat.format(calendar.getTime());
    }

    public static String getHours(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        if (DateFormat.is24HourFormat(AppApplication.getContext())) {
            return new SimpleDateFormat("H", Locale.US).format(calendar.getTime());
        } else {
            return new SimpleDateFormat("h a", Locale.US).format(calendar.getTime());
        }
    }
}
