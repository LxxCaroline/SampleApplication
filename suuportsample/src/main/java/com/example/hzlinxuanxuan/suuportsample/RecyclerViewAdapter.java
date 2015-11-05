package com.example.hzlinxuanxuan.suuportsample;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by hzlinxuanxuan on 2015/11/5.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private ArrayList<String> mDataset;
    private MyOnClickListener listener;

    public RecyclerViewAdapter(ArrayList<String> myDataset, MyOnClickListener listener) {
        mDataset = myDataset;
        this.listener = listener;
    }

    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        MyViewHolder vh = new MyViewHolder(v, listener);
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

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTextView;
        private MyOnClickListener listener;

        public MyViewHolder(View v, MyOnClickListener listener) {
            super(v);
            mTextView = (TextView) v.findViewById(android.R.id.text1);
            v.setOnClickListener(this);
            this.listener=listener;
        }

        @Override
        public void onClick(View v) {
            if(this.listener!=null){
                this.listener.onClick(v,getAdapterPosition());
            }
        }
    }

    public interface MyOnClickListener {
        void onClick(View view, int position);
    }
}
