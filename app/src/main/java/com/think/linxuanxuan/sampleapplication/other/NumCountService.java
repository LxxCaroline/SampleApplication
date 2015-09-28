package com.think.linxuanxuan.sampleapplication.other;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import com.think.linxuanxuan.sampleapplication.mainActivity.ServiceActivity;


public class NumCountService extends Service {

    private int n;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                n++;
                Intent broadcastIntent = new Intent(ServiceActivity.NumCountReceiver.ACTION);
                broadcastIntent.putExtra("num", n + "");
                sendBroadcast(broadcastIntent);
                handler.sendEmptyMessageDelayed(1, 1000);
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler.sendEmptyMessageDelayed(1, 1000);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        handler.removeMessages(1);
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
