package com.think.linxuanxuan.sampleapplication.other;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Think on 2015/9/4.
 */
public class DrawCircleView extends View implements View.OnTouchListener {

    //圆心所在的所标
    private float x;
    private float y;
    //触摸点所在坐标
    private float tx;
    private float ty;
    //是否被触摸，如果触摸，需要onDraw，直到小球到达目的地
    private boolean isTouch;
    //长宽比例，移动的方向是两个触摸点的直线，按照比例移动
    private float scale;
    //小球每次绘画前进的大小
    private float step = 8;
    //根据step来算每个step在垂直方向和水平方向应该走多少，sy^2+sx^2=step^2
    private double sy, sx;
    //半径大小
    private final float radius = 30;
    private Paint paint;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                invalidate();
            }
        }
    };

    public DrawCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(this);
        paint = new Paint();
        paint.setColor(Color.WHITE);
        x = 30;
        y = 30;
        isTouch = false;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isTouch) {
            calculateDraw();
        }
        canvas.drawCircle(x, y, radius, paint);
        //如果计算完发现已经走到目的地，则重置
        if (isTouch && y == ty) {
            isTouch = false;
        } else if (isTouch && y != ty) {
            //发送消息更新界面
            handler.sendEmptyMessageDelayed(1, 10);
        }
    }

    /**
     * 计算这次绘制小球时的圆心
     */
    private void calculateDraw() {
        //如果当小球快要接近目的地，step<5时，直接设置为ty，否则会出现小球在目标位置附近浮动
        if (y < ty) y = y < ty - (float) sy ? y + (float) sy : ty;
        else y = y > ty + (float) sy ? y - (float) sy : ty;

        if (x < tx) x = x < tx - (float) sx ? x + (float) sx : tx;
        else x = x > tx + (float) sx ? x - (float) sx : tx;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        tx = event.getX();
        ty = event.getY();
        scale = Math.abs(x - tx) / Math.abs(y - ty);
        sy = Math.sqrt(Math.pow(step, 2) / (1 + Math.pow(scale, 2)));
        sx = scale * sy;
        isTouch = true;
        invalidate();
        return false;
    }
}
