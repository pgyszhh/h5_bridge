package com.shuiyinhuo.component.mixdev.jinbean.pro;

import android.view.View;
import android.webkit.WebView;

import com.shuiyinhuo.component.mixdev.utils.comm.EmptyAndSizeUtils;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/8/6 0006
 * @ Description：布局配置参数
 * =====================================
 */
public class WidgetConfig {
    private com.shuiyinhuo.component.mixdev.jinbean.LayoutConfig mConfig;
    public WidgetConfig(com.shuiyinhuo.component.mixdev.jinbean.LayoutConfig mConfig){
        this.mConfig=mConfig;
    }
    /**
     * 绑定 url 到 Webview
     * <p>
     * 可以同时设置多个WebView 与 url，彼此绑定
     */
    public void bindUrlToWebView(WebView web, String url,String aliasKey) {
        if (EmptyAndSizeUtils.isNotEmpty(mConfig)) {
            mConfig.bindUrlToWebView(web, url,aliasKey);
        }

    }

}
