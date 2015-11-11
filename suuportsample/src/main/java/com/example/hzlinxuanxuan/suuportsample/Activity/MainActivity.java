package com.example.hzlinxuanxuan.suuportsample.Activity;


import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.hzlinxuanxuan.suuportsample.DelayedTask;
import com.example.hzlinxuanxuan.suuportsample.Fragment.ContactsFriendFragment;
import com.example.hzlinxuanxuan.suuportsample.Fragment.HomeFragment;
import com.example.hzlinxuanxuan.suuportsample.Fragment.PictureFragment;
import com.example.hzlinxuanxuan.suuportsample.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private NavigationView navigation;
    private DrawerLayout mDrawerLayout;
    private MenuItem selectedItem;
    private ArrayList<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new ContactsFriendFragment());
        fragments.add(new PictureFragment());
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigation = (NavigationView) findViewById(R.id.navigation);
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content_frame, fragments.get(0)).commit();
        selectedItem = navigation.getMenu().findItem(R.id.menu_home);
        selectedItem.setChecked(true);
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if (selectedItem == null || menuItem.getItemId() != selectedItem.getItemId()) {
                    Fragment frag = null;
                    switch (menuItem.getItemId()) {
                        case R.id.menu_home:
                            frag = fragments.get(0);
                            break;
                        case R.id.menu_friends:
                            frag = fragments.get(1);
                            break;
                        case R.id.menu_waterfall:
                            frag = fragments.get(2);
                    }
                    if (frag != null) {
                        FragmentManager manager = getSupportFragmentManager();
                        manager.beginTransaction().replace(R.id.content_frame, frag).commit();
                        if (selectedItem != null) {
                            selectedItem.setChecked(false);
                        }
                        menuItem.setChecked(true);
                        //采用1秒后关闭，防止画面不连贯
                        DelayedTask task = new DelayedTask(100, new DelayedTask.IDelayedListener() {
                            @Override
                            public void onDelayed() {
                                mDrawerLayout.closeDrawer(navigation);
                            }
                        });
                        task.execute();
                        selectedItem = menuItem;
                    }
                }
                return true;
            }
        });
    }

}
