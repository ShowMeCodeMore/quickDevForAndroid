package com.sa.all_cui.mix_ui.recycler;

import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sa.all_cui.mix_ui.R;
import com.sa.all_cui.mix_ui.banner.BannerCreator;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by all-cui on 2017/8/22.
 * 使用简单工厂模式创建数据类型
 */

public class MultipleRecylerAdapter extends
        BaseMultiItemQuickAdapter<MultipleItemEntity, MultipleViewHolder>
        implements BaseQuickAdapter.SpanSizeLookup, OnItemClickListener {

    //确保banner初始化只加载一次
    private boolean mIsInitBanner = true;

    @SuppressWarnings("unchecked")
    protected MultipleRecylerAdapter(List<MultipleItemEntity> data) {
        super(data);
        init();
    }

    private void init() {
        //设置不同的item布局
        addItemType(ItemType.TEXT, R.layout.item_multiple_text);
        addItemType(ItemType.IMAGE, R.layout.item_multiple_image);
        addItemType(ItemType.TEXT_IMAGE, R.layout.item_multiple_image_text);
        addItemType(ItemType.BANNER, R.layout.item_multiple_banner);
        //设置宽度监听
        setSpanSizeLookup(this);
        openLoadAnimation();
        //多次执行动画
        isFirstOnly(false);
    }

    public static MultipleRecylerAdapter create(List<MultipleItemEntity> list) {
        return new MultipleRecylerAdapter(list);
    }

    public static MultipleRecylerAdapter create(DataConvert convert) {
        return new MultipleRecylerAdapter(convert.convert());
    }

    @Override
    protected MultipleViewHolder createBaseViewHolder(View view) {
        return MultipleViewHolder.create(view);
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity item) {
        final String text;
        final String imageUrl;
        final ArrayList<String> bannerImages;
        switch (holder.getItemViewType()) {
            case ItemType.TEXT:
                text = item.getField(MultipleFields.TEXT);
                holder.setText(R.id.text_single, text);
                break;
            case ItemType.IMAGE:
                imageUrl = item.getField(MultipleFields.IMAGE_URL);
                loadImg(imageUrl, (ImageView) holder.getView(R.id.img_single));
                break;
            case ItemType.TEXT_IMAGE:
                text = item.getField(MultipleFields.TEXT);
                final String nickName = item.getField(MultipleFields.NICK_NAME);
                final String pubDate = item.getField(MultipleFields.PUB_DATE);
                holder.setText(R.id.title_multiple, text);
                holder.setText(R.id.nick_name_multiple, nickName);
                holder.setText(R.id.pub_date_multiple, pubDate);
                imageUrl = item.getField(MultipleFields.IMAGE_URL);
                if (!TextUtils.isEmpty(imageUrl)){
                    loadImg(imageUrl, (ImageView) holder.getView(R.id.content_img_multiple));
                }
                final String avatarUrl = item.getField(MultipleFields.USER_AVATAR);
                if (!TextUtils.isEmpty(avatarUrl)){
                    loadImg(avatarUrl,(CircleImageView)holder.getView(R.id.avatar_multiple));
                }
                break;
            case ItemType.BANNER:
                if (mIsInitBanner) {
                    bannerImages = item.getField(MultipleFields.BANNERS);
                    final ConvenientBanner<String> convenientBanner = holder.getView(R.id.banner_recycler_item);
                    mIsInitBanner = false;
                    BannerCreator.setDefault(convenientBanner, bannerImages, this);
                }
                break;
            default:
                break;
        }
    }

    /*加载图片*/
    private void loadImg(String imageUrl, ImageView view) {
        Glide.with(mContext)
                .load(imageUrl)
                .apply(
                        new RequestOptions()
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .dontAnimate()
                )
                .into(view);
    }


    @Override
    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
        return 1;
    }

    @Override
    public void onItemClick(int position) {

    }
}
