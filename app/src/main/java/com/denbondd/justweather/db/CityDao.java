package com.denbondd.justweather.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.denbondd.justweather.models.City;

import java.util.List;

@Dao
public interface CityDao {

    @Query("SELECT * FROM City")
    LiveData<List<City>> getAll();

    @Query("SELECT * FROM City WHERE id = :id")
    City getById(long id);

    @Query("SELECT * FROM City WHERE isCurrent = 1")
    City getCurrent();

    @Insert
    long insert(City city);

    @Update
    void update(City city);

    @Update
    void updateAll(List<City> cities);

    @Delete
    void delete(City city);
}
