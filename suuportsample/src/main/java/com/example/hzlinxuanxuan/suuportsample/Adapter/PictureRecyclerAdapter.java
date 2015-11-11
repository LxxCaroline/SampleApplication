package com.example.hzlinxuanxuan.suuportsample.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hzlinxuanxuan.suuportsample.R;

import java.util.ArrayList;

/**
 * Created by hzlinxuanxuan on 2015/11/9.
 */
public class PictureRecyclerAdapter extends RecyclerView.Adapter {

    private Context ctx;
    private ArrayList<String> datas;
    private int[] colors = new int[]{Color.CYAN, Color.GREEN, Color.MAGENTA};
    private ArrayList<Integer> heights = new ArrayList<>();
    private ArrayList<Integer> colorIndex=new ArrayList<>();

    public PictureRecyclerAdapter(Context ctx, ArrayList<String> data) {
        this.ctx = ctx;
        datas = data;
        for (int i = 0; i < data.size(); i++) {
            heights.add((int) (Math.random() * 100) + 300);
            colorIndex.add((int) (Math.random() * 3));
        }
    }

    public void addItem(String data, int index) {
        datas.add(index, data);
        heights.add(index, (int) (Math.random() * 300) + 100);
        colorIndex.add((int) (Math.random() * 3));
        notifyItemInserted(index);
    }

    public void removeItem(int index) {
        datas.remove(index);
        heights.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("tag", "onCreateViewHolder");
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_stagger_recycler_view, parent, false);
        return new PictureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.d("tag", "onBindViewHolder " + position);
        ViewGroup.LayoutParams lp = ((PictureViewHolder) holder).tvContent.getLayoutParams();
        lp.height = heights.get(position);
        ((PictureViewHolder) holder).tvContent.setLayoutParams(lp);
        ((PictureViewHolder) holder).tvContent.setText(datas.get(position));
        ((PictureViewHolder) holder).tvContent.setBackgroundColor(colors[colorIndex.get(position)]);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class PictureViewHolder extends RecyclerView.ViewHolder {

        public TextView tvContent;

        public PictureViewHolder(View itemView) {
            super(itemView);
            tvContent = (TextView) itemView.findViewById(R.id.id_num);
        }
    }
}
