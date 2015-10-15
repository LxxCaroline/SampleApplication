package com.think.linxuanxuan.sampleapplication.other;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.RadioButton;

import com.think.linxuanxuan.sampleapplication.R;

/**
 * Created by hzlinxuanxuan on 2015/10/14.
 */
public class RoundProgressView extends RadioButton {

    private int outerRadius = 50;
    private int outerStroke = 3;
    private int innerRadius = 30;
    private int innerColor = 0xff999999;
    private Paint innerPaint;
    private String text;
    private int textSize = 25;

    public RoundProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttributeSet(context, attrs);
        innerPaint = new Paint();
        innerPaint.setColor(innerColor);
        innerPaint.setAntiAlias(true);
        setBackground(new RoundBackgroundDrawable());
    }

    private void parseAttributeSet(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.round_progress, 0, 0);
        outerRadius = (int) ta.getDimension(R.styleable.round_progress_outerRadius, outerRadius);
        innerRadius = (int) ta.getDimension(R.styleable.round_progress_innerRadius, innerRadius);
        innerColor = ta.getColor(R.styleable.round_progress_innerColor, innerColor);
        ta.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isChecked()) {
            canvas.drawCircle(getWidth() / 2, (getHeight() - textSize) / 2, innerRadius, innerPaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(
                getPaddingLeft() + getPaddingRight() + outerRadius * 2 + outerStroke * 2,
                getPaddingTop() + getPaddingBottom() + outerRadius * 2 + outerStroke * 2 + textSize);
    }

    @Override
    public void toggle() {
        super.toggle();
        invalidate();
    }

    private class RoundBackgroundDrawable extends Drawable {

        @Override
        public void draw(Canvas canvas) {
            Paint outerPaint = new Paint();
            outerPaint.setColor(Color.DKGRAY);
            outerPaint.setStyle(Paint.Style.STROKE);
            outerPaint.setAntiAlias(true);
            outerPaint.setStrokeWidth(outerStroke);
            outerPaint.setStrokeCap(Paint.Cap.ROUND);
            canvas.drawCircle(getWidth() / 2, (getHeight() - textSize) / 2, outerRadius, outerPaint);

            text = (String) getText();
            outerPaint.setTextSize(textSize);
            outerPaint.setColor(Color.BLACK);
            outerPaint.setStrokeWidth(2);
            canvas.drawText(text, getWidth() / 2 - outerPaint.measureText(text) / 2, getHeight() - 2, outerPaint);
        }

        @Override
        public void setAlpha(int alpha) {
        }

        @Override
        public void setColorFilter(ColorFilter cf) {
        }

        @Override
        public int getOpacity() {
            return 0;
        }
    }
}
