package com.example.assignment_5.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.assignment_5.data.entities.CityHistory;
import com.example.assignment_5.data.entities.CityHistoryDetails;

import java.util.ArrayList;
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
