package com.denbondd.justweather.ui.fragments.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.denbondd.justweather.AppApplication;
import com.denbondd.justweather.R;
import com.denbondd.justweather.databinding.MainFragmentBinding;
import com.denbondd.justweather.db.AppDatabase;
import com.denbondd.justweather.models.City;
import com.denbondd.justweather.models.OneCallOWMModel;
import com.denbondd.justweather.models.onecallowm.Daily;
import com.denbondd.justweather.models.onecallowm.Hourly;
import com.denbondd.justweather.ui.activities.main.MainActivity;
import com.denbondd.justweather.ui.adapters.moreinfo.MoreInfoRVAdapter;
import com.denbondd.justweather.ui.base.BaseFragment;
import com.denbondd.justweather.ui.fragments.daily.DailyFragment;
import com.denbondd.justweather.ui.fragments.hourly.HourlyFragment;
import com.denbondd.justweather.util.FragmentExtensions;
import com.denbondd.justweather.util.OWMExtensions;

import java.util.ArrayList;

import javax.inject.Inject;

import static com.denbondd.justweather.util.Constants.CITY_KEY;

public class MainFragment extends BaseFragment<MainViewModel> {
    @Override
    public int getLayoutId() {
        return R.layout.main_fragment;
    }

    @Override
    public Class<MainViewModel> getViewModelClass() {
        return MainViewModel.class;
    }

    public static MainFragment newInstance(City city) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(CITY_KEY, city);
        MainFragment mainFragment = new MainFragment();
        mainFragment.setArguments(bundle);
        return mainFragment;
    }

    private MainFragmentBinding binding;
    private Location location;
    private OneCallOWMModel oneCallOWMModel;
    private City city;

    private RecyclerView moreInfoRecyclerView;
    private ImageView ivWeatherIco;
    private SwipeRefreshLayout srlMainFragment;

    @Inject
    AppDatabase appDatabase;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        moreInfoRecyclerView = view.findViewById(R.id.rvMoreInfo);
        ivWeatherIco = view.findViewById(R.id.ivWeatherIco);
        srlMainFragment = view.findViewById(R.id.srlMainFragment);
        city = requireArguments().getParcelable(CITY_KEY);

        view.findViewById(R.id.btnHourly).setOnClickListener(v -> btnHourlyOnClick());
        view.findViewById(R.id.btnDaily).setOnClickListener(v -> btnDailyOnClick());

        binding = MainFragmentBinding.bind(view);
        binding.setDate(System.currentTimeMillis());

        srlMainFragment.setRefreshing(true);
        useCityFromVM();

        srlMainFragment.setOnRefreshListener(() -> {
            if (!getViewModel().checkInternetConnection()) {
                Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                srlMainFragment.setRefreshing(false);
            } else {
                updateCityLocation();
            }
        });
        srlMainFragment.setColorSchemeResources(R.color.gradientStartColor);
    }

    private void useCityFromVM() {
        getViewModel().currentLocationNameOWM.observe(getViewLifecycleOwner(), name -> {
            if (name != null) {
                binding.setCityName(name);
                if (!city.getName().equals(name)) city.setName(name);
            }
        });
        getViewModel().oneCallOWM.observe(getViewLifecycleOwner(), oneCallOWM -> {
            if (oneCallOWM != null) {
                oneCallOWMModel = oneCallOWM;
                binding.setOneCallOWMModel(oneCallOWMModel);
                Glide.with(requireContext())
                        .load(OWMExtensions.getIconById(oneCallOWMModel.getCurrent().getWeather().get(0).getId()))
                        .into(ivWeatherIco);
                makeMoreInfoRecycler(oneCallOWMModel);
                if (city.getLon() != oneCallOWM.getLon() && city.getLat() != oneCallOWM.getLat()) {
                    city.setLat(oneCallOWM.getLat());
                    city.setLon(oneCallOWM.getLon());
                    updateCities();
                }
            }
            srlMainFragment.setRefreshing(false);
        });
        updateCityLocation();
    }

    private void updateCities() {
        new Thread(() -> appDatabase.cityDao().update(city)).start();
        new Handler().postDelayed(() -> ((MainActivity) requireActivity()).updateCities(), 1000);
    }

    private void btnHourlyOnClick() {
        FragmentExtensions.addFragmentWithAnim(
                (AppCompatActivity) requireActivity(),
                HourlyFragment.newInstance((ArrayList<Hourly>) oneCallOWMModel.getHourly()),
                "HourlyFragment",
                R.id.fcvMainContainer,
                true, true
        );
        ((MainActivity) requireActivity()).setBackArrow();
    }

    private void btnDailyOnClick() {
        FragmentExtensions.addFragmentWithAnim(
                (AppCompatActivity) requireActivity(),
                DailyFragment.newInstance((ArrayList<Daily>) oneCallOWMModel.getDaily()),
                "DailyFragment",
                R.id.fcvMainContainer,
                true, true
        );
        ((MainActivity) requireActivity()).setBackArrow();
    }

    private void makeMoreInfoRecycler(OneCallOWMModel oneCallOWM) {
        MoreInfoRVAdapter adapter = new MoreInfoRVAdapter();
        adapter.setItems(getViewModel().getMoreInfoArray(oneCallOWM));
        moreInfoRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        moreInfoRecyclerView.setAdapter(adapter);
    }

    private void updateWeatherLiveData() {
        getViewModel().updateLocationOWM(location.getLatitude(), location.getLongitude());
    }

    @SuppressLint("MissingPermission")
    private void updateCityLocation() {
        if (city.isGeolocation() && getViewModel().checkLocationPermissions()) {
            LocationManager locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);
            if (locationManager != null) {
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                updateWeatherLiveData();
            }
        } else {
            getViewModel().updateLocationOWM(city.getLat(), city.getLon());
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        AppApplication.getAppComponent().inject(this);
    }
}