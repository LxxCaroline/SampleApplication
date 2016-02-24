package com.example.hzlinxuanxuan.suuportsample.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.hzlinxuanxuan.suuportsample.Adapter.SampleRecyclerAdapter;
import com.example.hzlinxuanxuan.suuportsample.ItemTouchHelper.SampleItemTouchCallback;
import com.example.hzlinxuanxuan.suuportsample.DataUtil;
import com.example.hzlinxuanxuan.suuportsample.ItemDecoration.FlowerDecoration;
import com.example.hzlinxuanxuan.suuportsample.R;

import java.util.ArrayList;

/**
 * Created by hzlinxuanxuan on 2015/11/5.
 */
public class RecyclerViewFragment extends Fragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private RecyclerView lv;
    private ArrayList datas;
    private SampleRecyclerAdapter adapter;
    private ItemTouchHelper mItemTouchHelper;
    private ItemTouchHelper.Callback mCallback;
    private RadioGroup layoutGroup, orientationGroup;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_recyclerview, container, false);
        view.findViewById(R.id.btn_add).setOnClickListener(this);
        view.findViewById(R.id.btn_delete).setOnClickListener(this);
        lv = (RecyclerView) view.findViewById(R.id.lv_friends);
        layoutGroup = (RadioGroup) view.findViewById(R.id.layout_group);
        layoutGroup.setOnCheckedChangeListener(this);
        orientationGroup = (RadioGroup) view.findViewById(R.id.orientation_group);
        orientationGroup.setOnCheckedChangeListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        lv.setLayoutManager(layoutManager);

        datas = DataUtil.getData();
        adapter = new SampleRecyclerAdapter(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "click the button", Toast.LENGTH_SHORT).show();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "click the picture", Toast.LENGTH_SHORT).show();
            }
        }, datas);
        lv.setAdapter(adapter);

        mCallback = new SampleItemTouchCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(mCallback);
        mItemTouchHelper.attachToRecyclerView(lv);
        lv.addItemDecoration(new FlowerDecoration(getActivity()));
        return view;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                datas.add(0, 0);
                adapter.notifyItemInserted(0);
                lv.getLayoutManager().smoothScrollToPosition(lv, null, 0);
                break;
            case R.id.btn_delete:
                datas.remove(1);
                adapter.notifyItemRemoved(1);
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        int orientation = orientationGroup.getCheckedRadioButtonId() == R.id.horizontal ? LinearLayout.HORIZONTAL : LinearLayout.VERTICAL;
        RecyclerView.LayoutManager manager;
        switch (layoutGroup.getCheckedRadioButtonId()) {
            case R.id.rb_linear:
                manager = new LinearLayoutManager(getActivity(), orientation, false);
                break;
            case R.id.rb_grid:
                manager = new GridLayoutManager(getContext(), 3, orientation, false);
                break;
            case R.id.rb_staggered:
            default:
                manager = new StaggeredGridLayoutManager(3, orientation);
                break;
        }
        lv.setLayoutManager(manager);
    }
}