package com.sa.all_cui.mix_core.app;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.blankj.utilcode.util.Utils;
import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;
import com.sa.all_cui.mix_core.delegate.web.event.Event;
import com.sa.all_cui.mix_core.delegate.web.event.EventManager;
import com.sa.all_cui.mix_core.screenfit.ScreenFitHelper;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Interceptor;

/**
 * Created by all-cui on 2017/8/11.
 * app全局配置类
 */

public class Configurator {

    private static final HashMap<Object, Object> DOGGER_CONFIGE = new HashMap<>();
    private static final Handler HANDLER = new Handler();
    private static final ArrayList<IconFontDescriptor> ICONS = new ArrayList<>();
    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();

    private Configurator() {
        DOGGER_CONFIGE.put(ConfigKeys.CONFIG_READY, false);//初始化开始，因为是开始所以这里填入false，如果已经完成的话就是true
        DOGGER_CONFIGE.put(ConfigKeys.HANDLER, HANDLER);
    }


    static Configurator getInstance() {
        return Holder.INSTANCE;
    }

    //静态内部类的方式实现单例，在多线程中也是安全的
    private static class Holder {
        private static final Configurator INSTANCE = new Configurator();
    }

    //配置完成
    public final void configure() {
        DOGGER_CONFIGE.put(ConfigKeys.CONFIG_READY, true);
        Context context = MIX.getContext();
        initIcon();
        Utils.init((Application)context);
        ScreenFitHelper.create((Application) context).activate();
    }

    private void initIcon() {
        if (ICONS.size() > 0) {
            final Iconify.IconifyInitializer initializer = Iconify.with(ICONS.get(0));

            for (int i = 0; i < ICONS.size(); i++) {
                initializer.with(ICONS.get(i));
            }
        }
    }

    //加入自定义的图标
    public final Configurator withIcon(IconFontDescriptor descriptor) {
        ICONS.add(descriptor);
        return this;
    }

    final HashMap<Object, Object> getDoggerConfige() {
        return DOGGER_CONFIGE;
    }

    //配置网络请求域名
    public final Configurator withHostApi(String host) {
        DOGGER_CONFIGE.put(ConfigKeys.API_HOST, host);
        return this;
    }

    //配置拦截器
    public final Configurator withInterceptor(Interceptor interceptor) {
        INTERCEPTORS.add(interceptor);
        DOGGER_CONFIGE.put(ConfigKeys.INTERCEPTOR, INTERCEPTORS);
        return this;
    }

    public final Configurator withInterceptor(ArrayList<Interceptor> interceptors) {
        INTERCEPTORS.addAll(interceptors);
        DOGGER_CONFIGE.put(ConfigKeys.INTERCEPTOR, INTERCEPTORS);
        return this;
    }

    //检查是否配置成功，保证程序最大程度上可以正常运行
    //fianl关键子对于哪些以后不在修改的方法或者变量，android的虚拟机会对代码进行优化，性能上或多或少得到一定的提升
    private void checkConfigue() {
        final boolean isReady = (boolean) DOGGER_CONFIGE.get(ConfigKeys.CONFIG_READY);
        if (!isReady) {
            throw new RuntimeException("configuration is not ready,call configure");
        }
    }

    public Configurator withJavascriptInterface(@NonNull String name) {
        DOGGER_CONFIGE.put(ConfigKeys.JAVASCRIPT_INTERFACE, name);
        return this;
    }

    public Configurator withWebEvent(@NonNull String name, @NonNull Event event) {
        final EventManager manager = EventManager.getInstance();
        manager.addEvent(name, event);
        return this;
    }

    @SuppressWarnings("unchecked")//忽略黄色的提示线，提升代码的美观度
    final <T> T getConfiguration(Enum<ConfigKeys> key) {
        checkConfigue();
        final Object value = DOGGER_CONFIGE.get(key);
        if (value == null) {
            throw new RuntimeException(key.toString() + "is NULL");
        }
        return (T) value;
    }
}
