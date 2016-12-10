package com.example.hong.quickcoding1;

/**
 * Created by admin on 2016-12-11.
 */
public class MinValue extends MyValue {
    int min = super.value[0];

    int getMin(){
        for(int i=1; i< super.value.length; i++){

            if(super.value[i] < min){
                min = super.value[i];
            }
        }
        return min;
    }
}
