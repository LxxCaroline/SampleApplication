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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hzlinxuanxuan.suuportsample.R;

import java.util.ArrayList;
import java.util.Collections;


/**
 * Simple RecyclerView.Adapter that implements {@link ItemTouchHelperAdapter} to respond to move and
 * dismiss events from a {@link android.support.v7.widget.helper.ItemTouchHelper}.
 *
 * @author Paul Burke (ipaulpro)
 */
public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerListAdapter.ItemViewHolder>
        implements ItemTouchHelperAdapter {

    private final OnStartDragListener mDragStartListener;
    private ArrayList<String> datas;

    public RecyclerListAdapter(OnStartDragListener dragStartListener, ArrayList<String> data) {
        this.mDragStartListener = dragStartListener;
        datas = data;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_friend_item, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        holder.tvName.setText(datas.get(position));

        // Start a drag whenever the handle view it touched
//        holder.handleView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
//                    mDragStartListener.onStartDrag(holder);
//                }
//                return false;
//            }
//        });
    }

    @Override
    public void onItemDismiss(int position) {
        datas.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(datas, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    /**
     * Simple example of a view holder that implements {@link ItemTouchHelperViewHolder} and has a
     * "handle" view that initiates a drag event when touched.
     */
    public static class ItemViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {

        public CardView mCardView;
        public TextView tvName;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.card_view);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }
}
