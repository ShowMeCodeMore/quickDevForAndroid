package com.sa.all_cui.mix_ec.main.cart;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ViewStubCompat;
import android.view.View;
import android.widget.Toast;

import com.joanzapata.iconify.widget.IconTextView;
import com.sa.all_cui.mix_core.delegate.bottom.BottomItemDelegate;
import com.sa.all_cui.mix_core.net.RestClient;
import com.sa.all_cui.mix_core.net.callback.ISuccess;
import com.sa.all_cui.mix_core.utils.log.DoggerLog;
import com.sa.all_cui.mix_ec.R;
import com.sa.all_cui.mix_ec.R2;
import com.sa.all_cui.mix_ec.pay.FastPay;
import com.sa.all_cui.mix_ui.recycler.MultipleItemEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by all-cui on 2017/8/25.
 */

public class ShopCartDelegate extends BottomItemDelegate
        implements ISuccess, ICartItemListener {

    @BindView(R2.id.rv_shop_cart)
    RecyclerView mRecyclerView = null;
    @BindView(R2.id.itv_shop_select_all)
    IconTextView mItvSelectAll = null;
    @BindView(R2.id.stub_empty)
    ViewStubCompat mStubEmpty = null;
    @BindView(R2.id.tv_shop_cart_total_price)
    AppCompatTextView mTotalPrice = null;
    private ShopCartRecyclerAdapter mAdapter = null;
    private int mCurrentCount = 0;
    private int mTotalCount = 0;

    @OnClick(R2.id.itv_shop_select_all)
    void onSelectAllClick() {
        final int tag = (int) mItvSelectAll.getTag();
        if (tag == 0) {
            mItvSelectAll.
                    setTextColor(ContextCompat.getColor(getContext(), R.color.app_main));
            mItvSelectAll.setTag(1);
            mAdapter.setIsSelectedAll(true);
            //更新RecylerView选中的状态 通过修改保存到bean中的标记来实现的
            mAdapter.notifyItemRangeChanged(0, mAdapter.getItemCount());
        } else {
            mItvSelectAll.setTextColor(Color.GRAY);
            mItvSelectAll.setTag(0);
            mAdapter.setIsSelectedAll(false);
            mAdapter.notifyItemRangeChanged(0, mAdapter.getItemCount());
        }
    }

    @OnClick(R2.id.tv_shop_cart_clear)
    void onClickClear() {
        mAdapter.getData().clear();
        mAdapter.notifyDataSetChanged();
        checkItemCount();
    }

    @OnClick(R2.id.tv_shop_cart_remove_selected)
    void onClickRemoveSelected() {
        final List<MultipleItemEntity> data = mAdapter.getData();
        final int size = data.size();

        //要删除item的集合
        final List<MultipleItemEntity> dataDeletes = new ArrayList<>();
        for (MultipleItemEntity entity : data) {
            final boolean isSelected = entity.getField(ShopCartItemFields.IS_SELECTED);
            if (isSelected) {
                dataDeletes.add(entity);
            }
        }

        for (MultipleItemEntity entity : dataDeletes) {
            //int removePosition;
            int entityPosition = entity.getField(ShopCartItemFields.POSITION);
            if (entityPosition < size) {
                mAdapter.remove(entityPosition);
            }
        }
    }

    @OnClick(R2.id.tv_shop_cart_pay)
    void onClickPay() {
        FastPay.create(this).beginPayDialog();
    }


    //创建订单-》发送到自己的服务器，服务器在发送请求给第三方支付服务器进行一系列的处理，然后通知
    //自己的服务器可以进行支付，客户端调用第三方支付sdk进行支付-》显示支付的状态
    private void createOrder() {
        final String oderUrl = "生成订单的api";
        //传入参数的列表
        WeakHashMap<String, Object> params = new WeakHashMap<>();
        params.put("userId", "1122122");
        params.put("count", "0.01");
        params.put("comment", "测试支付");
        params.put("type", 1);
        params.put("ordertype", 0);
        params.put("isanonymous", true);//是否是匿名
        params.put("followeduser", 0);
        RestClient.builder()
                .url(oderUrl)
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        //开始支付
                    }
                })
                .build()
                .post();
    }

    //检查一下数据如果当前的数据为空的话就显示空数据提示
    @SuppressWarnings("RestrictedApi")
    private void checkItemCount() {
        final int count = mAdapter.getItemCount();
        if (count == 0) {
            AppCompatTextView view = (AppCompatTextView) mStubEmpty.inflate();
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "购物", Toast.LENGTH_SHORT).show();
                }
            });
            mRecyclerView.setVisibility(View.GONE);
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_shop_cart;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View view) {
        mItvSelectAll.setTag(0);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        RestClient.builder().
                url("shop_cart.php")
                .loader(getContext())
                .success(this)
                .build()
                .get();
    }

    @Override
    public void onSuccess(String response) {
        final ArrayList<MultipleItemEntity> list = new ShopCartDataConvert()
                .setJsonData(response)
                .convert();
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mAdapter = new ShopCartRecyclerAdapter(list);
        mAdapter.setCartItemListener(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        showTotalPrice();
        checkItemCount();
    }

    private void showTotalPrice() {
        final double totalPrice = mAdapter.getTotalPrice();
        DoggerLog.e(String.valueOf(totalPrice));

        mTotalPrice.setText(String.valueOf(totalPrice));
    }

    @Override
    public void itemClick(double itemTotalPrice) {
        showTotalPrice();
    }

}
