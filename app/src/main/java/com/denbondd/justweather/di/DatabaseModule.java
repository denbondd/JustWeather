package com.denbondd.justweather.di;

import android.content.Context;

import androidx.room.Room;

import com.denbondd.justweather.db.AppDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    @Singleton
    @Provides
    static AppDatabase provideAppDatabase(Context context){
        return Room
                .databaseBuilder(context, AppDatabase.class, "database")
                .allowMainThreadQueries()
                .build();
    }
}
