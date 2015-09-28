package com.think.linxuanxuan.sampleapplication.other;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;


public class PowerConnectionReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_POWER_CONNECTED.equals(intent.getAction())) {
            Toast.makeText(context, "开始充电", Toast.LENGTH_LONG).show();
        } else if (Intent.ACTION_POWER_DISCONNECTED.equals(intent.getAction())) {
            Toast.makeText(context, "结束充电", Toast.LENGTH_LONG).show();
        }
    }
}
