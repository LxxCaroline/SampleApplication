package com.think.linxuanxuan.sampleapplication.mainActivity;

import android.app.Activity;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.think.linxuanxuan.sampleapplication.R;


public class ClipBoardActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clipboard);
    }

    public void clipBoard(View view) {
        ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        //对sdk的要求最低为15
        manager.setText(((TextView) findViewById(R.id.tv_clipboard)).getText().toString());
    }

}
