package com.think.linxuanxuan.sampleapplication.mainActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.think.linxuanxuan.sampleapplication.R;

public class StartActivityActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity);
    }

    public void onClickActivity(View view) {
        switch (view.getId()) {
            case R.id.btn_autocall:
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:123456789")));
                break;
            case R.id.btn_autodial:
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:123456789")));
                break;
            case R.id.btn_dial:
                Intent touchDialerIntent = new Intent("com.android.phone.action.TOUCH_DIALER");
                startActivity(touchDialerIntent);
                break;
            case R.id.btn_web:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://wwww.baidu.com")));
                break;
            case R.id.btn_email:
                Intent sendEmailIntent = new Intent(Intent.ACTION_SEND);
                //发送的信息需要通过putExtra来指定
                sendEmailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"11111111@126.com"});
                sendEmailIntent.putExtra(Intent.EXTRA_CC, new String[]{"111dddd1@126.com"});
                sendEmailIntent.putExtra(Intent.EXTRA_SUBJECT, new String[]{"email subject"});
                sendEmailIntent.putExtra(Intent.EXTRA_TEXT, new String[]{"email detail"});
                sendEmailIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendEmailIntent, "选择发送消息的客户端"));
                break;
            case R.id.btn_contacts:
                Intent contactIntent = new Intent("com.android.contacts.action.LIST_CONTACTS");
                startActivity(contactIntent);
                break;
            case R.id.btn_setting:
                startActivity(new Intent("android.settings.SETTINGS"));
                break;
            case R.id.btn_audio:
                Intent audioIntent = new Intent(Intent.ACTION_GET_CONTENT);
                audioIntent.setType("audio/*");
                startActivity(Intent.createChooser(audioIntent, "选择音频程序"));
                break;
            case R.id.btn_animstart:
                Intent anotherIntent = new Intent(this, ClearTopActivity.class);
                startActivity(anotherIntent);
                overridePendingTransition(android.R.anim.slide_in_left,R.anim.abc_slide_in_top);
                break;
            case R.id.btn_animfinish:
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
        }

    }
}
