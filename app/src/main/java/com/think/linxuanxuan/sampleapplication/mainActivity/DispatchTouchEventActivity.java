package com.think.linxuanxuan.sampleapplication.mainActivity;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.think.linxuanxuan.sampleapplication.R;


public class DispatchTouchEventActivity extends Activity implements View.OnClickListener {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch_touch_event);
        button = (Button) findViewById(R.id.mybutton);
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                String temp=event.getAction()==MotionEvent.ACTION_DOWN?"Action_Down":"Action_Up";
                Log.d("test", "button onTouch:false--------"+temp);
                return false;
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        String temp=ev.getAction()==MotionEvent.ACTION_DOWN?"Action_Down":"Action_Up";
        boolean flag = super.dispatchTouchEvent(ev);
        Log.d("test", "dispatchTouchEvent:" + flag+"-------"+temp);
        return flag;
    }

    @Override
    public void onUserInteraction() {
        Log.d("test", "onUserInteraction");
        super.onUserInteraction();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        String temp=event.getAction()==MotionEvent.ACTION_DOWN?"Action_Down":"Action_Up";
        boolean flag = super.onTouchEvent(event);
        Log.d("test", "onTouchEvent:" + flag+"-------"+temp);
        return flag;
    }

    @Override
    public void onClick(View v) {
        Log.d("test", "onClick");
    }
}
