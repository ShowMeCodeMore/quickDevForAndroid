package com.sa.all_cui.mix_ec.main.sort;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.sa.all_cui.mix_core.delegate.bottom.BottomItemDelegate;
import com.sa.all_cui.mix_ec.R;
import com.sa.all_cui.mix_ec.main.sort.content.SortContentDelegate;
import com.sa.all_cui.mix_ec.main.sort.list.SortListDelegate;


/**
 * Created by all-cui on 2017/8/21.
 */

public class SortDelegate extends BottomItemDelegate {

    @Override
    public Object setLayout() {
        return R.layout.delegate_sort;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View view) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        final SortListDelegate listDelegate = SortListDelegate.create();
        getSupportDelegate().loadRootFragment(R.id.cfl_sort_list_container,listDelegate);
        getSupportDelegate().loadRootFragment(R.id.cfl_sort_content_container, SortContentDelegate.newInstance(1));
    }
}
