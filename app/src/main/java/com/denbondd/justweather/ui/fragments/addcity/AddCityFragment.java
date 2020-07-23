package com.denbondd.justweather.ui.fragments.addcity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.denbondd.justweather.R;
import com.denbondd.justweather.models.FindCityOWMModel;
import com.denbondd.justweather.ui.base.BaseFragment;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    private EditText tietCityName;
    private TextInputLayout tilCityName;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tilCityName = view.findViewById(R.id.tilCityName);
        tietCityName = tilCityName.getEditText();
        setUpEditTexts();
    }

    private void setUpEditTexts() {
        tilCityName.setEndIconOnClickListener((v) -> getViewModel().updateFindCity(tietCityName.getText().toString()));
        setUpObserver();
    }

    private void setUpObserver() {
        getViewModel().findCity.observe(getViewLifecycleOwner(), findCityOWMModelCall -> findCityOWMModelCall.enqueue(new Callback<FindCityOWMModel>() {
            @Override
            public void onResponse(@NotNull Call<FindCityOWMModel> call, @NotNull Response<FindCityOWMModel> response) {
                if (response.isSuccessful()) {
                    Log.d("CITY_FIND", response.message());
                    if (response.body() != null) {
                        Log.d("CITY_FIND", response.body().getCount().toString()); //ok
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<FindCityOWMModel> call, @NotNull Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        }));
    }
}