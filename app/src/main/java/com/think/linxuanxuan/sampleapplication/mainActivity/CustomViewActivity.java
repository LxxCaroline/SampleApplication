package com.think.linxuanxuan.sampleapplication.mainActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.think.linxuanxuan.sampleapplication.R;


public class CustomViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
    }

    public void onClick(View view) {
        TextView tv = (TextView) findViewById(R.id.tvScroll);
        tv.scrollBy(-50, 0);
    }
}
