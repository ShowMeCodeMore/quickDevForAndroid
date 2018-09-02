package com.sa.all_cui.mix;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.sa.all_cui.mix_core.push.IWsListener;
import com.sa.all_cui.mix_core.push.WSManager;
import com.sa.all_cui.mix_core.ui.loading.DoggerLoader;
import com.sa.all_cui.mix_core.ui.loading.LoaderStyle;
import com.sa.all_cui.quick_lib.fix.screenlock.ScreenLockManager;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.WebSocket;
import okio.ByteString;

public class TestActivity extends AppCompatActivity implements IWsListener {
    @BindView(R.id.tv_msg)
    TextView mTv;
    private ScreenLockManager manager;
    private StringBuilder mStringBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        DoggerLoader.showLoading(this, LoaderStyle.CubeTransitionIndicator);

        ButterKnife.bind(this);

        /*
        new Thread(new Runnable() {
            @Override
            public void run() {
                WebSocketServer server = new WebSocketServer();
                String wsUrl = "ws://" + server.getHome() + ":" + server.getPort() + "/";
                new WebSocketClient(wsUrl);
            }
        }).start();*/
        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .pingInterval(15, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
        mStringBuilder = new StringBuilder();
        WSManager.getInstance()
                .startConnect("ws://121.40.165.18:8088/",this,client);
//        manager = new ScreenLockManager(this, MixApp.getMixApp());

    }

    @Override
    protected void onStart() {
        super.onStart();
//        manager.onStart(getSupportFragmentManager());
    }

    public void toDisplaySetting(View v) {
        Intent intent = new Intent(Settings.ACTION_DISPLAY_SETTINGS);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        manager.onUnRegisterBroadcast();
    }

    @Override
    public void onOpenSuccess(WebSocket webSocket, Response response) {
        LogUtils.i(response.isSuccessful()+"=========onOpenSuccess===========");
    }

    @Override
    public void onOpenError(WebSocket webSocket, Response response) {
        LogUtils.i(response.isSuccessful()+"=========onOpenError=============");
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        LogUtils.i(text.length()+"=========onMessage===============================");
        mTv.setText(text);
    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {

    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        LogUtils.i("code:"+code+"\r\n"+reason+"========onClosing=========");
    }

    @Override
    public void onClosed(int code, String reason) {
        LogUtils.i("code:"+code+"\r\n"+reason+"========onClosed==========");
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, @Nullable Response response) {
        mStringBuilder.append("Throwable:").append(t).append("\r\n").append(response).append("========onFailure=========");
        mTv.setText(mStringBuilder.toString());
        LogUtils.i("Throwable:"+t+"\r\n"+response+"========onFailure=========");
    }

    @Override
    public void onReConnect() {
        LogUtils.i("==========================onReconnect================");
    }
}
