package com.sa.all_cui.mix_ec.main.personal.address;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sa.all_cui.mix_core.delegate.DoggerDelegate;
import com.sa.all_cui.mix_core.net.RestClient;
import com.sa.all_cui.mix_core.net.callback.ISuccess;
import com.sa.all_cui.mix_core.utils.log.DoggerLog;
import com.sa.all_cui.mix_ec.R;
import com.sa.all_cui.mix_ec.R2;
import com.sa.all_cui.mix_ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by all-cui on 2017/8/29.
 */

public class AddressDelegate extends DoggerDelegate {

    @BindView(R2.id.rv_address)
    RecyclerView mRecylerView = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_adress;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View view) {
        DoggerLog.d("懒加载");
        RestClient.builder()
                .url("address.php")
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final ArrayList<MultipleItemEntity> data = new AddressDataConverter().setJsonData(response).convert();
                        final AddressAdapter adapter = new AddressAdapter(data);
                        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
                        mRecylerView.setLayoutManager(manager);
                        mRecylerView.setAdapter(adapter);
                    }
                })
                .build()
                .get();
    }


}
