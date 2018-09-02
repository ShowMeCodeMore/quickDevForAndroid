package com.sa.all_cui.mix_ui.recycler;

import java.util.LinkedHashMap;

/**
 * Created by all-cui on 2017/8/22.
 */

public class MultipleEntityBuilder {
    private static final LinkedHashMap<Object,Object> FILEDS = new LinkedHashMap<>();

    public MultipleEntityBuilder() {
        //清除旧数据，保证显示的数据都是最新的
        FILEDS.clear();
    }

    public final MultipleEntityBuilder setItemType(int itemType){
        FILEDS.put(MultipleFields.ITEM_TYPE,itemType);
        return this;
    }

    public final MultipleEntityBuilder setField(Object key,Object value){
        FILEDS.put(key,value);
        return this;
    }

    public final MultipleEntityBuilder setFields(LinkedHashMap<?,?> map){
        FILEDS.putAll(map);
        return this;
    }

    public final MultipleItemEntity build(){
        return new MultipleItemEntity(FILEDS);
    }


}
