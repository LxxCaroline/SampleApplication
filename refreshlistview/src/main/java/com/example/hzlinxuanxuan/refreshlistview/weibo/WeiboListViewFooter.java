package com.example.hzlinxuanxuan.refreshlistview.weibo;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hzlinxuanxuan.refreshlistview.R;

/**
 * Created by hzlinxuanxuan on 2015/10/29.
 */
public class WeiboListViewFooter extends LinearLayout {
    //该状态表示footer view还未完全显示，可以继续向上拉
    public final static int STATE_NORMAL = 0;
    //该状态表示可以松手更新了
    public final static int STATE_READY = 1;
    //该状态表示正在加载中
    public final static int STATE_LOADING = 2;

    private Context mContext;

    private RelativeLayout footerLayout;
    private ProgressBar mProgressBar;
    private TextView tvHint;

    public WeiboListViewFooter(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        footerLayout = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.view_listview_footer, null);
        addView(footerLayout);
        footerLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        mProgressBar = (ProgressBar) footerLayout.findViewById(R.id.pb_footer_progress);
        tvHint = (TextView) footerLayout.findViewById(R.id.tv_footer_hint);
    }

    public void setState(int state) {
        switch (state) {
            case STATE_READY:
            case STATE_NORMAL:
                tvHint.setText("加载完毕");
                mProgressBar.setVisibility(INVISIBLE);
                break;
            case STATE_LOADING:
                tvHint.setText("加载中");
                mProgressBar.setVisibility(VISIBLE);
                break;
        }
    }

    public void setHeight(int height) {
        LayoutParams lp = (LayoutParams) footerLayout.getLayoutParams();
        lp.height = height;
        footerLayout.setLayoutParams(lp);
    }
}
