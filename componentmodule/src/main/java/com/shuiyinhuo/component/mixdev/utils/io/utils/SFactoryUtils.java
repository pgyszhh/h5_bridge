package com.shuiyinhuo.component.mixdev.utils.io.utils;

import android.content.Context;



/**
 * @author：zhiheng su
 * @Date: on 2017/9/1
 * @description: a factory to generator /File/IOStream/SystemLout/SUtils
 */
public class SFactoryUtils {
    private Context mContext;
    private static SFactoryUtils mInstance;

    //File option
    private FileMakeFactory mFileFactory;
    //IO
    private IOUtils mIOInstance;

    private SFactoryUtils(Context context) {
        this.mContext = context;
        init();
    }

    /**
     * init all failds
     */
    private void init() {

    }

    public static SFactoryUtils getInstance(Context context) {
        if (mInstance == null) {
            synchronized (SFactoryUtils.class) {
                if (mInstance == null) {
                    mInstance = new SFactoryUtils(context);
                }
            }
        }
        return mInstance;
    }

    public FileMakeFactory getFileFactory(){
            return mFileFactory=(mFileFactory==null)?new FileMakeFactory():mFileFactory;
    }

    public IOUtils getIOInstance(){
        return mIOInstance=(mIOInstance==null)?new IOUtils():mIOInstance;
    }
}
