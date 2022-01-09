package com.example.assignment_5;

import java.io.Serializable;

public class Location implements Serializable {
    private String name;
    private String country;

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getCountry(){
        return country;
    }
    public void setCountry(String country){
        this.country = country;
    }

}
