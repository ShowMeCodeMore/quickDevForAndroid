package com.sa.all_cui.mix_ec.main.personal.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.sa.all_cui.mix_core.delegate.DoggerDelegate;
import com.sa.all_cui.mix_core.utils.log.DoggerLog;
import com.sa.all_cui.mix_ec.R;
import com.sa.all_cui.mix_ec.R2;
import com.sa.all_cui.mix_ui.widget.StarLayout;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by all-cui on 2017/9/4.
 */

public class OrderCommentDelegate extends DoggerDelegate {

    @BindView(R2.id.star_order_comment)
    StarLayout mStarLayout = null;

    @OnClick(R2.id.tv_order_comment_commit)
    void commentCommit() {
        //提交评价申请
        DoggerLog.d("已选中控件的数量"+mStarLayout.getStarCount());
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_oder_comment;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View view) {

    }
}
