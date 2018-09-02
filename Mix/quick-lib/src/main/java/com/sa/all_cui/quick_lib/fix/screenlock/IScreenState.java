package com.sa.all_cui.quick_lib.fix.screenlock;

/**
 * Created by all-cui on 2017/9/18.
 */

public interface IScreenState {

    void onScreenOn();

    void onScreenOff();

    void onUserPresent();//解锁
}
