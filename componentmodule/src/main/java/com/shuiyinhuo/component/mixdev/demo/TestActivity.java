package com.shuiyinhuo.component.mixdev.demo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import com.shuiyinhuo.component.mixdev.activity.ProxyWebViewActivity;
import com.shuiyinhuo.component.mixdev.caller.OnJSCarryValuesBackInterface;
import com.shuiyinhuo.component.mixdev.dispatcher.observe.FunctionHunter;
import com.shuiyinhuo.component.mixdev.dispatcher.observe.INetStater;
import com.shuiyinhuo.component.mixdev.jinbean.LayoutConfig;
import com.shuiyinhuo.component.mixdev.jinbean.pro.AndroidToJsSignalCommunicationProxy;
import com.shuiyinhuo.component.mixdev.jinbean.WebJNISignalProxy;
import com.shuiyinhuo.component.mixdev.jinbean.pro.BridgeBinderAlias;


/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/8/6 0006
 * @ Description：测试与Webview交互的Activity实例
 * =====================================
 */
public class TestActivity extends ProxyWebViewActivity {
    private WebJNISignalProxy mDataProxy;

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
        return "Test.html";
    }

    @Override
    public WebJNISignalProxy getWebDataProxy() {
        mDataProxy = new WebJNISignalProxy() {




            @Override
            public boolean onConfirm(String Alias, WebView view, String message) {
                showToast(message);
                return false;
            }

            @Override
            public boolean onJsPrompt(String Alias, WebView view, String message) {
                showToast(message);
                return false;
            }

            @Override
            public boolean onJsAlert(String Alias, WebView view, String message) {
                showToast(message);
                return false;
            }



            @Override
            public void showMsgWindow(String Alias, String tagName,String content) {
                showToast(tagName+"  "+content);
            }

            @Override
            public void startUploadImage(String Alias, String targetName, String index) {

            }

            @Override
            public void commDispatcher(String Alias, String tagName, String data) {
               // showToast(Alias+"  "+tagName+"         "+data);
                if (isTagName(tagName,"1")){
                   callJsWithBackValues(Alias, new OnJSCarryValuesBackInterface() {
                       @Override
                       public void onSucc(String Alias, String backValue) {
                           showToast("返回值:"+backValue);
                       }

                       @Override
                       public void onfailed(String Alias, String error) {
                           showToast("错误值:"+error);
                       }
                   }, "Shows", "false");
                }else if (isTagName(tagName,"2")){
                    callJs(Alias,"Shows","false");
                }else if (isTagName(tagName, "3")){
                    callJs(Alias,"Shows","False");
                }else if (isTagName(tagName,"4")){
                    callJs(Alias,"Shows","12345");
                }else if (isTagName(tagName,"5")){
                    callJs(Alias,"Shows","12dsg");
                }else if (isTagName(tagName,"6")){
                    callJs(Alias,"Shows","sgg12dsg");
                }else if (isTagName(tagName,"7")){
                    callJs(Alias,"Shows","12.5000");
                }
            }

            @Override
            public void submit(String Alias, String tagName, String _json) {
                String json = "[{\"name\":\"音乐\"},{\"name\":\"会话\"},{\"name\":,\"测试\"}]";
              //  callJsByDefault(json);
            }

            @Override
            public boolean checkForJsCall(String Alias, String tagName, String _json) {
                return false;
            }
        };
        return mDataProxy;
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



    @Override
    public void defaultSubmit(String tagName,String data) {
        Toast.makeText(this, "开始提交", Toast.LENGTH_SHORT).show();
        String json = "[{\"name\":\"音乐\"},{\"name\":\"会话\"},{\"name\":,\"雷彪\"}]";
       // callJsByDefault(json);
        super.defaultSubmit(tagName,data);
    }

    @NonNull
    @Override
    public LayoutInflater getLayoutInflater() {
        return super.getLayoutInflater();
    }

    /*@Override
    public void setLayoutResId(WidgetConfig config) {
        super.setLayoutResId(config);
        config.layoutId = R.layout.test_layout;
        config.webViewId = R.id.com_wb;
    }*/

    @Override
    public void setLayoutResConfig(LayoutConfig config) {

    }

    @Override
    public void bindJNIEntity(BridgeBinderAlias jniBinderProxy) {
        jniBinderProxy.bindJNIEntity("jnicall",new TesN(mContext));
    }



    @SuppressLint("JavascriptInterface")
    class TesN extends AndroidToJsSignalCommunicationProxy {

        public TesN(Context webView) {
            super(webView);

        }

        @JavascriptInterface
        public void showName(){
            Toast.makeText(TestActivity.this, "无参数方法被调用", Toast.LENGTH_SHORT).show();
        }

        @JavascriptInterface
        public void showName(String msg){
            Toast.makeText(TestActivity.this, "带参数的 "+ msg, Toast.LENGTH_SHORT).show();
        }
    }

}
