package com.weather_app_android.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.weather_app_android.data.dao.CityHistoryDAO;
import com.weather_app_android.data.dao.CityHistoryDetailsDAO;
import com.weather_app_android.data.entities.CityHistory;
import com.weather_app_android.data.entities.CityHistoryDetails;

@Database(entities = {CityHistory.class, CityHistoryDetails.class}, version = 1, exportSchema = false)
public abstract class CityHistoryDatabase extends RoomDatabase {
    public abstract CityHistoryDAO cityHistoryDAO();
    public abstract CityHistoryDetailsDAO cityHistoryDetailsDAO();
}
