package com.think.linxuanxuan.sampleapplication.mainActivity;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.think.linxuanxuan.sampleapplication.R;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class InjectViewActivitiy extends Activity {
    @InjectView(R.id.injectviewTxV)
    TextView injectviewTxV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_injectview);
        ButterKnife.inject(this);
        injectviewTxV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(InjectViewActivitiy.this, "happy", Toast.LENGTH_LONG).show();
            }
        });
    }
}
