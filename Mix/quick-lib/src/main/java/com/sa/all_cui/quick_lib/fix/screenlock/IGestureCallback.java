package com.sa.all_cui.quick_lib.fix.screenlock;

import android.support.annotation.NonNull;

/**
 * Created by all-cui on 2017/9/18.
 */

public interface IGestureCallback {

    //节点连接成功
    void onPointConnected(@NonNull int[] numbers);

    //手势完成
    void onGestureFinished(@NonNull int[] numbers);
}
