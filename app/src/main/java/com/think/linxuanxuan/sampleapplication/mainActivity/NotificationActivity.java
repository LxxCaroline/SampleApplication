package com.think.linxuanxuan.sampleapplication.mainActivity;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;

import com.think.linxuanxuan.sampleapplication.R;


public class NotificationActivity extends Activity {

    private static final int FIRST_NOTIFICATION = 1;
    private NotificationManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    public void sendNotification(View view) {
        switch (view.getId()) {
            case R.id.btn_send:
                PendingIntent intent = PendingIntent.getActivity(this, 0, getIntent(), 0);
                Notification n = new Notification(R.drawable.test, "您有一条新消息", System.currentTimeMillis());
                n.flags = Notification.FLAG_AUTO_CANCEL;
                n.setLatestEventInfo(this, "微信", "某某发来一条贺电", intent);
                manager.notify(FIRST_NOTIFICATION, n);
                break;
            case R.id.btn_cancel:
                manager.cancel(FIRST_NOTIFICATION);
                break;
            case R.id.btn_custom:
                intent = PendingIntent.getActivity(this, 0, getIntent(), 0);
                n = new Notification(R.drawable.test, "您有一条新消息", System.currentTimeMillis());
                n.flags = Notification.FLAG_AUTO_CANCEL;
                /*
                 * remoteViews中只支持部分控件，layout中包括RelativeLayout,LinearLayout，FrameLayout。
                 * 控件包括button,ImageView，TextView等
                 * 如果包含了其他控件，则会抛出异常
                 */
                RemoteViews views = new RemoteViews(getPackageName(), R.layout.view_notification);
                n.contentView = views;
                n.contentIntent = intent;
                manager.notify(FIRST_NOTIFICATION, n);
                break;
        }
    }
}
