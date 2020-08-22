package com.denbondd.justweather.ui.fragments.weather;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;

import com.denbondd.justweather.AppApplication;
import com.denbondd.justweather.R;
import com.denbondd.justweather.models.MoreInfoItemModel;
import com.denbondd.justweather.models.MoreInfoTypeEnum;
import com.denbondd.justweather.models.OneCallOWMModel;
import com.denbondd.justweather.ui.base.BaseVM;
import com.denbondd.justweather.util.WeatherExtensions;

import java.util.ArrayList;

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
                getDrawable(R.drawable.ic_humidity),
                getString(R.string.humidity),
                oneCallOWM.getCurrent().getHumidity() + getString(R.string.percent)
        ));
        answ.add(new MoreInfoItemModel(
                MoreInfoTypeEnum.WIND,
                getDrawable(R.drawable.ic_winddirection),
                getString(R.string.wind),
                WeatherExtensions.getWindStr(oneCallOWM.getCurrent().getWindSpeed(), -1, false)
        ));
        answ.add(new MoreInfoItemModel(
                MoreInfoTypeEnum.PRESSURE,
                getDrawable(R.drawable.ic_pressure),
                getString(R.string.pressure),
                WeatherExtensions.getPressureStr(oneCallOWM.getCurrent().getPressure())
        ));
        answ.add(new MoreInfoItemModel(
                MoreInfoTypeEnum.CLOUDINESS,
                getDrawable(R.drawable.ic_cloudiness),
                getString(R.string.cloudiness),
                oneCallOWM.getCurrent().getClouds() + getString(R.string.percent)
        ));
        answ.add(new MoreInfoItemModel(
                MoreInfoTypeEnum.UV_INDEX,
                getDrawable(R.drawable.ic_uvindex),
                getString(R.string.uv_index),
                Double.toString(oneCallOWM.getCurrent().getUvi())
        ));
        answ.add(new MoreInfoItemModel(
                MoreInfoTypeEnum.FEELS_LIKE,
                getDrawable(R.drawable.ic_feelslike),
                getString(R.string.feels_like),
                WeatherExtensions.getTemp(oneCallOWM.getCurrent().getFeelsLike())
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