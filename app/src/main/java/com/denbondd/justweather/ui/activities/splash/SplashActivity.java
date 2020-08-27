package com.denbondd.justweather.ui.activities.splash;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.preference.PreferenceManager;

import com.denbondd.justweather.AppApplication;
import com.denbondd.justweather.R;
import com.denbondd.justweather.ui.activities.main.MainActivity;
import com.denbondd.justweather.ui.base.BaseActivity;
import com.denbondd.justweather.util.ActivityExtensions;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.concurrent.atomic.AtomicBoolean;

import static com.denbondd.justweather.util.Constants.LOCATION_PERMISSION_CODE;
import static com.denbondd.justweather.util.Constants.PREFERENCES_NEED_PERMISSION;
import static com.denbondd.justweather.util.Constants.SHOW_ADD_CITY;

public class SplashActivity extends BaseActivity<SplashVM> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public Class<SplashVM> getViewModelClass() {
        return SplashVM.class;
    }

    MainActivity mainActivity;
    Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainActivity = new MainActivity();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (sharedPreferences.getBoolean(PREFERENCES_NEED_PERMISSION, true)) {
            getViewModel().firstStart(getBaseContext());
            showPrePermissionDialog();
            sharedPreferences.edit().putBoolean(PREFERENCES_NEED_PERMISSION, false).apply();
        } else {
            getViewModel().notFirstStart(getBaseContext());
            new Handler().postDelayed(() -> startMainActivity(false), 1500);
            return;
        }

        AtomicBoolean isCityAdded = new AtomicBoolean(false);
        getViewModel().isCityReadyToGo.observe(this, isCityReady -> {
            if (isCityReady != null && isCityReady) {
                if (!isCityAdded.get()) getViewModel().insertCity();
                isCityAdded.set(true);
                new Handler().postDelayed(() -> startMainActivity(false), 1500);
            }
        });
    }

    private void showPrePermissionDialog() {
        new MaterialAlertDialogBuilder(this, R.style.AlertDialog)
                .setMessage(R.string.locationDialogMessage)
                .setCancelable(false)
                .setNegativeButton(R.string.typeCity, (dialog, which) -> addCityManually())
                .setPositiveButton(R.string.useLocation, (dialog, which) -> askPermission())
                .show();
    }

    private void askPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                addGeolocationCity();
                setLocationToCity();
            } else {
                Toast.makeText(this, "City will be added manually", Toast.LENGTH_SHORT).show();
                addCityManually();
            }
        }
    }

    private void addGeolocationCity() {
        getViewModel().addGeolocationCity();
    }

    @SuppressLint("MissingPermission")
    private void setLocationToCity() {
        if (getViewModel().geolocationCity.isGeolocation() && AppApplication.checkLocationPermissions()) {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (locationManager != null) {
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                getViewModel().setCityLocation(location);
            }
        } else {
            Toast.makeText(this, "Something went wrong. Please, add city manually", Toast.LENGTH_SHORT).show();
            addCityManually();
        }
    }

    private void addCityManually() {
        startMainActivity(true);
    }

    private void startMainActivity(boolean showAddCity) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(SHOW_ADD_CITY, showAddCity);
        ActivityExtensions.startSplashActivityWithAnim(this, intent);
        finish();
    }
}