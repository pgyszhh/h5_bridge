package com.shuiyinhuo.component.mixdev.proxy;

import android.app.Activity;

import com.shuiyinhuo.component.mixdev.caller.OnJSCarryValuesBackInterface;
import com.shuiyinhuo.component.mixdev.caller.WidgetBackupInterface;
import com.shuiyinhuo.component.mixdev.caller.WidgetInterface;
import com.shuiyinhuo.component.mixdev.dispatcher.observe.FunctionHunter;
import com.shuiyinhuo.component.mixdev.dispatcher.observe.INetStater;
import com.shuiyinhuo.component.mixdev.locationmanager.UtilsManager;
import com.shuiyinhuo.component.mixdev.utils.comm.EmptyAndSizeUtils;
import com.shuiyinhuo.component.mixdev.utils.comm.ToastManager;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/9/3 0003
 * @ Description：通用的组件Webview 代理
 * =====================================
 */
public class ProxyWidgetForWebView {
    private ProxyWidgetWebView mWidgetWebView;
    /**
     * 全局对象
     */
    protected INetStater mGlobalINetStater;
    protected FunctionHunter mGlobalFunHunter;
    private static ProxyWidgetForWebView mInstance = null;
    protected ToastManager mToastManager;

    private ProxyWidgetForWebView() {

    }

    public static ProxyWidgetForWebView getInstance() {
        if (EmptyAndSizeUtils.isEmpty(mInstance)) {
            synchronized (ProxyWidgetForWebView.class) {
                if (EmptyAndSizeUtils.isEmpty(mInstance)) {
                    mInstance = new ProxyWidgetForWebView();
                }
            }
        }
        return mInstance;
    }


    public void register(Activity activity, WidgetInterface mWidgetInterface) {
        mWidgetWebView = new ProxyWidgetWebView();
        mWidgetWebView.registerWebView(activity, mWidgetInterface);
    }

    public void register2(Activity activity, WidgetInterface mWidgetInterface, WidgetBackupInterface ...mWidgetBackupInterface) {
        if (EmptyAndSizeUtils.isEmpty(mWidgetWebView)) {
            mWidgetWebView = new ProxyWidgetWebView();
        }
        if (EmptyAndSizeUtils.isNotEmpty(mWidgetBackupInterface)&&mWidgetBackupInterface.length!=0) {
            mWidgetWebView.registComponentListener(mWidgetBackupInterface[0]);
        }
        mWidgetWebView.registerWebView(activity, mWidgetInterface);


    }


    public ToastManager getToastManager() {
        if (EmptyAndSizeUtils.isNotEmpty(mWidgetWebView)) {
            mToastManager = mWidgetWebView.getToastManager();
        }

        if (EmptyAndSizeUtils.isEmpty(mToastManager)) {
            mToastManager = UtilsManager.getInstance().getToastManager();
        }

        return mToastManager;
    }

    public void showWindow(Object title, Object content) {
        if (EmptyAndSizeUtils.isNotEmpty(mWidgetWebView)) {
            mWidgetWebView.showWindow(title, content);
        }
    }

    ;

    public INetStater getGlobalINetStater() {
        return mGlobalINetStater;
    }

    public FunctionHunter getGlobalFunHunter() {
        return mGlobalFunHunter;
    }

    /**
     * 带参数方法
     * 调用js中的带返回值的方法
     *
     * @param methodName js中的方法名
     * @param data       参数
     * @return
     */
    public void callJsWithBackValues(String alias, OnJSCarryValuesBackInterface backValueCall, String methodName, String... data) {
        if (EmptyAndSizeUtils.isNotEmpty(mWidgetWebView)) {
            mWidgetWebView.callJsWithBackValues(alias, backValueCall, methodName, data);
        }
    }


    /**
     * 不带参数方法
     * 掉用js中的函数
     *
     * @methodName js 中定义的方法名
     */
    public void callJs(String alias, String methodName) {
        if (!EmptyAndSizeUtils.isEmpty(mWidgetWebView)) {
            mWidgetWebView.callJs(alias, methodName);
        }
    }


    /**
     * 不带参数方法
     * 掉用js中的函数
     *
     * @methodName js 中定义的方法名
     */
    public void callJs(String alias, String methodName, String _jsonDatas) {
        if (!EmptyAndSizeUtils.isEmpty(mWidgetWebView)) {
            mWidgetWebView.callJs(alias, methodName, _jsonDatas);
        }

    }

    public String getDefaultAlias() {
        if (!EmptyAndSizeUtils.isEmpty(mWidgetWebView)) {
            return mWidgetWebView.getDefaultAlias();
        }
        return null;
    }


}
