package com.sa.all_cui.quick_lib.fix.screenlock;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by hh on 2017/1/4.
 *
 * function 使用DialogFragment创建Dialog有生命周期好管理
 */

public abstract class BaseDiaFragment extends DialogFragment {
    private Dialog mDialog;
    public Context mCtx;
    private View mView;
    public static final int SCREEN_FULL = -1;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCtx = context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //保存dialog的状态，反转屏幕能够恢复当前状态
        mDialog = super.onCreateDialog(savedInstanceState);

        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        mView = View.inflate(mCtx, getLayotId(), null);
        if (mView != null) {
            //底部fragement的布局
            mDialog.setContentView(mView);
        }
        //使用注解的方式获取控件
        setAttr();
        initView();
        initData();
        return mDialog;
    }

    /**
     * @author cui
     * function 初始化视图控件，获取控件的对象引用
     */
    public void initView() {

    }

    /**
     * 设置diaolog属性
     */
    private void setAttr() {
        //设置布局的位置以及大小
        Window window = mDialog.getWindow();
        assert window != null;
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = getGravity();//dialog显示的位置

        if (getLayoutHeight() == SCREEN_FULL) {
            //必须设置背景后全屏才能生效
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        } else if (getLayoutHeight() != 0) {
            lp.height = (int) dpToPx(getLayoutHeight());
        }
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;//宽度占满整个屏幕
        window.setAttributes(lp);//设置属性
    }

    public abstract int getLayotId();

    public abstract void initData();

    public abstract int getGravity();

    public abstract float getLayoutHeight();

    /**
     * @return dialog布局
     * @author cui
     * function 获取dialog视图布局 不能和系统方法重复，导致调用系统方法获取不到正确的值
     */
    public View getLayoutView() {
        return mView;
    }


    @SuppressWarnings("UnnecessaryReturnStatement")
    public void isAttatched() {
        if (!isAdded()) {
            return;
        }
    }

    @SuppressWarnings("EmptyCatchBlock")
    @Override
    public void show(FragmentManager manager, String tag) {
        if (getDialog() == null || !getDialog().isShowing()) {
            try {
                super.show(manager, tag);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("EmptyCatchBlock")
    @Override
    public void dismiss() {
        if (getDialog() != null && getDialog().isShowing()) {
            try {
                super.dismiss();
            } catch (IllegalArgumentException e) {

            }
        }
    }

    public void dismiss(boolean isResume) {
        if (isResume) {
            dismiss();
        } else {
            dismissAllowingStateLoss();
        }
    }

    @SuppressLint("CommitTransaction")
    public void show(FragmentManager manager, String tag, boolean isResume) {
        if (isResume) {
            show(manager.beginTransaction(), tag);
        }
    }

    public float dpToPx(float dp) {
        return dp * getResources().getDisplayMetrics().density;
    }
}
