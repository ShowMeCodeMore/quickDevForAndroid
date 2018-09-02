package com.sa.all_cui.mix_ec.main.sort.content;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.sa.all_cui.mix_core.delegate.DoggerDelegate;
import com.sa.all_cui.mix_core.net.RestClient;
import com.sa.all_cui.mix_core.net.callback.ISuccess;
import com.sa.all_cui.mix_ec.R;
import com.sa.all_cui.mix_ec.R2;

import java.util.List;

import butterknife.BindView;

/**
 * Created by all-cui on 2017/8/23.
 */

public class SortContentDelegate extends DoggerDelegate {

    @BindView(R2.id.rv_soft_content)
    RecyclerView mRecyclerView = null;

    private static final String ARG_CONTENT_ID = "CONTENT_ID";
    private int mContentId = -1;
    private List<SectionBean> mData = null;

    public static SortContentDelegate newInstance(int contentId) {
        Bundle args = new Bundle();
        args.putInt(ARG_CONTENT_ID, contentId);
        final SortContentDelegate fragment = new SortContentDelegate();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if (args != null) {
            mContentId = args.getInt(ARG_CONTENT_ID);
        }
    }

    //初始化数据
    private void initData(){
        RestClient.builder()
                .url("sort_content_list.php?contentId="+mContentId)
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        mData = new SectionDataConverter().convert(response);

                        final SectionAdapter adapter =
                                new SectionAdapter(R.layout.item_section_content,
                                        R.layout.item_section_header,mData);
                        mRecyclerView.setAdapter(adapter);
                    }
                })
                .build()
                .get();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sort_content;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View view) {
        //由于图片的大小不一致，采用瀑布流的方式
        final StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        initData();
    }
}
