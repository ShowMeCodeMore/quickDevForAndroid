package com.sa.all_cui.mix_ec.main.sort.list;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.sa.all_cui.mix_core.delegate.DoggerDelegate;
import com.sa.all_cui.mix_ec.R;
import com.sa.all_cui.mix_ec.main.sort.SortDelegate;
import com.sa.all_cui.mix_ec.main.sort.content.SortContentDelegate;
import com.sa.all_cui.mix_ui.recycler.ItemType;
import com.sa.all_cui.mix_ui.recycler.MultipleFields;
import com.sa.all_cui.mix_ui.recycler.MultipleItemEntity;
import com.sa.all_cui.mix_ui.recycler.MultipleViewHolder;
import com.sa.all_cui.mix_ui.recycler.MultipleRecylerAdapter;

import java.util.List;

import me.yokeyword.fragmentation.SupportHelper;

/**
 * Created by all-cui on 2017/8/24.
 */

public class SortRecyclerAdapter extends MultipleRecylerAdapter {
    private final SortDelegate DELEGATE;
    private int mPrePosition = 0;

    protected SortRecyclerAdapter(List<MultipleItemEntity> data, SortDelegate delegate) {
        super(data);
        this.DELEGATE = delegate;
        //添加垂直布局
        addItemType(ItemType.VERTICAL_LIST, R.layout.item_sort_menu_list);
    }

    @Override
    protected void convert(final MultipleViewHolder holder, final MultipleItemEntity item) {
        super.convert(holder, item);
        switch (holder.getItemViewType()) {
            case ItemType.VERTICAL_LIST:
                final String nameStr = item.getField(MultipleFields.TEXT);
                final boolean isClicked = item.getField(MultipleFields.TAG);
                final AppCompatTextView nameTv = holder.getView(R.id.tv_sort_list_item);
                final View line = holder.getView(R.id.line);
                final View itemView = holder.itemView;
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final int currentPos = holder.getAdapterPosition();
                        if (mPrePosition != currentPos) {
                            //设置上一个选中item的状态为未选中状态
                            getData().get(mPrePosition).setField(MultipleFields.TAG, false);
                            notifyItemChanged(mPrePosition);

                            //更新选中的状态
                            item.setField(MultipleFields.TAG, true);
                            notifyItemChanged(currentPos);
                            mPrePosition = currentPos;

                            final int contentId = getData().get(currentPos).getField(MultipleFields.ID);
                            showContent(contentId);
                        }
                    }
                });

                if (!isClicked) {
                    line.setVisibility(View.INVISIBLE);
                    nameTv.setTextColor(ContextCompat.getColor(mContext, R.color.we_chat_black));
                    itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.item_background));
                } else {
                    line.setVisibility(View.VISIBLE);
                    nameTv.setTextColor(ContextCompat.getColor(mContext, R.color.app_main));
                    line.setBackgroundColor(ContextCompat.getColor(mContext, R.color.app_main));
                    itemView.setBackgroundColor(Color.WHITE);
                }
                holder.setText(R.id.tv_sort_list_item, nameStr);
                break;
            default:
                break;
        }
    }

    private void showContent(int contentId){
        final SortContentDelegate delegate = SortContentDelegate.newInstance(contentId);
        switchContent(delegate);
    }

    private void switchContent(SortContentDelegate sortContentDelegate){
        //TODO 不明白这句话的意思
        final DoggerDelegate contentDelegate = SupportHelper.findFragment(DELEGATE.getChildFragmentManager(),SortContentDelegate.class);

        if (contentDelegate!=null){
            contentDelegate.getSupportDelegate().replaceFragment(sortContentDelegate,false);
        }
    }
}
