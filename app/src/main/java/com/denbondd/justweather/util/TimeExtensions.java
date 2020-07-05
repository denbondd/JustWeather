package com.denbondd.justweather.util;

import android.text.format.DateFormat;

import com.denbondd.justweather.AppApplication;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimeExtensions {
    public static String convertMillisToDate(long millis, String pattern) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.US);
        return simpleDateFormat.format(calendar.getTime());
    }

    public static String getDaysUTC(long utx) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.setTimeInMillis(utx * 1000);
        SimpleDateFormat simpleDateFormat;
        simpleDateFormat = new SimpleDateFormat("EEEE, d MMMM", Locale.US);
        return simpleDateFormat.format(calendar.getTime());
    }

    public static String getHoursUTC(long utx) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.setTimeInMillis(utx * 1000);
        SimpleDateFormat simpleDateFormat;
        if (DateFormat.is24HourFormat(AppApplication.getContext())) {
            simpleDateFormat = new SimpleDateFormat("H:mm", Locale.US);
        } else {
            simpleDateFormat = new SimpleDateFormat("h:mm a", Locale.US);
        }
        return simpleDateFormat.format(calendar.getTime());
    }
}
