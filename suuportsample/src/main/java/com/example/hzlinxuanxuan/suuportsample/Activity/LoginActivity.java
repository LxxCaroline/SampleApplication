package com.example.hzlinxuanxuan.suuportsample.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.hzlinxuanxuan.suuportsample.R;

/**
 * Created by hzlinxuanxuan on 2015/11/5.
 */
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onClick(View view) {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

}
