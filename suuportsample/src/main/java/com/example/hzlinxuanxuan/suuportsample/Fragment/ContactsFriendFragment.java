package com.example.hzlinxuanxuan.suuportsample.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.hzlinxuanxuan.suuportsample.Adapter.RecyclerListAdapter;
import com.example.hzlinxuanxuan.suuportsample.Adapter.SimpleItemTouchHelperCallback;
import com.example.hzlinxuanxuan.suuportsample.DataUtil;
import com.example.hzlinxuanxuan.suuportsample.R;

import java.util.ArrayList;

/**
 * Created by hzlinxuanxuan on 2015/11/5.
 */
public class ContactsFriendFragment extends Fragment implements View.OnClickListener {

    private RecyclerView lv;
    private ArrayList<String> datas;
    private RecyclerListAdapter adapter;
    private ItemTouchHelper mItemTouchHelper;
    private ItemTouchHelper.Callback mCallback;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_contacts_friend, container, false);
        ((Button) view.findViewById(R.id.btn_add)).setOnClickListener(this);
        ((Button) view.findViewById(R.id.btn_delete)).setOnClickListener(this);
        lv = (RecyclerView) view.findViewById(R.id.lv_friends);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        lv.setLayoutManager(layoutManager);

        datas = DataUtil.getData();
        adapter = new RecyclerListAdapter(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "click the button", Toast.LENGTH_SHORT).show();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "click the item", Toast.LENGTH_SHORT).show();
            }
        }, datas);
        lv.setAdapter(adapter);

        mCallback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(mCallback);
        mItemTouchHelper.attachToRecyclerView(lv);

        return view;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                datas.add(1, "data " + datas.size());
                adapter.notifyItemInserted(1);
                break;
            case R.id.btn_delete:
                datas.remove(1);
                adapter.notifyItemRemoved(1);
                break;
        }
    }

}