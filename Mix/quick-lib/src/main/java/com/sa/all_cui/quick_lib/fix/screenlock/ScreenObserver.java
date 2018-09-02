package com.sa.all_cui.quick_lib.fix.screenlock;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Created by all-cui on 2017/9/18.
 */

@SuppressWarnings("WeakerAccess")
public class ScreenObserver {
    private Context mContext;
    private ScreenBroadReceive mScreenBroadReceiver;
    @SuppressWarnings("FieldCanBeLocal")
    private IScreenState mState;

    private ScreenObserver(Context context, ScreenBroadReceive screenReceive) {
        if (context == null) {
            throw new IllegalArgumentException("Context must not be null!");
        }
        if (screenReceive == null) {
            throw new IllegalArgumentException("ScreenBroadReceive must not be null!");
        }
        this.mContext = context.getApplicationContext();
        this.mScreenBroadReceiver = screenReceive;
    }

    public static ScreenObserver create(Context context, ScreenBroadReceive receive) {
        @SuppressWarnings("UnnecessaryLocalVariable") final ScreenObserver observer = new ScreenObserver(context, receive);
        return observer;
    }

    public void regesterObserve(IScreenState state) {
        this.mState = state;
        //注册监听过滤器
        registerListenerFilter();
    }

    //注销广播接收者
    public void unRegesterObserve() {
        if (mScreenBroadReceiver != null) {
            mContext.unregisterReceiver(mScreenBroadReceiver);
        }
    }

    private void registerListenerFilter() {
        final IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        if (mScreenBroadReceiver != null) {
            mContext.registerReceiver(mScreenBroadReceiver, filter);
        }
    }

}
