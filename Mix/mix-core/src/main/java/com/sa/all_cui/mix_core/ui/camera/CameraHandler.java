package com.sa.all_cui.mix_core.ui.camera;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.blankj.utilcode.util.FileUtils;
import com.sa.all_cui.mix_core.R;
import com.sa.all_cui.mix_core.delegate.PermissionCheckerDelegate;
import com.sa.all_cui.mix_core.utils.file.FileUtil;
import com.sa.all_cui.mix_core.utils.log.DoggerLog;

import java.io.File;

/**
 * Created by all-cui on 2017/8/29.
 * 照片处理类
 */
public class CameraHandler implements View.OnClickListener {

    private AlertDialog mDialog = null;
    private PermissionCheckerDelegate mDelegate = null;

    public CameraHandler(PermissionCheckerDelegate delegate) {
        this.mDialog = new AlertDialog.Builder(delegate.getContext()).create();
        this.mDelegate = delegate;
    }

    public void beginCamera() {
        mDialog.show();
        final Window windowDialog = mDialog.getWindow();
        if (windowDialog != null) {
            windowDialog.setContentView(R.layout.dialog_camera_pannel);
            windowDialog.setGravity(Gravity.BOTTOM);
            windowDialog.setWindowAnimations(R.style.anim_pannel_from_bottom);
            //设置背景透明
            windowDialog.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //设置属性
            WindowManager.LayoutParams params = windowDialog.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;//背景变暗
            params.dimAmount = 0.5f;
            windowDialog.setAttributes(params);

            windowDialog.findViewById(R.id.photodialog_btn_cancel).setOnClickListener(this);
            windowDialog.findViewById(R.id.photodialog_btn_take).setOnClickListener(this);
            windowDialog.findViewById(R.id.photodialog_btn_native).setOnClickListener(this);
        }
    }

    private String getPhotoName() {
        return FileUtil.getFileNameByTime("IMG", "jpg");
    }

    //拍照 通过contentReservior的方式来兼容7.0及以上的版本
    private void takePhoto() {
        final String currentPhotoName = getPhotoName();
        //打开系统相机的意图
        final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //CAMERA_PHOTO_DIR是系统相册的目录
        final File tempFile = new File(FileUtil.CAMERA_PHOTO_DIR, currentPhotoName);
        //兼容7.0及以上的写法
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            final ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA, tempFile.getPath());
            final Uri uri = mDelegate.getContext().getContentResolver()
                    .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            //需要将uri路径转换为真实的路径
            final File realFile =
                    FileUtils.getFileByPath(FileUtil.getRealFilePath(mDelegate.getContext(), uri));
            final Uri realUri = Uri.fromFile(realFile);
            CameraImageBean.getInstance().setPath(realUri);
            DoggerLog.i(realUri.getPath());

            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        } else {
            final Uri uri = Uri.fromFile(tempFile);
            CameraImageBean.getInstance().setPath(uri);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }

        mDelegate.startActivityForResult(intent, RequestCodes.TAKE_PHOTO);
    }


    private void pickPhoto() {
        final Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        mDelegate.startActivityForResult(Intent.createChooser(intent, mDelegate.getString(R.string.camera_pick_style))
                , RequestCodes.PICK_PHOTO);
    }


    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.photodialog_btn_take) {
            takePhoto();
            mDialog.cancel();
        } else if (id == R.id.photodialog_btn_native) {
            pickPhoto();
            mDialog.cancel();
        } else if (id == R.id.photodialog_btn_cancel) {
            mDialog.cancel();
        }
    }
}
