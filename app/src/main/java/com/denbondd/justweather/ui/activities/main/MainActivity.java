package com.denbondd.justweather.ui.activities.main;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.denbondd.justweather.R;
import com.denbondd.justweather.models.CityModel;
import com.denbondd.justweather.ui.adapters.NavItemsRVAdapter;
import com.denbondd.justweather.ui.base.BaseActivity;
import com.denbondd.justweather.ui.fragments.addcity.AddCityFragment;
import com.denbondd.justweather.ui.fragments.main.MainFragment;
import com.denbondd.justweather.ui.fragments.settings.SettingsFragment;
import com.denbondd.justweather.util.ActivityExtensions;
import com.denbondd.justweather.util.FragmentExtensions;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends BaseActivity<MainVM> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public Class<MainVM> getViewModelClass() {
        return MainVM.class;
    }

    private Toolbar tbMain;
    private DrawerLayout dlMain;
    private RecyclerView rvCities;
    private ArrayList<CityModel> arrayList = new ArrayList<>();
    private NavItemsRVAdapter adapter;
    private Button btnSettings;
    private Button btnAddCity;

    private final String GEOLOCATION_TAG = "Geolocation";
    private final String SETTINGS_TAG = "Settings";
    private final String ADD_CITY_TAG = "AddCity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tbMain = findViewById(R.id.tbMain);
        dlMain = findViewById(R.id.dlMain);
        rvCities = findViewById(R.id.rvCities);
        btnSettings = findViewById(R.id.btnSettings);
        btnAddCity = findViewById(R.id.btnAddCity);

        setSupportActionBar(tbMain);
        if (getSupportActionBar() != null) getSupportActionBar().setTitle("");
        setMenuIcon();
        setRecyclerView();
        makeObserver();

        FragmentExtensions.replaceFragmentWithAnim(
                this,
                MainFragment.newInstance(new CityModel(true)),
                "MainFragment",
                R.id.fcvMainContainer,
                true,
                false
        );
        getViewModel().activeFragment.postValue(GEOLOCATION_TAG);

        btnSettings.setOnClickListener(v -> {
            FragmentExtensions.replaceFragmentWithAnim(
                    this,
                    new SettingsFragment(),
                    "SettingsFragment",
                    R.id.fcvMainContainer,
                    true,
                    true
            );
            dlMain.closeDrawer(GravityCompat.START);
            getViewModel().activeFragment.postValue(SETTINGS_TAG);
        });

        btnAddCity.setOnClickListener(v -> {
            FragmentExtensions.replaceFragmentWithAnim(
                    this,
                    AddCityFragment.newInstance(),
                    "AddCityFragment",
                    R.id.fcvMainContainer,
                    true,
                    true
            );
            dlMain.closeDrawer(GravityCompat.START);
            getViewModel().activeFragment.postValue(ADD_CITY_TAG);
        });
    }

    private void makeObserver() {
        getViewModel().activeFragment.observe(this, str -> {
            btnSettings.setBackground(null);
            btnAddCity.setBackground(null);
            if (str.equals(SETTINGS_TAG)) {
                btnSettings.setBackground(getDrawable(R.drawable.btn_nav_item));
            } else if (str.equals(ADD_CITY_TAG)) {
                btnAddCity.setBackground(getDrawable(R.drawable.btn_nav_item));
            }
            for (CityModel city : arrayList){
                city.setCurrent(false);
                if (city.isGeolocation() && str.equals(GEOLOCATION_TAG)) {
                    arrayList.get(0).setCurrent(true);
                } else if (city.getName() != null && city.getName().equals(str)) {
                    arrayList.get(arrayList.indexOf(city)).setCurrent(true);
                }
                adapter.notifyItemChanged(arrayList.indexOf(city));
                Log.d("CITY", city.getName() == null ? "null" : city.getName());
            }
        });
    }

    private void setRecyclerView() {
        arrayList.add(new CityModel(true));
        arrayList.add(new CityModel("Kharkiv", false, 50, 36.25));
        arrayList.add(new CityModel("Lviv", false, 49.8383, 24.0232));
        arrayList.add(new CityModel("London", false, 51.5085, -0.1257));

        adapter = new NavItemsRVAdapter(city -> {
            if (city.isCurrent()) {
                dlMain.closeDrawer(GravityCompat.START);
                return;
            }
            FragmentExtensions.replaceFragmentWithAnim(
                    this,
                    MainFragment.newInstance(city),
                    city.getName(),
                    R.id.fcvMainContainer,
                    true,
                    false
            );
            dlMain.closeDrawer(GravityCompat.START);
            getViewModel().activeFragment.postValue(city.isGeolocation() ? GEOLOCATION_TAG : city.getName());
        });
        adapter.setCityModels(arrayList);
        rvCities.setAdapter(adapter);
    }

    public void setBackArrow() {
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        tbMain.setNavigationOnClickListener((view) -> onBackPressed());
    }

    private void setMenuIcon() {
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        ActivityExtensions.setMenuIcon(this, dlMain, tbMain);
    }

    @Override
    public void onBackPressed() {
        if (dlMain.isDrawerOpen(GravityCompat.START)) {
            dlMain.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            setMenuIcon();
        }
    }
}