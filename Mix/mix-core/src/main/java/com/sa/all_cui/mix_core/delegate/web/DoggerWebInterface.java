package com.sa.all_cui.mix_core.delegate.web;

import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSON;
import com.sa.all_cui.mix_core.delegate.web.event.Event;
import com.sa.all_cui.mix_core.delegate.web.event.EventManager;

/**
 * Created by all-cui on 2017/8/24.
 */

public final class DoggerWebInterface {

    private final WebDelegate DELEGATE;

    private DoggerWebInterface(WebDelegate delegate) {
        this.DELEGATE = delegate;
    }

    static DoggerWebInterface create(WebDelegate delegate) {
        return new DoggerWebInterface(delegate);
    }

    @SuppressWarnings("unused")
    @JavascriptInterface
    public String event(String params) {
        final String action = JSON.parseObject(params).getString("action");
        final Event event = EventManager.getInstance().createEvent(action);
        if (event != null) {
            event.setAction(action);
            event.setContext(DELEGATE.getContext());
            event.setDelegate(DELEGATE);
            event.setUrl(DELEGATE.getUrl());
            return event.execute(params);
        }
        return null;
    }
}
