package com.shuiyinhuo.component.mixdev.jinbean.js;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.text.Spanned;
import android.webkit.WebView;

import com.shuiyinhuo.component.mixdev.activity.ProxyWebViewActivity;
import com.shuiyinhuo.component.mixdev.caller.OnJSCarryValuesBackInterface;
import com.shuiyinhuo.component.mixdev.caller.OnJSUtilsCarryValuesBackInterface;
import com.shuiyinhuo.component.mixdev.locationmanager.UtilsManager;
import com.shuiyinhuo.component.mixdev.utils.comm.EmptyAndSizeUtils;
import com.shuiyinhuo.component.mixdev.utils.comm.JNILog;
import com.shuiyinhuo.component.mixdev.utils.comm.ToastManager;


/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/8/6 0006
 * @ Description：
 * =====================================
 */
public class JSJNI {
    //通信状态是否可用
    public  boolean isCanSignalCommunication = false;
    protected  WebView mWebView;
    protected  Context mActivity;
    private  Handler mHandler;
    protected ToastManager mToastManager;
    protected OnJSUtilsCarryValuesBackInterface backInterface;

    protected   Handler getHandler() {
        return mHandler;
    }

    protected void setHandler(Handler handler) {
        mHandler = handler;
        initContext();
    }


    protected void initContext() {
        if (EmptyAndSizeUtils.isNotEmpty(mWebView)) {
            mActivity = (Activity) mWebView.getContext();
            mToastManager=UtilsManager.getInstance().getToastManager();
        }
    }

    public WebView getWebView() {
        return mWebView;
    }

    public void setWView(WebView webView) {
        mWebView = webView;
    }



    /**
     * @param jsMethodName js 中要被掉的方法
     */
    public void callJs(final String jsMethodName) {
        if (isCanCalled()) {
            getHandler().post(new Runnable() {
                @Override
                public void run() {
                    mWebView.loadUrl("javascript:" + jsMethodName + "('')");
                }
            });

        }
    }

    private  boolean isCanCalled() {
        return EmptyAndSizeUtils.isNotEmpty(mWebView);
    }

    /**
     * @param jsMethodName js 中要被掉的方法
     * @param _json        要被传递的参数
     */
    public  void callJsByArgs(final String jsMethodName, final String _json) {
        if (isCanCalled()) {
            getHandler().post(new Runnable() {
                @Override
                public void run() {
                    JNILog.e(jsMethodName+" ---- "+_json);
                    mWebView.loadUrl("javascript:" + jsMethodName + "('" + _json + "')");
                }
            });

        }
    }



    public  void callJsWithBackValues(OnJSUtilsCarryValuesBackInterface backValueCall,String methodName, String ...data) {
        callJsWithValuesBackFromJs();
        backInterface=backValueCall;
        startRequest(methodName,data);
    }


    public  void callJsWithBackValues(String methodName,OnJSUtilsCarryValuesBackInterface backValueCall) {
        backInterface=backValueCall;
        callJsWithValuesBackFromJs();
        startRequest(methodName);
    }

    /**
     * 带参数返回值请求
     * @param methodName
     * @param data
     */
    private  void startRequest(String methodName, String ...data){
        String newParams= "";
        if (EmptyAndSizeUtils.isNotEmpty(data)){
            for (int i=0;i<data.length;i++){
                if (i !=data.length-1) {
                    newParams += data[i] + "-v-";
                }else {
                    newParams += data[i];
                }
            };
        }
        String params = "{\"methodName\":\""+methodName.trim()+"\",\"data\":\""+newParams+"\"}";
        JNILog.e("调用入参："+params);
        callJsByArgs("ToolsJavaCallJsRequestBackValuesBridge", params);
    }


    /**
     * 调用js中的方法，有返回值
     * 掉用js中的函数
     *
     * @methodName js 中定义的方法名
     */
    private  void callJsWithValuesBackFromJs() {
        JNILog.e("通信状态监测："+isCanSignalCommunication);
        if (!isCanSignalCommunication){
           String msg ="<b>无法与js通信</b><br/><br/>" +
                   "<font color='red'>1、请检查你 JavaCallJsUtils.registJs()注册是提供的js文件和路径是否正确；<br/><br/>" +
                   "2、请检查 'bridgeproxy.js '路径是否为默认或者提供的位置是否正确；<br/><br/>" +
                   "3、请检查你提供的路径是是否包含\"file:///协议\"或者\"file:///android_asset/\"路径；</font>";
            Spanned mSpanned = UtilsManager.getInstance().getHtmlUtilsController().formatHtmlForSpanned(msg);
            mToastManager.showAlert(mSpanned);
        }


    }

    public boolean onJsAlert(WebView view, String message) {
        String msg = "onJsAlert:\n\n" + "message = " + message;
        JNILog.e("参数：\n "+msg);
        mToastManager.showAlert(msg);
        return false;
    }

    /**
     * 内部调用
     *
     * @param web
     * @param jsMethodName
     * @param _json
     */
    private void innerCallJs(final WebView web, final String jsMethodName, final String _json) {
        if (EmptyAndSizeUtils.isEmpty(jsMethodName)){
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

}
