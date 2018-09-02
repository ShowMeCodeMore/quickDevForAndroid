package com.sa.all_cui.mix_core.push;

import com.blankj.utilcode.util.EmptyUtils;

import okhttp3.OkHttpClient;

/**
 * Created by all-cui on 2017/11/22.
 */

@SuppressWarnings("WeakerAccess")
public final class WebClientBuilder {
    private String mWsUrl = null;
    private OkHttpClient mClient = null;
    private boolean isNeedConnected = false;
    private int mConnectedNum = 0;//重连的次数
    private IWsListener mListener = null;

    public WebClientBuilder() {
    }

    public WebClientBuilder setWsUrl(String url) {
        if (EmptyUtils.isEmpty(url)) {
            throw new NullPointerException("WebSocket url must be not null!");
        }
        this.mWsUrl = url;
        return this;
    }

    public WebClientBuilder setReConnect(boolean isConnected) {
        this.isNeedConnected = isConnected;
        return this;
    }

    public WebClientBuilder setConnectedNum(int connectedNum) {
        this.mConnectedNum = connectedNum;
        return this;
    }

    public WebClientBuilder setClient(OkHttpClient client) {
        this.mClient = client;
        return this;
    }


    public WebClientBuilder setWsListener(IWsListener listener) {
        if (listener == null) {
            throw new NullPointerException("IWsListener must be not null!");
        }
        this.mListener = listener;
        return this;
    }

    public WebClient build() {
        return new WebClient(mWsUrl, mClient, isNeedConnected, mConnectedNum, mListener);
    }
}
