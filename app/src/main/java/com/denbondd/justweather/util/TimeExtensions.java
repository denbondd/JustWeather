package com.denbondd.justweather.util;

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
}
