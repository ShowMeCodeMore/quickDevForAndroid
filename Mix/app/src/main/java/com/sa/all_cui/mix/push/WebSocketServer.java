package com.sa.all_cui.mix.push;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

/**
 * Created by all-cui on 2017/11/22.
 */

public class WebSocketServer {

    private MockWebServer mServer;

    public WebSocketServer() {
        if (mServer == null) {
            mServer = new MockWebServer();
        }
        mServer.enqueue(new MockResponse().
                withWebSocketUpgrade(new WebSocketListener() {
                    @Override
                    public void onOpen(WebSocket webSocket, Response response) {
                        super.onOpen(webSocket, response);
                        System.out.println("server open=================================");
                        System.out.println("server request header:" + response.request().headers());
                        System.out.println("server response header:" + response.headers());
                        System.out.println("server response:" + response);
                    }

                    @Override
                    public void onMessage(WebSocket webSocket, String text) {
                        super.onMessage(webSocket, text);
                        System.out.println("server onMessage=============================");
                        System.out.println("message:" + text);
                        webSocket.send("response:" + text);
                    }

                    @Override
                    public void onClosing(WebSocket webSocket, int code, String reason) {
                        super.onClosing(webSocket, code, reason);
                        System.out.println("server onClosing=============================");
                        System.out.println("code:" + code + "====reason:" + reason);

                    }

                    @Override
                    public void onClosed(WebSocket webSocket, int code, String reason) {
                        super.onClosed(webSocket, code, reason);
                        System.out.println("server onClosed=================================");
                        System.out.println("code:" + code + "======" + "reason:" + reason);
                    }

                    @Override
                    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                        super.onFailure(webSocket, t, response);
                        System.out.println("server onFailure=================================");
                        System.out.println("throwable:" + t + "\r\nresponse" + response.message());
                    }
                }));
    }

    public int getPort() {
        return mServer.getPort();
    }

    public String getHome() {
        return mServer.getHostName();
    }
}
