package com.shuiyinhuo.component.mixdev.demo;

import android.os.SystemClock;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.shuiyinhuo.component.R;
import com.shuiyinhuo.component.mixdev.activity.ProxyWebViewActivity;
import com.shuiyinhuo.component.mixdev.caller.OnJSCarryValuesBackInterface;
import com.shuiyinhuo.component.mixdev.caller.OnJSUtilsCarryValuesBackInterface;
import com.shuiyinhuo.component.mixdev.cnf.comm.HtmlUtilsController;
import com.shuiyinhuo.component.mixdev.dispatcher.observe.FunctionHunter;
import com.shuiyinhuo.component.mixdev.dispatcher.observe.INetStater;
import com.shuiyinhuo.component.mixdev.locationmanager.UtilsManager;
import com.shuiyinhuo.component.mixdev.utils.comm.EmptyAndSizeUtils;
import com.shuiyinhuo.component.mixdev.utils.comm.JNILog;
import com.shuiyinhuo.component.mixdev.utils.comm.WebViewProxy;
import com.shuiyinhuo.component.mixdev.utils.html.JavaCallJsUtils;
import com.shuiyinhuo.component.mixdev.jinbean.LayoutConfig;
import com.shuiyinhuo.component.mixdev.jinbean.WebJNISignalProxy;
import com.shuiyinhuo.component.mixdev.utils.widget.MessageBoxWindow;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/8/17 0017
 * @ Description：测试加载Html与js交互
 * =====================================
 */
public class JsAndAndroidCallMessageTestActivity extends ProxyWebViewActivity {
    private Button mButton;
    private Button mRegister;
    private TextView mTextView;
    JavaCallJsUtils mJsUtils;
    private WebView mWebView;

    MessageBoxWindow mBoxWindow = null;

    WebViewProxy mWebViewProxy;
    private String mUrl = "file:///android_asset/web_config/page/MyShowing.html";


    @Override
    public void initViews() {
     /*  mButton = viewFinder(R.id.bt_test);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //fHunter.callJsByArgs(alias1,"MyTestYes","123456");
                showToast("默认别名："+getDefaultAlias());
            }
        });
        mRegister = viewFinder(R.id.bt_register);
        mTextView = viewFinder(R.id.tv_text);


        HtmlUtilsController mUtilsController = UtilsManager.getInstance().getHtmlUtilsController();
        Spanned nS = mUtilsController.formatAppointColorOfText("这是测试", "#008BFF", 200);
        if (EmptyAndSizeUtils.isNotEmpty(nS)) {
            mTextView.setText(nS);
        }
*/
       /* mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callJsWithBackValues( getDefaultAlias(),new OnJSCarryValuesBackInterface() {
                    @Override
                    public void onSucc(String Alias, String backValue) {
                        showToast("返回值 ： "+backValue);
                    }

                    @Override
                    public void onfailed(String Alias, String error) {

                        showToast("返回值失败 ： "+error);
                    }
             },"myTest","newKey");*/


       /* mJsUtils.callJsWithBackValues(new OnJSUtilsCarryValuesBackInterface() {
            @Override
            public void onSucc(String backValue) {
                showToast("返回值：" + backValue);
            }

            @Override
            public void onfailed(String error) {
                showToast("返回错误：" + error);
            }
        }, "getbool", "12");

    }

     });
*/

      /* mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mJsUtils = UtilsManager.getInstance().getJsUtils();
                mJsUtils.registJs(mContext, "/test/myUtils", "DateUtils");
               *//* mBoxWindow = new MsgWindow("用各种修辞手法对事物进行形象化的阐述。包括的修辞手法有比喻、拟人、夸张、双关、排比等，可以描述人，也可以描述物，通过描述可以让人或物形象更生动具体，给人明了","测试标题",mContext);
                mBoxWindow.showWindow(mBoxWindow,mRegister);*//*
               showToast(getDefaultAlias());
            }
        });*/
}

