package com.think.linxuanxuan.sampleapplication.mainActivity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.think.linxuanxuan.sampleapplication.R;
import com.think.linxuanxuan.sampleapplication.other.CharCountService;
import com.think.linxuanxuan.sampleapplication.other.NumCountService;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class ServiceActivity extends Activity implements CharCountService.CharCountInterface {

    private CharCountService charService;

    private ServiceConnection charConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            charService = ((CharCountService.CharCountBinder) service).getService();
            charService.setCharCountInterface(ServiceActivity.this);
            Toast.makeText(ServiceActivity.this, "char service connected", Toast.LENGTH_SHORT).show();
        }

        /**
         * 调用unbind并不会触发该函数
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {
            charService = null;
            Toast.makeText(ServiceActivity.this, "char service failed", Toast.LENGTH_SHORT).show();
        }
    };

    private Intent numIntent;
    private Intent charIntent;
    private NumCountReceiver receiver;

    @InjectView(R.id.tv_num)
    TextView tvNum;
    @InjectView(R.id.tv_char)
    TextView tvChar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        ButterKnife.inject(this);
        numIntent = new Intent(this, NumCountService.class);
        charIntent = new Intent(this, CharCountService.class);
        receiver = new NumCountReceiver();
        IntentFilter filter = new IntentFilter(NumCountReceiver.ACTION);
        registerReceiver(receiver, filter);
    }

    public void serviceClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                startService(numIntent);
                break;
            case R.id.btn_stop:
                stopService(numIntent);
                break;
            case R.id.btn_bind:
                bindService(charIntent, charConnection, Context.BIND_AUTO_CREATE);
                break;
            case R.id.btn_unbind:
                unbindService(charConnection);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        Log.d("tag","-------------------"+CharCountService.class.getName());
        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        //返回所有正在运行的服务信息
        List<ActivityManager.RunningServiceInfo> infos = am.getRunningServices(100);
        for (int i = 0; i < infos.size(); i++) {
            ActivityManager.RunningServiceInfo info = infos.get(i);
            Log.d("tag",info.service.getClassName());
            if (CharCountService.class.getName().equals(info.service.getClassName())) {
                unbindService(charConnection);
                break;
            }
        }
        super.onDestroy();
    }

    @Override
    public void showChar(String s) {
        tvChar.setText(s);
    }

    public class NumCountReceiver extends BroadcastReceiver {

        public static final String ACTION = "com.think.linxuanxuan.NUM_COUNT_RECEIVER";

        @Override
        public void onReceive(Context context, Intent intent) {
            if (ACTION.equals(intent.getAction())) {
                //获得广播数据
                String num = intent.getStringExtra("num");
                tvNum.setText(num);
            }
        }
    }

}
