package com.denbondd.justweather.ui.fragments.main;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Handler;
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
import com.denbondd.justweather.ui.activities.main.MainActivity;
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

    private String getString(int id) {
        return AppApplication.getContext().getString(id);
    }

    private Drawable getDrawable(int id) {
        return AppApplication.getContext().getDrawable(id);
    }
}