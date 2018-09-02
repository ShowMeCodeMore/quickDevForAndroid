package com.sa.all_cui.mix_core.net.download;

import android.os.AsyncTask;

import com.sa.all_cui.mix_core.net.RestCreator;
import com.sa.all_cui.mix_core.net.callback.IError;
import com.sa.all_cui.mix_core.net.callback.IFailure;
import com.sa.all_cui.mix_core.net.callback.IRequest;
import com.sa.all_cui.mix_core.net.callback.ISuccess;

import java.util.WeakHashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by all-cui on 2017/8/13.
 */

public class DownloadHandler {
    private final String URL;
    private static WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private final ISuccess SUCCESS;
    private final IRequest REQUEST;
    private final IError ERROR;
    private final IFailure FAILURE;
    private final String DOWNLOAD_DIR;//下载目录
    private final String EXTENSION;//后缀名
    private final String NAME;//文件名

    public DownloadHandler(String url,
                           ISuccess success,
                           IRequest request,
                           IError error,
                           IFailure failure,
                           String downloadDir,
                           String extension,
                           String name) {
        this.URL = url;
        this.SUCCESS = success;
        this.REQUEST = request;
        this.ERROR = error;
        this.FAILURE = failure;
        this.DOWNLOAD_DIR = downloadDir;
        this.EXTENSION = extension;
        this.NAME = name;
    }

    public final void handlDownload(){
        if (REQUEST!=null){
            REQUEST.onRequestStart();
        }

        RestCreator.getResetService().download(URL,PARAMS)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            final ResponseBody body = response.body();
                            final SaveFileTask task = new SaveFileTask(REQUEST,SUCCESS);
                            task.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR,DOWNLOAD_DIR,EXTENSION,body,NAME);

                            //一定要注意判断，否则造成文件下载不全
                            if (task.isCancelled()){
                                if (REQUEST!=null){
                                    REQUEST.onRequestEnd();
                                }
                            }
                        }else{
                            if (ERROR!=null){
                                ERROR.onError(response.code(),response.message());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        if (FAILURE!=null){
                            FAILURE.onFailure();
                        }
                    }
                });
    }
}
