package com.think.linxuanxuan.sampleapplication.other;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Think on 2015/7/20.
 */
public class SendMessageUtil {

    private static String SENT_SMS_ACTION = "SENT_SMS_ACTION";
    private static String DELIVERED_SMS_ACTION = "DELIVERED_SMS_ACTION";
    private static Context mcontext;

    public static void sendMessage(Context context, String text,
                                   String phoneNumber) {
        mcontext = context;
        mcontext.registerReceiver(sendMessage, new IntentFilter(SENT_SMS_ACTION));
        mcontext.registerReceiver(receiver, new IntentFilter(
                DELIVERED_SMS_ACTION));

        // create the sentIntent parameter
        Intent sentIntent = new Intent(SENT_SMS_ACTION);
        PendingIntent sentPI = PendingIntent.getBroadcast(mcontext, 0, sentIntent, 0);
        // create the deilverIntent parameter
        Intent deliverIntent = new Intent(DELIVERED_SMS_ACTION);
        PendingIntent deliverPI = PendingIntent.getBroadcast(mcontext, 0, deliverIntent, 0);

        SmsManager smsManager = SmsManager.getDefault();
        if (text.length() > 70) {
            ArrayList<String> msgs = smsManager.divideMessage(text);
            for (String msg : msgs) {
                smsManager.sendTextMessage(phoneNumber, null, msg, sentPI, deliverPI);
            }
        } else {
            smsManager.sendTextMessage(phoneNumber, null, text, sentPI, deliverPI);
        }
    }

    public static void unregisterReceiver() {
        if (mcontext != null) {
            mcontext.unregisterReceiver(sendMessage);
            mcontext.unregisterReceiver(receiver);
        }
    }

    private static BroadcastReceiver sendMessage = new BroadcastReceiver() {

        @Override
        public void onReceive(Context mcontext, Intent intent) {
            switch (getResultCode()) {
                case Activity.RESULT_OK:
                    Toast.makeText(mcontext, "send message success", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(mcontext, "send message fail", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };
    private static BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context mcontext, Intent intent) {
            Toast.makeText(mcontext, "receive message success", Toast.LENGTH_LONG).show();
        }
    };
}
