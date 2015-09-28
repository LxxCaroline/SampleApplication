package com.think.linxuanxuan.sampleapplication.custompassword;

import android.text.method.PasswordTransformationMethod;
import android.view.View;

/**
 * 自定义password的样式，比如 * 或者 ●
 * Created by Think on 2015/8/16.
 */
public class MyPasswordTransformationMethod extends PasswordTransformationMethod {

    String transformation;

    public MyPasswordTransformationMethod(String transformation) {
        this.transformation = transformation;
    }

    @Override
    public CharSequence getTransformation(CharSequence source, View view) {
        return new PasswordCharSequence(source);
    }

    private class PasswordCharSequence implements CharSequence {
        private CharSequence mSource;

        public PasswordCharSequence(CharSequence source) {
            mSource = source;
        }

        @Override
        public int length() {
            return mSource.length();
        }

        @Override
        public char charAt(int index) {
            return transformation.charAt(0);
        }

        @Override
        public CharSequence subSequence(int start, int end) {
            return mSource.subSequence(start, end);
        }
    }
}
