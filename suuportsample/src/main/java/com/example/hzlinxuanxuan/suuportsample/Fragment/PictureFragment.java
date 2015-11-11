package com.example.hzlinxuanxuan.suuportsample.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.hzlinxuanxuan.suuportsample.Adapter.PictureRecyclerAdapter;
import com.example.hzlinxuanxuan.suuportsample.DataUtil;
import com.example.hzlinxuanxuan.suuportsample.ItemDecoration.GridDividerItemDecoration;
import com.example.hzlinxuanxuan.suuportsample.R;

import java.util.ArrayList;

/**
 * Created by hzlinxuanxuan on 2015/11/9.
 */
public class PictureFragment extends Fragment implements View.OnClickListener {

    private RecyclerView lv;
    private ArrayList<String> datas;
    private PictureRecyclerAdapter adapter;
    private StaggeredGridLayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_contacts_friend, container, false);
        ((Button) view.findViewById(R.id.btn_add)).setOnClickListener(this);
        ((Button) view.findViewById(R.id.btn_delete)).setOnClickListener(this);
        lv = (RecyclerView) view.findViewById(R.id.lv_friends);

        layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        lv.setLayoutManager(layoutManager);

        datas = DataUtil.getData();
        adapter = new PictureRecyclerAdapter(getActivity(), datas);
        lv.setAdapter(adapter);

        lv.addItemDecoration(new GridDividerItemDecoration(getActivity()));
        return view;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                adapter.addItem("data " + datas.size(), 0);
                layoutManager.smoothScrollToPosition(lv,null,0);
                break;
            case R.id.btn_delete:
                adapter.removeItem(0);
                break;
        }
    }

}
