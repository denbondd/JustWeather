package com.denbondd.justweather.ui.fragments.main;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherViewModel extends BaseVM {
    public MutableLiveData<OneCallOWMModel> oneCallOWM = new MutableLiveData<>();
    public MutableLiveData<String> currentLocationNameOWM = new MutableLiveData<>();

    public void updateLocationOWM(double lat, double lon) {
        weatherGetterOWM.updateOneCall(apiHelper, lat, lon);
        weatherGetterOWM.oneCall.observeForever(oneCallOWMModel -> oneCallOWM.postValue(oneCallOWMModel));
        updateCurrentLocationNameOWM(lat, lon);
    }

    public void updateCurrentLocationNameOWM(double lat, double lon) {
        weatherGetterOWM.updateCityName(apiHelper, lat, lon);
        weatherGetterOWM.cityName.observeForever(name -> currentLocationNameOWM.postValue(name));
    }

    public boolean checkInternetConnection() {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo mobileNI = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            NetworkInfo wifiNI = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo ethernetNI = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET);
            NetworkInfo vpnNI = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_VPN);

            boolean mobile = mobileNI.getState() == NetworkInfo.State.CONNECTED || mobileNI.getState() == NetworkInfo.State.CONNECTING;
            boolean wifi = wifiNI.getState() == NetworkInfo.State.CONNECTED || wifiNI.getState() == NetworkInfo.State.CONNECTING;
            boolean ethernet = ethernetNI.getState() == NetworkInfo.State.CONNECTED || ethernetNI.getState() == NetworkInfo.State.CONNECTING;
            boolean vpn = vpnNI.getState() == NetworkInfo.State.CONNECTED || vpnNI.getState() == NetworkInfo.State.CONNECTING;

            return mobile || wifi || ethernet || vpn;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
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

    private String getString(int id) {
        return AppApplication.getContext().getString(id);
    }

    private Drawable getDrawable(int id) {
        return ContextCompat.getDrawable(AppApplication.getContext(), id);
    }
}