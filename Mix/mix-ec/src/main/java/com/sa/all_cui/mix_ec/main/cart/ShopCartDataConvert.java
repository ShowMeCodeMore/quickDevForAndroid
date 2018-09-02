package com.sa.all_cui.mix_ec.main.cart;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sa.all_cui.mix_ui.recycler.DataConvert;
import com.sa.all_cui.mix_ui.recycler.MultipleFields;
import com.sa.all_cui.mix_ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

/**
 * Created by all-cui on 2017/8/25.
 */

public class ShopCartDataConvert extends DataConvert {


    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final ArrayList<MultipleItemEntity> dataList = new ArrayList<>();
        final JSONArray dataArray = JSON.parseObject(getJsonData()).getJSONArray("data");

        final int size = dataArray.size();
        for (int i = 0; i < size; i++) {
            final JSONObject data = dataArray.getJSONObject(i);
            final String thumb = data.getString("thumb");
            final String desc = data.getString("desc");
            final String title = data.getString("title");
            final int id = data.getInteger("id");
            final int count = data.getInteger("count");
            final double price = data.getDouble("price");

            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ID,id)
                    .setField(MultipleFields.IMAGE_URL,thumb)
                    .setField(MultipleFields.ITEM_TYPE,ShopCartItemType.SHOT_CART_TIME)
                    .setField(ShopCartItemFields.TITLE,title)
                    .setField(ShopCartItemFields.DESC,desc)
                    .setField(ShopCartItemFields.COUNT,count)
                    .setField(ShopCartItemFields.PRICE,price)
                    .setField(ShopCartItemFields.IS_SELECTED,false)
                    .setField(ShopCartItemFields.POSITION,i)
                    .build();

            dataList.add(entity);
        }

        return dataList;
    }
}
