package com.denbondd.justweather.ui.activities.main;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                MainFragment.newInstance(new CityModel(true)),
                "MainFragment",
                R.id.fcvMainContainer,
                true,
                false
        );
    }

    private void setRecyclerView() {
        ArrayList<CityModel> arrayList = new ArrayList<>();
        arrayList.add(new CityModel(true));
        arrayList.add(new CityModel("Kharkiv", false, 50, 36.25));
        arrayList.add(new CityModel("Lviv", false, 49.8383, 24.0232));
        arrayList.add(new CityModel("London", false, 51.5085, -0.1257));

        NavItemsRVAdapter adapter = new NavItemsRVAdapter(city -> {
            FragmentExtensions.replaceFragmentWithAnim(
                    this,
                    MainFragment.newInstance(city),
                    "MainFragment",
                    R.id.fcvMainContainer,
                    true,
                    false
            );
            dlMain.closeDrawer(GravityCompat.START);
        });
        adapter.setCityModels(arrayList);
        rvCities.setAdapter(adapter);
        new Handler().postDelayed(() -> {
            adapter.notifyDataSetChanged();
            Log.d("SMTH", "smth");
        }, 5000);
    }

    public void setBackArrow() {
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        tbMain.setNavigationOnClickListener((view) -> onBackPressed());
    }

    public void setMenuIcon() {
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