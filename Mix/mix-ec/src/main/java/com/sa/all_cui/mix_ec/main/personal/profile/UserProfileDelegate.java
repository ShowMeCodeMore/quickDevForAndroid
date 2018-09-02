package com.sa.all_cui.mix_ec.main.personal.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sa.all_cui.mix_core.delegate.DoggerDelegate;
import com.sa.all_cui.mix_ec.R;
import com.sa.all_cui.mix_ec.R2;
import com.sa.all_cui.mix_ec.main.personal.list.ListAdapter;
import com.sa.all_cui.mix_ec.main.personal.list.ListBean;
import com.sa.all_cui.mix_ec.main.personal.list.ListItemType;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by all-cui on 2017/8/28.
 */

public class UserProfileDelegate extends DoggerDelegate {

    @BindView(R2.id.rv_user_profile)
    RecyclerView mRecylerView = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_user_profile;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View view) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        final ListBean avator = new ListBean.Builder()
                .setItemType(ListItemType.ARROW_ITEM_AVATOR)
                .setId(1)
                .setImageUrl("http://i9.qhimg.com/t017d891ca365ef60b5.jpg")
                .create();
        final ListBean name = new ListBean.Builder()
                .setItemType(ListItemType.ARROW_ITEM_NOMAL)
                .setId(2)
                .setText(getString(R.string.userprofile_name))
                .setValue(getString(R.string.setting_not).concat(getString(R.string.userprofile_name)))
                .setDelegate(new UserNameDelegate())
                .create();
        final ListBean gender = new ListBean.Builder()
                .setItemType(ListItemType.ARROW_ITEM_NOMAL)
                .setId(3)
                .setText(getString(R.string.userprofile_gender))
                .setValue(getString(R.string.setting_not).concat(getString(R.string.userprofile_gender)))
                .create();
        final ListBean birth = new ListBean.Builder()
                .setItemType(ListItemType.ARROW_ITEM_NOMAL)
                .setId(4)
                .setText(getString(R.string.userprofile_birth))
                .setValue(getString(R.string.setting_not).concat(getString(R.string.userprofile_birth)))
                .create();


        final List<ListBean> data = new ArrayList<>();
        data.add(avator);
        data.add(name);
        data.add(gender);
        data.add(birth);
        final ListAdapter adapter = new ListAdapter(data);
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecylerView.setLayoutManager(manager);
        mRecylerView.setAdapter(adapter);

        mRecylerView.addOnItemTouchListener(new UserProfileClickListener(this));
    }


}
