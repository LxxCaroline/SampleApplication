package com.think.linxuanxuan.sampleapplication.mainActivity;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.think.linxuanxuan.sampleapplication.R;

/**
 * Created by Think on 2015/8/16.
 */
public class PasswordTransformationMethodActivity extends Activity implements CompoundButton.OnCheckedChangeListener {

    private EditText etPsw;

    private CheckBox cbShowPsw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_transformation_method);

        etPsw = (EditText) findViewById(R.id.etPsw);
        cbShowPsw = (CheckBox) findViewById(R.id.cbShowPsw);
        cbShowPsw.setOnCheckedChangeListener(this);
        etPsw.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked)
            etPsw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        else
            etPsw.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }
}
