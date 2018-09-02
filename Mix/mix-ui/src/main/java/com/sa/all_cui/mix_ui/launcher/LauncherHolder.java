package com.sa.all_cui.mix_ui.launcher;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.bigkoo.convenientbanner.holder.Holder;

/**
 * Created by all-cui on 2017/8/17.
 */

public class LauncherHolder implements Holder<Integer> {

    private AppCompatImageView mIv;

    @Override
    public View createView(Context context) {
        mIv = new AppCompatImageView(context);
        return mIv;
    }

    @Override
    public void UpdateUI(Context context, int position, Integer data) {
        mIv.setBackgroundResource(data);
    }
}
