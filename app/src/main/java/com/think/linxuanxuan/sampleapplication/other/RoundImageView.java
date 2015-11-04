package com.think.linxuanxuan.sampleapplication.other;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.ImageView;

import com.think.linxuanxuan.sampleapplication.R;


public class RoundImageView  extends ImageView {

    //圆形图片默认大小,用半径来记录
    private int circleRadius;
    //边框颜色和宽度
    private int borderColor = 0xffaabbcc;
    private int borderWidth = 10;

    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获取控件的参数
        parseAttributes(context.obtainStyledAttributes(attrs, R.styleable.RoundBitmapView));
    }

    /**
     * 测量view的大小
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int viewWidth = circleRadius * 2 + this.getPaddingLeft() + this.getPaddingRight() + borderWidth * 2;
        int viewHeight = circleRadius * 2 + this.getPaddingTop() + this.getPaddingBottom() + borderWidth * 2;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        //Measure Width
        if (widthMode == MeasureSpec.EXACTLY) {
            //Must be this size
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            width = Math.min(viewWidth, widthSize);
        } else {
            //Be whatever you want
            width = viewWidth;
        }

        //Measure Height
        if (heightMode == MeasureSpec.EXACTLY || widthMode == MeasureSpec.EXACTLY) {
            //Must be this size
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            height = Math.min(viewHeight, heightSize);
        } else {
            //Be whatever you want
            height = viewHeight;
        }
        setMeasuredDimension(width, height);
    }

    /**
     * Parse the attributes passed to the view from the XML
     * 读取xml中的布局属性来初始化参数
     *
     * @param a the attributes to parse
     */
    private void parseAttributes(TypedArray a) {
        // We transform the default values from DIP to pixels
        DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        borderWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, borderWidth, metrics);
        circleRadius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, circleRadius, metrics);

        circleRadius = (int) a.getDimension(R.styleable.RoundBitmapView_radius, circleRadius);
        borderWidth = (int) a.getDimension(R.styleable.RoundBitmapView_rborderWidth, borderWidth);
        borderColor = a.getColor(R.styleable.RoundBitmapView_borderColor, borderColor);
        // Recycle
        a.recycle();
    }

    /**
     * 绘制图片
     */
    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();

        if (drawable == null || getWidth() == 0 || getHeight() == 0)
            return;
        Bitmap b = ((BitmapDrawable) drawable).getBitmap();
        if (b != null) {
            Bitmap bitmap = b.copy(Bitmap.Config.ARGB_8888, true);
            Bitmap roundBitmap = getCroppedBitmap(bitmap, getHeight() / 2);
            canvas.drawBitmap(roundBitmap, 0, 0, null);
        }
    }

    public Bitmap getCroppedBitmap(Bitmap bmp, int radius) {
        Bitmap scaledBmp = Bitmap.createScaledBitmap(bmp, radius * 2, radius * 2, true);
        Bitmap output = Bitmap.createBitmap(radius * 2, radius * 2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();

        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        //将会以颜色ARBG填充整个控件的Canvas背景，现在指定的颜色是透明度为0，RGB：000000
        canvas.drawARGB(0, 0, 0, 0);

        // Draws a circle to create the border
        paint.setColor(borderColor);
        paint.setStrokeWidth(borderWidth);
        //画边框
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, circleRadius + borderWidth, paint);

        // Draws the image subtracting the border width
        BitmapShader s = new BitmapShader(scaledBmp, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(s);
        //画圆形图片
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, circleRadius, paint);

        //将刚刚画好的bitmap返回并画出来
        return output;
    }

}

