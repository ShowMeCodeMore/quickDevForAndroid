package com.sa.all_cui.mix_ec.main.personal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sa.all_cui.mix_core.delegate.bottom.BottomItemDelegate;
import com.sa.all_cui.mix_ec.R;
import com.sa.all_cui.mix_ec.R2;
import com.sa.all_cui.mix_ec.main.personal.address.AddressDelegate;
import com.sa.all_cui.mix_ec.main.personal.list.ListAdapter;
import com.sa.all_cui.mix_ec.main.personal.list.ListBean;
import com.sa.all_cui.mix_ec.main.personal.list.ListItemType;
import com.sa.all_cui.mix_ec.main.personal.order.OrderListDelegate;
import com.sa.all_cui.mix_ec.main.personal.profile.UserProfileDelegate;
import com.sa.all_cui.mix_ec.main.personal.setting.SettingsDelegate;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by all-cui on 2017/8/28.
 */

public class PersonDelegate extends BottomItemDelegate {

    public static String PARAMS = "PARAMS";

    @BindView(R2.id.rv_personal_setting)
    RecyclerView mRecylerView = null;
    private Bundle mBundle = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBundle = new Bundle();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_personal;
    }

    @OnClick(R2.id.img_user_avatar)
    void onClickToUserProfile() {
        getParentDelegate().getSupportDelegate().start(new UserProfileDelegate());
    }

    //跳转到订单列表
    @OnClick(R2.id.tv_all_order)
    void onClickToOrderList() {
        showOrderListByType(1);
    }

    private void showOrderListByType(int type) {
        final OrderListDelegate delegate = new OrderListDelegate();
        mBundle.putInt(PARAMS, type);
        delegate.setArguments(mBundle);
        //防止底部导航无法隐藏
        getParentDelegate().getSupportDelegate().start(delegate);
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View view) {
        final ListBean address = new ListBean.Builder()
                .setItemType(ListItemType.ARROW_ITEM_NOMAL)
                .setId(1)
                .setDelegate(new AddressDelegate())
                .setText("收获地址")
                .create();
        final ListBean system = new ListBean.Builder()
                .setItemType(ListItemType.ARROW_ITEM_NOMAL)
                .setId(2)
                .setDelegate(new SettingsDelegate())
                .setText("系统设置")
                .create();
        final List<ListBean> data = new ArrayList<>();
        data.add(address);
        data.add(system);
        final ListAdapter adapter = new ListAdapter(data);
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecylerView.setLayoutManager(manager);
        mRecylerView.setAdapter(adapter);
        mRecylerView.addOnItemTouchListener(new PersonalClickListener(this));
    }
}
