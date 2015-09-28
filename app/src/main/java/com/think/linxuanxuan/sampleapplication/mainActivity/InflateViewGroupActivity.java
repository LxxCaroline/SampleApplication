package com.think.linxuanxuan.sampleapplication.mainActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.think.linxuanxuan.sampleapplication.R;


public class InflateViewGroupActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //inflate函数中有两个参数
        // 第一个参数表示XML布局资源文件的id，第二个参数表示获得容器视图对象后，要将该对象添加到哪个容器视图中
        LinearLayout layout1 = (LinearLayout) getLayoutInflater().inflate(R.layout.view_test1, null);
        LinearLayout layout2 = (LinearLayout) getLayoutInflater().inflate(R.layout.view_test2, layout1);
        //这里也可以使用下面这种方法
//        layout2 = (LinearLayout) getLayoutInflater().inflate(R.layout.view_test2, null);
//        layout1.addView(layout2);
        setContentView(layout1);
    }
}
