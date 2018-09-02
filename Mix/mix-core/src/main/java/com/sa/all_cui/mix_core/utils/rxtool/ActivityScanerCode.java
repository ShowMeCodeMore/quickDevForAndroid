package com.sa.all_cui.mix_core.utils.rxtool;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.LogUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.sa.all_cui.mix_core.R;
import com.sa.all_cui.mix_core.app.MIX;
import com.sa.all_cui.mix_core.utils.rxtool.scaner.CameraManager;
import com.sa.all_cui.mix_core.utils.rxtool.scaner.CaptureActivityHandler;
import com.sa.all_cui.mix_core.utils.rxtool.scaner.decoding.InactivityTimer;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import static com.sa.all_cui.mix_core.utils.rxtool.RxConstants.QUICK_UP_QR_NAME;
import static com.sa.all_cui.mix_core.utils.rxtool.RxConstants.QUICK_UP_RESULT_CODE_HOME;
import static com.sa.all_cui.mix_core.utils.rxtool.RxConstants.QUICK_UP_RESULT_CODE_PICKUP;
import static com.sa.all_cui.mix_core.utils.rxtool.RxConstants.QUICK_UP_RESULT_CODE_TICKET;

public class ActivityScanerCode extends AppCompatActivity {

    private static OnRxScanerListener mScanerListener;//扫描结果监听
    private InactivityTimer inactivityTimer;
    private CaptureActivityHandler handler;//扫描处理
    private RelativeLayout mContainer = null;//整体根布局
    private RelativeLayout mCropLayout = null;//扫描框根布局
    private int mCropWidth = 0;//扫描边界的宽度
    private int mCropHeight = 0;//扫描边界的高度
    private boolean hasSurface;//是否有预览
    private boolean vibrate = true;//扫描成功后是否震动
    private boolean mFlashing = true;//闪光灯开启状态
    private LinearLayout mLlScanHelp;//生成二维码 & 条形码 布局
    private ImageView mIvLight;//闪光灯 按钮
    private RxDialogSure rxDialogSure;//扫描结果显示框

