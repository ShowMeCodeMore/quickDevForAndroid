package com.sa.all_cui.mix_core.net;


import com.sa.all_cui.mix_core.app.ConfigKeys;
import com.sa.all_cui.mix_core.app.MIX;

import java.util.ArrayList;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by all-cui on 2017/8/12.
 */
public class RestCreator {

    private static final class ParamHolder{
        private static final WeakHashMap<String,Object> PARAMS = new WeakHashMap<>();
    }

    public static WeakHashMap<String,Object> getParams(){
        return ParamHolder.PARAMS;
    }

    private static final class RetrofitHolder {
        //获取全局唯一的域名
        private static final String BASE_URL = (String) MIX.getConfigurations(ConfigKeys.API_HOST);
        private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                .client(OkHttpHolder.INSTANCE)
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
    }

    /*
    * okHttp具体实现请求
    * addInterceptor()--》response的时候会调用一次
    * addNetworkInterceptor()-->response和request个调用一次
    * */
    private static final class OkHttpHolder {
        private static final int TIME_OUT = 60;//超时时间
        private static final OkHttpClient.Builder BUILDER = new OkHttpClient.Builder();
        private static final ArrayList<Interceptor> INTERCEPTORS = MIX.getConfigurations(ConfigKeys.INTERCEPTOR);

        //依次将拦截器添加到okHttp中
        private static OkHttpClient.Builder getInterceptor(){
            if (INTERCEPTORS!=null&&!INTERCEPTORS.isEmpty()){
                for(Interceptor interceptor:INTERCEPTORS){
                    BUILDER.addInterceptor(interceptor);
                }
            }

            return BUILDER;
        }

        private static final OkHttpClient INSTANCE = getInterceptor()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();
    }

    //RestService
    private static final class ResetServiceHolder{
        private static final RestService RESET_SERVICE = RetrofitHolder.RETROFIT_CLIENT
                .create(RestService.class);
    }


    public static RestService getResetService(){
        return ResetServiceHolder.RESET_SERVICE;
    }
}
