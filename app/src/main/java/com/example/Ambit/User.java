package com.example.Ambit;

/**
 * Created by Joseph on 9/5/2015.
 */
public class User {

    private String name;
    private double lat;
    private double lon;
    private int radius;

    User(){
    }

    User(String name,double lat, double lon, int radius){
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.radius = radius;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
