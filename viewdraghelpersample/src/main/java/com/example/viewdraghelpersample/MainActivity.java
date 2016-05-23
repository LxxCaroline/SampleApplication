package com.example.viewdraghelpersample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private ScrollTextView tview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tview = (ScrollTextView) findViewById(R.id.stv);
    }

    public void onClick(View view) {
        tview.startScroll();
    }

}
