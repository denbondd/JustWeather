package com.denbondd.justweather.ui.activities.main;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.denbondd.justweather.AppApplication;
import com.denbondd.justweather.R;
import com.denbondd.justweather.db.AppDatabase;
import com.denbondd.justweather.models.City;
import com.denbondd.justweather.ui.adapters.navitems.NavItemsRVAdapter;
import com.denbondd.justweather.ui.base.BaseActivity;
import com.denbondd.justweather.ui.fragments.addcity.AddCityFragment;
import com.denbondd.justweather.ui.fragments.main.WeatherFragment;
import com.denbondd.justweather.ui.fragments.settings.SettingsFragment;
import com.denbondd.justweather.util.ActivityExtensions;
import com.denbondd.justweather.util.FragmentExtensions;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import static com.denbondd.justweather.util.Constants.ADD_CITY_TAG;
import static com.denbondd.justweather.util.Constants.SETTINGS_TAG;
import static com.denbondd.justweather.util.Constants.SHOW_ADD_CITY;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppApplication.getAppComponent().inject(this);
        super.onCreate(savedInstanceState);

        tbMain = findViewById(R.id.tbMain);
        dlMain = findViewById(R.id.dlMain);
        rvCities = findViewById(R.id.rvCities);

        btnSettings = findViewById(R.id.btnSettings);
        btnAddCity = findViewById(R.id.btnAddCity);

        boolean showAddCity = getIntent().getBooleanExtra(SHOW_ADD_CITY, false);

        setSupportActionBar(tbMain);
        if (getSupportActionBar() != null) getSupportActionBar().setTitle("");
        setMenuIcon();
        setRecyclerView();

        if (!showAddCity) {
            FragmentExtensions.replaceFragmentWithAnim(
                    this,
                    WeatherFragment.newInstance(appDatabase.cityDao().getCurrent()),
                    "MainFragment",
                    R.id.fcvMainContainer,
                    true,
                    false
            );
        } else {
            FragmentExtensions.replaceFragmentWithAnim(
                    this,
                    AddCityFragment.newInstance(),
                    "AddCityFragment",
                    R.id.fcvMainContainer,
                    true,
                    false
            );
            getViewModel().currentPage.postValue(ADD_CITY_TAG);
        }

        findViewById(R.id.btnSettings).setOnClickListener(v -> onNavItemClick(new SettingsFragment(), SETTINGS_TAG));
        findViewById(R.id.btnAddCity).setOnClickListener(v -> onNavItemClick(AddCityFragment.newInstance(), ADD_CITY_TAG));
        getViewModel().currentPage.observe(this, this::changeCurrent);

        //for change textClock fontStyle because it's not working in xml
        Typeface typeface = ResourcesCompat.getFont(this, R.font.montserrat_light);
        ((TextClock) findViewById(R.id.txtCurrentTime)).setTypeface(typeface);
    }

    public void addCity(City city) {
        city.setCurrent(true);
        long id = appDatabase.cityDao().insert(city);
        getViewModel().currentPage.postValue(Long.toString(id));
        FragmentExtensions.replaceFragmentWithAnim(
                this,
                WeatherFragment.newInstance(city),
                "MainFragment",
                R.id.fcvMainContainer,
                true,
                false);
        setMenuIcon();
    }

    private void changeCurrent(String state) {
        btnSettings.setBackground(null);
        btnAddCity.setBackground(null);
        switch (state) {
            case SETTINGS_TAG:
                btnSettings.setBackgroundResource(R.drawable.btn_nav_item);
                adapter.setCurrentCity(-1);
                break;
            case ADD_CITY_TAG:
                btnAddCity.setBackgroundResource(R.drawable.btn_nav_item);
                adapter.setCurrentCity(-1);
                break;
            default:
                if (adapter.setCurrentCity(Long.parseLong(state))) {
                    new Thread(() -> appDatabase.cityDao().updateAll(adapter.getCities())).start();
                }
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
                    WeatherFragment.newInstance(city),
                    city.getName(),
                    R.id.fcvMainContainer,
                    true,
                    false
            );
            getViewModel().currentPage.postValue(Long.toString(city.getId()));
            dlMain.closeDrawer(GravityCompat.START);
        }, getViewModel().getAllLD());
        rvCities.setAdapter(adapter);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT
        ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                int positionViewHolder = viewHolder.getAdapterPosition();
                int positionTarget = target.getAdapterPosition();

                if (positionTarget == positionViewHolder) return false;

                adapter.setNeedToNotify(false);

                City cityViewHolder = adapter.getCities().get(positionViewHolder);
                City cityTarget = adapter.getCities().get(positionTarget);

                long cityViewHolderId = cityViewHolder.getId();
                cityViewHolder.setId(cityTarget.getId());
                cityTarget.setId(cityViewHolderId);

                new Thread(() -> appDatabase.cityDao().updateAll((List<City>) adapter.getCities())).start();

                adapter.notifyItemMoved(positionViewHolder, positionTarget);
                new Handler().postDelayed(() -> adapter.setNeedToNotify(true), 1000);
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                City city = adapter.getCities().get(viewHolder.getAdapterPosition());
                if (adapter.getCities().size() == 1) {
                    Toast.makeText(MainActivity.this, R.string.deleteLastCity, Toast.LENGTH_SHORT).show();
                    adapter.notifyItemChanged(viewHolder.getAdapterPosition());
                    return;
                } else if (city.isCurrent()) {
                    Toast.makeText(MainActivity.this, R.string.snackbarCurrentCity, Toast.LENGTH_SHORT).show();
                    adapter.notifyItemChanged(viewHolder.getAdapterPosition());
                    return;
                }
                getViewModel().deleteCity(city);
                showUndoSnackbar(city, viewHolder.getAdapterPosition());
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(rvCities);
    }

    private void showUndoSnackbar(City city, int position) {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.clMainActivity), getString(R.string.snackbarUndoText, city.getName()), Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.undo, v -> getViewModel().undoDelete(city));
        snackbar.show();
        adapter.notifyItemInserted(position);
    }

    public void setBackArrow() {
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        tbMain.setNavigationOnClickListener((view) -> {
            ActivityExtensions.hideKeyboard(this);
            onBackPressed();
        });
    }

    private void setMenuIcon() {
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        ActivityExtensions.setMenuIcon(this, dlMain, tbMain);
    }

    public void onNavItemClick(Fragment fragment, String tag) {
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
        setBackArrow();
    }

    @Override
    public void onBackPressed() {
        if (dlMain.isDrawerOpen(GravityCompat.START)) {
            dlMain.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            changeCurrent(Long.toString(getViewModel().getCurrentId()));
            setMenuIcon();
        }
    }
}