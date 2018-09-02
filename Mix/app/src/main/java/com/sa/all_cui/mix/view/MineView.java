package com.sa.all_cui.mix.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by all-cui on 2017/10/26.
 */

public class MineView extends View {

    //在new的时候调用
    public MineView(Context context) {
        this(context, null);
    }

    //在layout中进行调用
    public MineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    //默认style是defStyleAttr,第三个参数style的方式是指当前Application或者activity，只有在明确调用的时候才会生效
    //系统默认调用两个参数的构造函数
    public MineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //取出宽度的确切数值
        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        //取出宽度的测量模式
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        //取出高度的确切数值
        final int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        //取出宽度的测量模式
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
    }
}
