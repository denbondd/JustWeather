package com.denbondd.justweather.di;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.denbondd.justweather.db.AppDatabase;
import com.denbondd.justweather.db.CityDao;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    @Provides
    AppDatabase provideAppDatabase(Context context){
        return Room
                .databaseBuilder(context, AppDatabase.class, "database")
                .allowMainThreadQueries()
                .build();
    }
}
