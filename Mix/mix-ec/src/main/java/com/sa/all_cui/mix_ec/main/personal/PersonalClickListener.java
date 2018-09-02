package com.sa.all_cui.mix_ec.main.personal;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.sa.all_cui.mix_core.delegate.DoggerDelegate;
import com.sa.all_cui.mix_ec.main.personal.list.ListBean;

/**
 * Created by all-cui on 2017/8/29.
 */

public class PersonalClickListener extends SimpleClickListener {

    private final DoggerDelegate DELEGATE;

    public PersonalClickListener(DoggerDelegate delegate) {
        this.DELEGATE = delegate;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final ListBean bean = (ListBean) baseQuickAdapter.getData().get(position);
        /*final int id = bean.getId();
        switch (id) {
            case 1:

                break;
            case 2:
                DELEGATE.getParentDelegate().getSupportDelegate().
                        start(bean.getDelegate());
                break;
            default:
                break;
        }*/
        DELEGATE.getParentDelegate().getSupportDelegate().
                start(bean.getDelegate());
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
