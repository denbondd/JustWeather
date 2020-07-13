package com.denbondd.justweather.ui.activities.main;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.denbondd.justweather.R;
import com.denbondd.justweather.models.CityModel;
import com.denbondd.justweather.ui.adapters.NavItemsRVAdapter;
import com.denbondd.justweather.ui.base.BaseActivity;
import com.denbondd.justweather.ui.fragments.main.MainFragment;
import com.denbondd.justweather.util.ActivityExtensions;
import com.denbondd.justweather.util.FragmentExtensions;

import java.util.ArrayList;
import java.util.Collection;
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

    private final String GEOLOCATION_TAG = "Geolocation";
    private final String SETTINGS_TAG = "Settings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tbMain = findViewById(R.id.tbMain);
        dlMain = findViewById(R.id.dlMain);
        rvCities = findViewById(R.id.rvCities);
        btnSettings = findViewById(R.id.btnSettings);

        setSupportActionBar(tbMain);
        if (getSupportActionBar() != null) getSupportActionBar().setTitle("");
        setMenuIcon();
        setRecyclerView();

        FragmentExtensions.replaceFragmentWithAnim(
                this,
                MainFragment.newInstance(new CityModel(true)),
                "MainFragment",
                R.id.fcvMainContainer,
                true,
                false
        );
        getViewModel().activeFragment.postValue(GEOLOCATION_TAG);

        getViewModel().activeFragment.observe(this, str -> {
            btnSettings.setBackground(null);
            if (str.equals(SETTINGS_TAG)) {
                btnSettings.setBackground(getDrawable(R.drawable.btn_nav_item));
                return;
            }
            for (CityModel city : arrayList){
                city.setCurrent(false);
                if (city.isGeolocation() && str.equals(GEOLOCATION_TAG)) {
                    arrayList.get(0).setCurrent(true);
                } else if (city.getName() != null && city.getName().equals(str)) {
                    arrayList.get(arrayList.indexOf(city)).setCurrent(true);
                }
                adapter.notifyItemChanged(arrayList.indexOf(city));
            }
        });

        btnSettings.setOnClickListener(v -> {
            //show settings fragment
            //getViewModel().activeFragment.postValue(SETTINGS_TAG);
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
            getViewModel().activeFragment.postValue(city.getName());
        });
        adapter.setCityModels(arrayList);
        rvCities.setAdapter(adapter);
    }

    private void setCurrentFragment() {
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