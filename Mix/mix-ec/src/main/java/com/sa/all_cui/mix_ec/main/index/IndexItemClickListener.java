package com.sa.all_cui.mix_ec.main.index;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.sa.all_cui.mix_core.delegate.DoggerDelegate;
import com.sa.all_cui.mix_ec.detail.GoodsDetailDelegate;

/**
 * Created by all-cui on 2017/8/23.
 */

public class IndexItemClickListener extends SimpleClickListener {

    private final DoggerDelegate DELEGATE;

    private IndexItemClickListener(DoggerDelegate delegate) {
        this.DELEGATE = delegate;
    }

    public static IndexItemClickListener create(DoggerDelegate delegate){
        return new IndexItemClickListener(delegate);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final GoodsDetailDelegate delegate = GoodsDetailDelegate.create();
        DELEGATE.getSupportDelegate().start(delegate);
    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
