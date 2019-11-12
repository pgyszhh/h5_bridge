package com.shuiyinhuo.component.mixdev.dispatcher.dispatch;

import android.webkit.WebView;

import com.shuiyinhuo.component.mixdev.caller.JSFunHunterProxy;
import com.shuiyinhuo.component.mixdev.caller.OnJSCarryValuesBackInterface;
import com.shuiyinhuo.component.mixdev.caller.WebPageStateInterface;
import com.shuiyinhuo.component.mixdev.jinbean.WebJNISignalProxy;
import com.shuiyinhuo.component.mixdev.jinbean.pro.UrlBinder;
import com.shuiyinhuo.component.mixdev.jinbean.pro.WebBundleStorager;
import com.shuiyinhuo.component.mixdev.utils.comm.EmptyAndSizeUtils;
import com.shuiyinhuo.component.mixdev.utils.comm.JNILog;

import java.util.ArrayList;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/8/24 0024
 * @ Description：网页数据分发器，负责通知某个Webview加载数据
 * 或者向某个Webview加载数据
 * =====================================
 */
public class MessageDisapther implements JSFunHunterProxy {
    private WebBundleStorager mWebBundleStorager = WebBundleStorager.getInstance();
    private WebPageStateInterface mStateInterface;
    private WebJNISignalProxy mWebJNISignalProxy;
    public static final int ERROR_CODE = 0x125482;
    public static final int SUCC_CODE = 0x125483;


    /**
     * 标记Webview的tagName
     */
    public static int webTagName = "12345679".hashCode();

    /**
     * 轮询
     */
    public void loopMessage() {

    }


    public MessageDisapther(WebJNISignalProxy webJNISignalProxy) {
        mWebJNISignalProxy = webJNISignalProxy;
    }


    public void registerStateInterface(WebPageStateInterface stateInterface) {
        mStateInterface = stateInterface;
    }




    public void startNetWork(String Alias, int... outime) {

        ArrayList<UrlBinder> mBinderss = mWebBundleStorager.getBinder("demo1");

        ArrayList<UrlBinder> mBinders = mWebBundleStorager.getBinder(Alias);
        if (EmptyAndSizeUtils.isNotEmpty(mBinders)) {
            for (UrlBinder mBinder : mBinders) {
                mBinder.startNetWork(outime);
                JNILog.e("Net ", "----------> 已经设置了状态...... ");
                mBinder.showMsg();
            }
        } else {
            JNILog.e("Net ", "----------> not need to Connection NetWork!");
        }

        JNILog.e("Net ", "----------> demo1 信息: ");
        mBinderss.get(0).showMsg();
    }

    public void endNetWork(String Alias) {
        ArrayList<UrlBinder> mBinders = mWebBundleStorager.getBinder(Alias);
        if (EmptyAndSizeUtils.isNotEmpty(mBinders)) {
            for (UrlBinder mBinder : mBinders) {
                mBinder.setNetWorkQueryCompleted(true);
            }
        } else {
            JNILog.e("----------> not need to Discut  Connection NetWork!");
        }
    }

    public void updateState(WebView webView, int code) {
        Object mTag = webView.getTag(webTagName);
        if (EmptyAndSizeUtils.isNotEmpty(mTag)) {
            String alias = (String) mTag;
            ArrayList<UrlBinder> mBinders = mWebBundleStorager.getBinder(alias);
            if (EmptyAndSizeUtils.isNotEmpty(mBinders)) {
                for (UrlBinder mBinder : mBinders) {
                    switch (code) {
                        case SUCC_CODE:
                            JNILog.e("------------> change inflaterState SUCC_CODE");
                            if (webView == mBinder.getPageContainer()) {
                                mBinder.setPageInflaterError(false);
                                mBinder.setPageInflaterSucc(true);
                                break;
                            }
                            break;
                        case ERROR_CODE:
                            JNILog.e("------------> change inflaterState ERROR_CODE");
                            mBinder.setPageInflaterError(true);
                            mBinder.setPageInflaterSucc(false);
                            break;
                    }

                }
            }else {
                JNILog.e("--------------------> this Alias not find Alias = "+alias);
            }
        }else {
            JNILog.e("--------------------> this WebView not find  Alias !");
        }

}

