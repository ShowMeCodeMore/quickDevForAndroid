package com.sa.all_cui.mix_ec.launcher;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.sa.all_cui.mix_core.app.AccountManager;
import com.sa.all_cui.mix_core.app.IUserChecker;
import com.sa.all_cui.mix_core.delegate.DoggerDelegate;
import com.sa.all_cui.mix_core.utils.storage.shareprefrence.DoggerPrefrence;
import com.sa.all_cui.mix_ec.R;
import com.sa.all_cui.mix_ui.launcher.ILauncherListener;
import com.sa.all_cui.mix_ui.launcher.LauncherHolderCreater;
import com.sa.all_cui.mix_ui.launcher.OnLauncherFinishTag;
import com.sa.all_cui.mix_ui.launcher.ScrollLauncherTag;

import java.util.ArrayList;

/**
 * Created by all-cui on 2017/8/17.
 */

public class LauncherScrollDeleagte extends DoggerDelegate implements OnItemClickListener {
    private ConvenientBanner<Integer> mConvenientBanner;
    private static final ArrayList<Integer> INTEGERS = new ArrayList<>();

    private ILauncherListener mLauncherListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ILauncherListener){
            mLauncherListener = (ILauncherListener) activity;
        }
    }

    private void initBanner(){
        //INTEGERS.clear();
        INTEGERS.add(R.drawable.launcher_01);
        INTEGERS.add(R.drawable.launcher_02);
        INTEGERS.add(R.drawable.launcher_03);
        INTEGERS.add(R.drawable.launcher_04);
        INTEGERS.add(R.drawable.launcher_05);

        mConvenientBanner
                .setPages(new LauncherHolderCreater(),INTEGERS)
                .setPageIndicator(new int[]{R.drawable.dot_normal,R.drawable.dot_foucs})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(this)
                .setCanLoop(false);
    }
    @Override
    public Object setLayout() {
        mConvenientBanner = new ConvenientBanner<>(getContext());
        return mConvenientBanner;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View view) {
        initBanner();
    }

    @Override
    public void onItemClick(int position) {

        //如果点击最后一页，就是第一次加载app,并保存标记
        if (INTEGERS.size()-1==position){
            DoggerPrefrence.setAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name(),true);
            //之后检查是否已经登录
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


}
