package com.shuiyinhuo.component.mixdev.caller;

import android.webkit.WebView;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/8/25 0025
 * @ Description：js方法转接中间桥梁
 * =====================================
 */
public interface JSFunHunterProxy {

    /**
     * @param jsMethodName js 中要被掉的方法
     */
    public void callJs(String alias, String jsMethodName);

    /**
     * @param jsMethodName js 中要被掉的方法
     * @param _json        要被传递的参数
     */
    public void callJsByArgs(String alias, String jsMethodName, String _json);

    /**
     * 通用的 加载方法
     *
     * @param _json 要被传递的参数
     */
    public void callJsByArgs(String alias, String _json);

    public void callJsWithBackValues(String alias, OnJSCarryValuesBackInterface backValueCall, String methodName, String... data);
    public void com_loadUrlToWeb(WebView webView, String url);
}
