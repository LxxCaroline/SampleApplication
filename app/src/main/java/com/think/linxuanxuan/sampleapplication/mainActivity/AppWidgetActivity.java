package com.think.linxuanxuan.sampleapplication.mainActivity;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetHost;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RemoteViews;

import com.think.linxuanxuan.sampleapplication.R;
import com.think.linxuanxuan.sampleapplication.other.MyAppWidgetProvider;

import static android.util.Log.d;


public class AppWidgetActivity extends Activity {

    //获取所有的AppWidget
    private AppWidgetHost mAppWidgetHost;
    private AppWidgetManager mAppWidgetManager;
    private FrameLayout frameLayout;
    private static final int REQUEST_PICK_APPWIDGET = 1;
    private static final int REQUEST_START_APPWIDGET = 2;
    private static final int REQUEST_BIND_APPWIDGET = 3;
    private static final int APPWIDGET_HOST_ID = 0x100;        //用于标识
    private static final String EXTRA_CUSTOM_WIDGET = "custom_widget";
    private int appWidgetId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appwidget);

        mAppWidgetManager = AppWidgetManager.getInstance(getApplicationContext());
        mAppWidgetHost = new AppWidgetHost(getApplicationContext(), APPWIDGET_HOST_ID);
        //开始监听widget的变化
        mAppWidgetHost.startListening();
        frameLayout = (FrameLayout) findViewById(R.id.appwidget_fl);
    }

    public void appwidgetClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btn_pick:
                //创建AppWidget前需要为其分配一个appWidgetId
                appWidgetId = mAppWidgetHost.allocateAppWidgetId();
                //跳转到选择AppWidget的页面，传入刚刚创建的参数
                intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_PICK);
                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
                startActivityForResult(intent, REQUEST_PICK_APPWIDGET);
                break;
            case R.id.btn_bind:
                //创建AppWidget前需要为其分配一个appWidgetId
                appWidgetId = mAppWidgetHost.allocateAppWidgetId();
                //跳转到选择AppWidget的页面，传入刚刚创建的参数
                intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_BIND);
                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_PROVIDER, new ComponentName(getPackageName(), MyAppWidgetProvider.class.getSimpleName()));
                startActivityForResult(intent, REQUEST_BIND_APPWIDGET);
                break;
            case R.id.btn_update:
                RemoteViews myRemoteViews = new RemoteViews(getPackageName(), R.layout.view_appwidget);

                //为RemoteView上的按钮设置点击事件，点击桌面组件时进入主程序入口
                Intent intent1 = new Intent(this, MainActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent1, 0);
                myRemoteViews.setOnClickPendingIntent(R.id.contentBtn, pendingIntent);

                //实时更新RemoteViews上的时间
                myRemoteViews.setTextViewText(R.id.tv_appwidget, "它成功被更新了");
                mAppWidgetManager.updateAppWidget(appWidgetId, myRemoteViews);
                break;
            case R.id.btn_extra_update:
                Bundle bundle = new Bundle();
                bundle.putString("key", "value");
                mAppWidgetManager.updateAppWidgetOptions(appWidgetId, bundle);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_PICK_APPWIDGET) {
                addAppWidget(data);
            } else if (requestCode == REQUEST_START_APPWIDGET) {
                startAddAppWidget(data.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1));
            } else if (requestCode == REQUEST_BIND_APPWIDGET) {
                startAddAppWidget(data.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1));
            }
        } else if (resultCode == RESULT_CANCELED) {
            if (requestCode == REQUEST_PICK_APPWIDGET && resultCode == RESULT_CANCELED && data != null) {
                // Clean up the appWidgetId if we canceled
                int appWidgetId = data.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
                if (appWidgetId != -1) {
                    mAppWidgetHost.deleteAppWidgetId(appWidgetId);
                }
            }
        }
    }

    /**
     * 选中了某个widget之后，根据是否有配置来决定直接添加还是弹出配置activity
     */
    private void addAppWidget(Intent data) {
        //获得刚刚选取的AppWidget的id
        int appWidgetId = data.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
        String customWidget = data.getStringExtra(EXTRA_CUSTOM_WIDGET);
        //可能为空
        d("addAppWidget", "data:" + customWidget);
        if ("search_widget".equals(customWidget)) {
            //这里直接将search_widget删掉了
            mAppWidgetHost.deleteAppWidgetId(appWidgetId);
        } else {
            //根据id来获取appWidget的信息
            AppWidgetProviderInfo appWidget = mAppWidgetManager.getAppWidgetInfo(appWidgetId);
            //如果该appWidget有配置的话，先显示配置页面
            if (appWidget.configure != null) {
                //有配置，弹出配置
                Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_CONFIGURE);
                intent.setComponent(appWidget.configure);
                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
                startActivityForResult(intent, REQUEST_START_APPWIDGET);
            } else {
                //没有配置，直接添加
                startAddAppWidget(appWidgetId);
            }
        }
    }

    /**
     * 根据AppWidgetId来启动该AppWidget
     */
    private void startAddAppWidget(int appWidgetId) {
        AppWidgetProviderInfo appWidgetInfo = mAppWidgetManager.getAppWidgetInfo(appWidgetId);
        //根据id和appWidgetInfo创建view，并将View添加到framelayout中
        View hostView = mAppWidgetHost.createView(this, appWidgetId, appWidgetInfo);
        frameLayout.removeAllViews();
        frameLayout.addView(hostView);
    }


}