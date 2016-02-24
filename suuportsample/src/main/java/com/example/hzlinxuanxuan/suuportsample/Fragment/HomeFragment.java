package com.example.hzlinxuanxuan.suuportsample.Fragment;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Toast;

import com.example.hzlinxuanxuan.suuportsample.Activity.FabToolBarActivity;
import com.example.hzlinxuanxuan.suuportsample.Adapter.HomeRecyclerAdapter;
import com.example.hzlinxuanxuan.suuportsample.ItemDecoration.FlowerDecoration;
import com.example.hzlinxuanxuan.suuportsample.R;

/**
 * Created by hzlinxuanxuan on 2015/11/5.
 */
public class HomeFragment extends Fragment {

    private Toolbar toolbar;

    private CoordinatorLayout rootLayout;
    private FloatingActionButton fabBtn;
    private RecyclerView rv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_content, container, false);
        initToolbar(view);
        initInstances(view);
        //设置fragment有菜单，才会调用onCreateOptionsMenu
        setHasOptionsMenu(true);
        return view;
    }

    private void initToolbar(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }

    private void initInstances(View view) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rv = (RecyclerView) view.findViewById(R.id.lv);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        rv.setLayoutManager(layoutManager);
        HomeRecyclerAdapter adapter = new HomeRecyclerAdapter(new HomeRecyclerAdapter.MyOnClickListener() {
            @Override
            public void onClick(View view, int position) {
                Snackbar.make(rootLayout, "u click item:" + position + "!", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        }).show();
            }
        });
        rv.setAdapter(adapter);
        rv.addItemDecoration(new FlowerDecoration(getActivity()));

        rootLayout = (CoordinatorLayout) view.findViewById(R.id.coordinator_layout);
        fabBtn = (FloatingActionButton) view.findViewById(R.id.fabBtn);
        fabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), FabToolBarActivity.class));
            }
        });
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.collapsingToolbarLayout);
        collapsingToolbarLayout.setTitle("Design Library");
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getActivity().getMenuInflater().inflate(R.menu.main_menu, menu);
        // Retrieve the SearchView and plug it into SearchManager
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(getActivity().SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(getActivity(), "u click menu -- " + item.getTitle(), Toast.LENGTH_SHORT).show();
        if(item.getItemId() == R.id.action_search){
            final OvershootInterpolator interpolator = new OvershootInterpolator();
            ViewCompat.animate(fabBtn).rotation(135f).withLayer().setDuration(5000).setInterpolator(interpolator).start();
        }else{
            final OvershootInterpolator interpolator = new OvershootInterpolator();
            ViewCompat.animate(fabBtn).rotation(-135f).withLayer().setDuration(5000).setInterpolator(interpolator).start();
        }
        return true;
//        return super.onOptionsItemSelected(item);
    }
}
