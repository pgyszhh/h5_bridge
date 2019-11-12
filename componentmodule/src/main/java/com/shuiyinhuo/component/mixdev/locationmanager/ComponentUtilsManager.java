package com.shuiyinhuo.component.mixdev.locationmanager;

import android.content.Context;

import com.shuiyinhuo.component.mixdev.utils.comm.ColorUtils;
import com.shuiyinhuo.component.mixdev.utils.comm.EmptyAndSizeUtils;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/9/6 0006
 * @ Description：组件工具类管理器 ColorUtils
 * =====================================
 */
public class ComponentUtilsManager {
    private static ComponentUtilsManager mManager = new ComponentUtilsManager();
    private Context mContext;
    private ComponentUtilsManager() {
    }

    /**
     * 颜色工具类：颜色校验，渲染等
     */
    private ColorUtils mColorUtils;
    public static ComponentUtilsManager getInstance() {
        if (EmptyAndSizeUtils.isEmpty(mManager)) {
            synchronized (UtilsManager.class) {
                if (EmptyAndSizeUtils.isEmpty(mManager)) {
                    mManager = new ComponentUtilsManager();
                }
            }
        }
        return mManager;
    }

    public void baseRegister(Context context) {
        if (EmptyAndSizeUtils.isEmpty(this.mContext)) {
            this.mContext = context;
        }
    }

    public ColorUtils getColorUtils() {
        if (EmptyAndSizeUtils.isEmpty(mColorUtils)) {
            mColorUtils = new ColorUtils();
        }
        return mColorUtils;
    }
}
