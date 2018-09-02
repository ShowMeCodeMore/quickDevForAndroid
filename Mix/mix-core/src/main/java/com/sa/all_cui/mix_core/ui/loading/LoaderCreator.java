package com.sa.all_cui.mix_core.ui.loading;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.sa.all_cui.mix_core.app.MIX;
import com.wang.avi.AVLoadingIndicatorView;
import com.wang.avi.Indicator;

import java.util.WeakHashMap;

/**
 * Created by all-cui on 2017/8/12.
 */

@SuppressWarnings("WeakerAccess")
public class LoaderCreator {
    private static final WeakHashMap<String, Indicator> LOADING_MAP = new WeakHashMap<>();

    static AVLoadingIndicatorView create(Context context, String type) {
        final AVLoadingIndicatorView avLoadingIndicatorView = new AVLoadingIndicatorView(context);

        if (LOADING_MAP.get(type) == null) {
            final Indicator indicator = getIndicator(type);
            LOADING_MAP.put(type, indicator);
        }
        avLoadingIndicatorView.setIndicatorColor(ContextCompat.getColor(MIX.getContext(), android.R.color.holo_orange_light));
        avLoadingIndicatorView.setIndicator(LOADING_MAP.get(type));
        return avLoadingIndicatorView;
    }

    private static Indicator getIndicator(String name) {
        if (TextUtils.isEmpty(name)) {
            return null;
        }

        final StringBuilder drawableClassName = new StringBuilder();
        if (!name.contains(".")) {
            final String defalutPackageName = AVLoadingIndicatorView.class.getPackage().getName();
            drawableClassName
                    .append(defalutPackageName)
                    .append(".indicators")
                    .append(".");
        }

        drawableClassName.append(name);

        try {
            final Class<?> drawableClass = Class.forName(drawableClassName.toString());
            return (Indicator) drawableClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
