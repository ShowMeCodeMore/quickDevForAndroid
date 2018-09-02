package com.sa.all_cui.mix;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVOSCloud;
import com.blankj.utilcode.util.ToastUtils;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.sa.all_cui.mix.event.TestEvent;
import com.sa.all_cui.mix_core.app.MIX;
import com.sa.all_cui.mix_core.net.interceptors.DebugInterceptor;
import com.sa.all_cui.mix_core.utils.callback.CallbackManager;
import com.sa.all_cui.mix_core.utils.callback.CallbackType;
import com.sa.all_cui.mix_core.utils.callback.IGlabolCallback;
import com.sa.all_cui.mix_ec.icon.FontEcMoudel;

/**
 * Created by all-cui on 2017/8/11.
 */

public class MixApp extends Application {
    private static MixApp mixApp;
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化配置
        MIX.init(this)
                .withHostApi("http://api.jcodecraeer.com/")
                .withIcon(new FontAwesomeModule())
                .withInterceptor(new DebugInterceptor("test", R.raw.test))
                .withJavascriptInterface("dogger")
                .withWebEvent("test", new TestEvent())
                .withIcon(new FontEcMoudel())
                .configure();

        //初始化logger
        Logger.addLogAdapter(new AndroidLogAdapter());

       // DataBaseManager.getInstance().init(this);


//        JPushInterface.setDebugMode(true);
//        JPushInterface.init(this);


        controlPushOpenOrStop(CallbackType.ON_PUSH_OPNE);
        controlPushOpenOrStop(CallbackType.ON_PUSH_STOP);
        //初始化leancloud云 参数context\AppId\AppKey
        AVOSCloud.initialize(this,"2l1e16Mjrkd5R4W3tPAAUvau-gzGzoHsz",
                "0u5CVST2LYXrvJ7Ino9EHYWu");
        //开始LeanCloud的调试模式
        AVOSCloud.setDebugLogEnabled(true);
        AVAnalytics.enableCrashReport(this,true);
    }




    //控制极光推送推送的消息的接收和停止接收
    private void controlPushOpenOrStop(final Object type) {
        CallbackManager.getInstance().addCallback(type, new IGlabolCallback<String>() {
            @Override
            public void executeCallback(String s) {
                ToastUtils.showShort(s);
               /* if (type.equals(CallbackType.ON_PUSH_OPNE)) {
                    if (JPushInterface.isPushStopped(getApplicationContext())) {
                        JPushInterface.resumePush(getApplicationContext());
                    }
                } else {
                    if (!JPushInterface.isPushStopped(getApplicationContext())) {
                        JPushInterface.stopPush(getApplicationContext());
                    }
                }*/
            }
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
