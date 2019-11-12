package com.shuiyinhuo.component.mixdev.demo;

import android.webkit.WebView;

import com.shuiyinhuo.component.mixdev.activity.ProxyWebViewActivity;
import com.shuiyinhuo.component.mixdev.caller.OnJSUtilsCarryValuesBackInterface;
import com.shuiyinhuo.component.mixdev.dispatcher.observe.FunctionHunter;
import com.shuiyinhuo.component.mixdev.dispatcher.observe.INetStater;
import com.shuiyinhuo.component.mixdev.jinbean.LayoutConfig;
import com.shuiyinhuo.component.mixdev.jinbean.WebJNISignalProxy;
import com.shuiyinhuo.component.mixdev.locationmanager.UtilsManager;
import com.shuiyinhuo.component.mixdev.utils.html.JavaCallJsUtils;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/8/27 0027
 * @ Description：测试原生与js交互实例
 * =====================================
 */
public class TestDate extends ProxyWebViewActivity {
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

    }

    @Override
    public void initDatas() {

    }

    @Override
    public String getPageNameOrUrl() {
        return "MyShowing";
    }

    @Override
    public WebJNISignalProxy getWebDataProxy() {
        WebJNISignalProxy mWebJNISignalProxy= new WebJNISignalProxy() {
                @Override
                public void commDispatcher(String Alias, String tagName, String data) {

                }

                @Override
                public boolean checkForJsCall(String Alias, String tagName, String _json) {
                    return false;
                }

                @Override
                public void submit(String Alias, String tagName, String _json) {
            }
        };
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
