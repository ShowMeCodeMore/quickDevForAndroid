package com.sa.all_cui.mix_ec.main.personal.list;

import android.support.v7.widget.SwitchCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sa.all_cui.mix_ec.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by all-cui on 2017/8/28.
 */

public class ListAdapter extends BaseMultiItemQuickAdapter<ListBean, BaseViewHolder> {
    private static RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .dontAnimate();

    public ListAdapter(List<ListBean> data) {
        super(data);
        addItemType(ListItemType.ARROW_ITEM_NOMAL, R.layout.arrow_item_layout);
        addItemType(ListItemType.ARROW_ITEM_AVATOR, R.layout.arrow_item_avator);
        addItemType(ListItemType.ARROW_ITEM_SWITCH, R.layout.arrow_switch_layout);
    }

    @Override
    protected void convert(BaseViewHolder holder, final ListBean item) {
        switch (holder.getItemViewType()) {
            case ListItemType.ARROW_ITEM_NOMAL:
                holder.setText(R.id.tv_arrow_text, item.getText());
                holder.setText(R.id.tv_arrow_value, item.getValue());
                break;
            case ListItemType.ARROW_ITEM_AVATOR:
                Glide.with(mContext)
                        .load(item.getImageUrl())
                        .apply(OPTIONS)
                        .into((CircleImageView) holder.getView(R.id.img_arrow_avatar));
                break;
            case ListItemType.ARROW_ITEM_SWITCH:
                holder.setText(R.id.tv_arrow_switch_text,item.getText());
                final SwitchCompat pushToggle = holder.getView(R.id.list_item_switch);
                pushToggle.setChecked(true);
                break;
            default:
                break;
        }
    }
}
