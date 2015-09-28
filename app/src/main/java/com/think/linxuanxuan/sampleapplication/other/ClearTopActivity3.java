package com.think.linxuanxuan.sampleapplication.other;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.think.linxuanxuan.sampleapplication.R;
import com.think.linxuanxuan.sampleapplication.mainActivity.ClearTopActivity;

public class ClearTopActivity3 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleartop3);
    }

    public void click3(View v) {
        Intent intent = new Intent(ClearTopActivity3.this, ClearTopActivity.class);
        intent.putExtra("cleartop", true);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
