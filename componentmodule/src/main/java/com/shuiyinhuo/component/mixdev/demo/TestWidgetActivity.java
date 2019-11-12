package com.shuiyinhuo.component.mixdev.demo;

import android.app.Activity;
import android.net.http.SslError;
import android.os.Bundle;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;

import com.shuiyinhuo.component.R;
import com.shuiyinhuo.component.mixdev.caller.WidgetBackupInterface;
import com.shuiyinhuo.component.mixdev.jinbean.pro.WidgetConfig;
import com.shuiyinhuo.component.mixdev.proxy.ProxyWidgetForWebView;
import com.shuiyinhuo.component.mixdev.caller.OnJSCarryValuesBackInterface;
import com.shuiyinhuo.component.mixdev.caller.WidgetInterface;
import com.shuiyinhuo.component.mixdev.dispatcher.observe.FunctionHunter;
import com.shuiyinhuo.component.mixdev.dispatcher.observe.INetStater;
import com.shuiyinhuo.component.mixdev.jinbean.WebJNISignalProxy;
import com.shuiyinhuo.component.mixdev.jinbean.pro.BridgeBinderAlias;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/9/3 0003
 * @ Description： 测试Webview 加载html，js与原生组件通信
 * =====================================
 */
public class TestWidgetActivity extends Activity implements WidgetInterface, WidgetBackupInterface {
    private WebView mWebView;
    private ProxyWidgetForWebView mWidgetWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_layout);
        mWebView = findViewById(R.id.wb);
        mWidgetWebView = ProxyWidgetForWebView.getInstance();

        //mWidgetWebView.register(this, this);
        mWidgetWebView.register2(this, this, this);
    }

    @Override
    public WebJNISignalProxy getWebDataProxy() {
        return new WebJNISignalProxy() {
            @Override
            public void commDispatcher(String Alias, String tagName, String data) {
                mToastManager.showToast(tagName + "  > " + data);
                switch (tagName) {
                    case "1":
                        mWidgetWebView.callJsWithBackValues(mWidgetWebView.getDefaultAlias(), new OnJSCarryValuesBackInterface() {
                            @Override
                            public void onSucc(String Alias, String backValue) {
                                mToastManager.showToast(backValue);
                            }

                            @Override
                            public void onfailed(String Alias, String error) {

                            }
                        },"test", "100");
                        break;
                    case "2":

                        break;
                }


            }

            @Override
            public boolean checkForJsCall(String Alias, String tagName, String _json) {
                return false;
            }

            @Override
            public void submit(String Alias, String tagName, String _json) {

                mToastManager.showToast(tagName + "  " + _json);
            }
        };
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

    @Override
    public void setLayoutResConfig(WidgetConfig config) {
        //config.bindUrlToWebView(mWebView, "WidgetTest", "WidgetTest");
        config.bindUrlToWebView(mWebView, "power_interaction", "WidgetTest");
    }

    @Override
    public void onPageLoadError(FunctionHunter mGlobalFunHunter,WebView webView, String Alias) {
        mWidgetWebView.getToastManager().showToast("onPageLoadError");
    }


    @Override
    public void onOneOfPagesFinished(String Alias, WebView view, String message) {
        //mWidgetWebView.getToastManager().showToast("onOneOfPagesFinished");
    }

    @Override
    public void onOneOfPagesError(String Alias, WebView view, WebResourceRequest request, WebResourceError error, String desMsg) {
        mWidgetWebView.getToastManager().showToast("onOneOfPagesError   "+desMsg);
    }

    @Override
    public void onReceivedSslError(String alias, WebView view, SslErrorHandler handler, SslError error, String desMsg) {
        mWidgetWebView.getToastManager().showToast("onReceivedSslError   "+desMsg);
    }

    @Override
    public void bindJNIEntity(BridgeBinderAlias jniBinderProxy) {

    }

    @Override
    public void showLoading() {
        mWidgetWebView.getToastManager().showToast("showLoading ....");
    }

    @Override
    public void dismissLoading() {
        mWidgetWebView.getToastManager().showToast("dismissLoading ....");
    }



}
