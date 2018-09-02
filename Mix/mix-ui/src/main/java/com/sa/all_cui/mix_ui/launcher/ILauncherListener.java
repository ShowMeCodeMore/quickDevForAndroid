package com.sa.all_cui.mix_ui.launcher;

/**
 * Created by all-cui on 2017/8/21.
 */

public interface ILauncherListener {

    void onLauncherFinish(OnLauncherFinishTag tag);//启动app后登录成功或者登录失败需要传入一个状态来证明是否传入成功
}
