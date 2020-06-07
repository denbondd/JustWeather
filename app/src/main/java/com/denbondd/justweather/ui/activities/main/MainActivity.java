package com.denbondd.justweather.ui.activities.main;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.denbondd.justweather.BuildConfig;
import com.denbondd.justweather.R;
import com.denbondd.justweather.models.CurrentWeatherModel;
import com.denbondd.justweather.services.OpenWeatherMapApi;
import com.denbondd.justweather.ui.base.BaseActivity;
import com.denbondd.justweather.util.ActivityExtensionsKt;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends BaseActivity<MainVM> {
    private Toolbar tbMain;
    private DrawerLayout dlMain;
    private TextView txtAnswer;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public Class<MainVM> getViewModelClass() {
        return MainVM.class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tbMain = findViewById(R.id.tbMain);
        dlMain = findViewById(R.id.dlMain);
        txtAnswer = findViewById(R.id.txtAnswer);

        setSupportActionBar(tbMain);
        if (getSupportActionBar() != null) getSupportActionBar().setTitle("");
        ActivityExtensionsKt.setMenuIcon(this, dlMain, tbMain);

        getViewModel().getCurrentWeatherOWM(50.06, 36.17).enqueue(new Callback<CurrentWeatherModel>() {
            @Override
            public void onResponse(@NotNull Call<CurrentWeatherModel> call, @NotNull Response<CurrentWeatherModel> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                CurrentWeatherModel currentWeather = response.body();
                txtAnswer.setText("Current temp is " + (currentWeather.getMain().getTemp() - 273));
            }

            @Override
            public void onFailure(@NotNull Call<CurrentWeatherModel> call, @NotNull Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}