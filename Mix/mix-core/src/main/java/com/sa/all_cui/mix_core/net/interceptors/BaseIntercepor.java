package com.sa.all_cui.mix_core.net.interceptors;

import java.util.LinkedHashMap;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;

/**
 * Created by all-cui on 2017/8/15.
 */

public abstract class BaseIntercepor implements Interceptor {


    //hashmap底层的是数组和链表，写入到内存中
    protected LinkedHashMap<String,String> getUrlParameters(Chain chain){
        final HttpUrl url = chain.request().url();
        final int size = url.querySize();
        final LinkedHashMap<String,String> params = new LinkedHashMap<>();
        for (int i=0;i<size;i++){
            params.put(url.queryParameterName(i),url.queryParameterValue(i));
        }
        return params;
    }

    /**
     * 传入key值获取相应的参数值
     * @param chain
     * @param key
     * @return
     */
    protected String getUrlParameters(Chain chain,String key){
        final Request request = chain.request();
        return request.url().queryParameter(key);
    }

    protected LinkedHashMap<String,String> getBodyParameters(Chain chain){
        final FormBody formBody = (FormBody) chain.request().body();
        final int size = formBody != null ? formBody.size() : 0;
        final LinkedHashMap<String,String> params = new LinkedHashMap<>();
        for (int i=0;i<size;i++){
            params.put(formBody.name(i),formBody.value(i));
        }
        return params;
    }

    protected String getBodyParameters(Chain chain,String key){
        return getBodyParameters(chain).get(key);
    }
}
