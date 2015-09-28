package com.think.linxuanxuan.sampleapplication.other;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * 因为该罗盘需要实时更新，如果设置delay的话，会导致旋转不顺畅
 */
public class Compass extends SurfaceView implements SurfaceHolder.Callback {

    private Thread drawThread;
    //当view初始化的时候，currentDegree和targetDegree一样大，当isInit为true时需要画
    private boolean isInit = true;

    //控件默认大小
    private int defaultWidth = 650, defaultHeight = 650;
    private int textPadding = 30;
    //控件的中心位置
    private int x, y;
    //箭头一半的长度
    private float compassLength = 200;
    //xoffset记录指针的头偏离控件正中间x轴的距离
    //yoffset记录指针的头偏离控件正中间y轴的距离
    private float xoffset, yoffset;
    //画文字的paint
    private Paint textPaint;
    private Paint linePaint;

    //记录当前指针的角度，这里都是用角度而不是弧度！但是在sin和cos计算的时候需要转换成弧度
    private float currentDegree;
    private float targetDegree;
    //记录每次指针旋转的角度
    private float step;
    private boolean isClockwise = true;

    private SurfaceHolder holder;
    private Canvas canvas;

    public Compass(Context context, AttributeSet attrs) {
        super(context, attrs);
        textPaint = new Paint();
        textPaint.setTextSize(30);
        textPaint.setColor(Color.BLACK);
        linePaint = new Paint();
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(10);
        step = 1f;
        linePaint.setStrokeCap(Paint.Cap.ROUND);
        currentDegree = 0;
        targetDegree = 0;
        this.holder = this.getHolder();
        this.holder.addCallback(this);
        drawThread = new ListenerThread();
        drawThread.start();
    }

    //在最开始的时候先画出东南西北的字样， 字样只用画一次，然后使箭头指向正北
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    }

    //根据旋转的角度来画箭头,与直角坐标系的y轴的夹角来算
    private void drawCompass(float degree) {
        if (this.holder == null)
            return;
        canvas = this.holder.lockCanvas();
        if (canvas == null)
            return;
        //保存之前画的东南西北
        canvas.drawColor(Color.WHITE);
        canvas.drawText("北", getWidth() / 2, getPaddingTop() + textPadding, textPaint);
        canvas.drawText("东", getWidth() - getPaddingRight() - textPadding, getHeight() / 2, textPaint);
        canvas.drawText("南", getWidth() / 2, getHeight() - getPaddingBottom() - textPadding, textPaint);
        canvas.drawText("西", getPaddingLeft() + textPadding, getHeight() / 2, textPaint);
        xoffset = (float) (Math.sin(angle2Radian(degree)) * compassLength);
        yoffset = (float) (Math.cos(angle2Radian(degree)) * compassLength);
        float cos = (float) (50 * Math.cos(angle2Radian(degree - 45)));
        float sin = (float) (50 * Math.sin(angle2Radian(degree - 45)));
        //箭头，最长的直线
        canvas.drawLine(x + xoffset, y - yoffset, x - xoffset, y + yoffset, linePaint);
        //箭头顶部左边的线
        canvas.drawLine(x + xoffset, y - yoffset, x + xoffset - cos, y - yoffset - sin, linePaint);
        //箭头顶部右边的线
        canvas.drawLine(x + xoffset, y - yoffset, x + xoffset - sin, y - yoffset + cos, linePaint);
        //释放canvas对象
        this.holder.unlockCanvasAndPost(canvas);
    }

    //角度转换弧度
    private double angle2Radian(float degree) {
        return 2 * Math.PI / 360 * degree;
    }

    public void setCurrentDegree(int degree) {
        //参数处理，当传入的degree过大时，将其卡在0至360之间
        if (degree > 0)
            degree -= 360 * (degree / 360);
        else
            degree += 360 * (degree / 360 + 1);
        targetDegree = (float) degree;
        Log.d("tag", "target:" + targetDegree + ",current:" + currentDegree);
    }

    /**
     * @param isClockwise 记录手机当前是顺时针旋转还是逆时针旋转
     */
    public void setClockwise(boolean isClockwise) {
        if (Math.abs(currentDegree - targetDegree) < 2 || Math.abs(currentDegree - targetDegree) > 358) {
            return;
        }
        this.isClockwise = isClockwise;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int viewWidth = defaultWidth + this.getPaddingLeft() + this.getPaddingRight();
        int viewHeight = defaultHeight + this.getPaddingTop() + this.getPaddingBottom();

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        //xml文件中layout_width和layout_height指定了数值大小或者使用match_parent，只能是这个大小
        if (widthMode == MeasureSpec.EXACTLY) {
            //Must be this size
            width = widthSize;
        }
        //xml文件中layout_width和layout_height使用了wrap_content，该控件的大小可以随着自身内容的大小而变化
        // 但是必须小于父控件，widthSize是指父控件的大小
        else if (widthMode == MeasureSpec.AT_MOST) {
            width = Math.min(viewWidth, widthSize);
        }
        //未指定，这种情况不多，一般都是父控件是AdapterView，通过measure方法传入的模式。
        else {
            //Be whatever you want
            width = viewWidth;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            height = Math.min(viewHeight, heightSize);
        } else {
            height = viewHeight;
        }
        compassLength = Math.min(width, height) * 3 / 5 / 2;
        x = width / 2;
        y = height / 2;
        setMeasuredDimension(width, height);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    class ListenerThread extends Thread {

        @Override
        public void run() {
            super.run();
            //一直监听targetDegree的改变
            while (true) {
                if (isInit) {
                    drawCompass(currentDegree);
                    isInit = false;
                }
                if (currentDegree != targetDegree) {
                    //指针的旋转方向由陀螺仪传感器的值决定
                    step = isClockwise ? Math.abs(step) : -Math.abs(step);
                    currentDegree = Math.abs(currentDegree - targetDegree) > Math.abs(step) ? currentDegree + step :
                            targetDegree;
                    if (currentDegree >= 360) {
                        currentDegree -= 360;
                    } else if (currentDegree < 0) {
                        currentDegree += 360;
                    }
                    drawCompass(currentDegree);
                }
            }
        }
    }

}
