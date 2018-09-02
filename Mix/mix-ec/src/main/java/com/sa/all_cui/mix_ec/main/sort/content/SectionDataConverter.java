package com.sa.all_cui.mix_ec.main.sort.content;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by all-cui on 2017/8/24.
 */

public class SectionDataConverter {

    final List<SectionBean> convert(String json) {
        final List<SectionBean> result = new ArrayList<>();
        final JSONArray dataArray = JSON.parseObject(json).getJSONArray("data");

        final int size = dataArray.size();
        for (int i = 0; i < size; i++) {
            final JSONObject data = dataArray.getJSONObject(i);
            final int id = data.getInteger("id");
            final String title = data.getString("section");

            //添加title
            final SectionBean setctionTitleBean = new SectionBean(true,title);
            setctionTitleBean.setId(id);
            setctionTitleBean.setIsMore(true);
            result.add(setctionTitleBean);

            final JSONArray goodsArray = data.getJSONArray("goods");
            final int goodsSize = goodsArray.size();
            //循环商品内容
            for (int j=0;j<goodsSize;j++){
                final JSONObject goodsData = goodsArray.getJSONObject(j);
                final int goodsId = goodsData.getInteger("goods_id");
                final String goodsName = goodsData.getString("goods_name");
                final String goodsThumb = goodsData.getString("goods_thumb");
                //获取商品展示的内容
                final SectionContentItemEntity entity = new SectionContentItemEntity();
                entity.setGoodsId(goodsId);
                entity.setGoodsName(goodsName);
                entity.setGoodsThumb(goodsThumb);
                //添加商品展示内容
                result.add(new SectionBean(entity));
            }
            //商品循环结束
        }
        //Section解析结束
        return result;
    }
}
