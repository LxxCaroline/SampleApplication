package com.think.linxuanxuan.sampleapplication.mainActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.think.linxuanxuan.sampleapplication.R;

import java.util.List;


public class BroadcastActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);
    }

    public void sendBroadcast(View view) {
        switch (view.getId()) {
            case R.id.btn_send:
                Intent broadcastIntent = new Intent("com.think.linxuanxuan.MY_BROADCAST");
                broadcastIntent.addCategory("com.think.linxuanxuan.mycategory");
                broadcastIntent.putExtra("name", "broadcast_data");
                sendBroadcast(broadcastIntent);
                Toast.makeText(this, "发送广播成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_show:
                //获得packageManager对象
                PackageManager manager = getPackageManager();
                //指定要查询广播的动作
                Intent intent = new Intent("com.think.linxuanxuan.MY_BROADCAST");
                //返回已查询到的广播接收器集合
                List<ResolveInfo> infos = manager.queryBroadcastReceivers(intent, PackageManager.GET_INTENT_FILTERS);
                Toast.makeText(this, "目前发现有" + infos.size() + "个接收MY_BROADCAST的接收器",Toast.LENGTH_SHORT).show();
                String result="";
                for(ResolveInfo info:infos){
                    result+=String.valueOf(info.toString())+"\n";
                }
                Toast.makeText(this,"查询结果;"+result,Toast.LENGTH_LONG).show();
                break;
        }

    }
}
