package com.shuiyinhuo.component.mixdev.caller;

import android.webkit.WebView;

import com.shuiyinhuo.component.mixdev.dispatcher.observe.INetStater;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/8/22 0022
 * @ Description：loading 状态回调接口
 * =====================================
 */
public interface WebPageStateInterface {
    void showLoading();
    void dismissLoading();
    void showWindow(Object title,Object content);
    void dismissWindow();
    boolean isCanBack();
  /*  void onPageInflaterCompleted(String Alias);*/
    void onPageLoadError(WebView webView,String Alias);
    void monitorNetWork(INetStater iNetStater);
    void synchronizationLoadDatasToWebView(String Alias);


}
