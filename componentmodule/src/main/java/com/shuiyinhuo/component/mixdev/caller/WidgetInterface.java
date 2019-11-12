package com.shuiyinhuo.component.mixdev.caller;

import android.webkit.WebView;

import com.shuiyinhuo.component.mixdev.dispatcher.observe.FunctionHunter;
import com.shuiyinhuo.component.mixdev.dispatcher.observe.INetStater;
import com.shuiyinhuo.component.mixdev.jinbean.WebJNISignalProxy;
import com.shuiyinhuo.component.mixdev.jinbean.pro.WidgetConfig;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/9/3 0003
 * @ Description：分离Activity 代理 Webview 交互组件
 * =====================================
 */
public interface WidgetInterface {
     /**
      * 创建代理
      * @return
      */
     WebJNISignalProxy getWebDataProxy();

     /**
      * 布局配置
      * @param config
      */
     void setLayoutResConfig(WidgetConfig config);

     /**
      * 网络状态监测
      * @param iNetStater
      */
     void monitorNetWork(INetStater iNetStater);
     /**
      *  错误页面加载
      * @param mGlobalFunHunter
      * @param webView
      * @param Alias
      */
     void onPageLoadError(FunctionHunter mGlobalFunHunter, WebView webView, String Alias);

     /**
      * 监测同步方法
      * @param Alias
      */
     void synchronizationLoadDatasToWebView(String Alias);

     /**
      * 函数捕猎器
      * @param funHunter
      */
     void JsHunting(FunctionHunter funHunter);

     /**
      *  页面开始加载展示 loading
      */
      void showLoading();

     /**
      *  销毁 loading
      */
      void dismissLoading();
}