    public static void setScanerListener(OnRxScanerListener scanerListener) {
        mScanerListener = scanerListener;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RxBarTool.setNoTitle(this);
        setContentView(R.layout.activity_scaner_code);
        RxBarTool.setTransparentStatusBar(this);
        initView();//界面控件初始化
        initScanerAnimation();//扫描动画初始化
        CameraManager.init(this);//初始化 CameraManager
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onResume() {
        super.onResume();
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.capture_preview);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);//Camera初始化
        } else {
            surfaceHolder.addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

                }

                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    if (!hasSurface) {
                        hasSurface = true;
                        initCamera(holder);
                    }
                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                    hasSurface = false;

                }
            });
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }


    private void initView() {
        mIvLight = (ImageView) findViewById(R.id.top_mask);
        mContainer = (RelativeLayout) findViewById(R.id.capture_containter);
        mCropLayout = (RelativeLayout) findViewById(R.id.capture_crop_layout);
        mLlScanHelp = (LinearLayout) findViewById(R.id.ll_scan_help);
        //请求Camera权限 与 文件读写 权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    private void initScanerAnimation() {
        ImageView mQrLineView = (ImageView) findViewById(R.id.capture_scan_line);
        RxAnimationTool.ScaleUpDowm(mQrLineView);
    }

    public int getCropWidth() {
        return mCropWidth;
    }

    public void setCropWidth(int cropWidth) {
        mCropWidth = cropWidth;
        CameraManager.FRAME_WIDTH = mCropWidth;

    }

    public int getCropHeight() {
        return mCropHeight;
    }

    public void setCropHeight(int cropHeight) {
        this.mCropHeight = cropHeight;
        CameraManager.FRAME_HEIGHT = mCropHeight;
    }

    public void btn(View view) {
        int viewId = view.getId();
        if (viewId == R.id.top_mask) {
            light();
        } else if (viewId == R.id.top_back) {
            finish();
        } else if (viewId == R.id.top_openpicture) {
            RxPhotoTool.openLocalImage(ActivityScanerCode.this);
        }
    }

    private void light() {
        if (mFlashing) {
            mFlashing = false;
            // 开闪光灯
            CameraManager.get().openLight();
        } else {
            mFlashing = true;
            // 关闪光灯
            CameraManager.get().offLight();
        }

    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
            Point point = CameraManager.get().getCameraResolution();
            AtomicInteger width = new AtomicInteger(point.y);
            AtomicInteger height = new AtomicInteger(point.x);
            int cropWidth = mCropLayout.getWidth() * width.get() / mContainer.getWidth();
            int cropHeight = mCropLayout.getHeight() * height.get() / mContainer.getHeight();
            setCropWidth(cropWidth);
            setCropHeight(cropHeight);
        } catch (IOException | RuntimeException ioe) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(ActivityScanerCode.this);
        }
    }
    //========================================打开本地图片识别二维码 end=================================

    //--------------------------------------打开本地图片识别二维码 start---------------------------------
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            ContentResolver resolver = getContentResolver();
            // 照片的原始资源地址
            Uri originalUri = data.getData();
            try {
                // 使用ContentProvider通过URI获取原始图片
                Bitmap photo = MediaStore.Images.Media.getBitmap(resolver, originalUri);

                // 开始对图像资源解码
                Result rawResult = RxQrBarTool.decodeFromPhoto(photo);
                if (rawResult != null) {
                    if (mScanerListener == null) {
                        obtainScanResult(rawResult);
                    } else {
                        mScanerListener.onSuccess("From to Picture", rawResult);
                    }
                } else {
                    if (mScanerListener == null) {
                        RxToast.error("图片识别失败.");
                    } else {
                        mScanerListener.onFail("From to Picture", "图片识别失败");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //==============================================================================================解析结果 及 后续处理 end
    private void obtainScanResult(Result result) {
        String content = result.getText();
        final int typeBack = getIntent().getIntExtra(QUICK_UP_QR_NAME, -1);
        Intent intent = new Intent();
        intent.putExtra(RxConstants.QRCODE_RESULT_CONTENT, content);
        switch (typeBack) {
            //从homedelegate界面跳转
            case QUICK_UP_RESULT_CODE_HOME:
                setResult(QUICK_UP_RESULT_CODE_HOME, intent);
                break;
            //提车和查询车辆信息共同使用同一个界面
            case QUICK_UP_RESULT_CODE_PICKUP:
                setResult(QUICK_UP_RESULT_CODE_PICKUP, intent);
                break;
            //补打小票
            case QUICK_UP_RESULT_CODE_TICKET:
                setResult(QUICK_UP_RESULT_CODE_TICKET, intent);
                break;
            default:
                break;
        }
        finish();
    }


    private void initDialogResult(Result result) {
        BarcodeFormat type = result.getBarcodeFormat();
        String realContent = result.getText();

        if (rxDialogSure == null) {
            rxDialogSure = new RxDialogSure(this);//提示弹窗
        }

        if (BarcodeFormat.QR_CODE.equals(type)) {
            rxDialogSure.setTitle("二维码扫描结果");
        } else if (BarcodeFormat.EAN_13.equals(type)) {
            rxDialogSure.setTitle("条形码扫描结果");
        } else {
            rxDialogSure.setTitle("扫描结果");
        }

        rxDialogSure.setContent(realContent);
        rxDialogSure.setSureListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rxDialogSure.cancel();
            }
        });
        rxDialogSure.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (handler != null) {
                    // 连续扫描，不发送此消息扫描一次结束后就不能再次扫描
                    handler.sendEmptyMessage(R.id.restart_preview);
                }
            }
        });

        if (!rxDialogSure.isShowing()) {
            rxDialogSure.show();
        }

        RxSPTool.putContent(this, RxConstants.SP_SCAN_CODE, RxDataTool.stringToInt(RxSPTool.getContent(MIX.getContext(), RxConstants.SP_SCAN_CODE)) + 1 + "");
    }

    public void handleDecode(Result result) {
        inactivityTimer.onActivity();
        RxBeepTool.playBeep(this, vibrate);//扫描成功之后的振动与声音提示
        String result1 = result.getText();
        Log.v("二维码/条形码 扫描结果", result1);
        if (mScanerListener == null) {
            //RxToast.success(result1);
            obtainScanResult(result);
        } else {
            mScanerListener.onSuccess("From to Camera", result);
        }
    }

    public Handler getHandler() {
        return handler;
    }


    @Override
    protected void onDestroy() {
        if (inactivityTimer != null) {
            inactivityTimer.shutdown();
        }
        mScanerListener = null;
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            LogUtils.i("关闭activity");
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}