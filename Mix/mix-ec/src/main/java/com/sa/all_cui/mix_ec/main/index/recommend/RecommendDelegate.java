package com.sa.all_cui.mix_ec.main.index.recommend;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sa.all_cui.mix_core.delegate.DoggerDelegate;
import com.sa.all_cui.mix_ec.R;
import com.sa.all_cui.mix_ec.R2;
import com.sa.all_cui.mix_ec.main.index.IndexConvertData;
import com.sa.all_cui.mix_ec.main.index.IndexDelegate;
import com.sa.all_cui.mix_ec.main.index.IndexItemClickListener;
import com.sa.all_cui.mix_ui.recycler.BaseDecoration;
import com.sa.all_cui.mix_ui.recycler.CustomLayoutManager;
import com.sa.all_cui.mix_ui.refresh.RefreshHandler;

import butterknife.BindView;

/**
 * Created by all-cui on 2017/10/13.
 */

public class RecommendDelegate extends DoggerDelegate {
    @BindView(R2.id.srl_index)
    SwipeRefreshLayout mRefreshLayout = null;
    @BindView(R2.id.rv_index)
    RecyclerView mRecyclerView = null;
    private RefreshHandler mRefreshHandler;

    @Override
    public Object setLayout() {
        return R.layout.delegate_home_list;
    }


    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View view) {
        mRefreshHandler = RefreshHandler.create(mRefreshLayout, mRecyclerView, new IndexConvertData());
    }

    private void initRefreshLayout() {
        mRefreshLayout.setColorSchemeResources(
                android.R.color.holo_red_dark,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light
        );
        //设置加载圆圈起始位置和结束位置，并且开始是由小变大，结束是由小变大
        mRefreshLayout.setProgressViewOffset(true, 120, 300);
    }

    private void initRecycleView() {
        final CustomLayoutManager layoutManager = new CustomLayoutManager(mRecyclerView.getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        //设置分割线
        mRecyclerView.addItemDecoration(
                BaseDecoration.create(ContextCompat.getColor(getContext(),
                        R.color.app_divider_gray), 5));
        final IndexDelegate indexDelegate = getParentDelegate();
        mRecyclerView.addOnItemTouchListener(IndexItemClickListener.create(indexDelegate));
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initRefreshLayout();
        initRecycleView();
        mRefreshHandler.setHomePageUrl("article_list.php").retainHomePageFirstNet();
    }


}
