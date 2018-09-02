package com.sa.all_cui.quick_lib.fix.cache;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * Created by zxkjc3 on 2017/3/15.
 *
 * function 主要是用来存储app的配置信息，如用户的账号、密码、app的设置信息等
 */

public class SharePreKit implements ICache {
    private static SharedPreferences mPreference;
    private SharedPreferences.Editor mEditor;//sp操作类
    private static String SP_NAME = "config";

    private static SharePreKit instance;

    public static void setSpName(String name) {
        if (!TextUtils.isEmpty(name)) {
            SP_NAME = name;
        }
    }

    @SuppressLint("CommitPrefEdits")
    private SharePreKit(Context ctx) {
        mPreference = ctx.getApplicationContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        mEditor = mPreference.edit();
    }

    /**
     * param ctx
     * @author cui
     * function 使用单例模式获取SharePreKit的实例防止出现多个实例
     */
    public static SharePreKit getInstance(Context ctx) {
        if (instance == null) {
            synchronized (SharePreKit.class) {
                if (instance == null)
                    instance = new SharePreKit(ctx);
            }
        }
        return instance;
    }

    @Override
    public void put(String key, Object value) {

    }

    @Override
    public Object get(String key) {
        return null;
    }

    @Override
    public void remove(String key) {
        //如果保存过这个key对应值才进行删除
        if (mPreference.contains(key)) {
            //throw new RuntimeException("请传入有效的key");
            mEditor.remove(key).apply();
        }
    }

    @Override
    public boolean cotain(String key) {
        return mPreference.contains(key);
    }

    @Override
    public void clear() {
        mEditor.clear().apply();
    }

    /**
     * @param key   保存int的名称
     * @param value 保存int值
     * @author cui
     * function
     */
    public void putInt(String key, int value) {
        mEditor.putInt(key, value).apply();//提交
    }

    /**
     * @param key 保存值对应的key
     * @return
     * @author cui
     * function 根据传入key获取相应的int值
     */
    public int getInt(String key) {
        return mPreference.getInt(key, 0);
    }

    /**
     * @param key 保存key值
     * @param value 保存的字符串
     * @author cui
     * function 保存string
     */
    public void putString(String key, String value) {
        mEditor.putString(key, value).apply();
    }

    /**
     * @param key 保存key值
     * @return
     * @author cui
     * function 根据key获取相应的string值
     */
    public String getString(String key) {
        return mPreference.getString(key, null);
    }

    /**
     * @param key 保存的key值
     * @param value 保存的boolean值
     * @author cui
     * function 保存布尔类型的值
     */
    public void putBoolean(String key, boolean value) {
        mEditor.putBoolean(key, value).apply();
    }

    /**
     * @param key 存储key值
     * @return 获取存储的boolean类型的值
     * @author cui
     * function 根据传入的key获取布尔值
     */
    public boolean getBoolean(String key) {
        return mPreference.getBoolean(key, false);
    }


}
