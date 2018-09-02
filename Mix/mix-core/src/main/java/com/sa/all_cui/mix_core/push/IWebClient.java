package com.sa.all_cui.mix_core.push;

import okhttp3.WebSocket;
import okio.ByteString;

/**
 * Created by all-cui on 2017/11/22.
 */

@SuppressWarnings("WeakerAccess")
public interface IWebClient {
    WebSocket getWebSocket();

    void startConnect();

    void stopConnect();

    boolean isConnected();

    int getCurrentStatus();

    void setCurrentStatus(int currentStatus);

    boolean sendMessage(String msg);

    boolean sendMessage(ByteString byteString);
}
