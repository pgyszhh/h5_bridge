package com.shuiyinhuo.component.mixdev.jinbean.pro;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.text.Spanned;
import android.webkit.WebView;

import com.shuiyinhuo.component.mixdev.activity.ProxyWebViewActivity;
import com.shuiyinhuo.component.mixdev.caller.OnJSCarryValuesBackInterface;
import com.shuiyinhuo.component.mixdev.locationmanager.UtilsManager;
import com.shuiyinhuo.component.mixdev.utils.comm.EmptyAndSizeUtils;
import com.shuiyinhuo.component.mixdev.utils.comm.JNILog;
import com.shuiyinhuo.component.mixdev.utils.comm.ToastManager;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/8/6 0006
 * @ Description：
 * =====================================
 */
public class BaseJNI {
    //通信状态是否可用
    public static boolean isCanSignalCommunication = false;
    private Handler mHandler;

    protected ToastManager mToastManager;
    protected String _json = null;
    protected Context context;
    private WebView mWebView;



    protected Handler getHandler() {
        return mHandler;
    }

    public BaseJNI() {
        mHandler = new Handler();
        initContext();
    }

    public WebView getWebView() {
        return mWebView;
    }

    public void setWebView(WebView webView) {
        mWebView = webView;
    }



    protected void initContext() {
        mToastManager = UtilsManager.getInstance().getToastManager();
    }


    /**
     * @param jsMethodName js 中要被掉的方法
     */
    public void callJs(final String jsMethodName) {
        if (isCanCalled()) {
            innerCallJs(getWebView(), jsMethodName);
        }
    }


    /**
     * @param jsMethodName js 中要被掉的方法
     * @param _json        要被传递的参数
     */
    public void callJsByArgs(final String jsMethodName, final String _json) {
        if (isCanCalled()) {
            JNILog.e("-------------------------> start dispather WebView events.....");
            innerCallJs(getWebView(), jsMethodName, _json);
        } else {
            JNILog.e("-------------------------> Conn't dispather WebView events.....");
        }
    }


    /**
     * 使用指定的 WebView回调
     *
     * @param
     * @param jsMethodName
     * @param _json
     */
    public void _registerCallJs(final String jsMethodName, final String _json) {
        if (isCanCalled()) {
            if (EmptyAndSizeUtils.isNotEmpty(getWebView())) {
                innerCallJs(getWebView(), jsMethodName, _json);
                JNILog.e("-------------------------> start call WebView for register succ ");
            } else {
                JNILog.e("-------------------------> register WebView is NoPointer ");
            }
        } else {
            JNILog.e("-------------------------> start call WebView for register failed");
        }
    }

    /**
     * 内部调用
     *
     * @param web
     * @param jsMethodName
     * @param _json
     */
    private void innerCallJs(final WebView web, final String jsMethodName, final String _json) {
        if (EmptyAndSizeUtils.isEmpty(jsMethodName)) {
            JNILog.e("---------------------------> javascript method function Name is NullPoint，can't called！");
            return;
        }
        if (EmptyAndSizeUtils.isNotEmpty(web)) {
            if (EmptyAndSizeUtils.isNotEmpty(getHandler())) {
                getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        JNILog.e(jsMethodName + " ---- " + _json);
                        web.loadUrl("javascript:" + jsMethodName + "('" + _json + "')");
                    }
                });
            } else {
                JNILog.e("=======================>can't called ，hander is NullPoint");
            }
        }
    }

    /**
     * 内部调用，五参数方法
     *
     * @param jsMethodName
     */
    private void innerCallJs(final WebView webView, final String jsMethodName) {
        if (EmptyAndSizeUtils.isEmpty(jsMethodName)) {
            JNILog.e("---------------------------> javascript method function Name is NullPoint，can't called！");
            return;
        }
        getHandler().post(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl("javascript:" + jsMethodName + "('')");
            }
        });
    }

    /**
     * 内部调用，五参数方法
     *
     * @param _json
     */
    private void innerCallJsDefault(final WebView webView, final String _json) {
        getHandler().post(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl("javascript:showJson('" + _json + "')");
            }
        });
    }


    /**
     * 通用的 加载方法
     *
     * @param _json 要被传递的参数
     */
    public void callJsByArgs(final String _json) {
        if (isCanCalled()) {
            innerCallJsDefault(getWebView(), _json);
        }
    }

    private boolean isCanCalled() {
        return EmptyAndSizeUtils.isNotEmpty(getWebView());
    }



    public void callJsWithBackValues(String methodName, String... data) {
        callJsWithValuesBackFromJs();
        startRequest(methodName, data);
    }



    /**
     * 带参数返回值请求
     *
     * @param methodName
     * @param data
     */
    private void startRequest(String methodName, String... data) {
        String newParams = "";
        if (EmptyAndSizeUtils.isNotEmpty(data)) {
            for (int i = 0; i < data.length; i++) {
                if (i != data.length - 1) {
                    newParams += data[i] + "-v-";
                } else {
                    newParams += data[i];
                }
            }
            ;
        }
        String params = "{\"methodName\":\"" + methodName.trim() + "\",\"data\":\"" + newParams + "\"}";
        JNILog.e("调用入参：" + params);
        callJsByArgs("AndroidCallJsRequestBackValuesBridge", params);
    }


    /**
     * 调用js中的方法，有返回值
     * 掉用js中的函数
     *
     * @methodName js 中定义的方法名
     */
    private void callJsWithValuesBackFromJs() {
        JNILog.e("通信状态监测：" + isCanSignalCommunication);
        if (!isCanSignalCommunication) {
            String msg = "<b>无法与js通信</b><br/><br/>" +
                    "<font color='red'>1、请检查是否正提供了正确的url地址；<br/><br/>" +
                    "2、在地址对应的页面是否包含了 'bridgeproxy.js '文件；<br/><br/>" +
                    "3、请检查是否在 initJNIConfig() 始化方法中创建 JNIManager实例对象并返回</font>";
            Spanned mSpanned = UtilsManager.getInstance().getHtmlUtilsController().formatHtmlForSpanned(msg);
            mToastManager.showAlert(mSpanned);
        }
    }




    public ToastManager getToastManager() {
        return mToastManager;
    }
}
