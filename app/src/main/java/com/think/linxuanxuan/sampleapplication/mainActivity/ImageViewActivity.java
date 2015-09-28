package com.think.linxuanxuan.sampleapplication.mainActivity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.think.linxuanxuan.sampleapplication.R;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class ImageViewActivity extends Activity {

    @InjectView(R.id.iv_origin)
    ImageView ivOrigin;
    @InjectView(R.id.iv_detail)
    ImageView ivDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageview);
        ButterKnife.inject(this);
        showImage();
    }

    void showImage() {
        //担心原图太大就先压缩
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;
        Bitmap originBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test, options);
        ivOrigin.setImageBitmap(originBitmap);
        ivOrigin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Bitmap bitmap = ((BitmapDrawable) getResources().getDrawable(R.drawable.test)).getBitmap();
                //x和y为中心，这只是一个比例，因为图片大小比控件大，如果从图片上x位置取，永远也取不完图片
                float x = event.getX() / ivOrigin.getWidth() * bitmap.getWidth();
                float y = event.getY() / ivOrigin.getHeight() * bitmap.getHeight();
                Log.d("tag", "x:" + x + ",y:" + y);
                //显示在ivDetail中的大小
                final int width = ivDetail.getWidth();
                final int height = ivDetail.getHeight();
                if (x < width / 2) {
                    x = width / 2;
                } else if (x > bitmap.getWidth() - width / 2) {
                    x = bitmap.getWidth() - width / 2;
                }
                if (y < height / 2) {
                    y = height / 2;
                } else if (y > bitmap.getHeight() - height / 2) {
                    y = bitmap.getHeight() - height / 2;
                }
                Log.d("tag", "width:" + width + ",height:" + height);
                ivDetail.setImageBitmap(Bitmap.createBitmap(bitmap, (int) x - width / 2, (int) y - height / 2, width,
                        height));
                return true;
            }
        });
    }
}
