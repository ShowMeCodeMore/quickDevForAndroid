package com.sa.all_cui.mix_ec.sign;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sa.all_cui.mix_core.app.AccountManager;
import com.sa.all_cui.mix_ec.database.UserProfile;


/**
 * Created by all-cui on 2017/8/21.
 */

public class SignHandler {

    public static void onSignUp(String response,ISignListener signListener) {
        final JSONObject profileJson = JSON.parseObject(response).getJSONObject("data");
        final long userId = profileJson.getLong("userId");
        final String name = profileJson.getString("name");
        final String avatar = profileJson.getString("avatar");
        final String gender = profileJson.getString("gender");
        final String address = profileJson.getString("address");

        final UserProfile profile = new UserProfile(userId, name, avatar, gender, address);
        //DataBaseManager.getInstance().getDao().insert(profile);

        //注册成功同时登录
        AccountManager.setSignInState(true);
        signListener.onSignUpSuccess();
    }

    public static void onSignIn(String response,ISignListener signListener) {
        final JSONObject profileJson = JSON.parseObject(response).getJSONObject("data");
        final long userId = profileJson.getLong("userId");
        final String name = profileJson.getString("name");
        final String avatar = profileJson.getString("avatar");
        final String gender = profileJson.getString("gender");
        final String address = profileJson.getString("address");

        final UserProfile profile = new UserProfile(userId, name, avatar, gender, address);
        //DataBaseManager.getInstance().getDao().insert(profile);

        //登录
        AccountManager.setSignInState(true);
        signListener.onSignInSuccess();
    }
}
