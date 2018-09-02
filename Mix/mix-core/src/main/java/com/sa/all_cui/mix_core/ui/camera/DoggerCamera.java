package com.sa.all_cui.mix_core.ui.camera;

import android.net.Uri;

import com.sa.all_cui.mix_core.delegate.PermissionCheckerDelegate;
import com.sa.all_cui.mix_core.utils.file.FileUtil;


/**
 * Created by all-cui on 2017/8/29.
 * 相机调用类
 */

public class DoggerCamera {

    public static Uri createCropUri() {
        return Uri.fromFile(FileUtil.createFile("crop_image",
                FileUtil.getFileNameByTime("IMG", "JPG")));
    }

    public static void start(PermissionCheckerDelegate delegate) {
        new CameraHandler(delegate).beginCamera();
    }
}
