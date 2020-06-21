package com.denbondd.justweather.ui.activities.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.denbondd.justweather.R;
import com.denbondd.justweather.ui.base.BaseActivity;
import com.denbondd.justweather.ui.fragments.main.MainFragment;
import com.denbondd.justweather.util.ActivityExtensions;
import com.denbondd.justweather.util.FragmentExtensions;

public class MainActivity extends BaseActivity<MainVM> {

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

        Toolbar tbMain = findViewById(R.id.tbMain);
        DrawerLayout dlMain = findViewById(R.id.dlMain);

        setSupportActionBar(tbMain);
        if (getSupportActionBar() != null) getSupportActionBar().setTitle("");
        ActivityExtensions.setMenuIcon(this, dlMain, tbMain);

        FragmentExtensions.replaceFragmentWithAnim(
                this,
                MainFragment.newInstance(),
                "MainFragment",
                R.id.fcvMainContainer,
                true,
                false
        );
    }
}