package com.denbondd.justweather.util;

import android.graphics.drawable.Drawable;

import com.denbondd.justweather.AppApplication;
import com.denbondd.justweather.R;

import java.util.Calendar;

public class OWMExtensions {

    public static String getRoundedOrNot(double data) {
        if (data % 0.1 != 0) data = Math.round(data * 10.0) / 10.0;
        return data % 1 == 0 ? Integer.toString((int) data) : Double.toString(data);
    }

    /**
     * open weather map returns weather id. this method take this id and return icon according to weather id
     * @param iconId weather id
     * @return drawable icon
     */
    public static Drawable getIconById(int iconId) {
        switch (iconId) {
            case 200:
            case 201:
            case 230:
            case 231:
                return getDrawableAccordingToDay(R.drawable.ic_weather1d, R.drawable.ic_weather1n);
            case 203:
            case 232:
                return getDrawableAccordingToDay(R.drawable.ic_weather2d, R.drawable.ic_weather2n);
            case 210:
            case 211:
                return getDrawableAccordingToDay(R.drawable.ic_weather3d, R.drawable.ic_weather3n);
            case 212:
            case 221:
                return getDrawableAccordingToDay(R.drawable.ic_weather4d, R.drawable.ic_weather4n);
            case 300:
            case 310:
            case 500:
                return getDrawableAccordingToDay(R.drawable.ic_weather5d, R.drawable.ic_weather5n);
            case 313:
            case 520:
                return getDrawable(R.drawable.ic_weather6);
            case 301:
            case 311:
            case 501:
                return getDrawableAccordingToDay(R.drawable.ic_weather7d, R.drawable.ic_weather7n);
            case 521:
                return getDrawable(R.drawable.ic_weather8);
            case 302:
            case 502:
            case 503:
            case 504:
                return getDrawableAccordingToDay(R.drawable.ic_weather8d, R.drawable.ic_weather8n);
            case 314:
            case 522:
            case 531:
                return getDrawable(R.drawable.ic_weather9);
            case 511:
                return getDrawableAccordingToDay(R.drawable.ic_weather10d, R.drawable.ic_weather10n);
            case 600:
            case 601:
            case 602:
                return getDrawableAccordingToDay(R.drawable.ic_weather11d, R.drawable.ic_weather11n);
            case 620:
            case 621:
            case 622:
                return getDrawable(R.drawable.ic_weather12);
            case 615:
            case 616:
                return getDrawableAccordingToDay(R.drawable.ic_weather13d, R.drawable.ic_weather13n);
            case 613:
                return getDrawable(R.drawable.ic_weather14);
            case 701:
            case 711:
            case 721:
            case 741:
                return getDrawable(R.drawable.ic_weather15);
            case 731:
            case 751:
            case 761:
            case 762:
            case 771:
                return getDrawable(R.drawable.ic_weather16);
            case 781:
                return getDrawable(R.drawable.ic_weather17);
            case 800:
                return getDrawableAccordingToDay(R.drawable.ic_weather18d, R.drawable.ic_weather18n);
            case 801:
                return getDrawableAccordingToDay(R.drawable.ic_weather19d, R.drawable.ic_weather19n);
            case 802:
                return getDrawable(R.drawable.ic_weather20);
            case 803:
            case 804:
                return getDrawable(R.drawable.ic_weather21);
            default:
                return null;
        }
    }

    private static Drawable getDrawable(int id) {
        return AppApplication.getContext().getDrawable(id);
    }

    /**
     * check if it's night or day
     *
     * @return if it's day, return true. otherwise return false
     */
    private static boolean isNowSunOrMoon() {
        Calendar calendar = Calendar.getInstance();
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        return !(hours <= 4 || hours >= 22);
    }

    private static Drawable getDrawableAccordingToDay(int dayId, int nightId) {
        return isNowSunOrMoon() ? getDrawable(dayId) : getDrawable(nightId);
    }
}
