package com.example.viewdraghelpersample;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by hzlinxuanxuan on 2016/5/13.
 */
public class ViewDragHelperLayout extends LinearLayout {

    private ViewDragHelper helper;
    private View mCommonView, mAutobackView, mEdgeTrackerView;

    private Point mAutoBackOriginPos = new Point();

    public ViewDragHelperLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        helper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return child == mCommonView || child == mAutobackView;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                return left;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                return top;
            }

            //手释放的时候回调
            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                if (releasedChild == mAutobackView) {
//                    helper.settleCapturedViewAt(mAutoBackOriginPos.x, mAutoBackOriginPos.y);
                    helper.flingCapturedView(200, 200, 300, 300);
                    invalidate();
                }
            }

            //在边界拖动的时候回调
            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                helper.captureChildView(mEdgeTrackerView, pointerId);
            }

            @Override
            public void onViewDragStateChanged(int state) {
                //拖拽一个view的时候，状态发生变化。
                Log.d("tag", "onViewDragStateChanged:" + state);
            }

            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
//                Log.d("tag", "onViewPositionChanged:" + left + "," + top + "," + dx + "," + dy);
            }

            @Override
            public void onViewCaptured(View capturedChild, int activePointerId) {
                Log.d("tag", "onViewCaptured:" + activePointerId);
            }
        });
        helper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return helper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        helper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if (helper.continueSettling(true)) {
            invalidate();
        }
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mAutoBackOriginPos.x = mAutobackView.getLeft();
        mAutoBackOriginPos.y = mAutobackView.getTop();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mCommonView = getChildAt(0);
        mAutobackView = getChildAt(1);
        mEdgeTrackerView = getChildAt(2);
    }
}
