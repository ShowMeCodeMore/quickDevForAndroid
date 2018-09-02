package com.sa.all_cui.mix_core.push;

import okhttp3.OkHttpClient;

/**
 * Created by all-cui on 2017/11/22.
 */

@SuppressWarnings("WeakerAccess")
public class WSManager {
    //    private okhttp3.WebSocket
//    private ThreadPoolManager mThreadPoolManager = ThreadPoolManager.getInstance();

    public WSManager() {
//        mThreadPoolManager.init(ThreadPoolManager.Type.FIXEDTHREAD, 2);
    }

    private static class Holder {
        private static WSManager INSTANCE = new WSManager();
    }

    public static WSManager getInstance() {
        return Holder.INSTANCE;
    }

    public void startConnect(String url, IWsListener listener) {
        startConnect(url, listener, null, true, 0);
    }

    public void startConnect(String url, IWsListener listener, OkHttpClient okHttpClient) {
        startConnect(url, listener, okHttpClient, true, 0);
    }

    public void startConnect(String url, IWsListener listener, OkHttpClient okHttpClient, boolean isReconnect) {
        startConnect(url, listener, okHttpClient, isReconnect, 0);
    }

    public void startConnect(String url, IWsListener listener, boolean isReconnect, int reConnectNum) {
        startConnect(url, listener, null, isReconnect, reConnectNum);
    }

    public void startConnect(String url, IWsListener listener, int reConnectNum) {
        startConnect(url, listener, null, true, reConnectNum);
    }

    private void startConnect(String url, IWsListener listener, OkHttpClient okHttpClient,
                              boolean isReConnect, int reConnectNum) {
        WebClient client = WebClient.builder()
                .setReConnect(isReConnect)
                .setConnectedNum(reConnectNum)
                .setWsListener(listener)
                .setClient(okHttpClient)
                .setWsUrl(url)
                .build();
        client.startConnect();
    }
}
