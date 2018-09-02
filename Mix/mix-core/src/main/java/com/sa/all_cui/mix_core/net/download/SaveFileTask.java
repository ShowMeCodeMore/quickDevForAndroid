package com.sa.all_cui.mix_core.net.download;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.sa.all_cui.mix_core.app.MIX;
import com.sa.all_cui.mix_core.net.callback.IRequest;
import com.sa.all_cui.mix_core.net.callback.ISuccess;
import com.sa.all_cui.mix_core.utils.file.FileUtil;

import java.io.File;
import java.io.InputStream;

import okhttp3.ResponseBody;

/**
 * Created by all-cui on 2017/8/13.
 */

public class SaveFileTask extends AsyncTask<Object,Void,File> {
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;

    public SaveFileTask(IRequest request, ISuccess success) {
        this.REQUEST = request;
        this.SUCCESS = success;
    }

    @Override
    protected File doInBackground(Object... params) {
        String downloadDir = (String) params[0];
        String extension = (String)params[1];
        final ResponseBody body = (ResponseBody) params[2];
        final String name = (String)params[3];
        final InputStream is = body.byteStream();

        if (TextUtils.isEmpty(downloadDir)){
            downloadDir = "down_loads";
        }

        if (TextUtils.isEmpty(extension)){
            extension = "";
        }

        if (TextUtils.isEmpty(name)){
            return FileUtil.writeToDisk(is,downloadDir,extension.toUpperCase(),extension);
        }else{
            return FileUtil.writeToDisk(is,downloadDir,name);
        }
    }

    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        if (SUCCESS!=null){
            SUCCESS.onSuccess(file.getPath());
        }

        if (REQUEST!=null){
            REQUEST.onRequestEnd();
        }

        autoInstallApk(file);
    }

    private void autoInstallApk(File file){
        if (FileUtil.getExtension(file.getPath()).equals("apk")){
            final Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
            MIX.getContext().startActivity(intent);
        }
    }
}
