package com.example.hzlinxuanxuan.suuportsample.ItemDecoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.hzlinxuanxuan.suuportsample.R;

/**
 * Created by hzlinxuanxuan on 2015/11/9.
 * 在四周画上花状的边框
 */
public class FlowerDecoration extends RecyclerView.ItemDecoration {

    private Drawable leftD, rightD, topD, bottomD;
    private int leftOffset, rightOffset, topOffset, bottomOffset;

    public FlowerDecoration(Context context) {
        leftD = context.getResources().getDrawable(R.drawable.left);
        rightD = context.getResources().getDrawable(R.drawable.right);
        topD = context.getResources().getDrawable(R.drawable.top);
        bottomD = context.getResources().getDrawable(R.drawable.bottom);
        //getIntrinsicHeight()和getIntrinsicWidth()获取drawable的高度和宽度
        leftOffset = leftD.getIntrinsicWidth() / 2;
        rightOffset = rightD.getIntrinsicWidth() / 2;
        topOffset = topD.getIntrinsicHeight() / 2;
        bottomOffset = bottomD.getIntrinsicHeight() / 2;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left, right, top, bottom;
        for (int i = 0; i < parent.getChildCount(); i++) {
            final View child = parent.getChildAt(i);
            left = child.getLeft();
            right = child.getRight();
            top = child.getTop();
            bottom = child.getBottom();
            topD.setBounds(left - leftOffset, top - topOffset, right + rightOffset, top);
            bottomD.setBounds(left - leftOffset, bottom, right + rightOffset, bottom + bottomOffset);
            leftD.setBounds(left - leftOffset, top, left, bottom);
            rightD.setBounds(right, top, right + rightOffset, bottom);
            topD.draw(c);
            bottomD.draw(c);
            leftD.draw(c);
            rightD.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(leftOffset, topOffset, rightOffset, bottomOffset);
    }
}
