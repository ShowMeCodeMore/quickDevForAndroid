package com.sa.all_cui.mix_core.net.callback;


import com.sa.all_cui.mix_core.ui.loading.DoggerLoader;
import com.sa.all_cui.mix_core.ui.loading.LoaderStyle;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by all-cui on 2017/8/12.
 */

public class RequestCallbacks implements Callback<String> {
    private final ISuccess SUCCESS;
    private final IRequest REQUEST;
    private final IError ERROR;
    private final IFailure FAILURE;
    private final RequestBody BODY;
    private final LoaderStyle LOADER_STYLE;

   // private static final Handler hanlder = new Handler();
    public RequestCallbacks(ISuccess success,
                      IRequest request,
                      IError error,
                      IFailure failure,
                      RequestBody body,
                            LoaderStyle style) {
        this.SUCCESS = success;
        this.REQUEST = request;
        this.ERROR = error;
        this.FAILURE = failure;
        this.BODY = body;
        this.LOADER_STYLE = style;
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if (response.isSuccessful()){//有response返回说明执行成功
            if (call.isExecuted()){
                if (SUCCESS!=null){
                    SUCCESS.onSuccess(response.body());
                }
            }
        }else{
            if (ERROR!=null){//响应失败
                ERROR.onError(response.code(),response.message());
            }
        }

        stopLaoding();
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        if (FAILURE!=null){
            FAILURE.onFailure();
        }

        if (REQUEST!=null){//请求结束
            REQUEST.onRequestEnd();
        }

        stopLaoding();
    }


    private void stopLaoding(){
        if (LOADER_STYLE!= null){
            DoggerLoader.stopLoading();
        }
    }
}
