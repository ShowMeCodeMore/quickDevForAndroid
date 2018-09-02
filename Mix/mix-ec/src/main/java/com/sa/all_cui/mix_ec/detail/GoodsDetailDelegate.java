package com.sa.all_cui.mix_ec.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.sa.all_cui.mix_core.delegate.DoggerDelegate;
import com.sa.all_cui.mix_ec.R;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Created by all-cui on 2017/8/23.
 */

public class GoodsDetailDelegate extends DoggerDelegate {

    public static GoodsDetailDelegate create(){
        return new GoodsDetailDelegate();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_home_list;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View view) {

    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }
}
