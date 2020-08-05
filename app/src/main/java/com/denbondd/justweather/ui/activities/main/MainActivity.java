package com.denbondd.justweather.ui.activities.main;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.denbondd.justweather.AppApplication;
import com.denbondd.justweather.R;
import com.denbondd.justweather.db.AppDatabase;
import com.denbondd.justweather.models.City;
import com.denbondd.justweather.ui.adapters.navitems.NavItemsRVAdapter;
import com.denbondd.justweather.ui.base.BaseActivity;
import com.denbondd.justweather.ui.fragments.addcity.AddCityFragment;
import com.denbondd.justweather.ui.fragments.main.MainFragment;
import com.denbondd.justweather.ui.fragments.settings.SettingsFragment;
import com.denbondd.justweather.util.ActivityExtensions;
import com.denbondd.justweather.util.FragmentExtensions;

import java.util.ArrayList;
import java.util.Objects;

import javax.inject.Inject;

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
    private NavItemsRVAdapter adapter;
    private Button btnSettings, btnAddCity;

    @Inject
    AppDatabase appDatabase;

    private final String SETTINGS_TAG = "SettingsFragment";
    private final String ADD_CITY_TAG = "AddCityFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((AppApplication)getApplication()).getAppComponent().inject(this);
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

        FragmentExtensions.replaceFragmentWithAnim(
                this,
                MainFragment.newInstance(new City(true)),
                "MainFragment",
                R.id.fcvMainContainer,
                true,
                false
        );

        findViewById(R.id.btnSettings).setOnClickListener(v -> onNavItemClick(new SettingsFragment(), "SettingsFragment"));
        findViewById(R.id.btnAddCity).setOnClickListener(v -> onNavItemClick(AddCityFragment.newInstance(), "AddCityFragment"));
        getViewModel().currentPage.observe(this, this::changeCurrent);
    }

    private void changeCurrent(String state) {
        btnSettings.setBackground(null);
        btnAddCity.setBackground(null);
        switch (state) {
            case SETTINGS_TAG:
                btnSettings.setBackgroundResource(R.drawable.btn_nav_item);
                break;
            case ADD_CITY_TAG:
                btnAddCity.setBackgroundResource(R.drawable.btn_nav_item);
                break;
            default:
                adapter.setCurrentCity(Long.parseLong(state));
                break;
        }
    }

    private void setRecyclerView() {
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
            getViewModel().currentPage.postValue(Long.toString(city.getId()));
            dlMain.closeDrawer(GravityCompat.START);
        });
        rvCities.setAdapter(adapter);
        updateCities();
    }

    public void updateCities() {
        runOnUiThread(() -> adapter.setCities((ArrayList<City>) appDatabase.cityDao().getAll()));
    }

    public void setBackArrow() {
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        tbMain.setNavigationOnClickListener((view) -> onBackPressed());
    }

    private void setMenuIcon() {
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        ActivityExtensions.setMenuIcon(this, dlMain, tbMain);
    }

    private void onNavItemClick(Fragment fragment, String tag) {
        FragmentExtensions.replaceFragmentWithAnim(
                this,
                fragment,
                tag,
                R.id.fcvMainContainer,
                true,
                true
        );
        getViewModel().currentPage.postValue(tag);
        dlMain.closeDrawer(GravityCompat.START);
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