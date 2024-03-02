package com.weather_app_android.data.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cities")
public class CityHistory {
    @PrimaryKey(autoGenerate = true)
    private int cityId;

    @ColumnInfo(name = "cityName")
    private String cityName;

    //functions

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
}
