package com.sa.all_cui.mix_ec.main.personal.setting;

import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.sa.all_cui.mix_core.delegate.DoggerDelegate;
import com.sa.all_cui.mix_core.utils.callback.CallbackManager;
import com.sa.all_cui.mix_core.utils.callback.CallbackType;
import com.sa.all_cui.mix_core.utils.callback.IGlabolCallback;
import com.sa.all_cui.mix_ec.R;
import com.sa.all_cui.mix_ec.main.personal.list.ListBean;


/**
 * Created by all-cui on 2017/8/30.
 */

public class SettingClickListener extends SimpleClickListener {

    private DoggerDelegate DELEGATE ;

    public SettingClickListener(DoggerDelegate delegate){
        this.DELEGATE = delegate;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final ListBean bean = (ListBean) baseQuickAdapter.getData().get(position);
        final int id = bean.getId();
        switch (id){
            case 1:
                //是否接收推送消息
                final SwitchCompat pushToggle = (SwitchCompat) view.findViewById(R.id.list_item_switch);
                pushToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @SuppressWarnings("unchecked")
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        final CallbackManager manager = CallbackManager.getInstance();
                        if (isChecked){
                            //ToastUtils.showShort("打开极光推送");
                            final IGlabolCallback<String> callback = manager.getCallback(CallbackType.ON_PUSH_OPNE);
                            if (callback!=null){
                                callback.executeCallback("打开极光推送");
                            }
                        }else{
                            //ToastUtils.showShort("关闭极光推送");
                            final IGlabolCallback<String> callback = manager.getCallback(CallbackType.ON_PUSH_STOP);
                            if (callback!=null){
                                callback.executeCallback("关闭极光推送");
                            }
                        }
                    }
                });
                break;
            case 2:
                //关于app

                break;
            default:
                break;
        }
    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
