package com.think.linxuanxuan.sampleapplication.mainActivity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.think.linxuanxuan.sampleapplication.R;


public class ToastActivity extends Activity {

    private Button btn;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toast);
        btn = (Button) findViewById(R.id.btn_toast);
        layout = (LinearLayout) findViewById(R.id.layout);
    }

    public void showToast(View view) {
        switch (view.getId()) {
            case R.id.btn_toast:
                final Toast toast = new Toast(this);
                View item = LayoutInflater.from(this).inflate(R.layout.view_login, null);
                toast.setView(item);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.show();
                break;
            case R.id.btn_snackbar:
                Snackbar.make(layout, "Hello. I am Snackbar!", Snackbar.LENGTH_LONG)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(ToastActivity.this, "刚刚点击了undo", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
                break;
        }
    }
}
