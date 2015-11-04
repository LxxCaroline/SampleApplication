package com.example.hzlinxuanxuan.suuportsample;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by hzlinxuanxuan on 2015/11/2.
 */
public class ThirdActivity extends AppCompatActivity {

    private RecyclerView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_content_test);
        initToolBar();
        lv = (RecyclerView) findViewById(R.id.lv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        // 设置布局管理器
        lv.setLayoutManager(layoutManager);
        MyAdapter adapter = new MyAdapter(getData());
        lv.setAdapter(adapter);
    }

    private void initToolBar() {
        Toolbar bar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(bar);
    }

    public static ArrayList<String> getData() {
        ArrayList<String> data = new ArrayList<>(20);
        for (int i = 0; i < 20; i++) {
            data.add("data #" + i);
        }
        return data;
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        private ArrayList<String> mDataset;

        public MyAdapter(ArrayList<String> myDataset) {
            mDataset = myDataset;
        }

        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            MyViewHolder vh = new MyViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.mTextView.setText(mDataset.get(position));

        }

        @Override
        public int getItemCount() {
            return mDataset.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView mTextView;

            public MyViewHolder(View v) {
                super(v);
                mTextView = (TextView) v.findViewById(android.R.id.text1);
            }
        }
    }

}