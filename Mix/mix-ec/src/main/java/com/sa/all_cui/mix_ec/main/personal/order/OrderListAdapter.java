package com.sa.all_cui.mix_ec.main.personal.order;

import android.annotation.SuppressLint;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.sa.all_cui.mix_ec.R;
import com.sa.all_cui.mix_ui.recycler.MultipleFields;
import com.sa.all_cui.mix_ui.recycler.MultipleItemEntity;
import com.sa.all_cui.mix_ui.recycler.MultipleViewHolder;
import com.sa.all_cui.mix_ui.recycler.MultipleRecylerAdapter;

import java.util.List;

/**
 * Created by all-cui on 2017/8/28.
 */

public class OrderListAdapter extends MultipleRecylerAdapter {
    private RequestOptions OPITONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .dontAnimate();

    public OrderListAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(OrderListItemType.ITEM_NORMAL, R.layout.item_order_list);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity item) {
        super.convert(holder, item);

        switch (holder.getItemViewType()) {
            case OrderListItemType.ITEM_NORMAL:
                final AppCompatImageView thumbIv = holder.getView(R.id.image_order_list);
                final AppCompatTextView titleTv = holder.getView(R.id.tv_order_list_title);
                final AppCompatTextView priceTv = holder.getView(R.id.tv_order_list_price);
                final AppCompatTextView timeTv = holder.getView(R.id.tv_order_list_time);

                //取值
                final String title = item.getField(OrderListItemFields.TITLE);
                final double price = item.getField(OrderListItemFields.PRICE);
                final String time = item.getField(OrderListItemFields.TIME);
                final String thumb = item.getField(MultipleFields.IMAGE_URL);

                titleTv.setText(title);
                priceTv.setText("价格:" + price);
                timeTv.setText(time);
                Glide.with(mContext)
                        .load(thumb)
                        .apply(OPITONS)
                        .into(thumbIv);
                break;
            default:
                break;
        }
    }
}
