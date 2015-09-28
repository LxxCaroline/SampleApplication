package com.think.linxuanxuan.sampleapplication.mainActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.think.linxuanxuan.sampleapplication.R;

public class ViewStubActivity extends Activity {

    boolean isClick = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewstub);
    }

    //按钮点击事件
    public void viewStubClick(View view) {
        if (!isClick) {
            isClick = true;
            ViewStub vs = (ViewStub) findViewById(R.id.viewstub);
            vs.inflate();
            LinearLayout layout = (LinearLayout) findViewById(R.id.vs_mylayout);
            layout.addView(new EditText(this));
        }
    }

}
