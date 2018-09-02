package com.sa.all_cui.mix_core.delegate.web;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by all-cui on 2017/8/24.
 */

public class WebViewInitalizar {

    @SuppressLint("SetJavaScriptEnabled")
    public WebView createWebView(WebView webView) {
        //开启调试模式
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        //不能横向滚动
        webView.setHorizontalScrollBarEnabled(false);
        //不能纵向滚动
        webView.setVerticalScrollBarEnabled(false);
        //允许截图
        webView.setDrawingCacheEnabled(true);
        //屏蔽长按事件
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        //初始化WebSetting
        final WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        final String ua = webSettings.getUserAgentString();
        webSettings.setUserAgentString(ua + "MIX");
        //隐藏缩放控件
        webSettings.setBuiltInZoomControls(false);
        webSettings.setDisplayZoomControls(false);
        //禁止缩放
        webSettings.setSupportZoom(false);
        //文件权限
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setAllowContentAccess(true);
        //缓存相关
        webSettings.setAppCacheEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        return webView;
    }
}
