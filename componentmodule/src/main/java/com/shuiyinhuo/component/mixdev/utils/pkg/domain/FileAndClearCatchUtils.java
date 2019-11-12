package com.shuiyinhuo.component.mixdev.utils.pkg.domain;


import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;


import com.shuiyinhuo.component.mixdev.utils.io.utils.util.Logout;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * =====================================
 *
 * @ Author: szhh
 * @ Date : 2017/6/12.
 * @ Description：清除缓存工具
 * =====================================
 */
public class FileAndClearCatchUtils {
    private static final String TAG = "FileAndClearCatchUtils";

    public static void clearCatch() {
        String imageCatchDir = Constact.getImageCatchDir();
        if (imageCatchDir != null) {
            File file = new File(imageCatchDir);
            File[] files = file.listFiles();
            if (files != null && files.length != 0) {
                for (File f : files) {
                    f.delete();
                }
            }
        }

    }

    public static boolean saveBitmapToSystemCameraDir(Bitmap sourceBitmap) {
        boolean isSucc=false;
        String basePath = Constact.SYSTEM_CAMERA_DIR;
        String mPhotoPath=getPhotoName();
        String url=basePath +"/"+mPhotoPath;
        Logout.e(TAG,"系统相册加图片路径名:"+basePath +"/"+mPhotoPath);
        if (sourceBitmap != null) {
            OutputStream fileOutputSteam = getFileOutputSteam(basePath,mPhotoPath);
            if (fileOutputSteam != null) {
                sourceBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputSteam);
                Logout.e(TAG,"照片保存成功");
                isSucc=true;
            }else {
                isSucc=false;
            }
            if (fileOutputSteam != null) {
                try {
                    fileOutputSteam.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (!sourceBitmap.isRecycled()){
                sourceBitmap.recycle();
                sourceBitmap.recycle();
                sourceBitmap=null;
            }
        } else {
            isSucc= false;
        }
        return isSucc;
    }


    private static String getPhotoName(){
        SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
        String format1 = format.format(new Date());
        return format1+".jpg";
    }
    /**
     * 通过视屏获取预览图
     *
     * @param videoPath
     * @return
     */
    public static String getTempImage(String videoPath) {
        String mTempUrl = Constact.getImageCatchDir();
        MediaMetadataRetriever media = new MediaMetadataRetriever();
        media.setDataSource(videoPath);
        Bitmap bitmap = media.getFrameAtTime();
        String imageName=generateImageName();
        OutputStream fileOutputSteam = getFileOutputSteam(mTempUrl,imageName);
        if (fileOutputSteam != null) {
            if (bitmap!=null){
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fileOutputSteam);
            }else {
                return null;
            }

        }
        if (fileOutputSteam != null) {
            try {
                fileOutputSteam.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (bitmap.isRecycled()){
            bitmap.recycle();
            bitmap=null;
        }
        return mTempUrl+"/"+imageName;

    }

    /**
     * 保存拍照图片
     *
     * @param sourceBitmap
     * @return
     */
    public static Object[]  saveCaptureTempImage(Bitmap sourceBitmap) {
        Object[] temp=new Object[2];
        boolean isSucc=false;
        String mTempUrl = Constact.getImageCatchDir();
        String imageName=generateImageName();
        String url=mTempUrl+imageName;
        OutputStream fileOutputSteam = getFileOutputSteam(mTempUrl,imageName);
        if (fileOutputSteam != null) {
            if (sourceBitmap!=null){
                sourceBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputSteam);
                isSucc=true;
            }else {
                isSucc=false;
            }

        }else {
            isSucc=false;
        }
        if (fileOutputSteam != null) {
            try {
                fileOutputSteam.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!sourceBitmap.isRecycled()){
            sourceBitmap.recycle();
            sourceBitmap=null;
        }
        temp[0]=isSucc;
        temp[1]=url;
        return temp;
    }


    public static OutputStream getFileOutputSteam(String rootPath,String fileName) {
        FileOutputStream fileOutputStream = null;
        File file = new File(rootPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String filePath=rootPath+"/"+fileName;
        File file1 = new File(filePath);
        if (!file1.exists()) {
            try {
                file1.createNewFile();
                fileOutputStream = new FileOutputStream(file1);
            } catch (IOException e) {
                Logout.e(TAG, "文件创建异常");
                return fileOutputStream;
            }
        }
        return fileOutputStream;
    }

    public static String generateImageName() {
        return System.currentTimeMillis() + Math.round(10) + ".jpg";
    }

    /**
     * 赋值文件
     *
     * @param sour
     * @param dest
     */
    private static void copyFileToCameraDir(String sour, String dest) {
        File sourFile = new File(sour);
        String filename = sourFile.list()[0];

        String inputname = sour + filename;
        String outputname = dest + filename;

        try {
            FileInputStream input = new FileInputStream(inputname);
            FileOutputStream output = new FileOutputStream(outputname);

            int in = input.read();
            while (in != -1) {
                output.write(in);
                in = input.read();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class Constact{

        public static final String SYSTEM_CAMERA_DIR = "";

        public static String getImageCatchDir() {
            return null;
        }
    }
}
