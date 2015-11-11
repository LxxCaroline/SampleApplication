package com.example.hzlinxuanxuan.suuportsample.ItemDecoration;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by hzlinxuanxuan on 2015/11/9.
 * 最后一行和最后一列不绘制Divider
 */
public class GridDividerItemDecoration extends RecyclerView.ItemDecoration {

    //获取系统的属性
    private final int[] ATTRS = new int[]{android.R.attr.listDivider};

    private Drawable mDivider;

    public GridDividerItemDecoration(Context context) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        drawVertical(c, parent);
        drawHorizontal(c, parent);
    }


    public void drawVertical(Canvas c, RecyclerView parent) {
        int left, right, top, bottom;
        for (int i = 0; i < parent.getChildCount(); i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            left = child.getLeft() - params.leftMargin;
            top = child.getBottom() + params.bottomMargin;
            right = child.getRight() + params.rightMargin;
            bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    public void drawHorizontal(Canvas c, RecyclerView parent) {
        int top, bottom, left, right;
        for (int i = 0; i < parent.getChildCount(); i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            left = child.getRight() + params.rightMargin;
            top = child.getTop() - params.topMargin;
            right = left + mDivider.getIntrinsicHeight();
            bottom = child.getBottom() + params.bottomMargin;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //getIntrinsicHeight()和getIntrinsicWidth()获取drawable的高度和宽度
        outRect.set(10, 10, mDivider.getIntrinsicWidth(), mDivider.getIntrinsicHeight());
    }
}
