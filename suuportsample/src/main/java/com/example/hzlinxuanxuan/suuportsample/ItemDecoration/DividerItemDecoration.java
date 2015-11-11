package com.example.hzlinxuanxuan.suuportsample.ItemDecoration;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by hzlinxuanxuan on 2015/11/9.
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    //获取系统的属性
    private final int[] ATTRS = new int[]{android.R.attr.listDivider};

    private Drawable mDivider;

    private int mOrientation;

    public DividerItemDecoration(Context context, int orientation) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
        setOrientation(orientation);
    }

    public void setOrientation(int orientation) {
        if (orientation != LinearLayout.HORIZONTAL && orientation != LinearLayout.VERTICAL) {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == LinearLayout.VERTICAL) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }


    public void drawVertical(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            Log.d("tag", i + "");
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();
            Log.d("tag", left + "," + top + "," + right + "," + bottom);
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    public void drawHorizontal(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }


    /**
     * 如果想要为item view添加多余的decoration（装饰)，则需要计算在该item view外围留出多少offset来绘制decoration
     * 在该例子中，需要绘制divider，例如在纵向绘制divider的话，只需要在纵向留出空白地方，即0, 0, 0, mDivider.getIntrinsicHeight()
     * 这里看到只有bottom方向留出了地方，其余三个方向left,top,right都不需要
     * 如果是绘制grid的divider的话，则需要在right和bottom都设置空白地方。
     * 这里需要注意的是，你需要多少空间绘制decoration就声明多少的空间，如果声明的空间大了，但是没有将该空间全部绘制到，则出现空白地方，较为难看
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //getIntrinsicHeight()和getIntrinsicWidth()获取drawable的高度和宽度
        if (mOrientation == LinearLayout.VERTICAL) {
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight()+10);
        } else {
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
        }
    }
}
