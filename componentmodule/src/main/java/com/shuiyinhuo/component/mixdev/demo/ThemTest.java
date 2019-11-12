package com.shuiyinhuo.component.mixdev.demo;

import android.webkit.WebView;

import com.shuiyinhuo.component.mixdev.activity.ProxyWebViewActivity;
import com.shuiyinhuo.component.mixdev.dispatcher.observe.FunctionHunter;
import com.shuiyinhuo.component.mixdev.dispatcher.observe.INetStater;
import com.shuiyinhuo.component.mixdev.jinbean.LayoutConfig;
import com.shuiyinhuo.component.mixdev.jinbean.WebJNISignalProxy;
import com.shuiyinhuo.component.mixdev.locationmanager.ComponentUtilsManager;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/9/16 0016
 * @ Description：
 * =====================================
 */
public class ThemTest extends ProxyWebViewActivity {
    @Override
    public void setLayoutResConfig(LayoutConfig config) {

    }

    @Override
    public void initViews() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initAppThem() {
        setCustomerThemEnable(true);
        setAppThem("#4169E1");
    }

    @Override
    public void initDatas() {

    }

    @Override
    public String getPageNameOrUrl() {
        return null;
    }

    @Override
    public WebJNISignalProxy getWebDataProxy() {
        return null;
    }

    @Override
    public void onPageLoadError(WebView webView, String Alias) {

    }

    @Override
    public void monitorNetWork(INetStater iNetStater) {

    }

    @Override
    public void synchronizationLoadDatasToWebView(String Alias) {

    }

    @Override
    public void JsHunting(FunctionHunter funHunter) {

    }
}
