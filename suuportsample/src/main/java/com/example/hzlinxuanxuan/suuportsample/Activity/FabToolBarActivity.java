package com.example.hzlinxuanxuan.suuportsample.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.hzlinxuanxuan.suuportsample.DataUtil;
import com.example.hzlinxuanxuan.suuportsample.R;

/**
 * Created by hzlinxuanxuan on 2016/1/18.
 * 在该页点击FloatingActionButton，该按钮会消失，出现一个ToolBar，该效果出自网上的一个效果，暂未学习
 */
public class FabToolBarActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actv_fab_toolbar);
        FloatingActionButton fabBtn = (FloatingActionButton) findViewById(R.id.fabBtn);
        fabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FabToolBarActivity.this, TabActivity.class));
            }
        });
        ListView lv = (ListView) findViewById(R.id.list);
        lv.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, DataUtil.getData()));
    }
}
