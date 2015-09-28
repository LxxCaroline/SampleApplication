package com.think.linxuanxuan.sampleapplication.mainActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.think.linxuanxuan.sampleapplication.R;

/**
 * google不推荐使用PreferenceActivity,推荐使用PreferenceFragment。里面大多数方法都没变
 */
public class SharedPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //改变保存数据使用的xml文件的名称
        getPreferenceManager().setSharedPreferencesName("setting");
        //设置导入的页面
        addPreferencesFromResource(R.xml.preference_setting);
        //获得“姓名”列表项对应的preference对象
        Preference individualNamePreference=findPreference("individual_name");
        //获得指向setting.xml文件的sharedPreferences对象
        SharedPreferences sp=individualNamePreference.getSharedPreferences();
        //设置“姓名”列表项的summary
        individualNamePreference.setSummary(sp.getString("individual_name",""));
        if(sp.getBoolean("yesno_save_individual_info",false))
            individualNamePreference.setEnabled(true);
        else
            individualNamePreference.setEnabled(false);
        individualNamePreference.setOnPreferenceChangeListener(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        //判断选中的是否为“是否保存个人信息”列表项的复选框
        if ("yesno_save_individual_info".equals(preference.getKey())) {
            //设置“姓名”列表项为可选或不可选
            findPreference("individual_name").setEnabled(!findPreference("individual_name").isEnabled());
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        preference.setSummary(String.valueOf(newValue));
        //该方法必须返回true，否则无法保存该设置的值
        return true;
    }
}

