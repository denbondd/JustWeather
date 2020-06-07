package com.denbondd.justweather.ui.base;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public abstract class BaseActivity<T extends BaseVM> extends AppCompatActivity {
    public abstract int getLayoutId();
    public abstract Class<T> getViewModelClass();
    private T viewModel;
    protected T getViewModel() {
        if (viewModel == null) {
            viewModel = new ViewModelProvider(this).get(getViewModelClass());
        }
        return viewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
