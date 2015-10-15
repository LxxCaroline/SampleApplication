package com.think.linxuanxuan.sampleapplication.other;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.think.linxuanxuan.sampleapplication.R;

import java.util.ArrayList;

/**
 * Created by hzlinxuanxuan on 2015/10/14.
 */
public class RoundProgressSetLayout extends LinearLayout {

    private ArrayList<Integer> progresses;
    private RoundProgressView[] views;

    public RoundProgressSetLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(HORIZONTAL);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        progresses = new ArrayList<>();
        progresses.add(200);
        progresses.add(300);
        progresses.add(500);
        progresses.add(800);
        progresses.add(1000);
        views = new RoundProgressView[progresses.size()];
        LayoutInflater inflater = LayoutInflater.from(context);
        for (int i = 0; i < progresses.size(); i++) {
            RoundProgressView view = new RoundProgressView(context, attrs);
            LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 3);
            views[i] = view;
            addView(view, layoutParams);
            if (i < progresses.size() - 1) {
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.addRule(Gravity.CENTER_VERTICAL);
                for (int j = 0; j < 4; j++) {
                    ImageView iv =(ImageView) inflater.inflate(R.layout.view_dot, null);
                    addView(iv, params);
                }
            }
        }
    }


}
