package com.sa.all_cui.mix.push;

import java.util.Timer;
import java.util.TimerTask;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

/**
 * Created by all-cui on 2017/11/22.
 */

public class WebSocketClient {

    public WebSocketClient(String url) {
        OkHttpClient client = new OkHttpClient.Builder().build();
        final Request request = new Request.Builder().url(url).build();
        //建立连接
        client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                super.onOpen(webSocket, response);
                System.out.println("WebClient onOpen======================");
                System.out.println("WebClient request header:" + response.request().headers());
                System.out.println("WebClient respose header:" + response.headers());
                System.out.println("WebClient response:" + response);
                start(webSocket);

            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                super.onMessage(webSocket, text);
                System.out.println("WebClient onMessage=====================");
                System.out.println("message:" + text);
            }


            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                super.onClosing(webSocket, code, reason);
                System.out.println("WebClient onClosing=======================");
                System.out.println("code:" + code + "\r\nreason:" + reason);
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                super.onClosed(webSocket, code, reason);
                System.out.println("WebClient onClosed==========================");
                System.out.println("code:" + code + "\r\nreason:" + reason);
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                super.onFailure(webSocket, t, response);
                System.out.println("WebClient onFailure===========================");
                System.out.println("Throwable:" + t + "\r\nresponse:" + response);
            }
        });
    }

    private void start(final WebSocket webSocket){
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                boolean send = webSocket.send("发送message-" + System.currentTimeMillis());
                System.out.println(send);
            }
        };
        timer.schedule(timerTask,0,1000);
    }

}
