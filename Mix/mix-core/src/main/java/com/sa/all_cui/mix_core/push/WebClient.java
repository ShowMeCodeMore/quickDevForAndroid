package com.sa.all_cui.mix_core.push;

import android.os.Handler;
import android.os.Looper;

import com.blankj.utilcode.util.NetworkUtils;
import com.sa.all_cui.mix_core.utils.thread.ThreadCallBackCode;

import java.util.concurrent.locks.ReentrantLock;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * Created by all-cui on 2017/11/22.
 * 通过OkHttp自带WebSocket实现长连接，进而实现消息推送；目前已经实现断线重连的功能
 * TODO 未实现心跳监测，监听网络变化广播
 */

@SuppressWarnings("WeakerAccess")
public class WebClient implements IWebClient {
    private final static int RECONNECT_INTERVAL = 10 * 1000;    //重连自增步长
    private final static long RECONNECT_MAX_TIME = 120 * 1000;   //最大重连间隔
    private String WSURL = null;
    private OkHttpClient CLIENT = null;
    private boolean ISNEEDCONNECTED = false;
    private boolean isManualClose = false;//是否手动关闭连接
    private int CONNECTEDNUM = 0;//重连的次数
    private IWsListener LISTENER = null;
    private OkHttpClient mHttpClient = null;
    private Request mRequest = null;
    private ReentrantLock mLock = null;
    private WebSocket mWebSocket;
    private int mCurrentStatus = WebStatus.WS_DISCONNECT;
    private int mReConnectCount = 0;//重连的次数
    private Handler mMainHandler = null;

    public WebClient(String wsUrl, OkHttpClient client,
                     boolean isNeedConnected, int connectedNum, IWsListener listener) {
        this.WSURL = wsUrl;
        this.CLIENT = client;
        this.ISNEEDCONNECTED = isNeedConnected;
        this.CONNECTEDNUM = connectedNum;
        this.LISTENER = listener;
        //不公平锁
        this.mLock = new ReentrantLock();
        if (mMainHandler == null) {
            mMainHandler = new Handler(Looper.getMainLooper());
        }
    }

    private void initWebSocket() {
        if (mHttpClient == null) {
            mHttpClient = (CLIENT == null) ? new OkHttpClient.Builder().build() : CLIENT;
        }
        if (mRequest == null) {
            mRequest = new Request.Builder().url(WSURL).build();
        }
        //取消所有正在执行中或者队列中的请求
        mHttpClient.dispatcher().cancelAll();
        try {
            mLock.lockInterruptibly();
            mHttpClient.newWebSocket(mRequest, mSocketListener);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            mLock.unlock();
        }

    }



