package com.think.linxuanxuan.sampleapplication.mainActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ViewFlipper;

import com.think.linxuanxuan.sampleapplication.R;

/**
 * Created by hzlinxuanxuan on 2015/11/20.
 */
public class ViewFlipperActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_flipper);
        ViewFlipper flipper= (ViewFlipper) findViewById(R.id.flipper);
        flipper.startFlipping();
    }
}
