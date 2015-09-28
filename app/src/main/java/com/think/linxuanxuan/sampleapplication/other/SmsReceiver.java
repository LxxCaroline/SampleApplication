package com.think.linxuanxuan.sampleapplication.other;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.util.Set;


public class SmsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Set<String> keys = bundle.keySet();
            //查看收到的广播包含哪些数据
            for (String key : keys) {
                Log.d("tag", "key:" + key);
            }
            //获得收到的短信数据，由于接收器可能接收到多条短信，所有通过“pdus"返回了一个短信数组
            Object[] objArray = (Object[]) bundle.get("pdus");
            //定义封装短信内容的SmsMessage对象数组
            SmsMessage[] messages = new SmsMessage[objArray.length];
            //循环处理收到的所有短信
            for (int i = 0; i < objArray.length; i++) {
                //将每条短信数据转换成SmsMessage对象
                messages[i] = SmsMessage.createFromPdu((byte[]) objArray[i]);
                //获得手机号和短信内容
                String s = "手机号:" + messages[i].getOriginatingAddress() + "\n";
                s += "内容:" + messages[i].getDisplayMessageBody();
                Toast.makeText(context, s, Toast.LENGTH_LONG).show();
            }
        }
    }
}
