package com.shuiyinhuo.component.mixdev.cnf.bridge;

import com.shuiyinhuo.component.mixdev.utils.comm.EmptyAndSizeUtils;
import com.shuiyinhuo.component.mixdev.utils.comm.JNILog;

import java.util.Random;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/8/18 0018
 * @ Description：交互交接生成方法工具类
 * =====================================
 */
public class BridgeTagFactory {
    public  String mTagName="";
    public  String mTagNameForJsUtils="";
    private String [] baseTag={"A","B","C","D","E","F","G", "H",
            "I","J","K",":","L","M","N",";","O","P","Q","R","S",
            "T","U","V","W","X", "Y","Z","a","b","c","d","e","f",
            "g","h","i", "j","k","l","m","n","o","p","q","r","s",
            "_","t","u","v","w","x","y","z"};
    public  String getRandomTagName(int tag){
        JNILog.e("Net","当前Tag 索引....  "+tag);
        if (EmptyAndSizeUtils.isNotEmpty(mTagName)){
            JNILog.e("没有再次生成");
            return mTagName+tag;
        }else {
            int min = 1;
            int max = baseTag.length;
            String key = "";
            for (int i = 0; i < 14; i++) {
                int index = new Random().nextInt(max - min) + min;
                key += baseTag[index];
            }
            mTagName=key;
            return mTagName+tag;
        }

    }

    public  String getRandomTagNameForJsUtils(){
        if (EmptyAndSizeUtils.isNotEmpty(mTagNameForJsUtils)){
            JNILog.e("工具类桥接");
            return mTagNameForJsUtils;
        }else {
            int min = 1;
            int max = baseTag.length;
            String key = "";
            for (int i = 0; i < 8; i++) {
                int index = new Random().nextInt(max - min) + min;
                key += baseTag[index];
            }
            mTagNameForJsUtils=key;
            JNILog.e("工具类中间件："+mTagNameForJsUtils);
            return mTagNameForJsUtils;
        }

    }
}
