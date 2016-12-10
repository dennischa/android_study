package com.example.hong.quickcoding1;

/**
 * Created by admin on 2016-12-11.
 */
public class MaxValue extends MinValue {
    int max = super.value[0];

    int getMax (){
        for(int i=0; i<super.value.length; i++){

            if(super.value[i] > max){
                max = super.value[i];
            }
        }
        return max;
    }


}
