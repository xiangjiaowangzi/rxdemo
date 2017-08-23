package com.example.administrator.rxdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.rxdemo.basic.OperatorAct;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lbin on 2017/4/26.
 */

public class Utils {
    public static void print(String s){
        Log.e("aa" , s);
    }

    public static void launch(Context context , Class<?> activityClass){
        context.startActivity(new Intent(context ,activityClass));
    }

    public static List<String> getStringList(){
        List<String> s = new ArrayList<>();
        s.add("1");
        s.add("2");
        s.add("3");
        s.add("4");
        s.add("5");
        return s ;
    }
}
