package com.shuiyinhuo.component.mixdev.dispatcher.observe;

import android.webkit.WebView;

import com.shuiyinhuo.component.mixdev.caller.JSFunHunterProxy;
import com.shuiyinhuo.component.mixdev.caller.OnJSCarryValuesBackInterface;
import com.shuiyinhuo.component.mixdev.utils.comm.EmptyAndSizeUtils;
import com.shuiyinhuo.component.mixdev.utils.comm.JNILog;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/8/24 0024
 * @ Description：方法回调猎人，开发者主要通过该类类调用对应Webview的方法
 * =====================================
 */
public class FunctionHunter {
    private JSFunHunterProxy mFunHunterProxy;

    public FunctionHunter(JSFunHunterProxy funHunterProxy) {
        mFunHunterProxy = funHunterProxy;
    }

    /**
     * @param jsMethodName js 中要被掉的方法
     */
    public void callJs(String Alias,String jsMethodName) {
        if (EmptyAndSizeUtils.isNotEmpty(mFunHunterProxy)){
            mFunHunterProxy.callJs(Alias,jsMethodName);
        }else {
            JNILog.e("--------------> js call failed，JSFunHunterProxy is NullPointer ...  ");
        }
    }


    /**
     * @param jsMethodName js 中要被掉的方法
     * @param _json        要被传递的参数
     */
    public void callJsByArgs(String Alias,String jsMethodName, String _json) {
        if (EmptyAndSizeUtils.isNotEmpty(mFunHunterProxy)){
            mFunHunterProxy.callJsByArgs(Alias,jsMethodName,_json);
        }else {
            JNILog.e("--------------> js call failed，JSFunHunterProxy is NullPointer ...  ");
        }
    }



    public void _registerCallJs(String Alias,String jsMethodName, final String _json) {
        if (EmptyAndSizeUtils.isNotEmpty(mFunHunterProxy)){
            mFunHunterProxy.callJsByArgs(Alias,jsMethodName,_json);
        }else {
            JNILog.e("--------------> js call failed，JSFunHunterProxy is NullPointer ...  ");
        }
    }





    /**
     * 通用的 加载方法
     *
     * @param _json 要被传递的参数
     */
    public void callJsByArgs(String Alias,String _json) {
        if (EmptyAndSizeUtils.isNotEmpty(mFunHunterProxy)){
            mFunHunterProxy.callJsByArgs(Alias,_json);
        }else {
            JNILog.e("--------------> js call failed，JSFunHunterProxy is NullPointer ...  ");
        }

    }



    public void callJsWithBackValues(String Alias, OnJSCarryValuesBackInterface backValueCall, String jsMethodName,String... data) {
        if (EmptyAndSizeUtils.isNotEmpty(mFunHunterProxy)){
            mFunHunterProxy.callJsWithBackValues(Alias,backValueCall,jsMethodName,data);
        }else {
            JNILog.e("--------------> js call failed，JSFunHunterProxy is NullPointer ...  ");
        }

    }

    public void com_loadUrlToWeb(WebView webView,String url){
        if (EmptyAndSizeUtils.isNotEmpty(url)&&EmptyAndSizeUtils.isNotEmpty(webView)){
            if (EmptyAndSizeUtils.isNotEmpty(mFunHunterProxy)){
                mFunHunterProxy.com_loadUrlToWeb(webView,url);
            }else {
                JNILog.e("--------------> js call failed，JSFunHunterProxy is NullPointer ...  ");
            }
        }
    }

}
