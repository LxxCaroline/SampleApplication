package com.think.linxuanxuan.sampleapplication.mainActivity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.think.linxuanxuan.sampleapplication.R;

import java.lang.reflect.Field;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class TextViewActivity extends Activity {

    @InjectView(R.id.tv_common)
    TextView tvCommon;
    @InjectView(R.id.tv_url)
    TextView tvUrl;
    @InjectView(R.id.tv_img)
    TextView tvImg;
    @InjectView(R.id.tv_spannable)
    TextView tvSpannable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textview);
        ButterKnife.inject(this);
        initCommonTextView();
        initImageGetterTextView();
        initSpannableTextView();
    }

    void initCommonTextView() {
        String html = "<font color='red'>I love android.</font><br>";
        html += "<font color='#0000ff'><big><i>I love Android.</i></big></font><p>";
        html += "<big><a href='http://www.baidu.com'>my website:www.baidu.com</a></big>";

        //将带预定义标签的字符串转化成CharSequence对象
        CharSequence cs = Html.fromHtml(html);
        tvCommon.setText(cs);
        //下面的语句非常重要，没有该语句，无法单击链接调用浏览器显示网页
        tvCommon.setMovementMethod(LinkMovementMethod.getInstance());
        String text = "My url:www.baidu.com\n";
        text += "My email:carolinepublic@126.com\n";
        text += "My phone: +86 137-38144006";
        tvUrl.setText(text);
        tvUrl.setMovementMethod(LinkMovementMethod.getInstance());
    }

    void initImageGetterTextView() {
        String img = "图像<img src='img'/>图像<img src='ic_launcher'/>";
        CharSequence cs2 = Html.fromHtml(img, new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(String source) {
                Drawable drawable = getResources().getDrawable(getDrawableId(source));
                //获得drawable资源后，必须使用Drawable.setBounds方法设置图像的显示区域，否则显示区域的面积为0
                //也就不会在textview中显示图像了
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                return drawable;
            }
        }, null);
        tvImg.setText(cs2);
    }

    void initSpannableTextView() {
        String s1 = "点击这里say hello,点击这里say byebye，黄色文本";
        SpannableString ss1 = new SpannableString(s1);
        ss1.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(TextViewActivity.this, "hello", Toast.LENGTH_SHORT).show();
            }
        }, 0, 13, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss1.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(TextViewActivity.this, "byebye", Toast.LENGTH_SHORT).show();
            }
        }, 14, 28, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        BackgroundColorSpan span = new BackgroundColorSpan(Color.YELLOW);
        //还有ClickableSpan UrlSpan
        ss1.setSpan(span,29,s1.length(),Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tvSpannable.setText(ss1);
        //该后加入的文本和黄色文本的一样，因为设置了SPAN_INCLUSIVE_INCLUSIVE
        tvSpannable.append("appendedString");
        tvSpannable.setMovementMethod(LinkMovementMethod.getInstance());
        /*
         * setSpan函数中最后一个参数是一个标志，在本例中设为Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
         * 该标志在TextView中意义不大，但在EditText控件中表示在当前Span效果的前后输入字符时并不应用Span的效果
         * 还可以设置如下几个类似的值
         * Spanned.SPAN_EXCLUSIVE_INCLUSIVE:在Span前面输入的字符不应用Span效果，后面应用
         * Spanned.SPAN_INCLUSIVE_EXCLUSIVE:在Span前面输入的字符应用Span效果，后面不应用
         * Spanned.SPAN_INCLUSIVE_INCLUSIVE:在Span前面后面输入的字符都应用Span效果
         */
        /*
         * 还可以自定义Span，同时设置文字颜色、背景色等
         * 具体内容请看Android开发权威指南Page125
         */
    }

    /**
     * name参数表示res/drawable中图像文件名（不含扩展名）
     *
     * @param name
     * @return
     */
    public int getDrawableId(String name) {
        try {
            Field field = R.drawable.class.getField(name);
            return Integer.parseInt(field.get(null).toString());
        } catch (Exception e) {
        }
        return 0;
    }
}
