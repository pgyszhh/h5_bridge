package com.shuiyinhuo.component.mixdev.utils.pkg.domain;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.View;

import com.shuiyinhuo.component.mixdev.utils.pkg.domain.out.Logout;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by hasee on 2016/10/21.
 */

public class ScreenHelperBitmapUtils {

    /**
     * 获取屏幕截图并保存
     */
    public static boolean getScreen(Activity context) {
        Bitmap bitmap;
        View view = context.getWindow().getDecorView();
        view.buildDrawingCache();
        // 获取状态栏高度
        Rect rect = new Rect();
        view.getWindowVisibleDisplayFrame(rect);
        int statusBarHeights = rect.top;
        int heights = ScreenHelperBitmapUtils.getScreenHeight(context);
        int widths = ScreenHelperBitmapUtils.getScreenWidth(context);
        view.layout(0, 0, widths, heights);
        // 允许当前窗口保存缓存信息
        view.setDrawingCacheEnabled(true);
        int statusBarHeight = 0;
        try {
            statusBarHeight = getStatusBarHeight();
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        /** 去掉状态栏高度和 虚拟键盘高度 如果有的话 */
        Bitmap bitmap1 = view.getDrawingCache();
        if (null == bitmap1) {
            return false;
        }
        /**
         * 去标题栏的高度
         */
        bitmap = Bitmap.createBitmap(bitmap1, 0, statusBarHeights, widths,
                heights - statusBarHeights * 1);
        /**
         * 压缩图片 bitmap=compressImage(bitmap);
         */
        //bitmap=compressImage(bitmap);

        view.destroyDrawingCache();
        /**
         * 图片要保存的位置
         */
        /*String imagePath = Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/data/" + context.getString(R.string.app_name) + "/database/"
                + "screen.jpg";

        File path = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/data/" + context.getString(R.string.app_name) + "/database/");
                */

        String imagePath = Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/截屏/"
                + "screen.jpg";


        File path = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/截屏/");
        File file = new File(imagePath);
        try {
            if (!path.exists()) {
                path.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = null;
            fos = new FileOutputStream(file);
            if (null != fos) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();
            }
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    public static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 1024) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    /**
     * 状态栏高度
     */
    public static int getStatusBarHeight() {
        return Resources.getSystem().getDimensionPixelSize(
                Resources.getSystem().getIdentifier("status_bar_height", "dimen",
                        "android"));
    }

    /**
     * @param context
     * @return
     * @description :获取屏幕高度
     */
    public static int getScreenHeight(Activity context) {
        return getDisplayMetrics(context).heightPixels;
    }


    /**
     * @param context
     * @return
     * @description :获取屏幕宽度
     */
    public static int getScreenWidth(Activity context) {
        return getDisplayMetrics(context).widthPixels;
    }

    public static DisplayMetrics getDisplayMetrics(Activity context) {
        DisplayMetrics metrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics;
    }


    private Bitmap getimage(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);//此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
    }

    public static boolean saveBitmapAsImg(Context context, Bitmap bitmap, String name) {
        String url = null;
        String img_name="/screen.jpg";
        String file_path = null;
        img_name=(name != null)?"/"+name:img_name;
        if (bitmap != null) {


            if (SDCardUtils.isSDCardEnable()) {
                file_path = SDCardUtils.getSDCardPath() + "截屏";
                url = file_path + img_name;
                File parentFile = new File(file_path);
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                File img_file = new File(url);
                if (!img_file.exists()) {
                    try {
                        img_file.createNewFile();
                        FileOutputStream outputStream = new FileOutputStream(img_file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                        if (outputStream != null) {
                            outputStream.close();
                        }
                        return true;
                    } catch (IOException e) {
                        e.printStackTrace();
                        Logout.e("图片文件创建失败");
                    }
                }
            } else {
                return false;
            }
        } else {
            throw new NullPointerException("Bitmao do not null");
        }
        return false;
    }
}
