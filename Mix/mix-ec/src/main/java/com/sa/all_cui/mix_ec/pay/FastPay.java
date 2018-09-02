package com.sa.all_cui.mix_ec.pay;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.sa.all_cui.mix_core.delegate.DoggerDelegate;
import com.sa.all_cui.mix_ec.R;


/**
 * Created by all-cui on 2017/8/28.
 * 快速支付入口类
 */

public class FastPay implements View.OnClickListener {

    private IAlPayResultListener mPayListener = null;
    private Activity mActivity = null;
    //通过显示dialog提供给用户选择支付包或者其他的支付方式
    private AlertDialog mDialog = null;
    private int mOrderId = -1;

    private FastPay(DoggerDelegate delegate) {
        this.mActivity = delegate.getProxyActivity();
        this.mDialog = new AlertDialog.Builder(delegate.getContext()).create();
    }

    //简单工厂的是模式
    public static FastPay create(DoggerDelegate delegate) {
        return new FastPay(delegate);
    }

    //开始支付
    public void beginPayDialog() {
        mDialog.show();
        final Window windowDialog = mDialog.getWindow();
        if (windowDialog != null) {
            windowDialog.setContentView(R.layout.dialog_pay_pannel);
            windowDialog.setGravity(Gravity.BOTTOM);
            windowDialog.setWindowAnimations(R.style.anim_pannel_from_bottom);
            //设置背景透明
            windowDialog.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //设置属性
            WindowManager.LayoutParams params = windowDialog.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;//背景变暗
            windowDialog.setAttributes(params);

            windowDialog.findViewById(R.id.bt_pay_cancel).setOnClickListener(this);
            windowDialog.findViewById(R.id.bt_pay_pannal_wechat).setOnClickListener(this);
            windowDialog.findViewById(R.id.bt_pay_pannal_zifubao).setOnClickListener(this);
        }
    }

    // TDDO: Lib中不能使用switch
    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id==R.id.bt_pay_cancel){
            mDialog.dismiss();
        }else if (id==R.id.bt_pay_pannal_wechat){
            mDialog.dismiss();
            Toast.makeText(mActivity, "微信支付", Toast.LENGTH_SHORT).show();
        }else if (id==R.id.bt_pay_pannal_zifubao){
            mDialog.dismiss();
            Toast.makeText(mActivity, "支付宝支付", Toast.LENGTH_SHORT).show();
        }
    }
}
