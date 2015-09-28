package com.think.linxuanxuan.sampleapplication.other;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import com.think.linxuanxuan.sampleapplication.R;


public class DrawBitmapView extends View {

    private Bitmap bitmap;
    //每次刷新比上一次多显示图片的比例
    private float step = 0.01f;
    //已经显示图片的比例
    private float currentScale = 0.0f;
    //显示图片的区域
    private RectF dst;
    private Rect src;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1)
                invalidate();
        }
    };

    public DrawBitmapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = 2;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.snail, opts);
        dst = new RectF();
        dst.left = 0;
        dst.top = 0;
        dst.bottom = bitmap.getHeight();
        src = new Rect();
        src.left = 0;
        src.top = 0;
        src.bottom = bitmap.getHeight();
    }

    /**
     * 默认从左到右显示
     */
    @Override
    protected void onDraw(Canvas canvas) {
        currentScale = currentScale + step > 1 ? 1 : currentScale + step;
        dst.right = bitmap.getWidth() * currentScale;
        src.right = (int) (bitmap.getWidth() * currentScale);
        /*
         * drawBitmap(Bitmap bitmap, Rect src, RectF dst, Paint paint)；
         * Rect src: 是对图片进行裁截，若是空null则显示整个图片
         * RectF dst：是图片在Canvas画布中显示的区域，
         * 大于src则把src的裁截区放大，
         * 小于src则把src的裁截区缩小。
         * 当想要让图片以画卷方式展现的话，主要是设置src大小，这边是默认从左到右显示，所以每次只要修改src中right的大小就好
         */
        canvas.drawBitmap(bitmap, src, dst, null);
        if (currentScale >= 1) {
            //当显示完图片，重置，循环显示
            currentScale = 0 - step;
        }
        //不想让整个页面处于频繁刷新的状态，这里延迟了10ms来刷新该页面
        handler.sendEmptyMessageDelayed(1, 10);
    }
}
