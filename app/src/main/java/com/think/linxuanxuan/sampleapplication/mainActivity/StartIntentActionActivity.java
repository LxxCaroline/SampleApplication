package com.think.linxuanxuan.sampleapplication.mainActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.think.linxuanxuan.sampleapplication.R;

public class StartIntentActionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startintentaction);
    }

    public void startIntentAction(View view) {
        Intent intent = new Intent("myaction");
        startActivity(intent);
    }
}
