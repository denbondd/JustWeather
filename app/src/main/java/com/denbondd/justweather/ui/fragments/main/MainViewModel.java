package com.denbondd.justweather.ui.fragments.main;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.lifecycle.MutableLiveData;

import com.denbondd.justweather.AppApplication;
import com.denbondd.justweather.R;
import com.denbondd.justweather.models.CurrentWeatherOWMModel;
import com.denbondd.justweather.models.MoreInfoItemModel;
import com.denbondd.justweather.models.MoreInfoTypeEnum;
import com.denbondd.justweather.models.OneCallOWMModel;
import com.denbondd.justweather.ui.base.BaseVM;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends BaseVM {
    public MutableLiveData<Call<OneCallOWMModel>> oneCallOWM = new MutableLiveData<>();
    public MutableLiveData<String> currentLocationNameOWM = new MutableLiveData<>();

    public void updateLocationOWM(double lat, double lon) {
        oneCallOWM.setValue(apiHelper.getOneCallOWM(lat, lon));
        updateCurrentLocationNameOWM(lat, lon);
    }

    public void updateCurrentLocationNameOWM(double lat, double lon) {
        apiHelper.getCurrentWeatherOWM(lat, lon).enqueue(new Callback<CurrentWeatherOWMModel>() {
            @Override
            public void onResponse(@NotNull Call<CurrentWeatherOWMModel> call, @NotNull Response<CurrentWeatherOWMModel> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(AppApplication.getContext(), response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                CurrentWeatherOWMModel currentWeatherOWMModel = response.body();
                if (currentWeatherOWMModel != null) {
                    currentLocationNameOWM.postValue(currentWeatherOWMModel.getName());
                }
            }

            @Override
            public void onFailure(@NotNull Call<CurrentWeatherOWMModel> call, @NotNull Throwable t) {
                Toast.makeText(AppApplication.getContext(), "Can't get location name", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public ArrayList<MoreInfoItemModel> getMoreInfoArray(OneCallOWMModel oneCallOWM) {
        ArrayList<MoreInfoItemModel> answ = new ArrayList<>();
        answ.add(new MoreInfoItemModel(
                MoreInfoTypeEnum.HUMIDITY,
                getString(R.string.humidity),
                oneCallOWM.getCurrent().getHumidity(),
                getDrawable(R.drawable.ic_humidity),
                getString(R.string.percent)
        ));
        answ.add(new MoreInfoItemModel(
                MoreInfoTypeEnum.WIND,
                getString(R.string.wind),
                oneCallOWM.getCurrent().getWindSpeed(),
                getDrawable(R.drawable.ic_winddirection),
                getString(R.string.m_per_sec)
        ));
        answ.add(new MoreInfoItemModel(
                MoreInfoTypeEnum.PRESSURE,
                getString(R.string.pressure),
                oneCallOWM.getCurrent().getPressure(),
                getDrawable(R.drawable.ic_pressure),
                getString(R.string.h_pa)
        ));
        answ.add(new MoreInfoItemModel(
                MoreInfoTypeEnum.CLOUDINESS,
                getString(R.string.cloudiness),
                oneCallOWM.getCurrent().getClouds(),
                getDrawable(R.drawable.ic_cloudiness),
                getString(R.string.percent)
        ));
        answ.add(new MoreInfoItemModel(
                MoreInfoTypeEnum.UV_INDEX,
                getString(R.string.uv_index),
                oneCallOWM.getCurrent().getUvi(),
                getDrawable(R.drawable.ic_uvindex),
                ""
        ));
        answ.add(new MoreInfoItemModel(
                MoreInfoTypeEnum.FEELS_LIKE,
                getString(R.string.feels_like),
                oneCallOWM.getCurrent().getFeelsLike(),
                getDrawable(R.drawable.ic_feelslike),
                getString(R.string.celsiusSign)
        ));
        return answ;
    }

    public boolean checkLocationPermissions() {
        return ActivityCompat.checkSelfPermission(AppApplication.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(AppApplication.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * open weather map returns weather id. this method take this id and return icon according to weather id
     * @param iconId weather id
     * @return drawable icon
     */
    public Drawable getIconById(int iconId) {
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

    private Drawable getDrawable(int id) {
        return AppApplication.getContext().getDrawable(id);
    }

    private String getString(int id) {
        return AppApplication.getContext().getString(id);
    }

    /**
     * check if it's night or day
     *
     * @return if it's day, return true. otherwise return false
     */
    private boolean isNowSunOrMoon() {
        Calendar calendar = Calendar.getInstance();
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        return !(hours <= 4 || hours >= 22);
    }

    private Drawable getDrawableAccordingToDay(int dayId, int nightId) {
        return isNowSunOrMoon() ? getDrawable(dayId) : getDrawable(nightId);
    }
}