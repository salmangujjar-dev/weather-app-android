package com.weather_app_android.data.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "weatherRecords", foreignKeys = @ForeignKey(
        entity = CityHistory.class,
        parentColumns = "cityId",
        childColumns = "cityId",
        onDelete = ForeignKey.CASCADE),
        indices = @Index("cityId"))

public class CityHistoryDetails {
    @PrimaryKey(autoGenerate = true)
    private int recordId;

    @ColumnInfo(name = "cityId")
    private int cityId;

    @ColumnInfo(name = "recordDate")
    private String recordDate;

    @ColumnInfo(name = "temperature")
    private int temperature;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "windSpeed")
    private int windSpeed;

    @ColumnInfo(name = "windDir")
    private String windDir;

    @ColumnInfo(name = "precip")
    private double precip;

    @ColumnInfo(name = "humidity")
    private int humidity;


    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWindDir() {
        return windDir;
    }

    public void setWindDir(String windDir) {
        this.windDir = windDir;
    }

    public double getPrecip() {
        return precip;
    }

    public void setPrecip(double precip) {
        this.precip = precip;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }
}