    public String m(String[] arg) {
        String ts = "";
        for (String s : arg) {
            ts += s + ",";
        }
        return ts;
    }

    @Override
    public void initEvent() {

    }

    private String alias1 = "demo1";
    private String alias2 = "demo2";
    private String alias3 = "demo3";

    @Override
    public void setLayoutResConfig(LayoutConfig config) {
        //config.bindMainLayout(R.layout.message_layout);

       // config.bindUrlToWebView(R.id.id_wb, getPageNameOrUrl(), alias1);
        //config.bindUrlToWebView(R.id.id_wb2, mUrl, alias2);

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
        return new WebJNISignalProxy() {
            @Override
            public void commDispatcher(String Alias, String tagName, String data) {
                showToast(Alias + "   " + tagName + "      " + data);
                switch (tagName) {
                    case "1":
                        showToast("当前别名:" + getDefaultAlias());
                        //fHunter.callJsByArgs(getDefaultAlias(),"MyTestYes","123456");
                        mJsUtils = UtilsManager.getInstance().getJsUtils();
                        mJsUtils.registJs(mContext, "/test/myUtils", "DateUtils");
                        break;
                    case "2":
                        mJsUtils.callJsWithBackValues(new OnJSUtilsCarryValuesBackInterface() {
                            @Override
                            public void onSucc(String backValue) {
                                showToast("返回值：" + backValue);
                            }

                            @Override
                            public void onfailed(String error) {
                                showToast("返回错误：" + error);
                            }
                        }, "getbool", "12");
                        break;
                        case "3":
                            callJsWithBackValues(getDefaultAlias(), new OnJSCarryValuesBackInterface() {
                                @Override
                                public void onSucc(String Alias, String backValue) {
                                    showToast("回调成功："+Alias+"     返回值："+backValue);
                                }

                                @Override
                                public void onfailed(String Alias, String error) {
                                    showToast("回调成功："+Alias+"     错误值："+error);
                                }
                            },"MyTestYes","2019-8-45");
                            break;
                }

            }

            @Override
            public boolean checkForJsCall(String Alias, String tagName, String _json) {
                return true;
            }

            @Override
            public void submit(String Alias, String tagName, String _json) {
                showToast(Alias + "   " + tagName + "      " + _json);
            }


            @Override
            public String collectionValuePassToJs(String Alias, String targetName, String _json) {
                showToast(Alias + "   " + targetName + "        " + _json);
                return "1222" + "这是混合参数";
            }

        };
    }

    FunctionHunter fHunter;

    public void JsHunting(FunctionHunter funHunter) {
        fHunter = funHunter;

    }


    INetStater iNetStaters;

    @Override
    public void onPageLoadError(WebView webView, String Alias) {
        if (TextUtils.equals(Alias, alias1)) {
            showToast("超时" + Alias);
        }
        if (TextUtils.equals(Alias, alias2)) {
            showToast("超时......");
        }
    }

    @Override
    public void monitorNetWork(INetStater iNetStater) {
       /* iNetStaters=iNetStater;
        //iNetStater.setStartNetWork();
        iNetStaters.setStartNetWork(alias1);
        iNetStaters.setStartNetWork(alias2);
        new Thread(new Runnable() {
            @Override
            public void run() {
                JNILog.e("---------------> 正在等待网络请求完成......");
                SystemClock.sleep(1000*20);
                JNILog.e("---------------> 网络请求完成...............");
                iNetStaters.setEndNetWork(alias1);
                SystemClock.sleep(1000*30);
                iNetStaters.setEndNetWork(alias2);
            }
        }).start();*/
    }

    @Override
    public void synchronizationLoadDatasToWebView(String Alias) {
        if (TextUtils.equals(Alias, alias1)) {
            showToast("加载成功......" + Alias);
        }
        if (TextUtils.equals(Alias, alias2)) {
            showToast("加载成功......" + Alias);
        }

    }
}

