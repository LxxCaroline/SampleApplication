package com.think.linxuanxuan.sampleapplication.other;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by Think on 2015/7/20.
 */
public class MyLinearLayout extends LinearLayout {

    private static String TAG = "test_Mylinearlayout1";
    private Context mcontext;

    public MyLinearLayout(Context context) {
        super(context);
        mcontext = context;
    }

    public MyLinearLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        mcontext = context;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        String temp = event.getAction() == MotionEvent.ACTION_DOWN ? "Action_Down" : "Action_Up";
        boolean flag = super.onTouchEvent(event);
        Log.d(TAG, "onTouchEvent:" + flag + "-------" + temp);
        return flag;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        String temp = ev.getAction() == MotionEvent.ACTION_DOWN ? "Action_Down" : "Action_Up";
        boolean flag = super.dispatchTouchEvent(ev);
        Log.d(TAG, "dispatchTouchEvent:" + flag + "-------" + temp);
        return flag;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        String temp = ev.getAction() == MotionEvent.ACTION_DOWN ? "Action_Down" : "Action_Up";
        boolean flag = super.onInterceptTouchEvent(ev);
        Log.d(TAG, "onInterceptTouchEvent:" + flag + "-------" + temp);
        return flag;
    }

}
