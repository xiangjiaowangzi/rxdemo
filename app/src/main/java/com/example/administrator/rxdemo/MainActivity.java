package com.example.administrator.rxdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.administrator.rxdemo.basic.OperatorAct;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.basic).setOnClickListener(this);
        findViewById(R.id.operator).setOnClickListener(this);
        findViewById(R.id.thread).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.basic :
                break;
            case R.id.operator :
                Utils.launch(this , OperatorAct.class);
                break;
            case R.id.thread :
                break;
        }
    }
}
