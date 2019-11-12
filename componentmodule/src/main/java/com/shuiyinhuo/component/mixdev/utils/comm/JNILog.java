package com.shuiyinhuo.component.mixdev.utils.comm;

import android.util.Log;

import com.shuiyinhuo.component.mixdev.cnf.comm.StaticConfig;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/8/6 0006
 * @ Description：
 * =====================================
 */
public class JNILog {
    public static void e(String  msg){
        if (StaticConfig.isShowLogin) {
            Log.e(StaticConfig.TAG, msg);
        }
    }

    public static void e(String tag,String  msg){
        if (StaticConfig.isShowLogin) {
            if (EmptyAndSizeUtils.isNotEmpty(tag)){
                Log.e(tag, msg);
            }else {
                Log.e(StaticConfig.TAG, msg);
            };
        }
    }
}
