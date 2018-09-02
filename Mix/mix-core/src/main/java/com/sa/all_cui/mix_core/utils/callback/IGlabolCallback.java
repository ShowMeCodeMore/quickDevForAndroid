package com.sa.all_cui.mix_core.utils.callback;

/**
 * Created by all-cui on 2017/8/29.
 * 使用范型进行高级解耦，提高程序的可控制性以及
 * 执行效率
 */

public interface IGlabolCallback<T> {

    void executeCallback(T t);

}
