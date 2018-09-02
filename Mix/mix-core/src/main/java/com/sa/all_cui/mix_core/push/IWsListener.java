package com.sa.all_cui.mix_core.push;

import android.support.annotation.Nullable;

import okhttp3.Response;
import okhttp3.WebSocket;
import okio.ByteString;

/**
 * Created by all-cui on 2017/11/22.
 */

public interface IWsListener {
    /**
     * Invoked when a web socket has been accepted by the remote peer and may begin transmitting
     * messages.
     */
    void onOpenSuccess(WebSocket webSocket, Response response);

    void onOpenError(WebSocket webSocket, Response response);
    /**
     * Invoked when a text (type {@code 0x1}) message has been received.
     */
    void onMessage(WebSocket webSocket, String text);

    /**
     * Invoked when a binary (type {@code 0x2}) message has been received.
     */
    void onMessage(WebSocket webSocket, ByteString bytes);

    /**
     * Invoked when the remote peer has indicated that no more incoming messages will be
     * transmitted.
     */
    void onClosing(WebSocket webSocket, int code, String reason);


    /**
     * Invoked when both peers have indicated that no more messages will be transmitted and the
     * connection has been successfully released. No further calls to this listener will be made.
     */
    void onClosed(int code, String reason);

    /**
     * Invoked when a web socket has been closed due to an error reading from or writing to the
     * network. Both outgoing and incoming messages may have been lost. No further calls to this
     * listener will be made.
     */
    void onFailure(WebSocket webSocket, Throwable t, @Nullable Response response);

    void onReConnect();
}
