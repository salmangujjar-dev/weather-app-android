package com.weather_app_android;

import java.io.Serializable;

public class Current implements Serializable{
    private Integer temperature;
    private Integer wind_speed;
    private String wind_dir;
    private Double precip;
    private Integer humidity;


    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public Integer getWind_speed() {
        return wind_speed;
    }

    public void setWind_speed(Integer wind_speed) {
        this.wind_speed = wind_speed;
    }

    public String getWind_dir() {
        return wind_dir;
    }

    public void setWind_dir(String wind_dir) {
        this.wind_dir = wind_dir;
    }

    public Double getPrecip() {
        return precip;
    }

    public void setPrecip(Double precip) {
        this.precip = precip;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }
}
