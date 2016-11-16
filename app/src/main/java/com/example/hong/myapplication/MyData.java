package com.example.hong.myapplication;

/**
 * Created by admin on 2016-10-25.
 */
public class MyData {
    public String time;
    public String date;
    public double lat;
    public double lon;
    public String address;
    public String story;
    public MyData(String t, String d,  String a, double latitude, double longtitude, String str){
        this.time = t;
        this.date = d;
        this.lat = latitude;
        this.lon = longtitude;
        this.address = a;
        this.story = str;
    }
    public String getDate() {
        return date;
    }
    public String gettime() {
        return time;
    }
    public String getAddress() {
        return address;
    }

    public double getLatitude(){
        return lat;
    }

    public double getLongitude(){
        return lon;
    }
    public String getStory() {
        return story;
    }



}
