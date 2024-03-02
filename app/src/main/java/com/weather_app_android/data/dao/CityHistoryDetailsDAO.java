package com.weather_app_android.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.weather_app_android.data.entities.CityHistoryDetails;

import java.util.List;

@Dao
public interface CityHistoryDetailsDAO {

    @Insert
    void insertWeather(CityHistoryDetails cityHistoryDetails);

    @Query("SELECT * FROM weatherRecords where cityId = :cityId")
    List<CityHistoryDetails> getCityWeather(Integer cityId);


    @Query("SELECT * FROM weatherRecords")
    List<CityHistoryDetails> getAll();
}
