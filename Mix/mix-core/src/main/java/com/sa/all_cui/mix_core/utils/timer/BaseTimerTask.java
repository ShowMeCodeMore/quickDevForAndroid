package com.sa.all_cui.mix_core.utils.timer;

import java.util.TimerTask;

/**
 * Created by all-cui on 2017/8/16.
 * 倒计时
 */

public class BaseTimerTask extends TimerTask {

    private ITimerListener mITimerListener = null;

    public BaseTimerTask(ITimerListener timerListener) {
        this.mITimerListener = timerListener;
    }

    @Override
    public void run() {
        if (mITimerListener !=null){
            mITimerListener.onTimer();
        }
    }
}
