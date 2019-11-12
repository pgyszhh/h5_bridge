package com.shuiyinhuo.component.mixdev.jinbean.pro;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;


import com.shuiyinhuo.component.mixdev.activity.ProxyWebViewActivity;
import com.shuiyinhuo.component.mixdev.caller.OnJSCarryValuesBackInterface;
import com.shuiyinhuo.component.mixdev.jinbean.WebJNISignalProxy;
import com.shuiyinhuo.component.mixdev.locationmanager.JavaToJsManager;
import com.shuiyinhuo.component.mixdev.locationmanager.UtilsManager;
import com.shuiyinhuo.component.mixdev.utils.comm.EmptyAndSizeUtils;
import com.shuiyinhuo.component.mixdev.utils.comm.JNILog;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/8/6 0006
 * @ Description：JNI 回调桥接基类
 * =====================================
 */

@SuppressLint("JavascriptInterface")
public class AndroidToJsSignalCommunicationProxy extends BaseJNI implements Serializable {

    private WebJNISignalProxy mDataProxy;
    private Context mWebActivity;
    private String Alias = "";

    public String getAlias() {
        return Alias;
    }

    public void setAlias(String alias) {
        Alias = alias;
    }

    public AndroidToJsSignalCommunicationProxy(Context context) {
        super();
        this.context = context;
        initContext();
    }

    public ProxyWebViewActivity getWebActivity() {

        try {
            return  (ProxyWebViewActivity) mWebActivity;
        } catch (Exception e) {
            mWebActivity=null;
            return null;
        }
       /* return (ProxyWebViewActivity) mWebActivity;*/
    }

    public OnJSCarryValuesBackInterface getBackValueCall() {
        return mDataProxy.getBackInterface();
    }

    public void setBackValueCall(OnJSCarryValuesBackInterface backValueCall) {
        mDataProxy.setBackInterface(backValueCall);
    }

    public void setWebActivity(Context webActivity) {
        mWebActivity = webActivity;
    }

    public WebJNISignalProxy getDataProxy() {
        return mDataProxy;
    }

    public AndroidToJsSignalCommunicationProxy setDataProxy(WebJNISignalProxy dataProxy) {
        mDataProxy = dataProxy;
        return this;
    }


    @JavascriptInterface
    public void runOnAndroidJavaScript() {
        if (EmptyAndSizeUtils.isNotEmpty(getHandler())) {
            getHandler().post(new Runnable() {
                @Override
                public void run() {
                    BaseJNI.isCanSignalCommunication = true;
                    JNILog.e("runOnAndroidJavaScript");
                    if (EmptyAndSizeUtils.isNotEmpty(getWebActivity())) {
                        getWebActivity().dismissLoading();
                    }
                    if (EmptyAndSizeUtils.isNotEmpty(getWebView())) {
                        if (EmptyAndSizeUtils.isNotEmpty(mDataProxy)) {
                            mDataProxy.loadDatas(getAlias());
                        }
                    }
                }
            });
        } else {
            JNILog.e("没有可回调的Handler");
        }


    }

    @JavascriptInterface
    public void runOnAndroidForWindow(final String tagName, final String params) {
        JNILog.e("runOnAndroidForWindow");
        if (EmptyAndSizeUtils.isNotEmpty(getHandler())) {
            getHandler().post(new Runnable() {
                @Override
                public void run() {
                    if (EmptyAndSizeUtils.isNotEmpty(mDataProxy)) {
                        if (EmptyAndSizeUtils.isNotEmpty(getWebView())) {
                            mDataProxy.showMsgWindow(getAlias(), tagName, "参数：" + params);
                        }
                    } else {
                        mToastManager.showAlert(tagName, "参数：" + params);
                    }
                }
            });
        } else {
            mToastManager.showAlert("窗口展示", "没有可回调的Handler\n\n\n标签：" + tagName + "\n\n参数：" + params);
        }


    }

