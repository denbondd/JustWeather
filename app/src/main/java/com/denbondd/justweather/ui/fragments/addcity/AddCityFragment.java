package com.denbondd.justweather.ui.fragments.addcity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.denbondd.justweather.AppApplication;
import com.denbondd.justweather.R;
import com.denbondd.justweather.db.AppDatabase;
import com.denbondd.justweather.models.City;
import com.denbondd.justweather.models.FindCityOWMModel;
import com.denbondd.justweather.models.findowm.Data;
import com.denbondd.justweather.ui.activities.main.MainActivity;
import com.denbondd.justweather.ui.adapters.addcity.AddCityRVAdapter;
import com.denbondd.justweather.ui.base.BaseFragment;
import com.denbondd.justweather.ui.fragments.main.MainFragment;
import com.denbondd.justweather.util.ActivityExtensions;
import com.denbondd.justweather.util.FragmentExtensions;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import javax.inject.Inject;

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

    @Inject
    AppDatabase appDatabase;

    private EditText tietCityName;
    private TextInputLayout tilCityName;
    private final AddCityRVAdapter adapter = new AddCityRVAdapter(city -> new Thread(() -> {
        appDatabase.cityDao().insert(city);
        FragmentExtensions.replaceFragmentWithAnim(
                (AppCompatActivity) requireActivity(),
                MainFragment.newInstance(city),
                "MainFragment",
                R.id.fcvMainContainer,
                true,
                false);
        ((MainActivity) getActivity()).updateCities();
    }
        ).start()
    );

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
        getViewModel().findCity.observe(getViewLifecycleOwner(), findCityOWMModelCall -> findCityOWMModelCall.enqueue(new Callback<FindCityOWMModel>() {
            @Override
            public void onResponse(@NotNull Call<FindCityOWMModel> call, @NotNull Response<FindCityOWMModel> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        adapter.setCities((ArrayList<Data>) response.body().getData());
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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ((AppApplication) requireActivity().getApplication()).getAppComponent().inject(this);
    }
}