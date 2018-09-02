package com.sa.all_cui.mix_ec.main.index;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.LogUtils;
import com.sa.all_cui.mix_core.app.MIX;
import com.sa.all_cui.mix_ec.R;

import java.util.List;


/**
 * Created by all-cui on 2017/10/13.
 * FragmentPagerAdapter适用于页面少，固定界面占用比较少的内存
 */

public class IndexViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mDatas;
    private final String[] mTitles = {MIX.getContext().getString(R.string.home_table_refernce)
            , MIX.getContext().getString(R.string.home_table_android)
            , MIX.getContext().getString(R.string.home_table_head)
            , MIX.getContext().getString(R.string.home_table_monkey)
            , MIX.getContext().getString(R.string.home_table_news)};

    public IndexViewPagerAdapter(FragmentManager fm, List<Fragment> data) {
        super(fm);
        if (EmptyUtils.isEmpty(data)) {
            throw new NullPointerException("IndexViewPagerAdapter datas must be null");
        }
        this.mDatas = data;
    }

    @Override
    public Fragment getItem(int position) {
        LogUtils.i(position);
        return mDatas.get(position);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
