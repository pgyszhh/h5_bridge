package com.shuiyinhuo.component.mixdev.utils.comm;

import android.app.Activity;
import android.view.View;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/8/20 0020
 * @ Description：
 * =====================================
 */
public class ViewFinder {

    public ViewFinder() {
    }

    public <T> T finder(View view,int id) {
        if (EmptyAndSizeUtils.isNotEmpty(view)){
            try {
                return (T) view.findViewById(id);
            } catch (Exception e) {
                JNILog.e("寻找失败："+e.getLocalizedMessage());
                return null;
            }
        }else {
            JNILog.e("当前主 View 为空，不能完成寻找子View：");
            return null;
        }

    }

    public <T> T finder(Activity activity, int id) {
        if (EmptyAndSizeUtils.isNotEmpty(activity)){
            try {
                return (T) activity.findViewById(id);
            } catch (Exception e) {
                JNILog.e("寻找失败："+e.getLocalizedMessage());
                return null;
            }
        }else {
            JNILog.e("当前主 View 为空，不能完成寻找子View：");
            return null;
        }

    }
}
