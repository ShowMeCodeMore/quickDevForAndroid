package com.sa.all_cui.mix_core.app;

/**
 * Created by all-cui on 2017/8/21.
 * 检测是否登录，登录状态 对于整个app来说，可以将这个抽取出来作为一个接口
 */

public interface IUserChecker {

    void onSignIn();//已经登录

    void onNotSignIn();//未登录状态
}
