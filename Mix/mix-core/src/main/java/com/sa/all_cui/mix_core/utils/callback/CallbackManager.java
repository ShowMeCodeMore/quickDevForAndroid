package com.sa.all_cui.mix_core.utils.callback;

import java.util.WeakHashMap;

/**
 * Created by all-cui on 2017/8/29.
 */

public class CallbackManager {

    private static final WeakHashMap<Object, IGlabolCallback> CALLBACKS = new WeakHashMap<>();

    private static class Holder {
        private static CallbackManager INSTANCE = new CallbackManager();
    }

    public static CallbackManager getInstance() {
        return Holder.INSTANCE;
    }

    public CallbackManager addCallback(Object tag, IGlabolCallback callback) {
        CALLBACKS.put(tag, callback);
        return this;
    }

    public CallbackManager addCallback(WeakHashMap<Object, IGlabolCallback> callbackWeakHashMap) {
        CALLBACKS.putAll(callbackWeakHashMap);
        return this;
    }

    public IGlabolCallback getCallback(Object tag) {

        return CALLBACKS.get(tag);
    }
}
