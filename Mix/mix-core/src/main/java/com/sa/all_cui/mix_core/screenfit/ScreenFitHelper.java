package com.sa.all_cui.mix_core.screenfit;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.blankj.utilcode.util.LogUtils;

import java.lang.reflect.Field;

/**
 * Created by all-cui on 2017/10/30.
 */

@SuppressWarnings("WeakerAccess")
public class ScreenFitHelper implements Application.ActivityLifecycleCallbacks {
    private Application mApplication;
    private float mWidth = 720;//默认宽度为720px

    private ScreenFitHelper(@NonNull Application application) {
        this.mApplication = application;
    }

    public static ScreenFitHelper create(@NonNull Application application) {
        return new ScreenFitHelper(application);
    }

    /**
     * 恢复DisplayMetrics为原生状态，单位Pt为长度单位磅
     */
    private void recoveryDensity() {
        Resources resources = mApplication.getApplicationContext().getResources();
        resources.getDisplayMetrics().setToDefaults();
        DisplayMetrics metrics = getMetricsOnMiui(resources);
        if (metrics != null) {
            metrics.setToDefaults();
        }
    }

    //解决MIUI更改框架导致的MIUI7+Android5.1.1上出现的失效问题(以及极少数基于这部分miui去掉art然后置入xposed的手机)
    private static DisplayMetrics getMetricsOnMiui(Resources resources) {
        if ("MiuiResources".equals(resources.getClass().getSimpleName()) || "XResources".equals(resources.getClass().getSimpleName())) {
            try {
                Field field = Resources.class.getDeclaredField("mTmpMetrics");
                field.setAccessible(true);
                return (DisplayMetrics) field.get(resources);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    private void resetDensity(Context activity) {
        //获取手机分辨率
        Point size = new Point();
        Context context = activity.getApplicationContext();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getSize(size);
        Resources resources = context.getResources();
        //源码中1pt=72px
        resources.getDisplayMetrics().xdpi = size.x / mWidth * 72f;
        LogUtils.i("size x:" + size.x + "\r\n" + "size y:" + size.y + "\r\n" +
                resources.getDisplayMetrics().xdpi);
        DisplayMetrics metrics = getMetricsOnMiui(resources);
        if (metrics != null) {
            metrics.xdpi = size.x / mWidth * 72f;
        }
    }


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        //通常情况下application与activity得到的resource虽然不是一个实例，但是displayMetrics是同一个实例，只需调用一次即可
        //为了面对一些不可预计的情况以及向上兼容，分别调用一次较为保险
        resetDensity(mApplication);
        resetDensity(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        /*resetDensity(mApplication);
        resetDensity(activity);*/
    }

    @Override
    public void onActivityResumed(Activity activity) {
        /*resetDensity(mApplication);
        resetDensity(activity);*/
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    /**
     * 激活本方案使用自动布局
     */
    public void activate() {
        resetDensity(mApplication);
        mApplication.registerActivityLifecycleCallbacks(this);
    }

    /**
     * 恢复到原生方案
     */
    public void inActivate() {
        recoveryDensity();
        mApplication.unregisterActivityLifecycleCallbacks(this);
    }

    public ScreenFitHelper setWidth(float width) {
        this.mWidth = width;
        return this;
    }
}
