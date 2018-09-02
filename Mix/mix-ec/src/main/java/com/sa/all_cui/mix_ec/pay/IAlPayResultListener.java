package com.sa.all_cui.mix_ec.pay;

/**
 * Created by all-cui on 2017/8/28.
 * 支付结果的几种状态
 */

public interface IAlPayResultListener {

    void onPaySuccess();//支付成功

    void onPaying();//支付中

    void onPayFail();//支付失败

    void onPayCancel();//用户取消操作

    void onPayConnectError();//网络连接错误
}
