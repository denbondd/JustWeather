package com.denbondd.justweather.ui.fragments.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextClock;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.denbondd.justweather.R;
import com.denbondd.justweather.databinding.MainFragmentBinding;
import com.denbondd.justweather.models.OneCallOWMModel;
import com.denbondd.justweather.ui.base.BaseFragment;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainFragment extends BaseFragment<MainViewModel> {
    @Override
    public int getLayoutId() {
        return R.layout.main_fragment;
    }

    @Override
    public Class<MainViewModel> getViewModelClass() {
        return MainViewModel.class;
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    private static final int LOCATION_PERMISSION_CODE = 91;
    private MainFragmentBinding binding;
    private Location location;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding = MainFragmentBinding.bind(view);
        binding.setDate(System.currentTimeMillis());

        getViewModel().currentLocationNameOWM.observe(getViewLifecycleOwner(), name -> {
            if (name != null) {
                binding.setCityName(name);
            }
        });
        getViewModel().oneCallOWM.observe(getViewLifecycleOwner(), oneCallOWMCall -> {
            if (oneCallOWMCall != null) {
                oneCallOWMCall.enqueue(new Callback<OneCallOWMModel>() {
                    @Override
                    public void onResponse(@NotNull Call<OneCallOWMModel> call, @NotNull Response<OneCallOWMModel> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(getContext(), response.code(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        OneCallOWMModel oneCallOWM = response.body();
                        if (oneCallOWM != null) {
                            binding.setOneCallOWMModel(oneCallOWM);
                            binding.setWeatherIcon(getViewModel().getIconById(oneCallOWM.getCurrent().getWeather().get(0).getId()));
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<OneCallOWMModel> call, @NotNull Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        updateLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                updateLocation();
            } else {
                askPermission();
            }
        }
    }

    private void askPermission() {
        ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_CODE);
    }

    @SuppressLint("MissingPermission")
    private void updateLocation() {
        if (getViewModel().checkLocationPermissions()) {
            LocationManager locationManager = (LocationManager) Objects.requireNonNull(getActivity()).getSystemService(Context.LOCATION_SERVICE);
            if (locationManager != null) {
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                updateWeatherLiveData();
            }
        } else {
            askPermission();
            Toast.makeText(getContext(), "No permission granted", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateWeatherLiveData() {
        getViewModel().updateLocationOWM(location.getLatitude(), location.getLongitude());
    }
}