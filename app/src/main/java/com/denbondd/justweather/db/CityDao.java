package com.denbondd.justweather.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.denbondd.justweather.models.City;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface CityDao {

    @Query("SELECT * FROM City")
    List<City> getAll();

    @Query("SELECT * FROM City WHERE id = :id")
    City getById(long id);

    @Insert
    void insert(City city);

    @Update
    void update(City city);

    @Delete
    void delete(City city);
}
