package com.example.viewdraghelpersample;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Scroller;
import android.widget.TextView;

/**
 * Created by hzlinxuanxuan on 2016/5/16.
 */
public class ScrollTextView extends TextView {
    public ScrollTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private Scroller mScroller = new Scroller(getContext());

    public void startScroll() {
        mScroller.startScroll(0, 0, 50, 50, 1000);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            Log.d("tag", "computeScroll : " + mScroller.getCurrX() + "," + mScroller.getCurrY() + "," + mScroller.getFinalX() + "," + mScroller.getFinalY());
            ViewCompat.offsetLeftAndRight(this, mScroller.getCurrX());
            ViewCompat.offsetTopAndBottom(this, mScroller.getCurrY());
//            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }
}
