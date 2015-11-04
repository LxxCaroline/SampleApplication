package com.example.hzlinxuanxuan.refreshlistview.weibo;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.hzlinxuanxuan.refreshlistview.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hzlinxuanxuan on 2015/10/29.
 */
public class WeiboListViewHeader extends LinearLayout {
    private LinearLayout headerLayout;
    private ImageView ivArrow;
    private ProgressBar mProgressBar;
    private TextView tvHint;
    private TextView tvTime;
    private int mState = STATE_NORMAL;

    private Animation mRotateUpAnim;
    private Animation mRotateDownAnim;

    private final int ROTATE_ANIM_DURATION = 180;

    //表示当前header view处于的状态
    //该状态表示下拉还未完全显示header view
    public final static int STATE_NORMAL = 0;
    //该状态表示下拉已经完全显示header view，可以松手刷新了
    public final static int STATE_READY = 1;
    //该状态表示正在刷新中
    public final static int STATE_REFRESHING = 2;

    public WeiboListViewHeader(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {
        //初始情况，设置下拉刷新view高度为0
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, 0);
        headerLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.view_listview_header, null);
        addView(headerLayout, lp);
        //这步很重要，在下拉过程中，该layout一直处于底部，上面多出的空余部分都是headerView，但是这么做的话会让人只有底部那一点才是headerView
        setGravity(Gravity.BOTTOM);

        ivArrow = (ImageView) findViewById(R.id.iv_header_arrow);
        tvHint = (TextView) findViewById(R.id.tv_header_hint);
        tvTime = (TextView) findViewById(R.id.tv_header_time);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_header_progress);

        //这两个动画和箭头有关系，在下拉的时候箭头向下，在向上回收的时候箭头向上
        mRotateUpAnim = new RotateAnimation(0.0f, -180.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateUpAnim.setFillAfter(true);
        mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateDownAnim.setFillAfter(true);
    }

    public void setState(int state) {
        if (state == mState) return;
        switch (state) {
            case STATE_NORMAL:
                ivArrow.setVisibility(VISIBLE);
                tvTime.setVisibility(INVISIBLE);
                mProgressBar.setVisibility(INVISIBLE);
                if (mState == STATE_READY) {
                    ivArrow.startAnimation(mRotateDownAnim);
                }
                if (mState == STATE_REFRESHING) {
                    ivArrow.clearAnimation();
                }
                tvHint.setText("下拉刷新");
                break;
            case STATE_READY:
                ivArrow.setVisibility(VISIBLE);
                tvTime.setVisibility(INVISIBLE);
                mProgressBar.setVisibility(INVISIBLE);
                if (mState != STATE_READY) {
                    ivArrow.clearAnimation();
                    ivArrow.startAnimation(mRotateUpAnim);
                    tvHint.setText("释放更新");
                }
                break;
            case STATE_REFRESHING:
                tvHint.setText("加载中");
                ivArrow.clearAnimation();
                ivArrow.setVisibility(INVISIBLE);
                tvTime.setVisibility(VISIBLE);
                mProgressBar.setVisibility(VISIBLE);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date curDate = new Date(System.currentTimeMillis());
                tvTime.setText(formatter.format(curDate));
                break;
            default:
        }

        mState = state;
    }

    public void setVisiableHeight(int height) {
        if (height < 0)
            height = 0;
        LayoutParams lp = (LayoutParams) headerLayout.getLayoutParams();
        lp.height = height;
        headerLayout.setLayoutParams(lp);
    }

    public int getVisiableHeight() {
        return headerLayout.getHeight();
    }

}

