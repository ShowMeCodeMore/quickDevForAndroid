package com.sa.all_cui.mix_ec.sign;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;

import com.sa.all_cui.mix_core.delegate.DoggerDelegate;
import com.sa.all_cui.mix_core.net.RestClient;
import com.sa.all_cui.mix_core.net.callback.ISuccess;
import com.sa.all_cui.mix_core.utils.log.DoggerLog;
import com.sa.all_cui.mix_ec.R;
import com.sa.all_cui.mix_ec.R2;

import butterknife.BindViews;
import butterknife.OnClick;

/**
 * Created by all-cui on 2017/8/18.
 * 注册逻辑
 */

public class SignUpDelegate extends DoggerDelegate {
    @BindViews({R2.id.et_sign_up_name,
            R2.id.et_sign_up_email,
            R2.id.et_sign_up_phonenum,
            R2.id.et_sign_up_pwd,
            R2.id.et_sign_up_pwd_sure})
    TextInputEditText[] mInput;

    private ISignListener mSignListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ISignListener){
            mSignListener = (ISignListener) activity;
        }
    }

    @OnClick(R2.id.bt_sign_up)
    void onClickSignUp(){
        if (volidateForm()){
            RestClient.builder()
                    .url("http://192.168.1.39:8080/RestServer/api/user_profile.php")
                    .params("name",mInput[0].getText().toString())
                    .params("email",mInput[1].getText().toString())
                    .params("password",mInput[2].getText().toString())
                    .params("password_again",mInput[3].getText().toString())
                    .loader(getContext())
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            //Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                            DoggerLog.i("User_profile",response);

                            SignHandler.onSignUp(response,mSignListener);
                        }
                    })
                    .build()
                    .post();

        }
    }

    //判定用户输入的数据是否合法
    private boolean volidateForm(){
        final String name = mInput[0].getText().toString().trim();
        final String email = mInput[1].getText().toString().trim();
        final String phoneNum = mInput[2].getText().toString().trim();
        final String pwd = mInput[3].getText().toString().trim();
        final String pwdSure = mInput[4].getText().toString().trim();

        boolean isPass = true;

        if (TextUtils.isEmpty(name)){
            isPass = false;
            mInput[0].setError(getString(R.string.sign_up_tip_name));
        }else{
            mInput[0].setError(null);
        }

        if (TextUtils.isEmpty(email)||!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mInput[1].setError(getString(R.string.sign_up_tip_email));
            isPass = false;
        }else{
            mInput[1].setError(null);
        }

        if (TextUtils.isEmpty(phoneNum)||mInput[2].length()!=11){
            mInput[2].setError(getString(R.string.sign_up_tip_phonenum));
            isPass = false;
        }else{
            mInput[2].setError(null);
        }

        if (TextUtils.isEmpty(pwd)||pwd.length()<6){
            mInput[3].setError(getString(R.string.sign_up_tip_pwd));
            isPass = false;
        }else{
            mInput[3].setError(null);
        }

        if (TextUtils.isEmpty(pwdSure)||pwd.length()<6||!pwd.equals(pwdSure)){
            mInput[4].setError(getString(R.string.sign_up_error_pwd));
            isPass = false;
        }else{
            mInput[4].setError(null);
        }
        return isPass;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_up;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View view) {

    }
}
