package com.example.hzlinxuanxuan.suuportsample.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.hzlinxuanxuan.suuportsample.R;

/**
 * Created by hzlinxuanxuan on 2015/11/1.
 */
public class SecondActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        final PagerAdapter adapter = new PagerAdapter() {
            @Override
            public int getCount() {
                //设置总共有几个tab
                return 10;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                //指定对应position的tab的名字，显示在tab上的title文字
                return "tab" + position;
            }

            //初始化每个tab内容的页面，需要根据不同位置来加载不同的页面
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                Button btn = new Button(SecondActivity.this);
                btn.setText("tv" + position);
                btn.setTextSize(30.f);
                container.addView(btn);
                return btn;
            }

            //必须实现该函数，否则在左右滑动的时候会抛出异常说该函数未实现
            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                ((ViewPager) container).removeView((View) object);
            }
        };
        mViewPager.setAdapter(adapter);
        //将TabLayout和ViewPager连接起来
        mTabLayout.setupWithViewPager(mViewPager);
    }


}
