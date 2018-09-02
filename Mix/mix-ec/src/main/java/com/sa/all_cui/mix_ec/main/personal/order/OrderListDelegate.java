package com.sa.all_cui.mix_ec.main.personal.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sa.all_cui.mix_core.delegate.DoggerDelegate;
import com.sa.all_cui.mix_core.net.RestClient;
import com.sa.all_cui.mix_core.net.callback.ISuccess;
import com.sa.all_cui.mix_core.utils.log.DoggerLog;
import com.sa.all_cui.mix_ec.R;
import com.sa.all_cui.mix_ec.R2;
import com.sa.all_cui.mix_ec.main.personal.PersonDelegate;
import com.sa.all_cui.mix_ui.recycler.BaseDecoration;
import com.sa.all_cui.mix_ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by all-cui on 2017/8/28.
 */

public class OrderListDelegate extends DoggerDelegate {

    @BindView(R2.id.rv_order_list)
    RecyclerView mRecylerView = null;
    private int mType = 0;

    @Override
    public Object setLayout() {
        return R.layout.delegate_order_list;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mType = getArguments().getInt(PersonDelegate.PARAMS);
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View view) {
        DoggerLog.i("订单加载");
        RestClient.builder()
                .url("order_list.php")
                .params("type", mType)
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        //DoggerLog.i(response);
                        showOrderList(response);
                    }
                })
                .build()
                .get();
    }


    //展示订单
    private void showOrderList(String response) {
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        final ArrayList<MultipleItemEntity> dataList = new OrderListDataConveter()
                .setJsonData(response)
                .convert();
        final OrderListAdapter adapter = new OrderListAdapter(dataList);
        mRecylerView.setLayoutManager(manager);
        mRecylerView.setAdapter(adapter);
        //设置分割线
        mRecylerView.addItemDecoration(
                BaseDecoration.create(ContextCompat.getColor(getContext(),
                        R.color.app_divider_gray), 1));
        mRecylerView.addOnItemTouchListener(new OrderListClickListener(this));
    }
}
