/*
 * Copyright (C) 2015 Paul Burke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.hzlinxuanxuan.suuportsample.Adapter;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.hzlinxuanxuan.suuportsample.R;

import java.util.ArrayList;


/**
 * Simple RecyclerView.Adapter that implements {@link ItemTouchAdapter} to respond to move and
 * dismiss events from a {@link android.support.v7.widget.helper.ItemTouchHelper}.
 *
 * @author Paul Burke (ipaulpro)
 */
public class SampleRecyclerAdapter extends RecyclerView.Adapter<SampleRecyclerAdapter.SampleViewHolder> {

    private int[] drawables = new int[]{R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4, R.drawable.image5};
    private View.OnClickListener btnListener, itemListener;
    private ArrayList datas;

    public SampleRecyclerAdapter(View.OnClickListener btnListener, View.OnClickListener itemListener, ArrayList data) {
        this.btnListener = btnListener;
        this.itemListener = itemListener;
        this.datas = data;
    }

    @Override
    public SampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("tag", "onCreateViewHolder:");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_friend_item, parent, false);
        SampleViewHolder itemViewHolder = new SampleViewHolder(view, btnListener, itemListener);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(final SampleViewHolder holder, int position) {
        Log.d("tag", "onBindViewHolder:" + position);
        holder.iv.setImageResource(drawables[(int) datas.get(position) % drawables.length]);
    }

    public void onItemDismiss(int position) {
        notifyItemRemoved(position);
    }

    public void removeData(int position) {
        datas.remove(position);
    }

    public void swapData(int oldPosition, int newPosition) {
        Object old = datas.get(oldPosition);
        datas.add(oldPosition, datas.get(newPosition));
        datas.add(newPosition, old);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public static class SampleViewHolder extends RecyclerView.ViewHolder {

        public CardView mCardView;
        public ImageView iv;

        public SampleViewHolder(View itemView, View.OnClickListener listener, View.OnClickListener itemListener) {
            super(itemView);
//            mCardView = (CardView) itemView.findViewById(R.id.card_view);
            iv = (ImageView) itemView.findViewById(R.id.iv_portrait);
            iv.setOnClickListener(listener);
            this.itemView.setOnClickListener(itemListener);
        }

        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        public void onItemIdle() {
            itemView.setBackgroundColor(0);
        }
    }
}
