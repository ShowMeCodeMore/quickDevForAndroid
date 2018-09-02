package com.sa.all_cui.mix_ui.recycler;

import android.support.annotation.ColorInt;

import com.choices.divider.DividerItemDecoration;

/**
 * Created by all-cui on 2017/8/23.
 */

public class BaseDecoration extends DividerItemDecoration {

    //color 线的颜色 size是指分割线的粗细
    private BaseDecoration(@ColorInt int color,int size) {
        setDividerLookup(new DividerLookupImpl(color, size));
    }

    public static BaseDecoration create(@ColorInt int color,int size){
        return new BaseDecoration(color, size);
    }

}
