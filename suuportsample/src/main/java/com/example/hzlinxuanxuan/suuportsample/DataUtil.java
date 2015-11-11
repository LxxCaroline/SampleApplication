package com.example.hzlinxuanxuan.suuportsample;

import java.util.ArrayList;

/**
 * Created by hzlinxuanxuan on 2015/11/9.
 */
public class DataUtil {

    public static ArrayList<String> getData() {
        ArrayList<String> datas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            datas.add("data " + i);
        }
        return datas;
    }
}
