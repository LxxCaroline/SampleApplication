package com.think.linxuanxuan.sampleapplication.other;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/**
 * Created by Think on 2015/9/4.
 * SurfaceView允许在其他线程画，该类可以优化，开启一个线程去画。目前是在主线程画的。
 */
public class DrawCircleSurfaceView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {

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

    private SurfaceHolder holder;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                drawCircle();
            }
        }
    };

    public DrawCircleSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Color.WHITE);
        x = 30;
        y = 30;
        isTouch = false;
        this.holder = this.getHolder();
        this.holder.addCallback(this);
        setOnTouchListener(this);
        this.setFocusable(true);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawCircle();
    }

    /**
     * 画小球
     */
    private void drawCircle() {
        if (isTouch) {
            calculateDraw();
        }
        if (holder == null)
            return;
        //获得canvas对象
        Canvas canvas = holder.lockCanvas();
        //清空屏幕
        canvas.drawColor(Color.GRAY);
        if (canvas == null)
            return;
        canvas.drawCircle(x, y, radius, paint);
        //释放canvas对象
        holder.unlockCanvasAndPost(canvas);
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
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        tx = event.getX();
        ty = event.getY();
        scale = Math.abs(x - tx) / Math.abs(y - ty);
        sy = Math.sqrt(Math.pow(step, 2) / (1 + Math.pow(scale, 2)));
        sx = scale * sy;
        isTouch = true;
        drawCircle();
        return false;
    }
}
