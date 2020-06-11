package com.denbondd.justweather.ui.activities.splash;

import android.os.Bundle;
import android.os.Handler;

import com.denbondd.justweather.R;
import com.denbondd.justweather.ui.activities.main.MainActivity;
import com.denbondd.justweather.ui.base.BaseActivity;
import com.denbondd.justweather.util.ActivityExtensions;

public class SplashActivity extends BaseActivity<SplashVM> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public Class<SplashVM> getViewModelClass() {
        return SplashVM.class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(() -> {
            ActivityExtensions.startSplashActivityWithAnim(this, new MainActivity());
            finish();
        }, 4000);
    }
}