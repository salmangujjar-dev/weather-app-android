package com.example.assignment_5.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.assignment_5.data.entities.CityHistory;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface CityHistoryDAO {

    @Insert
    void insertCity(CityHistory cityHistory);

    @Query("SELECT max(cityId) FROM cities")
    Integer getMax();

    @Query("SELECT cityName FROM cities where cityName = :cityName")
    List<String> searchCity(String cityName);

    @Query("SELECT cityName FROM cities")
    List<String> getCityNames();

    @Query("SELECT * FROM cities")
    List<CityHistory> getAll();

}