    public String getAliasFromWebView(WebView webView) {
        if (EmptyAndSizeUtils.isNotEmpty(webView)) {
            Object mTag = webView.getTag(webTagName);
            String alias = (String) mTag;
            return alias;
        }
        return null;

    }


    @Override
    public void callJs(String alias, String jsMethodName) {
        ArrayList<UrlBinder> mBinder = mWebBundleStorager.getBinder(alias);
        if (EmptyAndSizeUtils.isNotEmpty(mBinder)) {
            for (UrlBinder mUrlBinder : mBinder) {
                mUrlBinder.getCommunicationProxy().callJs(jsMethodName);
            }
        } else {
            JNILog.e("------------> call js failed ! not find can call Entity;\n\n  alias = " + alias + "   jsMethodName = " + jsMethodName);
        }
    }

    @Override
    public void callJsByArgs(String alias, String jsMethodName, String _json) {
        ArrayList<UrlBinder> mBinder = mWebBundleStorager.getBinder(alias);
        if (EmptyAndSizeUtils.isNotEmpty(mBinder)) {
            for (UrlBinder mUrlBinder : mBinder) {
                mUrlBinder.getCommunicationProxy().callJsByArgs(jsMethodName, _json);
            }
        } else {
            JNILog.e("------------> call js failed ! not find can call Entity;\n\n  alias = " + alias + "   jsMethodName = " + jsMethodName);
        }
    }

   /* @Override
    public void _registerCallJs(String alias, String jsMethodName, String _json) {
        ArrayList<UrlBinder> mBinder = mWebBundleStorager.getBinder(alias);
        if (EmptyAndSizeUtils.isNotEmpty(mBinder)){
            for (UrlBinder mUrlBinder:mBinder){
                mUrlBinder.getCommunicationProxy()._registerCallJs(jsMethodName,_json);
            }
        }else {
            JNILog.e("------------> call js failed ! not find can call Entity;\n\n  alias = "+ alias+"   jsMethodName = "+jsMethodName);
        }
    }*/

    @Override
    public void callJsByArgs(String alias, String _json) {
        ArrayList<UrlBinder> mBinder = mWebBundleStorager.getBinder(alias);
        if (EmptyAndSizeUtils.isNotEmpty(mBinder)) {
            for (UrlBinder mUrlBinder : mBinder) {
                mUrlBinder.getCommunicationProxy().callJsByArgs(_json);
            }
        } else {
            JNILog.e("------------> call js failed ! not find can call Entity;\n\n  alias = " + alias + "   _json = " + _json);
        }
    }

    @Override
    public void callJsWithBackValues(String alias, OnJSCarryValuesBackInterface backValueCall, String methodName, String... data) {
        ArrayList<UrlBinder> mBinder = mWebBundleStorager.getBinder(alias);
        if (EmptyAndSizeUtils.isNotEmpty(mBinder)) {
            for (UrlBinder mUrlBinder : mBinder) {
                if (EmptyAndSizeUtils.isNotEmpty(mWebJNISignalProxy)) {
                    mWebJNISignalProxy.setBackInterface(backValueCall);
                    mUrlBinder.getCommunicationProxy().callJsWithBackValues(methodName, data);
                } else {
                    JNILog.e("--------------> backvalue call failed，WebJNISignalProxy is NullPointer .... ");
                }

            }
        } else {
            JNILog.e("------------> call js failed ! not find can call Entity;\n\n  alias = " + alias + "   jsMethodName = " + methodName);
        }
    }

    @Override
    public void com_loadUrlToWeb(WebView webView, String url) {
        String mAliasFromWebView = getAliasFromWebView(webView);
        if (EmptyAndSizeUtils.isNotEmpty(mAliasFromWebView)){
            ArrayList<UrlBinder> mBinder = mWebBundleStorager.getBinder(mAliasFromWebView);
            if (EmptyAndSizeUtils.isNotEmpty(mBinder)){
                for (UrlBinder myBinder:mBinder){
                    if (myBinder.getPageContainer() == webView){
                        myBinder.getWebViewProxy().loadUrl(url);
                    }
                }
            }
        }else {
            JNILog.e("----------------->can't load url to Webview!");
        }

    }

    public WebBundleStorager getWebBundleStorager() {
        return mWebBundleStorager;
    }
}
