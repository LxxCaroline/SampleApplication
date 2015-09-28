package com.think.linxuanxuan.sampleapplication.mainActivity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.think.linxuanxuan.sampleapplication.R;

/**
 * 不需要写存储键值对的代码，该PreferenceActivity会自动存储
 */
public class SharedPreferenceActivity extends FragmentActivity {

    private SharedPreferenceFragment preferenceFragment;
    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharepreference);
        preferenceFragment = new SharedPreferenceFragment();
        manager = getFragmentManager();
        android.app.FragmentTransaction ft = manager.beginTransaction();
        ft.add(R.id.fragment_container,preferenceFragment);
        ft.commit();
    }
}