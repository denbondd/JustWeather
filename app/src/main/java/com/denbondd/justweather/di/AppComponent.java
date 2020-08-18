package com.denbondd.justweather.di;

import android.content.Context;

import com.denbondd.justweather.db.AppDatabase;
import com.denbondd.justweather.ui.activities.main.MainActivity;
import com.denbondd.justweather.ui.base.BaseVM;
import com.denbondd.justweather.ui.fragments.addcity.AddCityFragment;
import com.denbondd.justweather.ui.fragments.main.WeatherFragment;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = {DatabaseModule.class, ApiModule.class})
public interface AppComponent {

    AppDatabase getAppDatabase();

    void inject(AddCityFragment addCityFragment);
    void inject(MainActivity mainActivity);

    void inject(WeatherFragment weatherFragment);

    void inject(BaseVM viewModel);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder context(Context context);

        AppComponent build();
    }
}
