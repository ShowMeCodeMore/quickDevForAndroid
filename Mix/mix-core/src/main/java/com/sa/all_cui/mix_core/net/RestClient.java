package com.sa.all_cui.mix_core.net;

import android.content.Context;

import com.sa.all_cui.mix_core.net.callback.IError;
import com.sa.all_cui.mix_core.net.callback.IFailure;
import com.sa.all_cui.mix_core.net.callback.IRequest;
import com.sa.all_cui.mix_core.net.callback.ISuccess;
import com.sa.all_cui.mix_core.net.callback.RequestCallbacks;
import com.sa.all_cui.mix_core.net.download.DownloadHandler;
import com.sa.all_cui.mix_core.ui.loading.DoggerLoader;
import com.sa.all_cui.mix_core.ui.loading.LoaderStyle;

import java.io.File;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by all-cui on 2017/8/11.
 * 先不使用rxjava
 */

public class RestClient {

    private final String URL;
    private static WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private final ISuccess SUCCESS;
    private final IRequest REQUEST;
    private final IError ERROR;
    private final IFailure FAILURE;
    private final RequestBody BODY;
    private final Context CONTEXT;
    private final LoaderStyle LOADERSTYLE;
    private final File FILE;
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;

    public RestClient(String url,
                      WeakHashMap<String, Object> params,
                      ISuccess success,
                      IRequest request,
                      IError error,
                      IFailure failure,
                      RequestBody body,
                      File file,
                      Context context,
                      LoaderStyle loaderStyle,
                      String downloadDir,
                      String extension,
                      String name) {
        this.URL = url;
        PARAMS.putAll(params);
        this.SUCCESS = success;
        this.REQUEST = request;
        this.ERROR = error;
        this.FAILURE = failure;
        this.BODY = body;
        this.FILE = file;
        this.CONTEXT = context;
        this.LOADERSTYLE = loaderStyle;
        this.DOWNLOAD_DIR = downloadDir;
        this.EXTENSION = extension;
        this.NAME = name;
    }

    private void request(HttpMethod method) {
        final RestService service = RestCreator.getResetService();
        if (REQUEST != null) {
            REQUEST.onRequestStart();
        }

        if (LOADERSTYLE!=null){
            DoggerLoader.showLoading(CONTEXT,LOADERSTYLE);
        }

        Call<String> call = null;
        switch (method) {
            case GET:
                call = service.get(URL, PARAMS);
                break;
            case POST:
                call = service.post(URL, PARAMS);
                break;
            case POST_RAW:
                call = service.postRaw(URL,BODY);
                break;
            case PUT:
                call = service.put(URL, PARAMS);
                break;
            case PUT_RAW:
                call = service.putRaw(URL,BODY);
                break;
            case DELETE:
                call = service.delete(URL, PARAMS);
                break;
            case UPLOAD:
                final RequestBody requestBody =
                        RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()),FILE);
                final MultipartBody.Part part =
                        MultipartBody.Part.createFormData("file",FILE.getName(),requestBody);
                call = RestCreator.getResetService().upLoad(URL,part);
                break;
            default:
                break;
        }

        if (call != null) {
            call.enqueue(getRequestCallback());
        }
    }

    private Callback<String> getRequestCallback() {

        return new RequestCallbacks(
                SUCCESS,
                REQUEST,
                ERROR,
                FAILURE,
                BODY,
                LOADERSTYLE
        );
    }

    public static RestClientBuilder builder() {
        return new RestClientBuilder();
    }

    //具体的请求方法
    public final void get(){
        request(HttpMethod.GET);
    }

    public final void post(){

        if (BODY == null){
            request(HttpMethod.POST);
        }else{
            if (!PARAMS.isEmpty()){
                throw new RuntimeException("params must be null");
            }
            request(HttpMethod.POST_RAW);
        }
    }

    public final void put(){
        if (BODY == null){
            request(HttpMethod.PUT);
        }else{
            if (!PARAMS.isEmpty()){
                throw new RuntimeException("params must be null");
            }
        }
        request(HttpMethod.POST_RAW);
    }

    public final void delete(){
        request(HttpMethod.DELETE);
    }

    public final void download(){
        new DownloadHandler(URL,
                SUCCESS,
                REQUEST,
                ERROR,
                FAILURE,
                DOWNLOAD_DIR,
                EXTENSION,
                NAME).handlDownload();
    }
}
