package com.sa.all_cui.mix_ec.main.sort.content;

import android.support.v7.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sa.all_cui.mix_ec.R;

import java.util.List;

/**
 * Created by all-cui on 2017/8/24.
 */

public class SectionAdapter extends BaseSectionQuickAdapter<SectionBean,BaseViewHolder> {

    private static RequestOptions REQUEST_OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .dontAnimate();

    public SectionAdapter(int layoutResId, int sectionHeadResId, List<SectionBean> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder holder, SectionBean item) {
        holder.setText(R.id.tv_section_header,item.header);
        holder.setVisible(R.id.tv_section_more,item.isMore());
        holder.addOnClickListener(R.id.tv_section_more);//添加到点击事件查看更多
    }

    @Override
    protected void convert(BaseViewHolder holder, SectionBean item) {
        //item.sectionbean 返回SectionBeanItem类型
        final String name = item.t.getGoodsName();
        final String thumb = item.t.getGoodsThumb();
        final int id = item.t.getGoodsId();
        final SectionContentItemEntity entity = item.t;

        holder.setText(R.id.tv_section_content,name);
        final AppCompatImageView thumbIv = holder.getView(R.id.iv_section_content);

        Glide.with(mContext).load(thumb)
                .apply(REQUEST_OPTIONS)
                .into(thumbIv);
    }
}
