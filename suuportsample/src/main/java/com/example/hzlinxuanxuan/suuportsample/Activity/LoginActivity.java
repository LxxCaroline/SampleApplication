package com.example.hzlinxuanxuan.suuportsample.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.Menu;
import android.view.View;

import com.example.hzlinxuanxuan.suuportsample.R;

/**
 * Created by hzlinxuanxuan on 2015/11/5.
 */
public class LoginActivity extends Activity {

    TextInputLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        layout= (TextInputLayout) findViewById(R.id.et_psw);
    }

    public void onClick(View view) {
        if(layout.getEditText().getText().toString().equals("")){
            layout.setError("cannot be empty");
            return ;
        }
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
