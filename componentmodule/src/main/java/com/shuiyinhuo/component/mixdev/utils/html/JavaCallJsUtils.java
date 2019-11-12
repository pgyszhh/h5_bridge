package com.shuiyinhuo.component.mixdev.utils.html;

import android.content.Context;
import android.net.http.SslError;
import android.os.Build;
import android.os.Handler;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;

import com.shuiyinhuo.component.mixdev.activity.ProxyWebViewActivity;
import com.shuiyinhuo.component.mixdev.caller.OnJSUtilsCarryValuesBackInterface;
import com.shuiyinhuo.component.mixdev.cnf.comm.HtmlUtilsController;
import com.shuiyinhuo.component.mixdev.cnf.comm.StaticConfig;
import com.shuiyinhuo.component.mixdev.jinbean.js.JsJniEntity;
import com.shuiyinhuo.component.mixdev.caller.OnJSCarryValuesBackInterface;
import com.shuiyinhuo.component.mixdev.locationmanager.AlertWindowManager;
import com.shuiyinhuo.component.mixdev.locationmanager.JavaToJsManager;
import com.shuiyinhuo.component.mixdev.locationmanager.UtilsManager;
import com.shuiyinhuo.component.mixdev.utils.comm.EmptyAndSizeUtils;
import com.shuiyinhuo.component.mixdev.utils.comm.JNILog;
import com.shuiyinhuo.component.mixdev.utils.comm.ToastManager;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/8/19 0019
 * @ Description：原生调用js工具类
 * =====================================
 */
public class JavaCallJsUtils {
    private Handler mHandler = new Handler();
    private JsJniEntity mEntity;
    private ToastManager mManager = UtilsManager.getInstance().getToastManager();



    public void registJs(Context context, String... path) {
        if (EmptyAndSizeUtils.isEmpty(path)){
            JNILog.e("---------------->js工具类文件为空，请提供可用的js文件");
            mManager.showToast("js工具类文件为空，请提供可用的js文件");
            return;
        }
        ArrayList<String> paths = new ArrayList<>();
        paths.clear();
        for (String s : path) {
            paths.add(s);
        }
        registJs(context, paths);
    }


    public void registJs(Context context, ArrayList<String> path) {
        mManager=UtilsManager.getInstance().getToastManager();
        final HtmlUtilsController mController = UtilsManager.getInstance().getHtmlUtilsController();
        mController.jsPathSorter(path);
        mController.initJsToolBox(context, mController.getWebContent());
        mEntity = new JsJniEntity(mController.getJsBoxProxy().getWebView());
        mEntity.setHandler(mHandler);
        mEntity.isCanSignalCommunication=false;
        mEntity.setContext(context);


        /**
         * 设置桥接
         */
        final String  java_key = mController.getJsBoxBridgeKey();
        mController.getJsBoxProxy().setJsSwitcherEntityByUtils(mEntity, java_key);
        mController.getJsBoxProxy().setWebViewWebClientForPopWindow(new AlertWindowManager.AlertWindowInterface() {
            @Override
            public boolean onConfirm(WebView view, String message) {
                mManager.showToast("tool:" + message);
                return false;
            }

            @Override
            public boolean onJsPrompt(WebView view, String message) {
                mManager.showToast("tool:" + message);
                return false;
            }

            @Override
            public boolean onJsAlert(WebView view, String message) {
                mManager.showToast("tool:" + message);
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String message) {
               /* String js = "var newscript = document.createElement(\"script\");";
                js += "newscript.src=\"file:////android_asset/web_config/test/myUtils.js\";";
                js += "document.body.appendChild(newscript);";
                String mSouce = getSouce();
                JNILog.e("文件内容："+mSouce);
                view.loadUrl("javascript:"+js);*/

                _register(mController,view,mController.getJsBoxBridgeKey());

            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                mEntity.isCanSignalCommunication=false;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    mManager.showToast("js工具加载失败：" + error.getDescription().toString());
                    JNILog.e("err:"+error.getDescription().toString());

                } else {
                    mManager.showToast("js工具加载失败");
                }
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {

            }
        });
    }

