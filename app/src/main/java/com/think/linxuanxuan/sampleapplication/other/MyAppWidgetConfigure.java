package com.think.linxuanxuan.sampleapplication.other;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.think.linxuanxuan.sampleapplication.R;

public class MyAppWidgetConfigure extends Activity implements View.OnClickListener {

    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private EditText et;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResult(RESULT_CANCELED);
        setContentView(R.layout.view_appwidget_configure);
        et = (EditText) findViewById(R.id.et_appwidget_configure);
        btn = (Button) findViewById(R.id.btn_appwidget_configure);
        btn.setOnClickListener(this);
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                //获取当前添加的AppWidget的ID
                mAppWidgetId = bundle.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager
                        .INVALID_APPWIDGET_ID);
            }
        }
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }
        et.setText(mAppWidgetId + "");
    }

    @Override
    public void onClick(View v) {
        final Context context = MyAppWidgetConfigure.this;
        String title = et.getText().toString();
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        MyAppWidgetProvider.updateAppWidget(context, mAppWidgetId, manager, title);
        Intent intent = new Intent();
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, intent);
        finish();
    }
}
