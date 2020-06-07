package com.denbondd.justweather.ui.activities.splash;

import android.os.Bundle;
import android.os.Handler;

import com.denbondd.justweather.R;
import com.denbondd.justweather.ui.activities.main.MainActivity;
import com.denbondd.justweather.ui.base.BaseActivity;
import com.denbondd.justweather.util.ActivityExtensionsKt;

public class SplashActivity extends BaseActivity<SplashVM> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public Class getViewModelClass() {
        return SplashVM.class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(() -> {
            ActivityExtensionsKt.startActivityWithAnim(this, new MainActivity());
            finish();
        }, 4000);
    }
}