package com.weather_app_android;


import java.io.Serializable;

public class WeatherData implements Serializable {

    private Location location;
    private Current current;

    public Location getLocation(){
        return location;
    }
    public void setLocation (Location location){
        this.location = location;
    }

    public Current getCurrent(){
        return current;
    }
    public void setCurrent(Current current){
        this.current = current;
    }
}
