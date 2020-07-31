package com.denbondd.justweather.ui.activities.main;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
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
import java.util.List;
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

    @Inject
    AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((AppApplication)getApplication()).getAppComponent().inject(this);
        super.onCreate(savedInstanceState);

        tbMain = findViewById(R.id.tbMain);
        dlMain = findViewById(R.id.dlMain);
        rvCities = findViewById(R.id.rvCities);

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

        findViewById(R.id.btnSettings).setOnClickListener(v -> {
            FragmentExtensions.replaceFragmentWithAnim(
                    this,
                    new SettingsFragment(),
                    "SettingsFragment",
                    R.id.fcvMainContainer,
                    true,
                    true
            );
            dlMain.closeDrawer(GravityCompat.START);
        });

        findViewById(R.id.btnAddCity).setOnClickListener(v -> {
            FragmentExtensions.replaceFragmentWithAnim(
                    this,
                    AddCityFragment.newInstance(),
                    "AddCityFragment",
                    R.id.fcvMainContainer,
                    true,
                    true
            );
            dlMain.closeDrawer(GravityCompat.START);
        });
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
            dlMain.closeDrawer(GravityCompat.START);
        });
        rvCities.setAdapter(adapter);
        appDatabase.cityDao().getAll().observe(this, cities -> {
            if (cities != null) {
                adapter.setCities((ArrayList<City>) cities);
            }
        });
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