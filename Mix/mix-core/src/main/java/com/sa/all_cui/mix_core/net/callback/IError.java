package com.sa.all_cui.mix_core.net.callback;

/**
 * Created by all-cui on 2017/8/12.
 */

public interface IError {
    /*
    * code --> 错误码
    * msg --> 错误提示信息
    * */
    void onError(int code, String msg);
}
