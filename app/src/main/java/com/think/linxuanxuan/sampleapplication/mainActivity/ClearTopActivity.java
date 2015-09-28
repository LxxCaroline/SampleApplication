package com.think.linxuanxuan.sampleapplication.mainActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.think.linxuanxuan.sampleapplication.other.ClearTopActivity2;
import com.think.linxuanxuan.sampleapplication.R;


public class ClearTopActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() != null) {
            if (getIntent().getBooleanExtra("cleartop", false)) {
                this.finish();
            }
        }
        setContentView(R.layout.activity_cleartop1);
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(ClearTopActivity.this, ClearTopActivity2.class));
    }
}
