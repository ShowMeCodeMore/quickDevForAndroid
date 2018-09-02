package com.sa.all_cui.mix_core.ui.camera;

import com.yalantis.ucrop.UCrop;

/**
 * Created by all-cui on 2017/8/29.
 * 请求码存储
 */
public class RequestCodes {
    //拍照请求码
    public static final int TAKE_PHOTO = 10;
    //选择照片请求码
    public static final int PICK_PHOTO = 11;
    //裁剪照片请求码
    public static final int CROP_PHOTO = UCrop.REQUEST_CROP;
    //裁剪照片错误请求码
    public static final int CROP_ERROR = UCrop.RESULT_ERROR;
    //扫描二维码
    public static final int SCAN = 12;
}
