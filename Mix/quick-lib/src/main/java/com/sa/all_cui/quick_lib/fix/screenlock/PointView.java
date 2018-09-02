package com.sa.all_cui.quick_lib.fix.screenlock;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.AnimationUtils;

/**
 * Created by all-cui on 2017/9/18.
 * 手势点
 */

@SuppressLint("ViewConstructor")
public class PointView extends View {

    private int mNumber;
    private boolean isHighLighted = false;
    private Drawable mNormalSrc;
    private Drawable mHightLigthSrc;
    private int mPointOnAnim;
    private boolean isEnableVibrate;//是否启用震动
    private Vibrator mVibrator;//震动器实例
    private int mVibratorTime;//震动时间

    //pointOnAnim节点亮时的动画
    PointView(Context context,
              Drawable pointNormalSrc,
              Drawable pointHighLightSrc,
              int pointOnAnim,
              int number,
              Vibrator vibrator,
              boolean enableVibrate,
              int vibrateTime) {
        super(context);
        this.mNumber = number;
        this.mPointOnAnim = pointOnAnim;
        this.isEnableVibrate = enableVibrate;
        this.mVibrator = vibrator;
        this.mVibratorTime = vibrateTime;
        this.mNormalSrc = pointNormalSrc;
        this.mHightLigthSrc = pointHighLightSrc;
        setBackground(pointNormalSrc);
    }

    public void setHighLighted(boolean isHighLighted, boolean isMid) {
        if (this.isHighLighted != isHighLighted) {
            this.isHighLighted = isHighLighted;

            //设置了高亮资源才变化
            if (mHightLigthSrc != null) {
                setBackground(isHighLighted?mHightLigthSrc:mNormalSrc);
            }
            //播放动画
            if (mPointOnAnim != 0) {
                if (isHighLighted) {
                    startAnimation(AnimationUtils.loadAnimation(getContext(), mPointOnAnim));
                } else {
                    clearAnimation();
                }
            }
            //不包含中间节点，能够震动
            if (isEnableVibrate && !isMid) {
                if (isHighLighted&&mVibrator!=null) {
                    mVibrator.vibrate(mVibratorTime);
                }
            }


        }
    }


    public int getCenterX() {
        return (getLeft() + getRight()) / 2;
    }

    public int getCenterY() {
        return (getTop() + getBottom()) / 2;
    }

    public int getNumber(){
        return mNumber;
    }

    public boolean isHighLighted() {
        return isHighLighted;
    }
}
