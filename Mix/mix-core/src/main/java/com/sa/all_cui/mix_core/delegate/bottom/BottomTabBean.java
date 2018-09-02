package com.sa.all_cui.mix_core.delegate.bottom;

/**
 * Created by all-cui on 2017/8/21.
 * final对于基本类型，一旦初始化之后值就不能改变
 * 对于应用类型，一旦初石化后对象的应用就不能在赋值给其他的对象
 */

public final class BottomTabBean {

    private final CharSequence ICON;
    private final CharSequence TITLE;

    public BottomTabBean(CharSequence icon, CharSequence title) {
        this.ICON = icon;
        this.TITLE = title;
    }

    public CharSequence getIcon() {
        return ICON;
    }

    public CharSequence getTitle() {
        return TITLE;
    }
}
