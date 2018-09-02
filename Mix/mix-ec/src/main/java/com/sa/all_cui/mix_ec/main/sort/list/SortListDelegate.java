package com.sa.all_cui.mix_ec.main.sort.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sa.all_cui.mix_core.delegate.DoggerDelegate;
import com.sa.all_cui.mix_core.net.RestClient;
import com.sa.all_cui.mix_core.net.callback.ISuccess;
import com.sa.all_cui.mix_ec.R;
import com.sa.all_cui.mix_ec.R2;
import com.sa.all_cui.mix_ec.main.sort.SortDelegate;
import com.sa.all_cui.mix_ui.recycler.MultipleItemEntity;

import java.util.List;

import butterknife.BindView;

/**
 * Created by all-cui on 2017/8/23.
 */

public class SortListDelegate extends DoggerDelegate {
    @BindView(R2.id.rv_vertical_menu_list)
    RecyclerView mRecyclerView = null;

    public static SortListDelegate create() {

        return new SortListDelegate();
    }

    private void initRecyclerView(){
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        //屏蔽掉动画显示
        mRecyclerView.setItemAnimator(null);
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sort_list;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View view) {
        initRecyclerView();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        //数据懒加载
        RestClient.builder()
                .url("sort_list.php")
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final List<MultipleItemEntity> data = new SortListDataConverter()
                                .setJsonData(response).convert();
                        final SortDelegate delegate = getParentDelegate();
                        final SortRecyclerAdapter adapter = new SortRecyclerAdapter(data,delegate);
                        mRecyclerView.setAdapter(adapter);
                    }
                })
                .build()
                .get();
    }
}
