package com.sa.all_cui.mix_ui.recycler;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;

/**
 * Created by all-cui on 2017/8/22.
 */

public class MultipleItemEntity implements MultiItemEntity {
    private final ReferenceQueue<LinkedHashMap<Object,Object>> ITME_QUEUE = new ReferenceQueue<>();
    private final LinkedHashMap<Object,Object> MULTIPLE_FILDS = new LinkedHashMap<>();
    private final SoftReference<LinkedHashMap<Object,Object>> FILDS_REFERENCE =
            new SoftReference<>(MULTIPLE_FILDS);

    public MultipleItemEntity(LinkedHashMap<Object,Object> fields) {
        FILDS_REFERENCE.get().putAll(fields);
    }

    @Override
    public int getItemType() {
        return (int)FILDS_REFERENCE.get().get(MultipleFields.ITEM_TYPE);
    }

    @SuppressWarnings("unchecked")
    public <T> T getField(Object key){
        return (T)FILDS_REFERENCE.get().get(key);
    }

    //返回整个数据
    public LinkedHashMap<?,?> getFields(){
        return FILDS_REFERENCE.get();
    }

    public final MultipleItemEntity setValue(Object key,Object value){
        FILDS_REFERENCE.get().put(key,value);
        return this;
    }

    public final MultipleItemEntity setField(Object key,Object value){
        FILDS_REFERENCE.get().put(key,value);
        return this;
    }

    public static MultipleEntityBuilder builder(){
        return new MultipleEntityBuilder();
    }


}
