package com.example.hzlinxuanxuan.refreshlistview.swipe;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.hzlinxuanxuan.refreshlistview.DelayedTask;
import com.example.hzlinxuanxuan.refreshlistview.R;

import java.util.ArrayList;

/**
 * Created by hzlinxuanxuan on 2015/10/29.
 * 该下拉刷新使用的是support v4包下的
 */
public class SwipeRefreshActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout layout;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);
        layout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        lv = (ListView) findViewById(R.id.lv);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, getData());
        lv.setAdapter(adapter);
        layout.setColorSchemeResources(android.R.color.holo_purple, android.R.color.darker_gray, android.R.color.holo_blue_bright);
        layout.setOnRefreshListener(this);
    }

    private ArrayList<String> getData() {
        ArrayList<String> data = new ArrayList<>();
        for (int i = 0; i < 30; i++)
            data.add("" + i);
        return data;
    }

    @Override
    public void onRefresh() {
        DelayedTask task = new DelayedTask(4000, new DelayedTask.IDelayedListener() {
            @Override
            public void onDelayed() {
                layout.setRefreshing(false);
            }
        });
        task.execute();
    }
}
