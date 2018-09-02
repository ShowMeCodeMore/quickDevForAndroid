package com.sa.all_cui.mix_core.ui.camera;

import android.net.Uri;

/**
 * Created by all-cui on 2017/8/29.
 * 存储一些中间值
 */
public class CameraImageBean {

    private Uri mImageUri = null;

    //饿汉式
    private static CameraImageBean INSTANCE = new CameraImageBean();

    public static CameraImageBean getInstance() {
        return INSTANCE;
    }

    public Uri getPath() {
        return mImageUri;
    }

    public void setPath(Uri imageUri) {
        this.mImageUri = imageUri;
    }

}
