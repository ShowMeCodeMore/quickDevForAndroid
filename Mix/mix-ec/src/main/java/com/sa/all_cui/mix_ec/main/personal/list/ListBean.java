package com.sa.all_cui.mix_ec.main.personal.list;

import android.text.TextUtils;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.sa.all_cui.mix_core.delegate.DoggerDelegate;

/**
 * Created by all-cui on 2017/8/28.
 */

public class ListBean implements MultiItemEntity {

    private int mId = 0;
    private int mItemType = 0;
    private String mImageUrl = null;
    private String mText = null;
    private String mValue = null;
    private DoggerDelegate mDelegate = null;
    private CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener = null;

    public ListBean(int id,
                    int itemType,
                    String imageUrl,
                    String text,
                    String value,
                    DoggerDelegate delegate,
                    CompoundButton.OnCheckedChangeListener checkedChangeListener) {
        this.mId = id;
        this.mItemType = itemType;
        this.mImageUrl = imageUrl;
        this.mText = text;
        this.mValue = value;
        this.mDelegate = delegate;
        this.mOnCheckedChangeListener = checkedChangeListener;
    }

    @Override
    public int getItemType() {
        return mItemType;
    }

    public int getId() {
        return mId;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getText() {
        if (TextUtils.isEmpty(mText)) {
            return "";
        }
        return mText;
    }

    public String getValue() {
        if (TextUtils.isEmpty(mValue)) {
            return "";
        }
        return mValue;
    }

    public DoggerDelegate getDelegate() {
        return mDelegate;
    }

    public CompoundButton.OnCheckedChangeListener getOnCheckedChangeListener() {
        return mOnCheckedChangeListener;
    }

    public static final class Builder {
        private int id = 0;
        private int itemType = 0;
        private String imageUrl = null;
        private String text = null;
        private String value = null;
        private DoggerDelegate delegate = null;
        private CompoundButton.OnCheckedChangeListener onCheckedChangeListener = null;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setItemType(int itemType) {
            this.itemType = itemType;
            return this;
        }

        public Builder setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setValue(String value) {
            this.value = value;
            return this;
        }

        public Builder setDelegate(DoggerDelegate delegate) {
            this.delegate = delegate;
            return this;
        }

        public Builder setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
            this.onCheckedChangeListener = onCheckedChangeListener;
            return this;
        }

        public ListBean create() {
            return new ListBean(id, itemType, imageUrl, text, value, delegate, onCheckedChangeListener);
        }
    }
}
