package com.sa.all_cui.mix_ec.sign;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;

import com.sa.all_cui.mix_core.delegate.DoggerDelegate;
import com.sa.all_cui.mix_core.net.RestClient;
import com.sa.all_cui.mix_core.net.callback.IError;
import com.sa.all_cui.mix_core.net.callback.ISuccess;
import com.sa.all_cui.mix_core.utils.log.DoggerLog;
import com.sa.all_cui.mix_ec.R;
import com.sa.all_cui.mix_ec.R2;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by all-cui on 2017/8/18.
 */

public class SignInDelegate extends DoggerDelegate {
    @BindView(R2.id.et_sign_in_email)
    TextInputEditText mEmail;
    @BindView(R2.id.et_sign_in_pwd)
    TextInputEditText mPwd;

    private ISignListener mSignListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ISignListener) {
             mSignListener = (ISignListener) context;
        }
    }

    @OnClick(R2.id.tv_link_sign_up)
    void onClickLinkSignUp() {
        getSupportDelegate().start(new SignUpDelegate());
    }

    @OnClick(R2.id.bt_sign_in)
    void clickSignInView() {
        RestClient.builder()
                .url("article_list.php")

                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        //Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                        DoggerLog.i("lol-zhangyoubao", response);

                        //SignHandler.onSignIn(response, mSignListener);
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        DoggerLog.i("lol-zhangyoubao", code+"\r\n"+msg);
                    }
                })
                .build()
                .get();
        if (checkForm()) {

        }
    }

    //检查表单的格式是否正确
    private boolean checkForm() {
        final String email = mEmail.getText().toString().trim();
        final String pwd = mPwd.getText().toString().trim();
        boolean isPass = true;
        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmail.setError(getString(R.string.sign_up_tip_email));
            isPass = false;
        } else {
            mEmail.setError(null);
        }

        if (TextUtils.isEmpty(pwd) || pwd.length() < 6) {
            mPwd.setError(getString(R.string.sign_up_error_pwd));
            isPass = false;
        } else {
            mPwd.setError(null);
        }
        return isPass;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_in;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View view) {

    }
}
