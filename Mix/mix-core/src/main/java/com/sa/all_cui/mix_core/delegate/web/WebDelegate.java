package com.sa.all_cui.mix_core.delegate.web;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;

import com.sa.all_cui.mix_core.app.ConfigKeys;
import com.sa.all_cui.mix_core.app.MIX;
import com.sa.all_cui.mix_core.delegate.DoggerDelegate;
import com.sa.all_cui.mix_core.delegate.web.route.RouteKeys;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * Created by all-cui on 2017/8/24.
 */

public abstract class WebDelegate extends DoggerDelegate implements IWebViewInitalizar {

    private WebView mWebView = null;
    private final ReferenceQueue<WebView> WEB_VIEW_QUEUE = new ReferenceQueue<>();
    private String mUrl = null;
    private boolean mIsWebviewAbailable = false;
    private DoggerDelegate mDelegate = null;

    public WebDelegate() {

    }

    public abstract IWebViewInitalizar setInitalizar();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUrl = getArguments().getString(RouteKeys.URL.name());
        initWebView();
    }

    //初始化WebView
    @SuppressLint("JavascriptInterface")
    private void initWebView() {
        //如果webview已经初始化了，销毁之前的WebView
        if (mWebView != null) {
            mWebView.removeAllViews();
            mWebView.destroy();
        } else {
            final IWebViewInitalizar initalizar = setInitalizar();
            if (initalizar != null) {
                final WeakReference<WebView> webViewWeakReference = new
                        WeakReference<>(new WebView(getContext()), WEB_VIEW_QUEUE);
                mWebView = webViewWeakReference.get();
                mWebView = initalizar.initWebView(mWebView);
                mWebView.setWebViewClient(initalizar.initWebViewClient());
                mWebView.setWebChromeClient(initalizar.initWebChromeClient());
                final String name = MIX.getConfigurations(ConfigKeys.JAVASCRIPT_INTERFACE);
                mWebView.addJavascriptInterface(DoggerWebInterface.create(this), name);
                mIsWebviewAbailable = true;
            } else {
                throw new RuntimeException("Initalizar is Null!");
            }

        }
    }

    public void setTopDelegate(DoggerDelegate delegate) {
        mDelegate = delegate;
    }

    public DoggerDelegate getTopDelegate() {
        if (mDelegate == null) {
            mDelegate = this;
        }
        return mDelegate;
    }

    public WebView getWebView() {

        if (mWebView == null) {
            throw new NullPointerException("WebView is null!");
        }
        return mIsWebviewAbailable ? mWebView : null;
    }

    public String getUrl() {
        if (mUrl == null) {
            throw new NullPointerException("WebView url is null!");
        }

        return mUrl;
    }


    @Override
    public void onPause() {
        super.onPause();
        if (mWebView != null) {
            mWebView.onPause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mWebView != null) {
            mWebView.onResume();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mIsWebviewAbailable = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.removeAllViews();
            mWebView.destroy();
            mWebView = null;
        }
    }
}
