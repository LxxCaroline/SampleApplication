package com.example.hzlinxuanxuan.refreshlistview.weibo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;

import com.example.hzlinxuanxuan.refreshlistview.DelayedTask;
import com.example.hzlinxuanxuan.refreshlistview.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by hzlinxuanxuan on 2015/10/29.
 */
public class WeiboActivity extends Activity implements WeiboListView.RefreshLoadListener {

    private WeiboListView lv;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weibo);
        lv = (WeiboListView) findViewById(R.id.lv);
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
        //用AsyncTask来模拟网络访问的时间
        DelayedTask task = new DelayedTask(3000, new DelayedTask.IDelayedListener() {
            @Override
            public void onDelayed() {
                adapter.addAll(getData());
                adapter.notifyDataSetChanged();
                //1秒后关闭加载
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        lv.stopLoadMore();
                    }
                }, 500);
            }
        });
        task.execute();
    }


}
