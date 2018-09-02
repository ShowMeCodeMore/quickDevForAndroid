package com.sa.all_cui.mix.event;

import android.os.Build;
import android.webkit.WebView;
import android.widget.Toast;

import com.sa.all_cui.mix_core.delegate.web.event.Event;
import com.sa.all_cui.mix_core.utils.log.DoggerLog;


/**
 * Created by all-cui on 2017/8/24.
 */

public class TestEvent extends Event {
    @Override
    public String execute(String param) {
        Toast.makeText(getContext(), getAction(), Toast.LENGTH_SHORT).show();
        if (getAction().equals("test")){
            final WebView webView = getWebView();
            webView.post(new Runnable() {
                @Override
                public void run() {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        webView.evaluateJavascript("nativeCall();",null);
                        DoggerLog.e("执行了");
                    }
                }
            });
        }
        return null;
    }
}
