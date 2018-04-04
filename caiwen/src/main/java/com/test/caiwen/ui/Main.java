package com.test.caiwen.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.test.caiwen.R;

/**
 *
 * Created by MrRight on 2017/12/15.
 */

public class Main extends Activity{

    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setContentView(R.layout.demo_layout);
    }


    @SuppressLint("SetJavaScriptEnabled")
    private void initView() {
        webView=findViewById(R.id.mWebView);
        webView.loadUrl("http://test.tnzyclub.com/user/login");
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        WebSettings webSettings =   webView .getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        //webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setDefaultTextEncodingName("utf-8");
    }


    /**
     *
     使点击回退按钮不会直接退出整个应用程序而是返回上一个页面
     *
     * */
    @Override
    public boolean
    onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK&&webView.canGoBack()){
            webView.goBack();//返回上个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);//退出整个应用程序
    }
}
