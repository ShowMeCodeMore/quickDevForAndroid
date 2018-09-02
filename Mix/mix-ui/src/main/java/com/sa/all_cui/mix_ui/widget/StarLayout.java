package com.sa.all_cui.mix_ui.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.joanzapata.iconify.widget.IconTextView;
import com.sa.all_cui.mix_ui.R;

import java.util.ArrayList;

/**
 * Created by all-cui on 2017/9/4.
 * 商品打分，星星显示控件
 * 使用static修饰的本意是在内存中只有一份拷贝，数据是用来共享的
 */

public class StarLayout extends LinearLayoutCompat implements View.OnClickListener {

    //常量使用static修饰的好处就是创建多个StarLayout对象时，ICon_UN_SELECTED也创建多个
    //在内存中有多分拷贝
    private final static CharSequence ICON_UN_SELECTED = "{fa-star-o}";
    //实心的星星
    private final static CharSequence ICON_SELECTED = "{fa-star}    ";
    private final static int STAR_TOTAL_COUNT = 5;
    private final  ArrayList<IconTextView> STAR_LIST = new ArrayList<>();

    public StarLayout(Context context) {
        this(context, null);
    }

    public StarLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initIcon();
    }

    //初始化Icon
    private void initIcon() {
        for (int i = 0; i < STAR_TOTAL_COUNT; i++) {
            final IconTextView star = new IconTextView(getContext());
            //ViewGroup的LayoutParams仅仅告诉这个View的宽度和高度
            //除了ViewGroup的之外LayoutParams都是告诉父控件，如何来放置自己
            final LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            lp.weight = 1;
            star.setGravity(Gravity.CENTER);
            star.setLayoutParams(lp);
            star.setText(ICON_UN_SELECTED);
            star.setTextSize(18);
            //给star添加标记，i=0 第一颗星 i=1 第二颗星。。
            star.setTag(R.id.start_count, i);
            //默认没有选中
            star.setTag(R.id.start_is_select, false);
            star.setOnClickListener(this);
            STAR_LIST.add(star);
            this.addView(star);
        }
    }

    //获取已经选中的星星的个数
    public int getStarCount() {
        int count = 0;
        for (int i = 0; i < STAR_TOTAL_COUNT; i++) {
            final IconTextView star = STAR_LIST.get(i);
            final boolean isSelect = (boolean) star.getTag(R.id.start_is_select);
            if (isSelect) {
                count++;
            }
        }

        return count;
    }

    /**
     * （点击星星）选中所有的当前所有之前的星星（同时包含已经点击的星星）
     *
     * @param count 星星的索引
     */
    private void starSelect(int count) {
        for (int i = 0; i <= count; i++) {
            if (i <= count) {
                final IconTextView star = STAR_LIST.get(i);
                star.setText(ICON_SELECTED);
                star.setTextColor(Color.RED);
                star.setTag(R.id.start_is_select, true);
            }
        }
    }

    /**
     * 将未点击以后的星星，还原没有点击之后的星星
     *
     * @param count 星星的索引
     */
    private void starUnSelect(int count) {
        for (int i = 0; i < STAR_TOTAL_COUNT; i++) {
            if (i >= count) {
                final IconTextView star = STAR_LIST.get(i);
                star.setText(ICON_UN_SELECTED);
                star.setTextColor(Color.GRAY);
                star.setTag(R.id.start_is_select, false);
            }
        }
    }

    @Override
    public void onClick(View v) {
        final IconTextView star = (IconTextView) v;
        //获取星星的索引
        final int starId = (int) star.getTag(R.id.start_count);
        //获取星星是否选中的状态
        final boolean isSelect = (boolean) star.getTag(R.id.start_is_select);

        if (!isSelect) {
            starSelect(starId);
        } else {
            starUnSelect(starId);
        }
    }
}
