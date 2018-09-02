package com.sa.all_cui.mix_core.delegate;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.sa.all_cui.mix_core.ui.camera.CameraImageBean;
import com.sa.all_cui.mix_core.ui.camera.DoggerCamera;
import com.sa.all_cui.mix_core.ui.camera.RequestCodes;
import com.sa.all_cui.mix_core.utils.callback.CallbackManager;
import com.sa.all_cui.mix_core.utils.callback.CallbackType;
import com.sa.all_cui.mix_core.utils.callback.IGlabolCallback;
import com.sa.all_cui.mix_core.utils.log.DoggerLog;
import com.yalantis.ucrop.UCrop;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by all-cui on 2017/8/11.
 */
@RuntimePermissions
public abstract class PermissionCheckerDelegate extends BaseDelegate {

    //不是直接调用的
    @NeedsPermission({Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE})
    void startCamera() {
        DoggerCamera.start(this);
    }

    //这个是真正调用的方法
    public void startCameraWithCheck() {
        //PermissionCheckerDelegatePermissionsDispatcher.startCameraWithCheck(this);
    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    void onPermissionDenied() {
        Toast.makeText(_mActivity, "拒绝拍照", Toast.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain(Manifest.permission.CAMERA)
    void onNeverAskAgain() {
        Toast.makeText(_mActivity, "永久拒绝拍照", Toast.LENGTH_SHORT).show();
    }

    @OnShowRationale(Manifest.permission.CAMERA)
    void onCameraRationale(PermissionRequest request) {
        showCameraPermissionDialog(request);
    }

    private void showCameraPermissionDialog(final PermissionRequest request) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle("权限管理")
                .setCancelable(false)
                .setPositiveButton("同意使用", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton("拒绝使用", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //PermissionCheckerDelegatePermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RequestCodes.TAKE_PHOTO:
                    final Uri imgUri = CameraImageBean.
                            getInstance().getPath();
                    DoggerLog.i("imageuri:"+imgUri.getPath());

                    UCrop.of(imgUri, imgUri)
                            .withMaxResultSize(400, 400)
                            .start(getContext(), this);
                    DoggerLog.i("imageuri2:"+"拍照");
                    break;
                case RequestCodes.PICK_PHOTO:
                    final Uri pickUri = data.getData();
                    //从相册选择图片进行裁剪之后需要一个地址来保存已经裁剪过的图片
                    final String cropPath = DoggerCamera.createCropUri().getPath();
                    UCrop.of(pickUri, Uri.parse(cropPath))
                            .withMaxResultSize(400, 400)
                            .start(getContext(), this);
                    break;
                case RequestCodes.CROP_PHOTO:
                    final Uri cropUri = UCrop.getOutput(data);
                    //剪裁后进行的一个回调
                    final IGlabolCallback<Uri> callback = CallbackManager.getInstance()
                            .getCallback(CallbackType.ON_CROP);
                    if (callback!=null){
                        callback.executeCallback(cropUri);
                    }
                    break;
                case RequestCodes.CROP_ERROR:
                    ToastUtils.showShort("裁剪出错！");
                    break;
                default:
                    break;
            }
        }
    }
}
