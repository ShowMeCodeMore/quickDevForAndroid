package com.sa.all_cui.quick_lib.fix.screenlock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by all-cui on 2017/9/18.
 */

public class ScreenBroadReceive extends BroadcastReceiver {

    private static final String TAG = "ScreenBroadReceive";
    private IScreenState mState;


    public ScreenBroadReceive(IScreenState state) {
        mState = state;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        Log.d(TAG, action);
        switch (action) {
            case Intent.ACTION_SCREEN_ON:
                //开屏
                mState.onScreenOn();
                break;
            case Intent.ACTION_SCREEN_OFF:
                //关屏
                mState.onScreenOff();
                break;
            case Intent.ACTION_USER_PRESENT:
                //解锁
                mState.onUserPresent();
                break;
            default:
                break;
        }
    }
}
