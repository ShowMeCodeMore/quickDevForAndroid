package com.sa.all_cui.mix_core.ui.loading;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.sa.all_cui.mix_core.R;
import com.sa.all_cui.mix_core.utils.dimen.DimenUtil;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

/**
 * Created by all-cui on 2017/8/12.
 */

@SuppressWarnings("WeakerAccess")
public class DoggerLoader {

    private static final int LOADER_SIZE_SCALE = 6;//默认的缩放比是8倍

    private static final ArrayList<AppCompatDialog> LOADERS = new ArrayList<>();

    private static final String LOADER_DEFAULT = LoaderStyle.BallScaleMultipleIndicator.name();
    /**
    * @param type 加载动画的类型
    * */
    public static void showLoading(Context context,String type){
        final AppCompatDialog dialog = new AppCompatDialog(context, R.style.dialog);
        AVLoadingIndicatorView avLoadingIndicatorView = LoaderCreator.create(context,type);
        dialog.setContentView(avLoadingIndicatorView);
        final int width = DimenUtil.getScreenWidth();
        final int height = DimenUtil.getScreenHeight();
        final Window dialogWindow = dialog.getWindow();
        if (dialogWindow != null){
            //获取属性对象
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = width/LOADER_SIZE_SCALE;
            lp.height = height/LOADER_SIZE_SCALE;
            lp.gravity = Gravity.CENTER;
        }
        //通过集合进行管理所有的Loaders
        LOADERS.add(dialog);
        dialog.show();
    }

    public static void showLoading(Context context){
        showLoading(context,LOADER_DEFAULT);
    }

    public static void showLoading(Context context,Enum<LoaderStyle> type){
        showLoading(context,type.name());
    }

    public static void stopLoading(){

        for (AppCompatDialog dialog :LOADERS){
            if (dialog!=null){
                if (dialog.isShowing()){
                    dialog.cancel();//cancel和dismiss的区别就是cancel会进行会进行回调一些接口，dismiss只会关闭dialog
                }
            }
        }
    }
}
