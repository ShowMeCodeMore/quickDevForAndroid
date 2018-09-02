package com.sa.all_cui.mix_core.utils.dimen;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.sa.all_cui.mix_core.app.MIX;


/**
 * Created by all-cui on 2017/8/12.
 */

public class DimenUtil {

    @SuppressWarnings("SuspiciousNameCombination")
    public static int getScreenWidth(){
        final Resources resources = MIX.getContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    @SuppressWarnings("SuspiciousNameCombination")
    public static int getScreenHeight(){
        final Resources resources = MIX.getContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }
}
