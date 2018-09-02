package com.sa.all_cui.mix_core.app;


import com.sa.all_cui.mix_core.utils.storage.shareprefrence.DoggerPrefrence;

/**
 * Created by all-cui on 2017/8/21.
 */

public class AccountManager {

    private enum SignTag{
        SIGN_TAG
    }

    //设置登录后的状态，登录成功或者失败
    public static void setSignInState(boolean state){
        DoggerPrefrence.setAppFlag(SignTag.SIGN_TAG.name(),state);
    }

    //判断登录的状态
    private static boolean isSign(){
        return DoggerPrefrence.getAppFlag(SignTag.SIGN_TAG.name());
    }

    //检测登录的状态，如果已经登录，执行登录回调；如果没有登录执行没有登录的回调。
    public static void checkAccount(IUserChecker iUserChecker){
        if (isSign()){
            iUserChecker.onSignIn();
        }else{
            iUserChecker.onNotSignIn();
        }
    }
}
