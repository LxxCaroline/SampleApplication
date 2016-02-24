package com.example.hzlinxuanxuan.suuportsample.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by hzlinxuanxuan on 2015/11/5.
 */
public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.MyViewHolder> {
    private MyOnClickListener listener;

    public HomeRecyclerAdapter(MyOnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public HomeRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        MyViewHolder vh = new MyViewHolder(v, listener);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mTextView.setText("data #" + position);
    }

    @Override
    public int getItemCount() {
        return 50;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTextView;
        private MyOnClickListener listener;

        public MyViewHolder(View v, MyOnClickListener listener) {
            super(v);
            mTextView = (TextView) v.findViewById(android.R.id.text1);
            v.setOnClickListener(this);
            this.listener = listener;
        }

        @Override
        public void onClick(View v) {
            if (this.listener != null) {
                this.listener.onClick(v, getAdapterPosition());
            }
        }
    }

    public interface MyOnClickListener {
        void onClick(View view, int position);
    }
}
