package com.think.linxuanxuan.sampleapplication.mainActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.think.linxuanxuan.sampleapplication.R;

/**
 * Created by hzlinxuanxuan on 2015/10/12.
 */
public class ActivityAnimationActivity extends AppCompatActivity {

    private ImageView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_animation);
        view = (ImageView) findViewById(R.id.iv_scale);
    }

    public void activityAnimationClick(View v) {
        Intent intent = new Intent(this, ClearTopActivity.class);
        ActivityOptionsCompat options;
        switch (v.getId()) {
            case R.id.btn_animstart:
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, R.anim.abc_slide_in_top);
                break;
            case R.id.btn_animfinish:
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.btn_compat_start:
                options = ActivityOptionsCompat.makeCustomAnimation(this, android.R.anim.slide_in_left, R.anim.abc_slide_out_top);
                ActivityCompat.startActivity(this, intent, options.toBundle());
                break;
            case R.id.btn_compat_finish:
                finish();
                ActivityCompat.finishAfterTransition(this);
                break;
            case R.id.btn_scale_start:
                options = ActivityOptionsCompat.makeScaleUpAnimation(view,
                        0, 0, 100, 100);
                ActivityCompat.startActivity(this, intent, options.toBundle());
                break;
            case R.id.btn_thumb_start:
                Bitmap bitmap = ((BitmapDrawable)getResources().getDrawable(R.drawable.snail)).getBitmap();
                options = ActivityOptionsCompat.makeThumbnailScaleUpAnimation(view, bitmap, 0, 0);
                ActivityCompat.startActivity(this, intent, options.toBundle());
                break;
        }
    }
}
