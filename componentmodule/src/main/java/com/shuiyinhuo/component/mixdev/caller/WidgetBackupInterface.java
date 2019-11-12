package com.shuiyinhuo.component.mixdev.caller;

import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;

import com.shuiyinhuo.component.mixdev.jinbean.pro.BridgeBinderAlias;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/9/5 0005
 * @ Description：组件备用信息服务接口
 * =====================================
 */
public interface WidgetBackupInterface {

    /**
     * 某个Webview加载完成
     * @param Alias 别名
     * @param view Webview
     * @param message
     */
    void onOneOfPagesFinished(String Alias, WebView view, String message);

    /**
     *    某个Webview加载错误
     * @param Alias
     * @param view
     * @param request
     * @param error
     * @param desMsg
     */
    void onOneOfPagesError(String Alias, WebView view, WebResourceRequest request, WebResourceError error, String desMsg);

    /**
     *   处理SSL加载异常
     * @param alias
     * @param view
     * @param handler
     * @param error
     * @param desMsg
     */
    void onReceivedSslError(String alias, WebView view, SslErrorHandler handler, SslError error, String desMsg);

    /**
     *  为当前WebView绑定别名，基本不需要
     * @param jniBinderProxy
     */
    void bindJNIEntity(BridgeBinderAlias jniBinderProxy);
}
