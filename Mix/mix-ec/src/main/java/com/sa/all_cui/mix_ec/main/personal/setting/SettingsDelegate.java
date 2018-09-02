package com.sa.all_cui.mix_ec.main.personal.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sa.all_cui.mix_core.delegate.DoggerDelegate;
import com.sa.all_cui.mix_ec.R;
import com.sa.all_cui.mix_ec.R2;
import com.sa.all_cui.mix_ec.main.personal.address.AddressDelegate;
import com.sa.all_cui.mix_ec.main.personal.list.ListAdapter;
import com.sa.all_cui.mix_ec.main.personal.list.ListBean;
import com.sa.all_cui.mix_ec.main.personal.list.ListItemType;
import com.sa.all_cui.mix_ui.recycler.BaseDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by all-cui on 2017/8/30.
 */

public class SettingsDelegate extends DoggerDelegate {

    @BindView(R2.id.rv_settings_list)
    RecyclerView mRecylerView = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_settings;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View view) {
        final ListBean push = new ListBean.Builder()
                .setItemType(ListItemType.ARROW_ITEM_SWITCH)
                .setId(1)
                .setText("收获地址")
                .create();
        final ListBean about = new ListBean.Builder()
                .setItemType(ListItemType.ARROW_ITEM_NOMAL)
                .setId(2)
                .setDelegate(new AddressDelegate())
                .setText("关于")
                .create();
        final List<ListBean> data = new ArrayList<>();
        data.add(push);
        data.add(about);
        final ListAdapter adapter = new ListAdapter(data);
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecylerView.setLayoutManager(manager);
        mRecylerView.setAdapter(adapter);
        //设置分割线
        mRecylerView.addItemDecoration(
                BaseDecoration.create(ContextCompat.getColor(getContext(),
                        R.color.app_divider_gray),1));
        mRecylerView.addOnItemTouchListener(new SettingClickListener(this));
    }

}
