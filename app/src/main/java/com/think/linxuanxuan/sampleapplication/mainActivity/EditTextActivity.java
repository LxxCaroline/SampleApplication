package com.think.linxuanxuan.sampleapplication.mainActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView;

import com.think.linxuanxuan.sampleapplication.R;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class EditTextActivity extends Activity {

    String autoString[] = {"12", "123", "1234", "12345", "234"};
    @InjectView(R.id.et_autocomplete)
    AutoCompleteTextView etAutoComplete;
    @InjectView(R.id.et_multiComplete)
    MultiAutoCompleteTextView etMultiComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edittext);
        ButterKnife.inject(this);

        setAutoCompleteEditText();
        setMultiAutoCompleteEditText();
        //不因为输入法的出现让界面上移
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    void setAutoCompleteEditText() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, autoString);
        //因为AutoCompleteTextView默认输入两个字符才开始匹配，这里可以设置输入一个字符就开始匹配
        etAutoComplete.setThreshold(1);
        etAutoComplete.setAdapter(adapter);
    }

    void setMultiAutoCompleteEditText() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, autoString);
        etMultiComplete.setAdapter(adapter);
        etMultiComplete.setThreshold(1);
        //输入逗号会重新匹配逗号后面的字符。
        etMultiComplete.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
    }
}
