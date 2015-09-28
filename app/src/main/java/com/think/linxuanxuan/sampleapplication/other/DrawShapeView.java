package com.think.linxuanxuan.sampleapplication.other;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


public class DrawShapeView extends View implements View.OnTouchListener {

    private Paint p1 = new Paint();
    private Paint p2 = new Paint();
    private Paint p3 = new Paint();
    private boolean userCenter = true;
    //用于设置绘制文本的字体大小(5个文本)
    private float[] textSizeArray = new float[]{15, 18, 21, 24, 27};

    public DrawShapeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        p1.setColor(Color.BLACK);
        p2.setColor(Color.RED);
        p3.setColor(Color.BLUE);
        //设置画笔的宽度
        p1.setStrokeWidth(2);
        p2.setStrokeWidth(4);
        p3.setStrokeWidth(6);
        setOnTouchListener(this);
    }

    /**
     * 画多条直线，首尾相接,当给出三个坐标，就可以绘制3条直线，而不像drawLine方法一样需要给出6个坐标
     */
    private void drawLineExt(Canvas canvas, float[] pts, Paint paint) {
        for (int i = 0; i < pts.length; i += 2) {
            if (i < pts.length - 2) {
                canvas.drawLine(pts[i], pts[i + 1], pts[i + 2], pts[i + 3], paint);
            } else {
                canvas.drawLine(pts[i], pts[i + 1], pts[0], pts[1], paint);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //绘制像素点
        canvas.drawPoint(60, 120, p3);
        canvas.drawPoint(70, 130, p3);
        canvas.drawPoints(new float[]{70, 140, 75, 145, 75, 160}, p2);
        //绘制直线
        canvas.drawLine(10, 10, 300, 10, p1);
        canvas.drawLine(10, 30, 300, 30, p2);
        canvas.drawLine(10, 50, 300, 50, p3);
        //绘制正方形
        drawLineExt(canvas, new float[]{10, 70, 120, 70, 120, 170, 10, 170}, p2);
        //绘制三角形
        drawLineExt(canvas, new float[]{160, 70, 230, 150, 170, 155}, p2);
        //设置非填充状态
        p2.setStyle(Paint.Style.STROKE);
        //画空心圆
        canvas.drawCircle(260, 110, 40, p2);
        //设置填充状态
        p2.setStyle(Paint.Style.FILL);
        //画实心圆
        canvas.drawCircle(260, 110, 30, p2);
        //画圆弧
        RectF rectF = new RectF(30, 190, 120, 280);
        canvas.drawArc(rectF, 0, 200, userCenter, p2);
        //画空心椭圆
        rectF = new RectF(140, 190, 280, 290);
        p2.setStyle(Paint.Style.STROKE);
        canvas.drawArc(rectF, 0, 360, userCenter, p2);
        //绘制文本
        float y = 0;
        for (int i = 0; i < textSizeArray.length; i++) {
            p1.setTextSize(textSizeArray[i]);
            p1.setColor(Color.BLUE);
            //获得文本宽度可以用measureText方法
            canvas.drawText("Android(宽度:" + p1.measureText("Android") + ")", 20, 315 + y, p1);
            y += p1.getTextSize() + 5;
        }
        p1.setTextSize(22);
        //绘制文本，单独设置每一个字符的坐标，第一个坐标（180,230）是圆的坐标，第二个坐标（210,250）是形的坐标
        canvas.drawPosText("圆形", new float[]{180, 230, 210, 250}, p1);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (userCenter) {
            userCenter = false;
            p1.setColor(Color.RED);
            p2.setColor(Color.BLACK);
            p3.setColor(Color.GREEN);
            //设置画笔的宽度
            p1.setStrokeWidth(6);
            p2.setStrokeWidth(4);
            p3.setStrokeWidth(2);
        } else {
            userCenter = true;
            p1.setColor(Color.BLACK);
            p2.setColor(Color.RED);
            p3.setColor(Color.BLUE);
            //设置画笔的宽度
            p1.setStrokeWidth(2);
            p2.setStrokeWidth(4);
            p3.setStrokeWidth(6);
        }
        //每次触摸屏幕，将字体大小倒置
        for (int i = 0; i < textSizeArray.length / 2; i++) {
            float textSize = textSizeArray[i];
            textSizeArray[i] = textSizeArray[textSizeArray.length - i - 1];
            textSizeArray[textSizeArray.length - i - 1] = textSize;
        }
        invalidate();
        return false;
    }
}
