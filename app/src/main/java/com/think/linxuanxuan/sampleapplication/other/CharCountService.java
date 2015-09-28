package com.think.linxuanxuan.sampleapplication.other;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;


public class CharCountService extends Service {

    private CharCountBinder binder = new CharCountBinder();

    private char c = 'a';
    private CharCountInterface ccInterface;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                c++;
                Log.d("tag", "c:" + c);

                if (ccInterface != null) {
                    ccInterface.showChar(c + "");
                }
                handler.sendEmptyMessageDelayed(1, 1000);
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        handler.sendEmptyMessageDelayed(1, 1000);
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        handler.removeMessages(1);
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        handler.removeMessages(1);
        super.onDestroy();
    }

    public void setCharCountInterface(CharCountInterface ccInterface) {
        this.ccInterface = ccInterface;
    }

    public class CharCountBinder extends Binder {
        public CharCountService getService() {
            return CharCountService.this;
        }
    }

    public interface CharCountInterface {

        void showChar(String s);
    }
}
