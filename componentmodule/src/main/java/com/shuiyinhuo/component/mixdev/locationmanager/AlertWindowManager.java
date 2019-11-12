package com.shuiyinhuo.component.mixdev.locationmanager;

import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/8/6 0006
 * @ Description：
 * =====================================
 */
public class AlertWindowManager {
    public  interface AlertWindowInterface{
        boolean onConfirm(WebView view, String message);
        boolean onJsPrompt(WebView view, String message);
        boolean onJsAlert(WebView view, String message);
        void onPageFinished(WebView view, String message);
        void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error);
        void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error);
    }
}
