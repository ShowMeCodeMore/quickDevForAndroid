package com.sa.all_cui.mix_ec.launcher;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.sa.all_cui.mix_core.app.AccountManager;
import com.sa.all_cui.mix_core.app.IUserChecker;
import com.sa.all_cui.mix_core.delegate.DoggerDelegate;
import com.sa.all_cui.mix_core.utils.storage.shareprefrence.DoggerPrefrence;
import com.sa.all_cui.mix_core.utils.timer.BaseTimerTask;
import com.sa.all_cui.mix_core.utils.timer.ITimerListener;
import com.sa.all_cui.mix_ec.R;
import com.sa.all_cui.mix_ec.R2;
import com.sa.all_cui.mix_ui.launcher.ILauncherListener;
import com.sa.all_cui.mix_ui.launcher.OnLauncherFinishTag;
import com.sa.all_cui.mix_ui.launcher.ScrollLauncherTag;

import java.text.MessageFormat;
import java.util.Timer;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by all-cui on 2017/8/16.
 */

public class LauncherDelegate extends DoggerDelegate implements ITimerListener {
    //lib中ButterKnife是使用的R2资源
    @BindView(R2.id.tv_launcher_timer)
    AppCompatTextView mTvTimer;
    private Timer mTimer = null;
    private int mCount =5;

    private ILauncherListener mLauncherListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ILauncherListener){
            mLauncherListener = (ILauncherListener) context;
        }
    }

    @OnClick(R2.id.tv_launcher_timer)
    void onClickTimerView() {
        cancelTimer();
    }

    private void initTimer() {
        mTimer = new Timer();
        final BaseTimerTask timer = new BaseTimerTask(this);
        //开始定时任务，第一个参数延迟时间，第二个参数的持续时间
        mTimer.schedule(timer, 0, 1000);
    }

    //判断是否显示滑动splash界面
    private void checkIsShowScroll(){
        if (!DoggerPrefrence.getAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name())){
            getSupportDelegate().start(new LauncherScrollDeleagte(),SINGLETASK);
        }else{
            //检查用户是否已经登录
            AccountManager.checkAccount(new IUserChecker() {
                @Override
                public void onSignIn() {
                    //登录成功
                    if (null!=mLauncherListener){
                        mLauncherListener.onLauncherFinish(OnLauncherFinishTag.SIGN);
                    }
                }

                @Override
                public void onNotSignIn() {
                    //登录失败
                    if (null!=mLauncherListener){
                        mLauncherListener.onLauncherFinish(OnLauncherFinishTag.NOT_SIGN);
                    }
                }
            });
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_launcher;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View view) {
        initTimer();
    }

    @Override
    public void onTimer() {
        //ui线程中更新
        getProxyActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mTvTimer != null) {
                    mTvTimer.setText(MessageFormat.format("跳过\n{0}s", mCount));
                    mCount--;
                    if (mCount < 0) {
                        cancelTimer();
                    }

                }
            }
        });
    }

    private void cancelTimer() {
        if (mTimer != null) {
            mTimer.cancel();//取消线程
            mTimer = null;//将引用置为null
            checkIsShowScroll();
        }
    }
}
