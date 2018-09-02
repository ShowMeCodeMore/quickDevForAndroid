package com.sa.all_cui.mix_ui.recycler;

import android.text.TextUtils;

import java.util.ArrayList;

/**
 * Created by all-cui on 2017/8/22.
 * 数据转换类
 * 定义一个约束
 */

public abstract class DataConvert {

    protected final ArrayList<MultipleItemEntity> ENTITYS = new ArrayList<>();
    private String mJsonData = null;

    public abstract ArrayList<MultipleItemEntity> convert();

    public final DataConvert setJsonData(String json) {
        this.mJsonData = json;
        return this;
    }

    @SuppressWarnings("WeakerAccess")
    public final String getJsonData() {
        if (TextUtils.isEmpty(mJsonData)) {
            throw new RuntimeException("JSONDATA NOT BE NULL");
        }
        //ENTITYS.clear();
        return mJsonData;
    }

}
