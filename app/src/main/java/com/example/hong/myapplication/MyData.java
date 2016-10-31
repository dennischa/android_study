package com.example.hong.myapplication;

/**
 * Created by admin on 2016-10-25.
 */
public class MyData {
    public String time;
    public String date;
    public MyData(){
        Add add = new Add();
        this.date = add.getDate();
        this.time = add.gettime();
    }

}
