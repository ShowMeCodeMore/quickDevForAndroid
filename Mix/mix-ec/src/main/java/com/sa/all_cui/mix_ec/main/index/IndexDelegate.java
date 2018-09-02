package com.sa.all_cui.mix_ec.main.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.sa.all_cui.mix_core.delegate.bottom.BottomItemDelegate;
import com.sa.all_cui.mix_ec.R;
import com.sa.all_cui.mix_ec.R2;
import com.sa.all_cui.mix_ec.main.index.android.AndroidDelegate;
import com.sa.all_cui.mix_ec.main.index.recommend.RecommendDelegate;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by all-cui on 2017/8/21.
 */

public class IndexDelegate extends BottomItemDelegate {

    @BindView(R2.id.tbl_index)
    TabLayout mTabLayout = null;
    @BindView(R2.id.vp_index_content)
    ViewPager mViewPager = null;

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View view) {
        initViewPager();
    }

    private void initViewPager() {
        final List<Fragment> fragments = new ArrayList<>();
        fragments.add(new RecommendDelegate());
        fragments.add(new AndroidDelegate());
        fragments.add(new RecommendDelegate());
        fragments.add(new RecommendDelegate());
        fragments.add(new RecommendDelegate());
        final IndexViewPagerAdapter adapter = new IndexViewPagerAdapter(getChildFragmentManager(), fragments);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(1);
        //将tableLayout与ViewPagerAdapter关联起来
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_idex;
    }


}

