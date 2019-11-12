package com.shuiyinhuo.component.mixdev.jinbean.js;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import com.shuiyinhuo.component.mixdev.caller.OnJSCarryValuesBackInterface;
import com.shuiyinhuo.component.mixdev.caller.OnJSUtilsCarryValuesBackInterface;
import com.shuiyinhuo.component.mixdev.utils.comm.EmptyAndSizeUtils;
import com.shuiyinhuo.component.mixdev.utils.comm.JNILog;

import java.io.Serializable;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/8/6 0006
 * @ Description：JNI 回调桥接基类
 * =====================================
 */

@SuppressLint("JavascriptInterface")
public class JsJniEntity extends JSJNI implements Serializable {
    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    private Context mContext;

    public void setHandler(Handler handler) {
        super.setHandler(handler);
    }



    public OnJSUtilsCarryValuesBackInterface getBackValueCall() {
        return backInterface;
    }

    public void setBackValueCall(OnJSUtilsCarryValuesBackInterface backValueCall) {
        this.backInterface = backValueCall;
    }



    public WebView getWebView() {
        return mWebView;
    }

    public JsJniEntity setWebView(WebView webView) {
        setWView(webView);
        initContext();
        return this;
    }


    public JsJniEntity(WebView webView) {
        mWebView = webView;
    }


    /**
     * js 给Android 返回值回调的方法
     *
     * @param
     * @param data
     * @return
     */
    @JavascriptInterface
    public void runOnAndroidWhenGoHomeFromJsWithVales(final String data, final String error) {
        JNILog.e("tools   runOnAndroidWhenGoHomeFromJsWithVales");
       if (EmptyAndSizeUtils.isNotEmpty(getHandler())){
            getHandler().post(new Runnable() {
                @Override
                public void run() {
                    if (EmptyAndSizeUtils.isNotEmpty(getBackValueCall())){
                        JNILog.e("工具类方法被掉");
                        if (EmptyAndSizeUtils.isNotEmpty(data)){
                            getBackValueCall().onSucc(data);
                        }else{
                            getBackValueCall().onfailed(error);
                        }

                    }else {
                        mToastManager.showAlert("工具类提示","当前工具类缺少回调 OnJSCarryValuesBackInterface，请检查在调用是是否传入回调接口；"+"\n\n数据信息："+data + "\n\n错误信息： " + error);
                    }
                }
            });
        }else {
           mToastManager.showAlert("工具类提示","当前工具类缺少Handler，请实例化"+"\n\n数据信息："+data + "\n\n错误信息： " + error);
       }

    }



    //为安卓中提供 Toast 弹框使用
    @JavascriptInterface
    public void runOnAndroidForToast(String msg) {
        JNILog.e("tools  runOnAndroidForToast");
        if (EmptyAndSizeUtils.isNotEmpty(msg)) {
            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(mContext, "工具空提示信息："+msg, Toast.LENGTH_SHORT).show();
        }
    }


}
