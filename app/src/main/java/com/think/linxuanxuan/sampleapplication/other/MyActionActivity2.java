package com.think.linxuanxuan.sampleapplication.other;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.think.linxuanxuan.sampleapplication.R;


public class MyActionActivity2 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myaction);
        TextView tv = (TextView) findViewById(R.id.tv_action);
        tv.setText("activity2 with myaction");
    }

}
