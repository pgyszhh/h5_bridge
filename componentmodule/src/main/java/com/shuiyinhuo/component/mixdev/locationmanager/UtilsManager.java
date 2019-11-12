package com.shuiyinhuo.component.mixdev.locationmanager;

import android.content.Context;
import android.view.View;

import com.shuiyinhuo.component.mixdev.cnf.bridge.BridgeTagFactory;
import com.shuiyinhuo.component.mixdev.cnf.comm.ConfigForJavaToJs;
import com.shuiyinhuo.component.mixdev.cnf.comm.HtmlUtilsController;
import com.shuiyinhuo.component.mixdev.utils.comm.ToastManager;
import com.shuiyinhuo.component.mixdev.utils.comm.ViewFinder;
import com.shuiyinhuo.component.mixdev.utils.html.JavaCallJsUtils;
import com.shuiyinhuo.component.mixdev.utils.comm.EmptyAndSizeUtils;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/8/18 0018
 * @ Description：所有工具的管理类
 * =====================================
 */
public class UtilsManager {
    private static UtilsManager mManager = new UtilsManager();
    /**
     * Html 管理工具类
     */
    private HtmlUtilsController mUtilsController;
    /**
     * 随机生成 Android 与js 通信桥接别名工具类
     */
    private BridgeTagFactory mBridgeTagFactory;
    /**
     * js工具类，通过java直接操作js中的方法
     */
    private JavaCallJsUtils mJsUtils;
    /**
     * 寻找渲染View帮助类
     */
    private ViewFinder mFinder;

    /**
     * 提示信息辅助类
     */
    private ToastManager mToastManager;
    /**
     * 动态桥接注册页面
     */
    private ConfigForJavaToJs mConfigForJavaToJs;
    private Context mContext;

    private UtilsManager() {

    }

    public static UtilsManager getInstance() {
        if (EmptyAndSizeUtils.isEmpty(mManager)) {
            synchronized (UtilsManager.class) {
                if (EmptyAndSizeUtils.isEmpty(mManager)) {
                    mManager = new UtilsManager();
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

    public HtmlUtilsController getHtmlUtilsController() {
        if (EmptyAndSizeUtils.isEmpty(mUtilsController)) {
            mUtilsController = new HtmlUtilsController();
        }
        return mUtilsController;
    }

    public BridgeTagFactory getBridgeTagFactory() {
        if (EmptyAndSizeUtils.isEmpty(mBridgeTagFactory)) {
            mBridgeTagFactory = new BridgeTagFactory();
        }
        return mBridgeTagFactory;
    }

    public JavaCallJsUtils getJsUtils() {
        if (EmptyAndSizeUtils.isEmpty(mJsUtils)) {
            mJsUtils = new JavaCallJsUtils();
        }
        return mJsUtils;
    }

    public ViewFinder getFinder() {
        if (EmptyAndSizeUtils.isEmpty(mFinder)) {
            mFinder = new ViewFinder();
        }
        return mFinder;
    }

    public ToastManager getToastManager() {
        if (EmptyAndSizeUtils.isEmpty(this.mContext)) {
            throw new NullPointerException("Context is NullPointer, Please call UtilsManager.getInstance().baseRegister(Contex) to Regist");
        }
        if (EmptyAndSizeUtils.isEmpty(mToastManager)) {
            mToastManager = new ToastManager(this.mContext);
        }

        return mToastManager;
    }


    public ConfigForJavaToJs getConfigForJavaToJs() {
        if (EmptyAndSizeUtils.isEmpty(mConfigForJavaToJs)) {
            mConfigForJavaToJs = new ConfigForJavaToJs();
        }
        return mConfigForJavaToJs;
    }
}
