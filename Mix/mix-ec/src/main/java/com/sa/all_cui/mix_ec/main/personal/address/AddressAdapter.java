package com.sa.all_cui.mix_ec.main.personal.address;

import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.sa.all_cui.mix_core.net.RestClient;
import com.sa.all_cui.mix_core.net.callback.ISuccess;
import com.sa.all_cui.mix_ec.R;
import com.sa.all_cui.mix_ui.recycler.ItemType;
import com.sa.all_cui.mix_ui.recycler.MultipleFields;
import com.sa.all_cui.mix_ui.recycler.MultipleItemEntity;
import com.sa.all_cui.mix_ui.recycler.MultipleViewHolder;
import com.sa.all_cui.mix_ui.recycler.MultipleRecylerAdapter;

import java.util.List;

/**
 * Created by all-cui on 2017/8/29.
 */

public class AddressAdapter extends MultipleRecylerAdapter {


    protected AddressAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(ItemType.VERTICAL_LIST, R.layout.item_address);
    }

    @Override
    protected void convert(final MultipleViewHolder holder, MultipleItemEntity item) {
        super.convert(holder, item);
        switch (holder.getItemViewType()) {
            case ItemType.VERTICAL_LIST:
                //取出值
                final String name = item.getField(MultipleFields.NAME);
                final String phome = item.getField(AddressMultipleFields.PHONE);
                final boolean isDefalut = item.getField(MultipleFields.TAG);
                final String address = item.getField(AddressMultipleFields.ADDRESS);
                //取出控件
                final AppCompatTextView nameTv = holder.getView(R.id.tv_address_name);
                final AppCompatTextView phoneTv = holder.getView(R.id.tv_address_phone);
                final AppCompatTextView addressTv = holder.getView(R.id.tv_address_address);
                final AppCompatTextView deleteTv = holder.getView(R.id.tv_address_delete);
                //赋值
                nameTv.setText(name);
                phoneTv.setText(phome);
                addressTv.setText(address);
                deleteTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //删除收货地址
                        RestClient.builder()
                                .url("address.php")
                                .loader(mContext)
                                .success(new ISuccess() {
                                    @Override
                                    public void onSuccess(String response) {
                                        remove(holder.getLayoutPosition());
                                    }
                                })
                                .build()
                                .post();
                    }
                });
                break;
            default:
                break;
        }
    }
}
