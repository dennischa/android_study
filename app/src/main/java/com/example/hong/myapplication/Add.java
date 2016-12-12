package com.example.hong.myapplication;

import android.app.Activity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by admin on 2016-10-30.
 */
public class Add extends Activity {
    long now = System.currentTimeMillis();
    Date date = new Date(now);

    SimpleDateFormat CurDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
    SimpleDateFormat CurTimeFormat = new SimpleDateFormat("HH시 mm분");

    String strCurDate = CurDateFormat.format(date);
    String strCurTime = CurTimeFormat.format(date);

    public String getDate(){
        return strCurDate + "@";
    }
    public String gettime(){
        return strCurTime;
    }

}
