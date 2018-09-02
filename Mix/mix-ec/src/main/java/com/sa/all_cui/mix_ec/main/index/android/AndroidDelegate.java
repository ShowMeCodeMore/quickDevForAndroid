package com.sa.all_cui.mix_ec.main.index.android;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.sa.all_cui.mix_core.delegate.DoggerDelegate;
import com.sa.all_cui.mix_ec.R;

/**
 * Created by all-cui on 2017/10/13.
 */

public class AndroidDelegate extends DoggerDelegate {
    /*@BindView(R2.id.tv_detail_text)
    TextView mTv = null;*/
    @Override
    public Object setLayout() {
        return R.layout.delegate_home_list;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View view) {
        //mTv.setText("android");
    }
}
