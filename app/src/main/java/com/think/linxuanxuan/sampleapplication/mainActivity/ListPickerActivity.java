package com.think.linxuanxuan.sampleapplication.mainActivity;

import android.app.Activity;
import android.os.Bundle;

import com.think.linxuanxuan.sampleapplication.R;

import net.simonvt.numberpicker.ListPicker;

/**
 * Created by hzlinxuanxuan on 2015/11/24.
 */
public class ListPickerActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_picker);
        ListPicker picker= (ListPicker) findViewById(R.id.numberPicker);
        picker.setMinValue(0);
        picker.setMaxValue(20);
        picker.setFocusable(true);
        picker.setFocusableInTouchMode(true);
    }
}
