package com.sa.all_cui.mix_ui.recycler;


import com.google.auto.value.AutoValue;

/**
 * Created by all-cui on 2017/8/23.
 * 存储颜色值
 */
@AutoValue
public abstract class RgbValue {

    public abstract int red();

    public abstract int green();

    public abstract int blue();

    public static RgbValue create(int red, int green, int blue) {
        return new AutoValue_RgbValue(red, green, blue);
    }
}
