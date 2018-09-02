package com.sa.all_cui.mix_core.app;

import android.content.Context;
import android.os.Handler;

/**
 * Created by all-cui on 2017/8/11.
 */

public final class MIX {

    //初始化配置文件，将配置保存在WeakHashMap中，方便使用
    public static Configurator init(Context context){

        getConfigurator().getDoggerConfige().put(ConfigKeys.APPLICATION_CONTEXT,context.getApplicationContext());

        return Configurator.getInstance();
    }

    private static Configurator getConfigurator(){
        return Configurator.getInstance();
    }

    public static <T> T getConfigurations(Enum<ConfigKeys> key){
        return getConfigurator().getConfiguration(key);
    }

    public static Context getContext(){
        return getConfigurations(ConfigKeys.APPLICATION_CONTEXT);
    }


    public static Handler getHandler(){
        return getConfigurations(ConfigKeys.HANDLER);
    }
}
