package com.think.linxuanxuan.sampleapplication.mainActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import com.think.linxuanxuan.sampleapplication.R;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class ImageSwitcherActivity extends Activity implements ViewSwitcher.ViewFactory {

    @InjectView(R.id.imageswithcer)
    ImageSwitcher switcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageswitcher);
        ButterKnife.inject(this);
        switcher.setFactory(this);
        switcher.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
        switcher.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
    }

    public void changeImage(View view) {
        switch (view.getId()) {
            case R.id.btn_image1:
                switcher.setImageResource(R.drawable.test);
                break;
            case R.id.btn_image2:
                switcher.setImageResource(R.drawable.ic_launcher);
                break;
        }
    }

    @Override
    public View makeView() {
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setLayoutParams(new ImageSwitcher.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                .LayoutParams.MATCH_PARENT));
        return imageView;
    }
}