    @JavascriptInterface
    public void runOnAndroidForImage(final String tagName, final String index) {
        JNILog.e("runOnAndroidForImage");
        if (EmptyAndSizeUtils.isNotEmpty(getHandler())) {
            getHandler().post(new Runnable() {
                @Override
                public void run() {
                    if (EmptyAndSizeUtils.isNotEmpty(mDataProxy)) {
                        if (EmptyAndSizeUtils.isNotEmpty(getWebView())) {
                            mDataProxy.startUploadImage(getAlias(), tagName, index);
                        } else {
                            mToastManager.showAlert("上传调用异常", "缺少可用的WebView");
                        }
                    } else {
                        mToastManager.showAlert("上传调用异常", "上传标签：" + tagName + "\n\n上传索引：" + index);
                    }
                }
            });
        } else {
            mToastManager.showAlert("上传调用异常", "没有可回调的Handler\n\n\n上传标签：" + tagName + "\n\n上传索引：" + index);
        }


    }


    /**
     * 获取Android中返回值
     *
     * @param tagName
     * @param index
     * @return
     */
    @JavascriptInterface
    public String runOnAndroidForJsRequestValues(String tagName, String index) {
        JNILog.e("runOnAndroidForImage");
        if (EmptyAndSizeUtils.isNotEmpty(mDataProxy)) {
            if (EmptyAndSizeUtils.isNotEmpty(getWebView())) {
                return mDataProxy.collectionValuePassToJs(getAlias(), tagName, index);
            }
        } else {
            mToastManager.showAlert("js请求返回值异常", "js传递标签：" + tagName + "\n\n数据：" + index);
            return null;
        }
        return null;
    }

