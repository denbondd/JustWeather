package com.denbondd.justweather.di;

import android.content.Context;

import com.denbondd.justweather.AppApplication;
import com.denbondd.justweather.db.AppDatabase;
import com.denbondd.justweather.db.CityDao;
import com.denbondd.justweather.ui.activities.main.MainActivity;
import com.denbondd.justweather.ui.fragments.addcity.AddCityFragment;

import dagger.BindsInstance;
import dagger.Component;

@Component(modules = {DatabaseModule.class})
public interface AppComponent {

    AppDatabase getAppDatabase();

    void inject(AddCityFragment addCityFragment);
    void inject(MainActivity mainActivity);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder context(Context context);

        AppComponent build();
    }
}
