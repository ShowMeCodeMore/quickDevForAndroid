package com.sa.all_cui.mix;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;
import com.blankj.utilcode.util.LogUtils;
import com.sa.all_cui.mix_core.activity.ProxyActivity;
import com.sa.all_cui.mix_core.delegate.DoggerDelegate;
import com.sa.all_cui.mix_ec.launcher.LauncherDelegate;
import com.sa.all_cui.mix_ec.main.EcBottomDelegate;
import com.sa.all_cui.mix_ec.sign.ISignListener;
import com.sa.all_cui.mix_ui.launcher.ILauncherListener;
import com.sa.all_cui.mix_ui.launcher.OnLauncherFinishTag;

import qiu.niorgai.StatusBarCompat;

public class HomeActivity extends ProxyActivity implements
        ISignListener,
        ILauncherListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar ac = getSupportActionBar();
        if (ac != null) {
            ac.hide();
        }
        StatusBarCompat.translucentStatusBar(this, true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this,R.color.colorWhite));
       // testSdkWorking();
    }
    //test LeanCloud sdk working
    private void testSdkWorking() {
        AVObject test = new AVObject("TestObject");
        test.put("mine", "hello LeanCloud");
        test.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {

                if (e == null) {
                    LogUtils.d("保存成功");
                }else{
                    LogUtils.d(e.getCode()+"\r\n"+e.getMessage());
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //activity调用统计api
        //AVAnalytics.onResume(this);
        //JPushInterface.onResume(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        //AVAnalytics.onPause(this);
        //JPushInterface.onPause(this);
    }

    @Override
    public DoggerDelegate setRootDelegate() {
        return new LauncherDelegate();
    }

    @Override
    public void onSignInSuccess() {
        //Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSignUpSuccess() {
        Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLauncherFinish(OnLauncherFinishTag tag) {
        switch (tag) {
            case SIGN:
                //Toast.makeText(this, "用户登录", Toast.LENGTH_SHORT).show();
                getSupportDelegate().startWithPop(new EcBottomDelegate());
                break;
            case NOT_SIGN:
                //Toast.makeText(this, "用户没有登录", Toast.LENGTH_SHORT).show();
                //getSupportDelegate().startWithPop(new SignInDelegate());
                getSupportDelegate().startWithPop(new EcBottomDelegate());
                break;
        }
    }

}
