package com.think.linxuanxuan.sampleapplication.other;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.think.linxuanxuan.sampleapplication.R;


public class ClearTopActivity2 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleartop2);
    }

    public void click2(View v) {
        startActivity(new Intent(ClearTopActivity2.this, ClearTopActivity3.class));
    }
}
