package com.denbondd.justweather.ui.activities.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;

import com.denbondd.justweather.R;
import com.denbondd.justweather.databinding.ActivityMainBinding;
import com.denbondd.justweather.models.OneCallOWM;
import com.denbondd.justweather.ui.base.BaseActivity;
import com.denbondd.justweather.util.ActivityExtensions;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity<MainVM> {
    private static final int LOCATION_PERMISSION_CODE = 91;
    private ActivityMainBinding binding;

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

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setDate(System.currentTimeMillis());

        Toolbar tbMain = findViewById(R.id.tbMain);
        DrawerLayout dlMain = findViewById(R.id.dlMain);

        setSupportActionBar(tbMain);
        if (getSupportActionBar() != null) getSupportActionBar().setTitle("");
        ActivityExtensions.setMenuIcon(this, dlMain, tbMain);
        getLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            } else {
                askPermission();
            }
        }
    }

    private void askPermission() {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_CODE);
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        if (getViewModel().checkLocationPermissions()) {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (locationManager != null) {
                Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (location != null) setLocation(location);
            }
        } else {
            askPermission();
            Toast.makeText(this, "No permission granted", Toast.LENGTH_SHORT).show();
        }
    }

    private void setLocation(Location location) {
        getViewModel().getCurrentWeatherOWM(location.getLatitude(), location.getLongitude()).enqueue(new Callback<OneCallOWM>() {
            @Override
            public void onResponse(@NotNull Call<OneCallOWM> call, @NotNull Response<OneCallOWM> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                OneCallOWM oneCallOWM = response.body();
                if (oneCallOWM != null) {
                    binding.setOneCallOWM(oneCallOWM);
                    binding.setWeatherIcon(getViewModel().getIconById(oneCallOWM.getCurrent().getWeather().get(0).getId()));
                }
            }

            @Override
            public void onFailure(@NotNull Call<OneCallOWM> call, @NotNull Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Geocoder geocoder = new Geocoder(this, Locale.US);
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            binding.setCityName(addresses.get(0).getLocality());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}