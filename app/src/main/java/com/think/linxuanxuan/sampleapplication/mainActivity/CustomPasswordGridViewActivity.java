package com.think.linxuanxuan.sampleapplication.mainActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.think.linxuanxuan.sampleapplication.R;
import com.think.linxuanxuan.sampleapplication.custompassword.PasswordGridLayout;


public class CustomPasswordGridViewActivity extends Activity implements PasswordGridLayout.OnMyPasswordChangedListener {

    private PasswordGridLayout passwordGridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custompassword);
        passwordGridLayout = (PasswordGridLayout) findViewById(R.id.et_setshorty_pwd);
        passwordGridLayout.setOnPasswordChangedListener(this);
    }

    @Override
    public void onChanged(String psw) {

    }

    @Override
    public void onMaxLength(String psw) {
        Toast.makeText(CustomPasswordGridViewActivity.this, "psw:" + psw, Toast.LENGTH_SHORT).show();
    }
}
