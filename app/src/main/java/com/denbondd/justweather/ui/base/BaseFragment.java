package com.denbondd.justweather.ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container, false);
    }

    @Override
    public void onPause() {
        super.onPause();
        //for delete previous values
        getViewModel().weatherGetterOWM.findCity.postValue(null);
        getViewModel().weatherGetterOWM.cityName.postValue(null);
        getViewModel().weatherGetterOWM.oneCall.postValue(null);
    }
}
