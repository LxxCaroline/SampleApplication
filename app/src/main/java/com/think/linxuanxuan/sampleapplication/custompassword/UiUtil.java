package com.think.linxuanxuan.sampleapplication.custompassword;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by Think on 2015/8/16.
 */
public class UiUtil {

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int dp2px(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) ((dp * displayMetrics.density) + 0.5);
    }
}
