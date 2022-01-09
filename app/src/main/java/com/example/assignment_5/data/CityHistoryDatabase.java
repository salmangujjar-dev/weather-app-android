package com.example.assignment_5.data;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.assignment_5.data.dao.CityHistoryDAO;
import com.example.assignment_5.data.dao.CityHistoryDetailsDAO;
import com.example.assignment_5.data.entities.CityHistory;
import com.example.assignment_5.data.entities.CityHistoryDetails;

@Database(entities = {CityHistory.class, CityHistoryDetails.class}, version = 1, exportSchema = false)
public abstract class CityHistoryDatabase extends RoomDatabase {
    public abstract CityHistoryDAO cityHistoryDAO();
    public abstract CityHistoryDetailsDAO cityHistoryDetailsDAO();
}
