package com.example.hong.quickcoding1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button maxbtn;
    Button minbtn;

    TextView text;
    int min, max;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        maxbtn = (Button) findViewById(R.id.maxbtn);
        minbtn = (Button) findViewById(R.id.minbtn);
        text = (TextView) findViewById(R.id.textView);


        maxbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                MaxValue maxValue = new MaxValue();
                max = maxValue.getMax();
                text.setText(String.valueOf(max));

            }
        });

        minbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                MinValue minValue = new MinValue();
                min = minValue.getMin();
                text.setText(String.valueOf(min));
            }
        });


    }
}
