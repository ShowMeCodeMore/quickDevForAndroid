package com.sa.all_cui.mix_core.utils.compress;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by all-cui on 2017/11/10.
 */

@SuppressWarnings("WeakerAccess")
public class ImageCompressFile {
    private static Bitmap decodeFromFileCompress(final String filename, final int reqWidth, final int reqHeight) {
        // 给定的BitmapFactory设置解码的参数
        final BitmapFactory.Options options = new BitmapFactory.Options();
        // 从解码器中获取原始图片的宽高，这样避免了直接申请内存空间
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filename, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);
        // 压缩完后便可以将inJustDecodeBounds设置为false了。
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filename, options);
    }


    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // 原始图片的宽、高
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 8;

//      if (height > reqHeight || width > reqWidth) {
//          //这里有两种压缩方式，可供选择。
//          /**
//           * 压缩方式二
//           */
//          // final int halfHeight = height / 2;
//          // final int halfWidth = width / 2;
//          // while ((halfHeight / inSampleSize) > reqHeight
//          // && (halfWidth / inSampleSize) > reqWidth) {
//          // inSampleSize *= 2;
//          // }
//
        /**
         * 压缩方式一
         */
        // 计算压缩的比例：分为宽高比例
        final int heightRatio = Math.round((float) height
                / (float) reqHeight);
        final int widthRatio = Math.round((float) width / (float) reqWidth);
        inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
//      }

        return inSampleSize;
    }

    /**
     * @param fileName 图片文件名称
     * @param heiht    指定文件高度
     * @param width    指定图片文件的宽度
     * @param file
     * @author cui
     * @function 压缩图片100k左右
     */
    public static Bitmap compressBmpToFile(String fileName, int width, int heiht, final File file) {
        Bitmap bmp = decodeFromFileCompress(fileName, width, heiht);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 90;

        while (baos.toByteArray().length / 1024 > 100 && options > 0) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset(); // 重置baos即清空baos
            bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        if (!bmp.isRecycled()) {
            bmp.isRecycled();//回收bmp防止内存泄漏
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeFile(file.getAbsolutePath());
    }
}
