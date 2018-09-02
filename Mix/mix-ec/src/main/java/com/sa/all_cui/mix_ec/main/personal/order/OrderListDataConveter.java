package com.sa.all_cui.mix_ec.main.personal.order;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sa.all_cui.mix_ui.recycler.DataConvert;
import com.sa.all_cui.mix_ui.recycler.MultipleFields;
import com.sa.all_cui.mix_ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

/**
 * Created by all-cui on 2017/8/28.
 */

public class OrderListDataConveter extends DataConvert {


    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final ArrayList<MultipleItemEntity> dataList = new ArrayList<>();
        final JSONArray jsonArray = JSON.parseObject(getJsonData()).getJSONArray("data");

        final int size = jsonArray.size();
        for (int i = 0; i < size; i++) {
            final JSONObject data = jsonArray.getJSONObject(i);
            final int id = data.getInteger("id");
            final String thumb = data.getString("thumb");
            final String title = data.getString("title");
            final String time = data.getString("time");
            final double price = data.getDouble("price");

            MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setItemType(OrderListItemType.ITEM_NORMAL)
                    .setField(MultipleFields.ID, id)
                    .setField(MultipleFields.IMAGE_URL, thumb)
                    .setField(OrderListItemFields.PRICE, price)
                    .setField(OrderListItemFields.TITLE, title)
                    .setField(OrderListItemFields.TIME, time)
                    .build();
            dataList.add(entity);
        }

        return dataList;
    }
}
