package com.shuiyinhuo.component.mixdev.cnf.comm;

import com.shuiyinhuo.component.mixdev.jinbean.WebConfig;
import com.shuiyinhuo.component.mixdev.utils.comm.EmptyAndSizeUtils;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/8/17 0017
 * @ Description： 日志控制类
 * =====================================
 */
public class StaticConfig {
    public static boolean isShowLogin=false;
    public static String TAG = "JNI";

    /**
     * 这只全局的引用网页加载路径
     * @param rootDir
     */
    public static void setAppGlobalRoot(String rootDir){
        if (EmptyAndSizeUtils.isNotEmpty(rootDir)){
            WebConfig.PAGE_URL_PREFIX_=rootDir;
        }
    }

    /**
     * 根据当前的文件名或者路径生成对应的:  协议+url
     * @param fileNameOrPath
     * @return
     */
    public static String getCurtPageUrl(String fileNameOrPath){
        if (EmptyAndSizeUtils.isNotEmpty(fileNameOrPath)){
        return     WebConfig.getPageUrl(fileNameOrPath);
        }
        return fileNameOrPath;
    }
}
