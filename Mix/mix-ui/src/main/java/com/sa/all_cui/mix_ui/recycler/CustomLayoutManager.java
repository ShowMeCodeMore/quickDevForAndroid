package com.sa.all_cui.mix_ui.recycler;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.blankj.utilcode.util.LogUtils;

/**
 * Created by all-cui on 2017/10/16.
 */

public class CustomLayoutManager extends LinearLayoutManager {
    private String TAG="CustomLayoutManager";

    public CustomLayoutManager(final Context context) {
        super(context);
    }

    @Override
    public void onLayoutChildren(final RecyclerView.Recycler recycler, final RecyclerView.State state) {
        try {
            super.onLayoutChildren(recycler, state);
        } catch (Exception e) {
            LogUtils.e(TAG, "onLayoutChildren: "+e.getLocalizedMessage() );
            e.printStackTrace();
        } finally {
        }
    }
}
