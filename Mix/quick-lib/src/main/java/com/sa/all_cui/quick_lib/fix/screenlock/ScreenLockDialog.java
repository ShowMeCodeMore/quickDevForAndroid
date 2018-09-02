package com.sa.all_cui.quick_lib.fix.screenlock;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.sa.all_cui.quick_lib.R;
import com.sa.all_cui.quick_lib.fix.cache.SharePreKit;


/**
 * Created by all-cui on 2017/9/19.
 */

public class ScreenLockDialog extends BaseDiaFragment implements IGestureCallback {
    private TextView mTvTip;
    private LockView mLockView;
    private Toast mToast;
    private SharePreKit mShareKit;
    public static final String SET_GESTURED_PWD = "gesture_pwd";
    private int mFailPwdNumber = 0;

    public static ScreenLockDialog newInstance() {

        final Bundle args = new Bundle();

        final ScreenLockDialog fragment = new ScreenLockDialog();
        fragment.setArguments(args);
        fragment.setCancelable(false);

        return fragment;
    }


    @Override
    public int getLayotId() {
        return R.layout.screen_lock_layout;
    }

    @Override
    public void initData() {
        mTvTip = (TextView) getLayoutView().findViewById(R.id.tv_screen_lock_tip);
        mLockView = (LockView) getLayoutView().findViewById(R.id.lock_view_screen);
        init();
    }

    @Override
    public int getGravity() {
        return 0;
    }

    @Override
    public float getLayoutHeight() {
        return SCREEN_FULL;
    }

    //初始化
    private void init() {
        mLockView.setCallback(this);
        if (mShareKit == null) {
            mShareKit = SharePreKit.getInstance(mCtx);
        }
        if (!TextUtils.isEmpty(mShareKit.getString(SET_GESTURED_PWD))) {
            mTvTip.setText(getString(R.string.screen_lock_open));
        }
    }

    @Override
    public void onPointConnected(@NonNull int[] numbers) {

    }

    @Override
    public void onGestureFinished(@NonNull int[] numbers) {
        final String pwd = mShareKit.getString(SET_GESTURED_PWD);
        if (TextUtils.isEmpty(pwd)) {
            mShareKit.putString(SET_GESTURED_PWD, getPwd(numbers));
            //再次输入手势密码
            mTvTip.setText(getString(R.string.screen_lock_again));
        } else if (getPwd(numbers).equals(pwd)) {
            dismiss();
        } else {
            //手势密码输入四次后自动提示重新输入
            mFailPwdNumber++;
            showS(String.format(getString(R.string.screen_lock_fail), mFailPwdNumber));
            if (mFailPwdNumber == 4) {
                mFailPwdNumber = 0;
                mShareKit.remove(SET_GESTURED_PWD);
                mTvTip.setText(getString(R.string.screen_lock_rest));
            }
        }
    }

    /*将坐标点整形数组，转换为字符串，容易保存*/
    private String getPwd(int[] numbers) {
        final StringBuilder pwds = new StringBuilder();
        final int size = numbers.length;
        for (int number : numbers) {
            pwds.append(number);
        }
        return pwds.toString();
    }

    private void showS(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(mCtx, msg, Toast.LENGTH_SHORT);
        }
        mToast.setText(msg);
        mToast.show();
    }


}
