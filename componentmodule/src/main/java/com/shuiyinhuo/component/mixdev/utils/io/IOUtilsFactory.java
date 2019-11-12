package com.shuiyinhuo.component.mixdev.utils.io;

import android.content.Context;

import com.shuiyinhuo.component.mixdev.utils.io.utils.FileMakeFactory;
import com.shuiyinhuo.component.mixdev.utils.io.utils.IOUtils;


/**
 * @author：zhiheng su
 * @Date: on 2017/9/1
 * @description: a factory to generator /File/IOStream/SystemLout/SUtils
 */
public class IOUtilsFactory {
    private Context mContext;
    private static IOUtilsFactory mInstance;

    //File option
    private FileMakeFactory mFileFactory;
    //IO
    private IOUtils mIOInstance;

    private IOUtilsFactory(Context context) {
        this.mContext = context;
        init();
    }

    /**
     * init all failds
     */
    private void init() {

    }

    public static IOUtilsFactory getInstance(Context context) {
        if (mInstance == null) {
            synchronized (IOUtilsFactory.class) {
                if (mInstance == null) {
                    mInstance = new IOUtilsFactory(context);
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
