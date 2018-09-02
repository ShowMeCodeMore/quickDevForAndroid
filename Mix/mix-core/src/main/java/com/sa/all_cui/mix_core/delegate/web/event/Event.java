package com.sa.all_cui.mix_core.delegate.web.event;

import android.content.Context;
import android.webkit.WebView;

import com.sa.all_cui.mix_core.delegate.DoggerDelegate;
import com.sa.all_cui.mix_core.delegate.web.WebDelegate;


/**
 * Created by all-cui on 2017/8/24.
 */

public abstract class Event implements IEvent {

    private Context mContext = null;
    private String mUrl = null;
    private String mAction = null;
    private WebDelegate mDelegate = null;

    public WebView getWebView() {
        return mDelegate.getWebView();
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }

    public String getAction() {
        return mAction;
    }

    public void setAction(String action) {
        this.mAction = action;
    }

    public DoggerDelegate getDelegate() {
        return mDelegate;
    }

    public void setDelegate(WebDelegate delegate) {
        this.mDelegate = delegate;
    }
}
