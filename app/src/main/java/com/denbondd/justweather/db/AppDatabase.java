package com.denbondd.justweather.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.denbondd.justweather.models.City;

import javax.inject.Singleton;

@Singleton
@Database(entities = {City.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CityDao cityDao();
}
