package com.example.hzlinxuanxuan.refreshlistview.bidirection;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;

import com.example.hzlinxuanxuan.refreshlistview.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BiDirectionActivity extends AppCompatActivity implements BiDirectionListView.IXListViewListener {

    private BiDirectionListView lv;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bidirection);
        lv = (BiDirectionListView) findViewById(R.id.lv);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, getData());
        lv.setAdapter(adapter);
        lv.setPullLoadEnable(true);
        lv.setPullRefreshEnable(true);
        lv.setXListViewListener(this);
    }

    private ArrayList<String> getData() {
        ArrayList<String> data = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        data.add(formatter.format(curDate));
        for (int i = 0; i < 30; i++) {
            data.add("" + i);
        }
        return data;
    }

    @Override
    public void onRefresh() {
        //1秒后关闭刷新
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                lv.stopRefresh();
            }
        }, 1000);
    }

    @Override
    public void onLoadMore() {
        //1秒后关闭加载
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                lv.stopLoadMore();
            }
        }, 1000);
    }
}
