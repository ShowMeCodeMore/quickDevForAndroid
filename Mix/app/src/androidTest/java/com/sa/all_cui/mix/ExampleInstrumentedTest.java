package com.sa.all_cui.mix;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.sa.all_cui.mix_core.net.RestClient;
import com.sa.all_cui.mix_core.net.callback.IError;
import com.sa.all_cui.mix_core.net.callback.ISuccess;
import com.sa.all_cui.mix_core.utils.log.DoggerLog;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        //assertEquals("com.sa.all_cui.mix", appContext.getPackageName());
        testSdkWorking();
    }
    //test LeanCloud sdk working
    private void testSdkWorking() {
        RestClient.builder()
                .params("api", "ad.list")
                .params("game", "lol")
                .params("userId","0")
                .params("platformVersion","501140")
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        //Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                        DoggerLog.i("lol-zhangyoubao", response);

                        //SignHandler.onSignIn(response, mSignListener);
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        DoggerLog.i("lol-error"+code+"\r\n"+msg);
                    }
               })
                .build()
                .get();
    }
}

