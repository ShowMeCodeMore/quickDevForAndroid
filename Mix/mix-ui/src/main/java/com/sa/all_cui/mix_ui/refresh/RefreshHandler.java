package com.sa.all_cui.mix_ui.refresh;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sa.all_cui.mix_core.net.RestClient;
import com.sa.all_cui.mix_core.net.callback.IError;
import com.sa.all_cui.mix_core.net.callback.ISuccess;
import com.sa.all_cui.mix_ui.recycler.DataConvert;
import com.sa.all_cui.mix_ui.recycler.MultipleItemEntity;
import com.sa.all_cui.mix_ui.recycler.MultipleRecylerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by all-cui on 2017/8/22.
 */

public class RefreshHandler implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    private final SwipeRefreshLayout mRefreshLayout;
    private final PagingBean BEAN;
    private final RecyclerView RECYCLERVIEW;
    private MultipleRecylerAdapter mAdapter = null;
    private final DataConvert CONVERT;
    private String url = null;

    private RefreshHandler(SwipeRefreshLayout refreshLayout,
                           RecyclerView recyclerView, DataConvert convert,
                           PagingBean bean) {
        this.mRefreshLayout = refreshLayout;
        mRefreshLayout.setOnRefreshListener(this);
        this.BEAN = bean;
        this.RECYCLERVIEW = recyclerView;
        this.CONVERT = convert;
    }

    //简单工厂模式
    public static RefreshHandler create(SwipeRefreshLayout refreshLayout,
                                        RecyclerView recyclerView,
                                        DataConvert convert) {
        return new RefreshHandler(refreshLayout, recyclerView, convert, new PagingBean());
    }

    public final RefreshHandler setHomePageUrl(String url) {
        this.url = url;
        return this;
    }

    /**
     * 初始化首页
     */
    private void initHomePager(List<MultipleItemEntity> response) {
        mAdapter = MultipleRecylerAdapter.create(response);
        mAdapter.setOnLoadMoreListener(RefreshHandler.this,RECYCLERVIEW);
        RECYCLERVIEW.setAdapter(mAdapter);
    }


    private void handlePageSuccess(String response) {
        mRefreshLayout.setRefreshing(false);
        final JSONObject data = JSON.parseObject(response);
        final List<MultipleItemEntity> pageDatas = CONVERT.setJsonData(response).convert();
        BEAN.setTotal(data.getInteger("total_count"));
        if (mAdapter == null) {
            initHomePager(pageDatas);
        } else {
            mAdapter.setNewData(pageDatas);
        }
        BEAN.setPageIndex(0);
        LogUtils.i("页面数2：" + mAdapter.getData().size() + "\r\n" + "页码2:" +
                (BEAN.getPageIndex()) + "\r\n数据累加" + pageDatas.size());
        mAdapter.loadMoreComplete();
    }


    public void retainHomePageFirstNet() {
        RestClient.builder()
                .url(url)
                .loader(mRefreshLayout.getContext())
                .params("p", 0)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        handlePageSuccess(response);
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        mRefreshLayout.setRefreshing(false);
                    }
                })
                .build()
                .get();
    }

    private void retainHomePagingNet() {
        final int pageSize = mAdapter.getData().size();
        //当列表的item数目大于服务器获取item总数量或者列表的item数量小于一页item，加载更多结束
        if (pageSize >= BEAN.getTotalItem() && pageSize < 20) {
            mAdapter.loadMoreEnd(false);
        } else if (pageSize < BEAN.getTotalItem()) {
            RestClient.builder()
                    .url(url)
                    .params("p", BEAN.getPageIndex()+1)
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            final ArrayList<MultipleItemEntity> pageResList = CONVERT.setJsonData(response).convert();
                            if (EmptyUtils.isNotEmpty(pageResList)){
                                mAdapter.setNewData(pageResList);
                                mAdapter.loadMoreComplete();
                                BEAN.addIndex();
                            }else {
                                mAdapter.loadMoreFail();
                            }

                        }
                    })
                    .error(new IError() {
                        @Override
                        public void onError(int code, String msg) {
                            mAdapter.loadMoreFail();
                        }
                    })
                    .build()
                    .get();
        }
    }

    @Override
    public void onRefresh() {
        retainHomePageFirstNet();
    }

    @Override
    public void onLoadMoreRequested() {
        LogUtils.i("加载更多");
        retainHomePagingNet();
    }

}
