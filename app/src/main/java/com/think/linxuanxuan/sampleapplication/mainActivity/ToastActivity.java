package com.think.linxuanxuan.sampleapplication.mainActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.think.linxuanxuan.sampleapplication.R;


public class ToastActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toast);
    }

    public void showToast(View view) {
        switch (view.getId()) {
            case R.id.btn_customview:
                Toast toast = new Toast(this);
                View item = LayoutInflater.from(this).inflate(R.layout.view_login, null);
                toast.setView(item);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.show();
                break;
        }
    }
}
