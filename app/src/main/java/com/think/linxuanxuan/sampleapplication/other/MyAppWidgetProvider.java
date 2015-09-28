package com.think.linxuanxuan.sampleapplication.other;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.think.linxuanxuan.sampleapplication.R;
import com.think.linxuanxuan.sampleapplication.mainActivity.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * AppWidgetProvider其实是一个广播接收器，继承BroadcastReceiver
 */
public class MyAppWidgetProvider extends AppWidgetProvider {

    /**
     * 该函数是BroadcastReceiver中的函数，当他接收到广播消息的之后，AppWidgetProvider中会在该函数中
     * 将消息进行处理，不同的消息会触发不同的函数，下面是消息和处理函数对应表
     * onUpdate()----AppWidgetManager.ACTION_APPWIDGET_UPDATE
     * onDeleted()----AppWidgetManager.ACTION_APPWIDGET_DELETED
     * onEnabled()----AppWidgetManager.ACTION_APPWIDGET_ENABLED
     * onDisabled()----AppWidgetManager.ACTION_APPWIDGET_DISABLED
     * 接收到每个广播时都会被调用，而且在上面的回调函数之前。
     * 所以如果将super.onReceive去掉的话，消息将不会被处理
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("tag", "onReceive");
        super.onReceive(context, intent);
    }

    /**
     * 用来更新App Widget。当添加一个AppWidget时也会调用该函数
     * 每个Widget都会有一个唯一的ID，在onUpdate方法中可能需要更新多个Widget，因此这些Widget的ID通过appWidgetIds参数传入onUpdate函数中
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int i = 0; i < appWidgetIds.length; i++) {
            Log.d("tag", "onUpdate:" + appWidgetIds[i]);
            updateAppWidget(context, appWidgetIds[i], appWidgetManager, "");
        }
    }

    public static void updateAppWidget(Context context, int appWidgetId, AppWidgetManager manager, String content) {
        SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        /**
         * RemoteViews类描述了一个View对象能够显示在其他进程中，可以融合layout资源文件实现布局。
         *虽然该类在android.widget.RemoteViews而不是appWidget下面,但在Android Widgets开发中会经常用到它，
         *主要是可以跨进程调用(appWidget由一个服务宿主来统一运行的)。
         */
        RemoteViews myRemoteViews = new RemoteViews(context.getPackageName(), R.layout.view_appwidget);

        //为RemoteView上的按钮设置点击事件，点击桌面组件时进入主程序入口
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        myRemoteViews.setOnClickPendingIntent(R.id.contentBtn, pendingIntent);

        //实时更新RemoteViews上的时间
        myRemoteViews.setTextViewText(R.id.tv_appwidget,
                format.format(curDate) + (content.equals("") ? "" : "====" + content));

        manager.updateAppWidget(appWidgetId, myRemoteViews);
    }

    /**
     * 当App Widget从宿主中删除时被调用。 删除一个AppWidget时调用
     */
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    /**
     * 当一个App Widget实例第一次创建时被调用。
     * 比如，如果用户添加两个App Widget实例，只在第一次被调用。
     * 如果你需要打开一个新的数据库或者执行其他对于所有的App Widget实例只需要发生一次的设置，那么这里是完成这个工作的好地方。
     */
    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    /**
     * 当最后一个appWidget被删除时调用
     */
    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

    /**
     * 当AppWidget发送extra info来更新appWidget触发该函数
     */
    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
        Log.d("tag", "onAppWidgetOptionsChanged:" + appWidgetId + "-------value is " + newOptions.getString("key"));
    }
}