    /**
     * 通用的回调接口，可以根据 tagName 来区分数据
     *
     * @param tagName
     * @param data
     */
    @JavascriptInterface
    public void runOnAndroidCommonDispatcher(final String tagName, final String data) {
        JNILog.e("runOnAndroidCommonDispatcher");
        if (EmptyAndSizeUtils.isNotEmpty(getHandler())) {
            getHandler().post(new Runnable() {
                @Override
                public void run() {
                    getHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            if (EmptyAndSizeUtils.isNotEmpty(mDataProxy)) {
                                if (EmptyAndSizeUtils.isNotEmpty(getWebView())) {
                                    mDataProxy.commDispatcher(getAlias(), tagName, data);
                                }
                            } else {
                                mToastManager.showAlert("公共回调异常", "js传递标签：" + tagName + "\n\n数据：" + data);
                            }
                        }
                    });
                }
            });
        } else {
            mToastManager.showAlert("公共回调异常", "没有可回调的Handler\n\n\njs传递标签：" + tagName + "\n\n数据：" + data);
        }


    }


    @JavascriptInterface
    public void runOnAndroidForSubmit(final String tagName, final String data) {
        JNILog.e("runOnAndroidForSubmit");
        if (EmptyAndSizeUtils.isNotEmpty(getHandler())) {
            getHandler().post(new Runnable() {
                @Override
                public void run() {
                    if (EmptyAndSizeUtils.isNotEmpty(mDataProxy)) {
                        if (EmptyAndSizeUtils.isNotEmpty(getWebView())) {
                            mDataProxy.submit(getAlias(), tagName, data);
                        }
                    } else {
                        if (EmptyAndSizeUtils.isNotEmpty(getWebActivity())) {
                            getWebActivity().defaultSubmit(tagName, data);
                        } else {
                            mToastManager.showAlert("默认提交", "回调标签：tagName =" + tagName + "\n\n 数据：data = " + data);
                        }
                    }
                }
            });
        } else {
            mToastManager.showAlert("默认提交", "回调标签：tagName =" + tagName + "\n\n 数据：data = " + data);
        }


    }

    /**
     * js 回调 安卓 拿返回值方法
     *
     * @param tagName
     * @param data
     * @return
     */
    @JavascriptInterface
    public boolean runOnAndroidCheck(String tagName, String data) {
        JNILog.e("runOnAndroidCheck");
        if (EmptyAndSizeUtils.isNotEmpty(mDataProxy)) {
            if (EmptyAndSizeUtils.isNotEmpty(getWebView())) {
                return mDataProxy.checkForJsCall(getAlias(), tagName, data);
            }
        } else {
            if (EmptyAndSizeUtils.isNotEmpty(getWebActivity())) {
                return getWebActivity().defaultCheck(tagName, data);
            } else {
                mToastManager.showAlert("默认js调用check方法", "如需使用，请复写defaultCheck 方法；\n\n回调标签：tagName =" + tagName + "\n 数据：data = " + data);
            }
        }
        return false;

    }

    /**
     * js 给Android 返回值回调的方法
     *
     * @param
     * @param data
     * @return
     */
    @JavascriptInterface
    public void runOnAndroidWhenGoBackHomeFromJsWithVales(final String data, final String error) {
        if (EmptyAndSizeUtils.isNotEmpty(getHandler())) {
            getHandler().post(new Runnable() {
                @Override
                public void run() {
                    JNILog.e("runOnAndroidWhenGoHomeFromJsWithVales");
                    if (EmptyAndSizeUtils.isNotEmpty(getBackValueCall())) {
                        JNILog.e("工具类方法被掉");
                        if (EmptyAndSizeUtils.isNotEmpty(data)) {
                            getBackValueCall().onSucc(getAlias(), data);
                        } else {
                            getBackValueCall().onfailed(getAlias(), error);
                        }

                    } else {
                        try {
                            if (EmptyAndSizeUtils.isNotEmpty(mDataProxy)) {
                                if (EmptyAndSizeUtils.isNotEmpty(getWebView())) {
                                    mDataProxy.whenBackValuesCalledByJs(getAlias(), data, error);
                                }
                            } else {
                                UtilsManager.getInstance().getToastManager().showAlert("\n   data =" + _json + "\n  error =" + error);
                                //whenBackValuesCalledByJs(data, error);
                            }
                        } catch (Exception e) {
                            mToastManager.showAlert("返回值异常", "异常信息：" + e.getMessage() + "\n\n调用返回结果：\n" + "数据：" + data + "\n错误信息：" + error);
                        }
                    }
                }
            });
        } else {
            mToastManager.showAlert("返回值回调失败", "缺少可使用的handler； \n\n调用返回结果：\n" + "数据：" + data + "\n错误信息：" + error);
        }

    }


    //为安卓中提供 Toast 弹框使用
    @JavascriptInterface
    public void runOnAndroidForToast(String msg) {
        JNILog.e("runOnAndroidForToast");
        if (EmptyAndSizeUtils.isNotEmpty(msg)) {
            mToastManager.showToast(msg);
        }
    }

    //为安卓中提供 Toast 弹框使用
    @JavascriptInterface
    public void runOnAndroidForCloseActivity() {
        JNILog.e("runOnAndroidForToast");
        if (EmptyAndSizeUtils.isNotEmpty(getHandler())) {
            getHandler().post(new Runnable() {
                @Override
                public void run() {
                    if (EmptyAndSizeUtils.isNotEmpty(getWebActivity())) {
                        getWebActivity().closeActivity(getAlias());
                    }else {
                        if (EmptyAndSizeUtils.isNotEmpty(mDataProxy)) {
                            if (EmptyAndSizeUtils.isNotEmpty(getWebView())) {
                                mDataProxy.closeWindow(getAlias());
                            }
                        }else {
                            mToastManager.showAlert("Activity关闭失败", "未找到需要的Activity");
                        }
                    }
                }
            });
        }else  {
            mToastManager.showAlert("Activity关闭失败", "缺少可使用的handler");
        }
    }


}
