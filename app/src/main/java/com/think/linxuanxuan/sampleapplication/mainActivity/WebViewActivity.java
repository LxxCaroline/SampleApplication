package com.think.linxuanxuan.sampleapplication.mainActivity;

import android.app.Activity;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

import com.think.linxuanxuan.sampleapplication.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Think on 2015/7/19.
 */
public class WebViewActivity extends Activity {

    @InjectView(R.id.webview)
    WebView webView;
    @InjectView(R.id.et_url)
    EditText etUrl;

    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.inject(this);
    }

    public void webClick(View view) {
        switch (view.getId()) {
            case R.id.btn_go:
                url = etUrl.getText().toString();
                if (URLUtil.isNetworkUrl(url)) {
                    webView.loadUrl(url);
                    webView.setWebViewClient(new WebViewClient() {
                        @Override
                        public boolean shouldOverrideUrlLoading(WebView view, String url) {
                            view.loadUrl(url);
                            return true;
                        }

                        @Override
                        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                            handler.proceed();
                        }
                    });
                } else {
                    Toast.makeText(this, "输入的网址不正确", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_next:
                webView.goForward();
                etUrl.setText(webView.getOriginalUrl());
                Log.d("tag","original url:"+webView.getOriginalUrl());
                Log.d("tag","url:"+webView.getUrl());
                break;
            case R.id.btn_back:
                webView.goBack();
                etUrl.setText(webView.getOriginalUrl());
                break;
            case R.id.btn_clear:
                webView.clearHistory();
                break;
        }
    }
}
