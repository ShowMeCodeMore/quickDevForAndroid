package com.sa.all_cui.mix_core.delegate.web.client;

import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sa.all_cui.mix_core.app.MIX;
import com.sa.all_cui.mix_core.delegate.web.IpageLoadListener;
import com.sa.all_cui.mix_core.delegate.web.WebDelegate;
import com.sa.all_cui.mix_core.delegate.web.route.Router;
import com.sa.all_cui.mix_core.ui.loading.DoggerLoader;
import com.sa.all_cui.mix_core.utils.log.DoggerLog;


/**
 * Created by all-cui on 2017/8/24.
 */

public class WebViewClientImpl extends WebViewClient {

    private final WebDelegate DELEGATE;

    private IpageLoadListener mPageLoadListener = null;

    public WebViewClientImpl(WebDelegate delegate) {
        this.DELEGATE = delegate;
    }

    public void setPageLoadListener(IpageLoadListener listener) {
        this.mPageLoadListener = listener;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        DoggerLog.i(url);
        //所有的操作都有原生接管
        return Router.getInstance().handleWebUrl(DELEGATE, url);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        if (mPageLoadListener != null) {
            mPageLoadListener.onPageStart();
        }
        DoggerLoader.showLoading(view.getContext());
    }

    @Override
    public void onPageFinished(final WebView view, String url) {
        super.onPageFinished(view, url);

        if (mPageLoadListener != null) {
            mPageLoadListener.onPageEnd();
        }

        MIX.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        DoggerLoader.stopLoading();
                    }
                });
            }
        }, 2000);
    }
}
