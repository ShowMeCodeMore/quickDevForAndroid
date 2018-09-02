package com.sa.all_cui.mix_ui.recycler;

import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by all-cui on 2017/8/22.
 * 简单工厂模式
 */

public class MultipleViewHolder extends BaseViewHolder {

    private MultipleViewHolder(View view) {
        super(view);
    }

    static MultipleViewHolder create(View view){
        return new MultipleViewHolder(view);
    }
}
