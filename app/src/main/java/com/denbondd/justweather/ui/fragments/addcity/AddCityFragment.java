package com.denbondd.justweather.ui.fragments.addcity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.denbondd.justweather.AppApplication;
import com.denbondd.justweather.R;
import com.denbondd.justweather.db.AppDatabase;
import com.denbondd.justweather.ui.activities.main.MainActivity;
import com.denbondd.justweather.ui.adapters.addcity.AddCityRVAdapter;
import com.denbondd.justweather.ui.base.BaseFragment;
import com.denbondd.justweather.util.ActivityExtensions;
import com.google.android.material.textfield.TextInputLayout;

import javax.inject.Inject;

public class AddCityFragment extends BaseFragment<AddCityViewModel> {

    @Override
    public int getLayoutId() {
        return R.layout.add_city_fragment;
    }

    @Override
    public Class<AddCityViewModel> getViewModelClass() {
        return AddCityViewModel.class;
    }

    public static AddCityFragment newInstance() {
        return new AddCityFragment();
    }

    @Inject
    AppDatabase appDatabase;

    private EditText tietCityName;
    private TextInputLayout tilCityName;
    private final AddCityRVAdapter adapter = new AddCityRVAdapter(city -> ((MainActivity) requireActivity()).addCity(city));

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tilCityName = view.findViewById(R.id.tilCityName);
        tietCityName = tilCityName.getEditText();
        RecyclerView rvAddCity = view.findViewById(R.id.rvAddCity);
        setUpEditTexts();
        rvAddCity.setAdapter(adapter);
    }

    private void setUpEditTexts() {
        tilCityName.setEndIconOnClickListener((v) -> {
            getViewModel().updateFindCity(tietCityName.getText().toString());
            ActivityExtensions.hideKeyboard(requireActivity());
        });
        tietCityName.setOnEditorActionListener((v, actionId, event) -> {
            getViewModel().updateFindCity(tietCityName.getText().toString());
            ActivityExtensions.hideKeyboard(requireActivity());
            return false;
        });
        setUpObserver();
    }

    private void setUpObserver() {
        getViewModel().findCity.observe(getViewLifecycleOwner(), (array) -> {
            if (array != null) {
                adapter.setCities(array);
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        AppApplication.getAppComponent().inject(this);
    }
}