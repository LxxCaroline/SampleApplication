package com.think.linxuanxuan.sampleapplication.other;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;


public class MyCustomReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if ("com.think.linxuanxuan.MY_BROADCAST".equals(intent.getAction())) {
            //获得广播数据
            String name = intent.getStringExtra("name");
            Toast.makeText(context, name, Toast.LENGTH_LONG).show();
        }
    }
}
