package com.sa.all_cui.mix_ec.main.cart;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.joanzapata.iconify.widget.IconTextView;
import com.sa.all_cui.mix_core.net.RestClient;
import com.sa.all_cui.mix_core.net.callback.ISuccess;
import com.sa.all_cui.mix_ec.R;
import com.sa.all_cui.mix_ui.recycler.MultipleFields;
import com.sa.all_cui.mix_ui.recycler.MultipleItemEntity;
import com.sa.all_cui.mix_ui.recycler.MultipleViewHolder;
import com.sa.all_cui.mix_ui.recycler.MultipleRecylerAdapter;

import java.util.List;

/**
 * Created by all-cui on 2017/8/25.
 */

public class ShopCartRecyclerAdapter extends MultipleRecylerAdapter {

    private boolean mIsSelectedAll = false;
    private ICartItemListener mCartItemListener = null;
    private double mTotalPrice = 0.00;//购物车合计总价钱

    private static RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .dontAnimate();

    protected ShopCartRecyclerAdapter(List<MultipleItemEntity> data) {
        super(data);
        //初始化总价钱
        for (MultipleItemEntity entity : data) {
            final int count = entity.getField(ShopCartItemFields.COUNT);
            final double price = entity.getField(ShopCartItemFields.PRICE);
            mTotalPrice = mTotalPrice + price * count;
        }

        addItemType(ShopCartItemType.SHOT_CART_TIME, R.layout.item_shop_cart);
    }


    public double getTotalPrice() {
        return mTotalPrice;
    }

    public void setCartItemListener(ICartItemListener cartItemListener) {
        this.mCartItemListener = cartItemListener;
    }

    //设置是否是全选
    public void setIsSelectedAll(boolean isSelectedAll) {
        this.mIsSelectedAll = isSelectedAll;
    }

    @Override
    protected void convert(MultipleViewHolder holder, final MultipleItemEntity item) {
        super.convert(holder, item);
        switch (holder.getItemViewType()) {
            case ShopCartItemType.SHOT_CART_TIME:
                //取出返回所有的值
                final String thumb = item.getField(MultipleFields.IMAGE_URL);
                final int id = item.getField(MultipleFields.ID);
                final String desc = item.getField(ShopCartItemFields.DESC);
                final String title = item.getField(ShopCartItemFields.TITLE);
                final int count = item.getField(ShopCartItemFields.COUNT);
                final double price = item.getField(ShopCartItemFields.PRICE);
                //取出所有的控件
                final AppCompatImageView thumbIv = holder.getView(R.id.iv_shop_item_thumb);
                final IconTextView selectedIcon = holder.getView(R.id.itv_shop_item_check);
                final AppCompatTextView titleTv = holder.getView(R.id.tv_shop_item_title);
                final AppCompatTextView descTv = holder.getView(R.id.tv_shop_item_desc);
                final AppCompatTextView priceTv = holder.getView(R.id.tv_shop_item_price);
                final IconTextView minusIcon = holder.getView(R.id.itv_shop_item_minus);
                final AppCompatTextView countTv = holder.getView(R.id.tv_shop_item_count);
                final IconTextView plusIcon = holder.getView(R.id.itv_shop_item_plus);
                //赋值
                titleTv.setText(title);
                descTv.setText(desc);
                countTv.setText(String.valueOf(count));
                priceTv.setText(String.valueOf(price));
                Glide.with(mContext)
                        .load(thumb)
                        .apply(OPTIONS)
                        .into(thumbIv);
                //在Checker渲染之前改变对应是否选中与否
                item.setField(ShopCartItemFields.IS_SELECTED, mIsSelectedAll);
                final boolean isSelected = item.getField(ShopCartItemFields.IS_SELECTED);

                //设置是否选中的状态
                if (isSelected) {
                    selectedIcon.setTextColor(ContextCompat.getColor(mContext, R.color.app_main));
                } else {
                    selectedIcon.setTextColor(Color.GRAY);
                }
                //对应这每一个item创建一个点击事件监听
                selectedIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final boolean currentSelected = item.getField(ShopCartItemFields.IS_SELECTED);
                        if (currentSelected) {
                            selectedIcon.setTextColor(Color.GRAY);
                            item.setField(ShopCartItemFields.IS_SELECTED, false);
                        } else {
                            selectedIcon.setTextColor(ContextCompat.getColor(mContext, R.color.app_main));
                            item.setField(ShopCartItemFields.IS_SELECTED, true);
                        }
                    }
                });
                minusGoodsCount(item, minusIcon, countTv, price);
                plusGoodsCount(item, countTv, plusIcon, price);
                break;
            default:
                break;
        }
    }

    /**
     * 实现增加商品的数量
     *
     * @param item
     * @param countTv
     * @param plusIcon
     */
    private void plusGoodsCount(final MultipleItemEntity item, final AppCompatTextView countTv, IconTextView plusIcon
            , final double price) {
        plusIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int currentCount = item.getField(ShopCartItemFields.COUNT);
                final int showCount = Integer.parseInt(countTv.getText().toString());
                if (showCount < 10) {
                    RestClient.builder()
                            .url("shop_cart_count.php")
                            .loader(mContext)
                            .params("count", currentCount)
                            .success(new ISuccess() {
                                @Override
                                public void onSuccess(String response) {
                                    int plusCount = showCount;
                                    plusCount++;
                                    countTv.setText(String.valueOf(plusCount));
                                    item.setField(ShopCartItemFields.COUNT, plusCount);
                                    if (mCartItemListener != null) {
                                        mTotalPrice = mTotalPrice + price;
                                        final double itemPrice = plusCount * price;
                                        mCartItemListener.itemClick(itemPrice);
                                    }
                                }
                            })
                            .build()
                            .post();
                } else {
                    Toast.makeText(mContext, mContext.getString(R.string.shop_cart_plus_tip), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 实现商品的逻辑
     * 边刷新界面上显示的商品个数，边同步到数据服务器
     *
     * @param item      存储数据的实例
     * @param minusIcon
     * @param countTv
     */
    private void minusGoodsCount(final MultipleItemEntity item, IconTextView minusIcon, final AppCompatTextView countTv,
                                 final double price) {
        minusIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int currentCount = item.getField(ShopCartItemFields.COUNT);
                final String showCount = countTv.getText().toString();
                if (Integer.parseInt(showCount) > 1) {
                    RestClient.builder()
                            .url("shop_cart_count.php")
                            .params("count", currentCount)
                            .loader(mContext)
                            .success(new ISuccess() {
                                @Override
                                public void onSuccess(String response) {
                                    int minusCount = Integer.parseInt(showCount);
                                    minusCount--;
                                    countTv.setText(String.valueOf(minusCount));
                                    item.setField(ShopCartItemFields.COUNT, minusCount);

                                    if (mCartItemListener != null) {
                                        mTotalPrice = mTotalPrice - price;
                                        final double itemPrice = minusCount * price;
                                        mCartItemListener.itemClick(itemPrice);
                                    }
                                }
                            })
                            .build()
                            .post();
                } else {
                    Toast.makeText(mContext, mContext.getString(R.string.shop_cart_minus_tip), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