    Runnable mReConnectRunnable = new Runnable() {
        @Override
        public void run() {
            if (LISTENER != null) {
                if (null != mMainHandler) {
                    mMainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            LISTENER.onReConnect();
                        }
                    });
                    createConnect();
                }
            }
        }
    };

    /**
     * 发送消息
     */
    private boolean send(Object msg) {
        boolean isSend = false;
        if (null != mWebSocket && getCurrentStatus() == WebStatus.WS_CONNECTED) {
            if (msg instanceof String) {
                isSend = mWebSocket.send((String) msg);
            } else if (msg instanceof ByteString) {
                isSend = mWebSocket.send((ByteString) msg);
            }
        }
        //发送消息失败后重新连接
        if (!isSend) {
            createConnect();
        }
        return isSend;
    }

    /**
     * 重连
     */
    private void reConnect() {
        if (!NetworkUtils.isConnected() ||
                getCurrentStatus() == WebStatus.WS_CONNECTED ||
                !ISNEEDCONNECTED || isManualClose) {
            setCurrentStatus(WebStatus.WS_DISCONNECT);
            return;
        }
        setCurrentStatus(WebStatus.WS_RECONNECT);
        mReConnectCount++;
        final long delay = mReConnectCount * RECONNECT_INTERVAL;
        if (null != mMainHandler) {
            mMainHandler.postDelayed(mReConnectRunnable, delay > RECONNECT_MAX_TIME ? RECONNECT_MAX_TIME : delay);
        }
    }

    /**
     * 断开连接
     */
    private void disConnect() {
        if (getCurrentStatus() == WebStatus.WS_DISCONNECT) {
            return;
        }
        if (mHttpClient != null) {
            mHttpClient.dispatcher().cancelAll();
        }
        if (mWebSocket != null) {
            boolean isClosed = mWebSocket.close(WebStatus.Code.NORMAL_CLOSE, WebStatus.Tip.NORMAL_CLOSE);
            //非正常关闭
            if (!isClosed) {
                if (LISTENER != null) {
                    LISTENER.onClosed(WebStatus.Code.UNNORMAL_CLOSE, WebStatus.Tip.UN_NORMAL_CLOSE);
                }
            }
        }
        setCurrentStatus(WebStatus.WS_DISCONNECT);
        mReConnectCount = 0;
    }

    /**
     * 创建连接
     */
    private void createConnect() {
        if (!NetworkUtils.isConnected()) {
            setCurrentStatus(WebStatus.WS_DISCONNECT);
            return;
        }
        if (getCurrentStatus() != WebStatus.WS_CONNECTED && getCurrentStatus() != WebStatus.WS_CONNETING) {
            initWebSocket();
        }
    }

    @Override
    public WebSocket getWebSocket() {
        return mWebSocket;
    }

    @Override
    public void startConnect() {
        isManualClose = false;
        createConnect();
    }

    @Override
    public void stopConnect() {
        isManualClose = true;
        disConnect();
    }

    @Override
    public boolean isConnected() {
        return mCurrentStatus == WebStatus.WS_CONNECTED;
    }

    @Override
    public int getCurrentStatus() {
        return mCurrentStatus;
    }

    @Override
    public void setCurrentStatus(int currentStatus) {
        this.mCurrentStatus = currentStatus;
    }

    /**
     * 发送字符串
     *
     * @param msg 需要发送字符串
     * @return true 发送成功（服务器端收到并响应） false 发送失败
     */
    @Override
    public boolean sendMessage(String msg) {
        return send(msg);
    }

    /**
     * 发送语音、视频等影音消息
     *
     * @param byteString 发送二进制格式消息
     * @return true 发送成功 false 发送失败
     */
    @Override
    public boolean sendMessage(ByteString byteString) {
        return send(byteString);
    }


    public static WebClientBuilder builder() {
        return new WebClientBuilder();
    }

    /**
     * 执行websocket不同的回调
     */
    private void exeCallback(final int type, final WebSocket webSocket, final Response response,
                             final String text, final ByteString bytes,
                             final int code, final String reason, final Throwable t) {
        if (null != mMainHandler) {
            mMainHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (LISTENER != null) {
                        switch (type) {
                            case ThreadCallBackCode.CODE_OPEN:
                                if (response.isSuccessful()) {
                                    setCurrentStatus(WebStatus.WS_CONNECTED);
                                    LISTENER.onOpenSuccess(webSocket, response);
                                } else {
                                    setCurrentStatus(WebStatus.WS_DISCONNECT);
                                    LISTENER.onOpenError(webSocket, response);
                                }
                                break;
                            case ThreadCallBackCode.CODE_MESSAGE_STRING:
                                LISTENER.onMessage(webSocket, text);
                                break;
                            case ThreadCallBackCode.CODE_MESSAGE_BYTES:
                                LISTENER.onMessage(webSocket, bytes);
                                break;
                            case ThreadCallBackCode.CODE_CLOSING:
                                setCurrentStatus(WebStatus.WS_CONNETING);
                                LISTENER.onClosing(webSocket, code, reason);
                                break;
                            case ThreadCallBackCode.CODE_CLOSED:
                                setCurrentStatus(WebStatus.WS_DISCONNECT);
                                LISTENER.onClosed(code, reason);
                                break;
                            case ThreadCallBackCode.CODE_FAILURE:
                                setCurrentStatus(WebStatus.WS_DISCONNECT);
                                LISTENER.onFailure(webSocket, t, response);
                                break;
                            default:
                                break;
                        }
                    }
                }
            });

        }
    }


    private WebSocketListener mSocketListener = new WebSocketListener() {

        @Override
        public void onOpen(final WebSocket webSocket, final Response response) {
            super.onOpen(webSocket, response);
            mWebSocket = webSocket;
            setCurrentStatus(WebStatus.WS_CONNETING);
            exeCallback(ThreadCallBackCode.CODE_OPEN, webSocket, response, null, null, 0, null, null);
        }

        @Override
        public void onMessage(final WebSocket webSocket, final String text) {
            super.onMessage(webSocket, text);
            exeCallback(ThreadCallBackCode.CODE_MESSAGE_STRING, webSocket, null, text, null, 0, null, null);
        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            super.onMessage(webSocket, bytes);
            exeCallback(ThreadCallBackCode.CODE_MESSAGE_BYTES, webSocket, null, null, bytes, 0, null, null);

        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            super.onClosing(webSocket, code, reason);
            exeCallback(ThreadCallBackCode.CODE_CLOSING, webSocket, null, null, null, 0, reason, null);
        }

        @Override
        public void onClosed(WebSocket webSocket, int code, String reason) {
            super.onClosed(webSocket, code, reason);
            exeCallback(ThreadCallBackCode.CODE_CLOSED, webSocket, null, null, null, 0, reason, null);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            super.onFailure(webSocket, t, response);
            reConnect();
            exeCallback(ThreadCallBackCode.CODE_FAILURE, webSocket, response, null, null, 0, null, t);
        }
    };

}
