package com.denbondd.justweather.ui.base;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public abstract class BaseFragment<T extends BaseVM> extends Fragment {
    public abstract int getLayoutId();
    public abstract Class<T> getViewModelClass();
    private T viewModel;
    protected T getViewModel() {
        if (viewModel == null) {
            viewModel = new ViewModelProvider(this).get(getViewModelClass());
        }
        return viewModel;
    }
}