    /**
     * 带参数方法
     * 调用js中的带返回值的方法
     *
     * @param methodName js中的方法名
     * @param data       参数
     * @return
     */
    public void callJsWithBackValues(OnJSUtilsCarryValuesBackInterface backValueCall, String methodName, String... data) {
        if (EmptyAndSizeUtils.isNotEmpty(mEntity)) {
            mEntity.setBackValueCall(backValueCall);
            mEntity.callJsWithBackValues(backValueCall, methodName, data);
        } else {
            JNILog.e("请设置返回值监听 OnJSCarryValuesBackInterface");
        }
    }

    public void callJsByArgs(String jsMethodName,  String _json){
        if (EmptyAndSizeUtils.isNotEmpty(mEntity)){
            mEntity.callJsByArgs(jsMethodName, _json);
        }
    }

    /**
     * 不带参数方法
     * 调用js中的带返回值的方法
     *
     * @param methodName js中的方法名
     * @return
     */
    public void callJsWithBackValues(String methodName,OnJSUtilsCarryValuesBackInterface backValueCall) {
        if (EmptyAndSizeUtils.isNotEmpty(mEntity)) {
            mEntity.setBackValueCall(backValueCall);
            mEntity.callJsWithBackValues( methodName,backValueCall);
        } else {
            JNILog.e("请设置返回值监听 OnJSCarryValuesBackInterface");
        }
    }

    /**
     * 注册使用
     * 掉用js中的函数
     *
     * @methodName js 中定义的方法名
     */
    private   void _register(HtmlUtilsController htmlUtilsController, WebView webView, String aliasKey) {
        if (EmptyAndSizeUtils.isNotEmpty(webView)){


            if (EmptyAndSizeUtils.isNotEmpty(mEntity)) {
                JNILog.e("开始注册工具中间件：" + aliasKey);

                String mBridgeSource = htmlUtilsController.getBridgeSource(StaticConfig.isShowLogin);
                JNILog.e("bridge 手册：：" + mBridgeSource);
                Object mDynamic_tag = webView.getTag(mBridgeSource.hashCode());
                webView.loadUrl("javascript:" + mBridgeSource);
             /*   if (EmptyAndSizeUtils.isEmpty(mDynamic_tag)){
                    if (!EmptyAndSizeUtils.isEmpty(htmlUtilsController)) {
                        JNILog.e(">>>>>>>>>>>>>>>>>>>>>>>> tool need dynamic regist js to page");
                        JNILog.e(">>>>>>>>>>>>>>>>>>>>>>>> tag  key = "+mBridgeSource.hashCode()+ " value = "+mDynamic_tag);
                        webView.loadUrl("javascript:" + mBridgeSource);
                        webView.setTag(mBridgeSource.hashCode(),true);
                        mEntity.isCanSignalCommunication=true;
                    }
                }else {
                      mEntity.isCanSignalCommunication=true;
                    JNILog.e(">>>>>>>>>>>>>>>>>>>>>>>> tool bridge is exist,  need not dynamic regist js to page");
                    JNILog.e(">>>>>>>>>>>>>>>>>>>>>>>> exist state :\n tag  key = "+mBridgeSource.hashCode()+ " value = "+mDynamic_tag);
                }*/
                mEntity.isCanSignalCommunication=true;
                // 注册桥接
                mEntity.callJsByArgs("java_vip", aliasKey);
            }else {
                mEntity.isCanSignalCommunication=false;
                JNILog.e("工具类中间件注册失败 java_vip");
            }
        }else {
            if (EmptyAndSizeUtils.isNotEmpty( mManager)) {
                mManager.showToast("工具类js动态注册失败，当前Weview 为空");
            }
            JNILog.e(">>>>>>>>>>>>>>>>>>>>>>>>  js工具类动态注册失败，当前Weview 为空，需要创建");
        }
        JNILog.e("中间件注册状态：" + mEntity.isCanSignalCommunication);
    }
}
