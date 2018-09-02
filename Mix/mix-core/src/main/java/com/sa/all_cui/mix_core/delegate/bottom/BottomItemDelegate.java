package com.sa.all_cui.mix_core.delegate.bottom;

import android.widget.Toast;

import com.sa.all_cui.mix_core.R;
import com.sa.all_cui.mix_core.delegate.DoggerDelegate;

import me.yokeyword.fragmentation.ISupportFragment;

/**
 * Created by all-cui on 2017/8/21.
 * 实现具体类界面的基类并且同时实现back退出提示，当在指定的时间内连续两次
 * 直接退出，否则就弹出toast提示用户
 */

public abstract class BottomItemDelegate extends DoggerDelegate implements ISupportFragment {
    private long mExitTime;
    private final int TIME_GAP = 2000;

    @Override
    public boolean onBackPressedSupport() {
        if ((System.currentTimeMillis() - mExitTime) > TIME_GAP) {
            Toast.makeText(getContext(), "双击退出" + getString(R.string.app_name), Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
            return true;
        } else {
            _mActivity.finish();
            if (mExitTime != 0) {
                mExitTime = 0;//退出将保存退出时间临时变量置为初始状态
            }

            return true;
        }
    }
}
