package com.shuiyinhuo.component.mixdev.demo;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;

import com.shuiyinhuo.component.mixdev.abs.ProxyOnclickListener;
import com.shuiyinhuo.component.mixdev.activity.ProxyBaseActivityOfDialogWindow;
import com.shuiyinhuo.component.mixdev.caller.BackListenerInterface;
import com.shuiyinhuo.component.mixdev.caller.OnJSCarryValuesBackInterface;
import com.shuiyinhuo.component.mixdev.caller.WidgetBtnConfigProxyInterface;
import com.shuiyinhuo.component.mixdev.caller.WidgetInterface;
import com.shuiyinhuo.component.mixdev.dispatcher.observe.FunctionHunter;
import com.shuiyinhuo.component.mixdev.dispatcher.observe.INetStater;
import com.shuiyinhuo.component.mixdev.jinbean.WebJNISignalProxy;
import com.shuiyinhuo.component.mixdev.jinbean.pro.WidgetConfig;
import com.shuiyinhuo.component.mixdev.jinbean.pro.WindowButtonBaseConfig;
import com.shuiyinhuo.component.mixdev.utils.comm.EmptyAndSizeUtils;
import com.shuiyinhuo.component.mixdev.entity.WindowBtnConfig;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/9/4 0004
 * @ Description：
 * =====================================
 */
public class WindowProxyTest extends ProxyBaseActivityOfDialogWindow implements WidgetInterface {
    @Override
    public int getResId() {
        return /*R.layout.widget_test*/0;
    }



    @Override
    public WidgetBtnConfigProxyInterface setButtonConfig() {
        return new WidgetBtnConfigProxyInterface() {
            @Override
            public void setLeftButtonConfig(WindowBtnConfig leftButtonConfig) {
                leftButtonConfig.setButtonText("哈哈哈哈");
                leftButtonConfig.setBackgroundColor("#008B8B");
                leftButtonConfig.setOnclickListener(new ProxyOnclickListener() {
                    @Override
                    public void onClick(View view, int id) {
                        showToast("哈哈哈");
                    }
                });
            }

            @Override
            public void setRightButtonConfig(WindowBtnConfig rightButtonConfig) {
                rightButtonConfig.setButtonText("妮妮");
                rightButtonConfig.setBackgroundColor("#DC143C");
                rightButtonConfig.setOnclickListener(new ProxyOnclickListener() {
                    @Override
                    public void onClick(View view, int id) {
                        showToast("哈哈哈哈哈哈或或");
                    }
                });
            }

            @Override
            public void setWindBaseConfig(WindowButtonBaseConfig baseConfig) {

            }
        };
    }

    @Override
    public void initEvents() {
        setBackPressListener(new BackListenerInterface() {
            @Override
            public void onKeyDownBack(Activity activity) {
                showToast("返回");
            }

            @Override
            public boolean isCanDismissWindow() {
                return false;
            }
        });
    }

    @Override
    public WebJNISignalProxy getWebDataProxy() {
        return new WebJNISignalProxy() {
            @Override
            public void commDispatcher(String Alias, String tagName, String data) {
                showToast(Alias+"  "+data);
                if (TextUtils.equals(tagName,"1")){
                    mWidgetProxy.callJsWithBackValues(Alias, new OnJSCarryValuesBackInterface() {
                        @Override
                        public void onSucc(String Alias, String backValue) {

                        }

                        @Override
                        public void onfailed(String Alias, String error) {

                        }
                    },"mybool","true");
                }
            }

            @Override
            public boolean checkForJsCall(String Alias, String tagName, String _json) {
                return false;
            }

            @Override
            public void submit(String Alias, String tagName, String _json) {

            }

            @Override
            public void closeWindow(String alias) {
                finish();
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
    public void showLoading() {
        super.showLoading();
    }

    @Override
    public void dismissLoading() {
        super.dismissLoading();
    }

    @Override
    public void initView() {
        registJavaScript(this);
        //setBottomButton("#008B8B","#A8A8A8");
        //setBottomButton("#DC143C","#2E8B57","#DCDCDC");
        //设置底部边框背景颜色
        setFooterBackgroundBorderColor("#008B8B");
        //设置顶部边框背景颜色
        //setHeaderBackgroundBorderColor("#008B8B");
    }


    @Override
    public void setLayoutResConfig(WidgetConfig config) {
        if (EmptyAndSizeUtils.isNotEmpty(getDefaultWebView())) {
            config.bindUrlToWebView(getDefaultWebView(), "Window", "ssssss");
        }
    }

    @Override
    public void onPageLoadError(FunctionHunter mGlobalFunHunter, WebView webView, String Alias) {

    }


}
