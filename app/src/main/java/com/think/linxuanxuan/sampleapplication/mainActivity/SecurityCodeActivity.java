package com.think.linxuanxuan.sampleapplication.mainActivity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.think.linxuanxuan.sampleapplication.R;
import com.think.linxuanxuan.sampleapplication.other.SendMessageUtil;


public class SecurityCodeActivity extends Activity implements View.OnClickListener {
    private SmsObserver smsObserver;
    private Uri SMS_INBOX = Uri.parse("content://sms/");
    private EditText messageEdTx;

    public Handler smsHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            Log.d("test", "smsHandler is executed.....");
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_code);
        messageEdTx = (EditText) findViewById(R.id.messageEdTx);
        smsObserver = new SmsObserver(this, smsHandler);
        getContentResolver().registerContentObserver(SMS_INBOX, true, smsObserver);
    }

    public void getSmsFromPhone() {
        ContentResolver cr = getContentResolver();
        String[] projection = new String[]{"body", "address", "person"};// "_id", "address",
        String where = " date >  "
                + (System.currentTimeMillis() - 10 * 60 * 1000);
        Cursor cur = cr.query(SMS_INBOX, projection, where, null, "date desc");
        if (null == cur)
            return;
        if (cur.moveToNext()) {
            String number = cur.getString(cur.getColumnIndex("address"));// �ֻ���
            String name = cur.getString(cur.getColumnIndex("person"));// ��ϵ�������б�
            String body = cur.getString(cur.getColumnIndex("body"));

            Log.d("test", ">>>>>>>>>>>>>>>>phone number:" + number);
            Log.d("test", ">>>>>>>>>>>>>>>>contact name:" + name);
            Log.d("test", ">>>>>>>>>>>>>>>>short message:" + body);
            messageEdTx.setText(body);
        }
    }

    protected void showToast(String text) {
        Toast.makeText(SecurityCodeActivity.this, text, Toast.LENGTH_LONG).show();
    }

    class SmsObserver extends ContentObserver {

        public SmsObserver(Context context, Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            Log.d("test", "SmsObserver's onChange");
            getSmsFromPhone();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        SendMessageUtil.unregisterReceiver();
    }

    @Override
    public void onClick(View v) {
        SendMessageUtil.sendMessage(SecurityCodeActivity.this, "12345", "13738144006");
    }
}
