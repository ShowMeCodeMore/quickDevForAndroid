package com.sa.all_cui.mix_ec.main.index;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

import com.sa.all_cui.mix_ec.R;
import com.sa.all_cui.mix_ui.recycler.RgbValue;


/**
 * Created by all-cui on 2017/8/23.
 * 控制toolbar的滑动行为
 */

@SuppressWarnings("unused")
public class TransluncentBehavior extends CoordinatorLayout.Behavior<Toolbar> {

    //顶部距离
    private int mDistanceY = 0;
    //颜色变化速度
    private static final int SHOW_SPEED = 3;
    //定义变化的颜色
    private final RgbValue RGB_VALUE = RgbValue.create(255,124,2);

    //不加会报错
    public TransluncentBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //依赖的view 这里式依赖recyvlerview滑动而改变效果
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, Toolbar child, View dependency) {
        return dependency.getId() == R.id.rv_index;
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, Toolbar child, View directTargetChild, View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, Toolbar child, View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
        //增加滑动距离
        mDistanceY += dy;
        //toolbar的高度
        final int targetHeight = child.getBottom();
        //当滑动时，并且距离小于toolbar高度时，调整渐变色
        if (mDistanceY>0&&mDistanceY<=targetHeight){
            final float scale = (float) mDistanceY/targetHeight;
            final float alpha = scale * 255;
            child.setBackgroundColor(Color.argb((int) alpha,RGB_VALUE.red(),RGB_VALUE.green(),RGB_VALUE.blue()));
        }else if (mDistanceY>targetHeight){
            child.setBackgroundColor(Color.rgb(RGB_VALUE.red(),RGB_VALUE.green(),RGB_VALUE.blue()));
        }
    }
}
