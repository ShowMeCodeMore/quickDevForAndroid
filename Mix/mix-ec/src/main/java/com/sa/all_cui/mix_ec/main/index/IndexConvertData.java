package com.sa.all_cui.mix_ec.main.index;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.sa.all_cui.mix_ui.recycler.DataConvert;
import com.sa.all_cui.mix_ui.recycler.ItemType;
import com.sa.all_cui.mix_ui.recycler.MultipleFields;
import com.sa.all_cui.mix_ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

/**
 * Created by all-cui on 2017/8/22.
 */

public final class IndexConvertData extends DataConvert {

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final JSONArray dataArray = JSON.parseObject(getJsonData()).getJSONArray("items");
        final int size = dataArray.size();

        for (int i = 0; i < size; i++) {
            final JSONObject data = dataArray.getJSONObject(i);
            //user相关信息
            final JSONObject user = data.getJSONObject("user");
            //用户头像
            final String userAvatar = user.getString("face");
            //用户昵称
            final String nickName = user.getString("nickname");
            final String imageUrl = data.getString("thumbnail");
            final String title = data.getString("title");
            //发布时间
            final String pubDate = data.getString("pubDate");

           // int type = 0;
            //final ArrayList<String> bannerImages = new ArrayList<>();
           /* if (TextUtils.isEmpty(imageUrl) && !TextUtils.isEmpty(title)) {
                type = ItemType.TEXT;
            } else if (!TextUtils.isEmpty(imageUrl) && TextUtils.isEmpty(title)) {
                type = ItemType.IMAGE;
            } else */
            final MultipleItemEntity entitys = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, ItemType.TEXT_IMAGE)
                    .setField(MultipleFields.TEXT, title)
                    .setField(MultipleFields.IMAGE_URL, imageUrl)
                    .setField(MultipleFields.USER_AVATAR, userAvatar)
                    .setField(MultipleFields.NICK_NAME, nickName)
                    .setField(MultipleFields.PUB_DATE, pubDate)
                    .build();
            ENTITYS.add(entitys);

        }
        LogUtils.i("entity2:"+ENTITYS.size());

        return ENTITYS;
    }
}
