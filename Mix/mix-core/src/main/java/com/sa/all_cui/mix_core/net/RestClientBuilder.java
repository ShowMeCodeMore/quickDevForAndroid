package com.sa.all_cui.mix_core.net;

import android.content.Context;

import com.sa.all_cui.mix_core.net.callback.IError;
import com.sa.all_cui.mix_core.net.callback.IFailure;
import com.sa.all_cui.mix_core.net.callback.IRequest;
import com.sa.all_cui.mix_core.net.callback.ISuccess;
import com.sa.all_cui.mix_core.ui.loading.LoaderStyle;

import java.io.File;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by all-cui on 2017/8/12.
 */

public class RestClientBuilder {
    private String mUrl;
    //全局使用直接使用静态
    private static WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private ISuccess mISuccess;
    private IRequest mIRequest;
    private IError mIError;
    private IFailure mIFailure;
    private RequestBody mIBody;
    private Context mContext;
    private LoaderStyle mLoaderStyle;
    private File mFile;

    private String mDownLoadDir;
    private String mExtension;
    private String mName;

    RestClientBuilder() {

    }

    public final RestClientBuilder url(String url) {
        this.mUrl = url;
        return this;
    }

    public final RestClientBuilder param(WeakHashMap<String, Object> params) {
        PARAMS.putAll(params);
        return this;
    }

    public final RestClientBuilder params(String key, Object params) {
        PARAMS.put(key, params);
        return this;
    }


    public final RestClientBuilder raw(String raw) {
        this.mIBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }

    public final RestClientBuilder success(ISuccess iSuccess) {
        this.mISuccess = iSuccess;
        return this;
    }


    public final RestClientBuilder error(IError iError) {
        this.mIError = iError;
        return this;
    }

    public final RestClientBuilder failure(IFailure iFailure) {
        this.mIFailure = iFailure;
        return this;
    }

    public final RestClientBuilder request(IRequest iRequest) {
        this.mIRequest = iRequest;
        return this;
    }

    public final RestClientBuilder loader(Context context, LoaderStyle loaderStyle) {
        this.mContext = context;
        this.mLoaderStyle = loaderStyle;

        return this;
    }

    public final RestClientBuilder loader(Context context) {
        this.mContext = context;
        this.mLoaderStyle = LoaderStyle.BallScaleMultipleIndicator;

        return this;
    }

    public final RestClientBuilder file(File file) {
        this.mFile = file;
        return this;
    }

    public final RestClientBuilder file(String filePath) {
        this.mFile = new File(filePath);
        return this;
    }

    //文件名称
    public final RestClientBuilder name(String name){
        this.mName = name;
        return this;
    }

    //文件下载的目录
    public final RestClientBuilder downloadDir(String downloadDir){
        this.mDownLoadDir = downloadDir;
        return this;
    }
    //后缀名
    public final RestClientBuilder external(String extension){
        this.mExtension = extension;
        return this;
    }

    public final RestClient build() {
        return new RestClient(mUrl, PARAMS, mISuccess,
                mIRequest, mIError, mIFailure, mIBody,
                mFile, mContext, mLoaderStyle,mDownLoadDir,mExtension,mName);
    }
}
