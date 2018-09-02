package com.sa.all_cui.quick_lib.fix.cache;

/**
 * Created by zxkjc3 on 2017/3/15.
 *  缓存的接口
 */

public interface ICache {
    void put(String key, Object value);//存数据

    Object get(String key);//取数据

    void remove(String key);//删除某一个key对应的值

    boolean cotain(String key);//是否包含key对应的value

    void clear();//清除所有
}
