package com.sa.all_cui.mix_core.delegate.web;

import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by all-cui on 2017/8/24.
 * WebView初始化
 */

public interface IWebViewInitalizar {

    WebView initWebView(WebView webView);

    //WebViewClient是镇对于浏览器本身的
    WebViewClient initWebViewClient();

    //WebViewChromClient是针对于浏览器中显示的网页
    WebChromeClient initWebChromeClient();
}
