package com.think.linxuanxuan.sampleapplication.mainActivity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.think.linxuanxuan.sampleapplication.R;
import com.think.linxuanxuan.sampleapplication.other.PickDateFragment;

import net.simonvt.numberpicker.ListPicker;

import java.util.Calendar;

/**
 * Created by hzlinxuanxuan on 2015/11/24.
 */
public class ListPickerActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_picker);
        ListPicker picker = (ListPicker) findViewById(R.id.numberPicker);
        picker.setMinValue(0);
        picker.setMaxValue(20);
        picker.setFocusable(true);
        picker.setFocusableInTouchMode(true);

        DatePicker datePicker = (DatePicker)
                findViewById(R.id.datePicker);
        // 获取当前的年、月、日、小时、分钟
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        // 初始化DatePicker组件，初始化时指定监听器
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker arg0, int year, int month, int day) {
                Toast.makeText(ListPickerActivity.this, "您选择的日期：" + year + "年  "
                        + month + "月  " + day + "日", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void datePicker(View view) {
        PickDateFragment frag = new PickDateFragment();
        frag.show(getSupportFragmentManager(), "date");
    }
}
