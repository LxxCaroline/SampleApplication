package com.think.linxuanxuan.sampleapplication.mainActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.think.linxuanxuan.sampleapplication.R;

import java.util.ArrayList;
import java.util.List;


public class GetItemViewTypeActivity extends Activity {
    private ListView lv;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getitemviewtype);
        lv = (ListView) findViewById(R.id.lv);
        adapter = new MyAdapter();
        ArrayList<CardInfosItem> data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            CardInfosItem item = new CardInfosItem();
            item.bank = i + "";
            item.msg = "不可用";
            item.useable = i % 3 == 0 ? "USEABLE" : "other";
            data.add(item);
        }
        adapter.setData(data);
        lv.setAdapter(adapter);
    }

    class MyAdapter extends BaseAdapter {

        private List<CardInfosItem> data;
        private LayoutInflater inflater;

        public MyAdapter() {
            inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public void setData(List<CardInfosItem> data) {
            this.data = data;
        }

        @Override
        public int getItemViewType(int position) {
            return data.get(position).useable.equals("USEABLE") ? 0 : 1;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            ViewHolder holder;
            int type = getItemViewType(position);
            if (view == null) {
                holder = new ViewHolder();
                switch (type) {
                    case 0:
                        view = inflater.inflate(R.layout.item_getitemviewtype, null);

                        break;
                    case 1:
                        view = inflater.inflate(R.layout.item_getitemviewtype2, null);
//                        holder.tvPayCard = (TextView) view.findViewById(R.id.tv_paymentitem_card2);
//                        holder.tvDesc = (TextView) view.findViewById(R.id.tv_payment_item_desp2);
//                        holder.ivChecked = (ImageView) view.findViewById(R.id.iv_paymentitem_checked2);
                        break;
                }
                holder.tvPayCard = (TextView) view.findViewById(R.id.tv_paymentitem_card);
                holder.tvDesc = (TextView) view.findViewById(R.id.tv_payment_item_desp);
                holder.ivChecked = (ImageView) view.findViewById(R.id.iv_paymentitem_checked);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            if (data != null && data.size() > position) {
                CardInfosItem item = data.get(position);
                holder.tvPayCard.setText(item.bank);
//                if ("USEABLE".equals(item.useable) || "DIFFERENT_SIGN_GATE".equals(item.useable)) {
//                    holder.tvDesc.setVisibility(View.GONE);
//                    RelativeLayout.LayoutParams detailParams = (RelativeLayout.LayoutParams) holder.tvPayCard
//                            .getLayoutParams();
//                    detailParams.addRule(RelativeLayout.CENTER_VERTICAL);
//                    holder.tvPayCard.setLayoutParams(detailParams);
//                    holder.tvPayCard.setEnabled(true);
//                } else {
//                    holder.tvDesc.setVisibility(View.VISIBLE);
//                    holder.tvDesc.setText(item.msg);
//                    holder.tvPayCard.setEnabled(false);
//                }
                if (!"USEABLE".equals(item.useable) && !"DIFFERENT_SIGN_GATE".equals(item.useable)) {
                    holder.tvDesc.setText(item.msg);
                }
            }
            return view;
        }

        @Override
        public int getCount() {
            return data == null ? 0 : data.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        class ViewHolder {
            public TextView tvPayCard;
            public TextView tvDesc;
            public ImageView ivChecked;
        }

        class ViewHolder2 {
            public TextView tvPayCard;
            public TextView tvDesc;
            public ImageView ivChecked;
        }
    }

    class CardInfosItem {
        public String useable, msg, bank;
    }

}
