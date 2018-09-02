package com.sa.all_cui.mix_ec.main.personal.profile;

import android.app.Dialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.sa.all_cui.mix_core.delegate.DoggerDelegate;
import com.sa.all_cui.mix_core.utils.callback.CallbackManager;
import com.sa.all_cui.mix_core.utils.callback.CallbackType;
import com.sa.all_cui.mix_core.utils.callback.IGlabolCallback;
import com.sa.all_cui.mix_core.utils.storage.shareprefrence.DoggerPrefrence;
import com.sa.all_cui.mix_ec.R;
import com.sa.all_cui.mix_ec.main.personal.list.ListBean;
import com.sa.all_cui.mix_ui.date.DateDialogUtil;


/**
 * Created by all-cui on 2017/8/28.
 */

public class UserProfileClickListener extends SimpleClickListener {
    private final DoggerDelegate DELEGATE;
    private final String[] mGenders = {"男", "女", "保密"};
    private static final String GENDER = "GENDERID";

    public UserProfileClickListener(DoggerDelegate delegate) {

        this.DELEGATE = delegate;

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, final View view, int position) {
        final ListBean bean = (ListBean) baseQuickAdapter.getData().get(position);
        final int id = bean.getId();
        switch (id) {
            case 1:
                //拍照上传照片
                DELEGATE.startCameraWithCheck();
                CallbackManager.getInstance().addCallback(CallbackType.ON_CROP
                        , new IGlabolCallback<Uri>() {
                            @Override
                            public void executeCallback(Uri uri) {
                                Glide.with(DELEGATE.getContext())
                                .load(uri)
                                .into((ImageView) view.findViewById(R.id.img_arrow_avatar));
                            }
                        });

                //图片上传到服务器成功后，接下来有两种做法
                //1、本地存储有用户信息，然后将图片地址字段上传到服务进行更新，
                // 然后将返回的数据将本地的数据中需要的字段进行更新
                //2、本地没有存储app，每次都通过请求api的方式来展示页面

                break;
            case 2:
                final UserNameDelegate delegate = (UserNameDelegate) bean.getDelegate();
                DELEGATE.getSupportDelegate().start(delegate);
                //设置姓名
                break;
            case 3:
                //设置性别

                setGenders(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final AppCompatTextView value = (AppCompatTextView) view.findViewById(R.id.tv_arrow_value);
                        DoggerPrefrence.setAppInt(GENDER, which);
                        value.setText(mGenders[which]);
                        dialog.dismiss();
                    }
                });
                break;
            case 4:
                //设置生日
                final DateDialogUtil dateUtil = new DateDialogUtil();
                dateUtil.setDateListener(new DateDialogUtil.IDateListener() {
                    @Override
                    public void onDataChange(String date) {
                        final AppCompatTextView value = (AppCompatTextView) view.findViewById(R.id.tv_arrow_value);
                        value.setText(date);
                    }
                });

                dateUtil.showDialog(DELEGATE.getContext());
                break;
            default:
                break;
        }
    }

    private void setGenders(Dialog.OnClickListener listener) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(DELEGATE.getContext());
        alert.setSingleChoiceItems(mGenders, DoggerPrefrence.getAppInt(GENDER), listener)
                .show();
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
