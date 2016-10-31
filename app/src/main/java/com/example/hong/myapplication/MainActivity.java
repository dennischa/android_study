package com.example.hong.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static String stateS = "";
    Button update;
    TextView textview;
    static int count = 0;
    //static double x= 1.1, y= 1.1;
    private MyLocation gps;
    static double beforeX = 0.0, beforeY = 0.0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        update = (Button) findViewById(R.id.updates);
        textview = (TextView) findViewById(R.id.textView);

        update.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                gps= new MyLocation(MainActivity.this);
                if(gps.isGetLocation()){
                    double x = gps.getLatitude();
                    double y = gps.getLongitude();
                    if(beforeX != x || beforeY != y){
                        count +=1;
                        MyData mydata = new MyData();
                        if(count == 14) {
                            stateS = "";
                            count = 0;
                        }
                        stateS += mydata.date + " " + mydata.time + "\n"+"위도: " +String.valueOf(x)+ " "+ "경도: "+ String.valueOf(y)+ "\n";
                        textview.setText(stateS);
                        beforeX = x;
                        beforeY = y;
                    }
                }
                else {
                    // GPS 를 사용할수 없으므로
                    gps.showSettingsAlert();
                }
            }
        });

    }

}
