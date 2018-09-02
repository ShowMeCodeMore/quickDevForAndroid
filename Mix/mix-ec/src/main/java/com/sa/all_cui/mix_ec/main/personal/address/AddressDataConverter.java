package com.sa.all_cui.mix_ec.main.personal.address;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sa.all_cui.mix_ui.recycler.DataConvert;
import com.sa.all_cui.mix_ui.recycler.ItemType;
import com.sa.all_cui.mix_ui.recycler.MultipleFields;
import com.sa.all_cui.mix_ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

/**
 * Created by all-cui on 2017/8/29.
 * 收货地址数据转换器
 */

public class AddressDataConverter extends DataConvert {

    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final ArrayList<MultipleItemEntity> dataList = new ArrayList<>();
        final JSONArray jsonArray = JSON.parseObject(getJsonData()).getJSONArray("data");

        final int size = jsonArray.size();

        for (int i = 0; i < size; i++) {
            final JSONObject data = jsonArray.getJSONObject(i);
            final int id = data.getInteger("id");
            final boolean isDefault = data.getBoolean("default");
            final String name = data.getString("name");
            final String phone = data.getString("phone");
            final String address = data.getString("address");

            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setItemType(ItemType.VERTICAL_LIST)
                    .setField(MultipleFields.ID, id)
                    .setField(MultipleFields.NAME, name)
                    .setField(MultipleFields.TAG, isDefault)
                    .setField(AddressMultipleFields.PHONE, phone)
                    .setField(AddressMultipleFields.ADDRESS, address)
                    .build();
            dataList.add(entity);
        }


        return dataList;
    }
}
