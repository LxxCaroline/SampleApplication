package com.think.linxuanxuan.sampleapplication.other;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * Created by Think on 2015/7/20.
 */
public class MyButton extends Button {
    private static String TAG="test_mybutton";
    private Context mcontext;
    public MyButton(Context context) {
        super(context);
        mcontext=context;
    }

    public MyButton(Context context,AttributeSet attributeSet){
        super(context,attributeSet);
        mcontext=context;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        String temp=event.getAction()==MotionEvent.ACTION_DOWN?"Action_Down":"Action_Up";
        boolean flag = super.dispatchTouchEvent(event);
        Log.d(TAG, "dispatchTouchEvent:" + flag+"-------"+temp);
        return flag;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        String temp=event.getAction()==MotionEvent.ACTION_DOWN?"Action_Down":"Action_Up";
        boolean flag = super.onTouchEvent(event);
        Log.d(TAG, "onTouchEvent:" + flag+"-------"+temp);
        return flag;
    }

}
