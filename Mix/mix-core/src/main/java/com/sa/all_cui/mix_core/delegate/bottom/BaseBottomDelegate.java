package com.sa.all_cui.mix_core.delegate.bottom;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.joanzapata.iconify.widget.IconTextView;
import com.sa.all_cui.mix_core.R;
import com.sa.all_cui.mix_core.R2;
import com.sa.all_cui.mix_core.delegate.DoggerDelegate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import me.yokeyword.fragmentation.ISupportFragment;

/**
 * Created by all-cui on 2017/8/21.
 */

public abstract class BaseBottomDelegate extends DoggerDelegate implements View.OnClickListener {

    private final ArrayList<BottomItemDelegate> ITEM_DELEGATE = new ArrayList<>();
    private final ArrayList<BottomTabBean> TAB_BEAN = new ArrayList<>();
    private final LinkedHashMap<BottomTabBean, BottomItemDelegate> ITEMS = new LinkedHashMap<>();
    private int mCurrentDelegate = 0;
    private int mIndexDelegate = 0;
    private int mClickedColor = Color.RED;

    @BindView(R2.id.ll_bottom_bar_container)
    LinearLayoutCompat mBttomBar = null;

    public abstract LinkedHashMap<BottomTabBean, BottomItemDelegate> setItems(ItemBuilder builder);

    @Override
    public Object setLayout() {
        return R.layout.bottom_layout;
    }

    public abstract int setIndexDelegate();

    @ColorInt
    public abstract int setClickedColor();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIndexDelegate = setIndexDelegate();
        if (setClickedColor() != 0) {
            mClickedColor = setClickedColor();
        }

        final ItemBuilder builder = ItemBuilder.builder();
        final LinkedHashMap<BottomTabBean, BottomItemDelegate> items = setItems(builder);
        ITEMS.putAll(items);
        for (Map.Entry<BottomTabBean, BottomItemDelegate> item : ITEMS.entrySet()) {
            final BottomTabBean tabBean = item.getKey();
            final BottomItemDelegate delegate = item.getValue();
            TAB_BEAN.add(tabBean);
            ITEM_DELEGATE.add(delegate);
        }
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View view) {
        final int size = ITEMS.size();//防止每次循环都调用size方法，这样的话就执行一次
        for (int i = 0; i < size; i++) {
            LayoutInflater.from(getContext()).inflate(R.layout.bottom_item_text_icon_layout, mBttomBar);
            //获取每一个item的布局
            final RelativeLayout item = (RelativeLayout) mBttomBar.getChildAt(i);
            //设置每个item的点击事件
            item.setTag(i);
            item.setOnClickListener(this);
            final IconTextView itemIcon = (IconTextView) item.getChildAt(0);
            final AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1);
            final BottomTabBean beans = TAB_BEAN.get(i);
            //初始化数据
            itemIcon.setText(beans.getIcon());
            itemTitle.setText(beans.getTitle());
            //默认选中颜色显示和别的item颜色不一样
            if (i == mIndexDelegate) {
                itemIcon.setTextColor(mClickedColor);
                itemTitle.setTextColor(mClickedColor);
            }
        }

        final ISupportFragment[] delegateArray = ITEM_DELEGATE.toArray(new ISupportFragment[size]);
        getSupportDelegate().loadMultipleRootFragment(R.id.fl_bottom_container, mIndexDelegate, delegateArray);

    }

    private void resetColor() {
        final int count = mBttomBar.getChildCount();
        for (int i = 0; i < count; i++) {
            final RelativeLayout item = (RelativeLayout) mBttomBar.getChildAt(i);
            setBottomItemColor(item, Color.GRAY);
        }
    }

    @Override
    public void onClick(View v) {
        final int tag = (int) v.getTag();
        final RelativeLayout item = (RelativeLayout) v;
        resetColor();
        setBottomItemColor(item, mClickedColor);
        //一定要注意先后顺序
        getSupportDelegate().showHideFragment(ITEM_DELEGATE.get(tag), ITEM_DELEGATE.get(mCurrentDelegate));
        mCurrentDelegate = tag;
    }

    private void setBottomItemColor(RelativeLayout item, int color) {
        final IconTextView itemIcon = (IconTextView) item.getChildAt(0);
        itemIcon.setTextColor(color);
        final AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1);
        itemTitle.setTextColor(color);
    }

}
