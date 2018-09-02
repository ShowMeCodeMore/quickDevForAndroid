package com.sa.all_cui.mix_core.delegate.web;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sa.all_cui.mix_core.delegate.web.chromeclient.WebChromeClientImpl;
import com.sa.all_cui.mix_core.delegate.web.client.WebViewClientImpl;
import com.sa.all_cui.mix_core.delegate.web.route.RouteKeys;
import com.sa.all_cui.mix_core.delegate.web.route.Router;


/**
 * Created by all-cui on 2017/8/24.
 */

public class WebDelegateImpl extends WebDelegate {

    private IpageLoadListener mPageLoadListener = null;

    public static WebDelegateImpl create(String url) {
        final Bundle bundle = new Bundle();
        bundle.putString(RouteKeys.URL.name(), url);
        final WebDelegateImpl delegate = new WebDelegateImpl();
        delegate.setArguments(bundle);
        return delegate;
    }

    public void setPageLoadListener(IpageLoadListener listener) {
        this.mPageLoadListener = listener;
    }

    @Override
    public Object setLayout() {
        return getWebView();
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View view) {
        if (!TextUtils.isEmpty(getUrl())) {
            //这里使用原生的方式模式web的跳转
            Router.getInstance().loadPage(this, getUrl());
        }
    }

    @Override
    public IWebViewInitalizar setInitalizar() {
        return this;
    }

    @Override
    public WebView initWebView(WebView webView) {
        return new WebViewInitalizar().createWebView(webView);
    }

    @Override
    public WebViewClient initWebViewClient() {
        final WebViewClientImpl client = new WebViewClientImpl(this);
        client.setPageLoadListener(mPageLoadListener);
        return client;
    }

    @Override
    public WebChromeClient initWebChromeClient() {
        return new WebChromeClientImpl();
    }
}
