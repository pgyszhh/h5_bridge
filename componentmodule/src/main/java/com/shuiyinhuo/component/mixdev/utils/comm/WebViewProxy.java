package com.shuiyinhuo.component.mixdev.utils.comm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.shuiyinhuo.component.mixdev.jinbean.pro.BaseJNI;
import com.shuiyinhuo.component.mixdev.locationmanager.AlertWindowManager;
import com.shuiyinhuo.component.mixdev.locationmanager.UtilsManager;

import java.net.MalformedURLException;
import java.net.URL;


/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2018/1/4 0004;
 * @ Description：
 * =====================================
 */
public class WebViewProxy {
    private Context mContext;
    private WebView mWebView;

    public WebViewProxy(Context context, WebView webView) {
        JNILog.e("--------------------------> WebViewProxy created ....  "+webView);
        mWebView = webView;
        mContext = context;
    }

    public WebView getWebView(){
        return mWebView;
    }

    public WebViewProxy restWebView(WebView view){
        if (Build.VERSION.SDK_INT >= 17) {
            // 在sdk4.2以上的系统上继续使用addJavascriptInterface
//			webView.addJavascriptInterface(obj,objName);
        } else {
            //4.2之前 addJavascriptInterface有安全泄漏风险
            //移除js中的searchBoxJavaBridge_对象,在Android 3.0以下，系统自己添加了一个叫
            //searchBoxJavaBridge_的Js接口，要解决这个安全问题，我们也需要把这个接口删除
            view.removeJavascriptInterface("searchBoxJavaBridge_");
            view.removeJavascriptInterface("accessibility");
            view.removeJavascriptInterface("accessibilityTraversal");
        }
        return this;
    }
    @SuppressLint("WrongConstant")
    public WebViewProxy initWebSettings(){
        WebSettings mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);// 支持JS
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);//支持通过js打开新的窗口
        mWebSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);//提高渲染等级
        mWebSettings.setBuiltInZoomControls(false);// 设置支持缩放
        mWebSettings.setDomStorageEnabled(true);//使用localStorage则必须打开
        mWebSettings.setBlockNetworkImage(true);// 首先阻塞图片，让图片不显示
        mWebSettings.setBlockNetworkImage(false);//  页面加载好以后，在放开图片：
        mWebSettings.setSupportMultipleWindows(false);// 设置同一个界面
        mWebSettings.setBlockNetworkImage(false);
        mWebSettings.setCacheMode(1);
        mWebSettings.setNeedInitialFocus(false);// 禁止webview上面控件获取焦点(黄色边框)
        return this;
    }

    public void loadUrl(String url){
        initSwicherforJs();
        if (this.mWebView!=null){
            JNILog.e("\n\nstart loading url  --------->\n\n|新地址:"+url+"\n缓存地址:"+mWebView.getUrl()+"  \n\n");
            /*String loadUrl=mWebView.getUrl();
            if (EmptyAndSizeUtils.isNotEmpty(loadUrl)){
                JNILog.e("reload 资源");
                mWebView.reload();
            }else {
                JNILog.e("重新加载");
                this.mWebView.loadUrl(url);
            }*/
            this.mWebView.loadUrl(url);
            /*if (EmptyAndSizeUtils.isNotEmpty(mUrl)&& TextUtils.equals(url,mUrl)){
                mWebView.loadUrl("http://www.baidu.com");
                JNILog.e("-----------------------------> start reload web");
            }else {
                this.mWebView.loadUrl(url);
            }*/

        }else {
            JNILog.e("url is Empty!");
        }
    }


    public void loadPageSource(String source){
        if (this.mWebView!=null){
            //JNILog.e("start loading url"+url+"  "+mWebView.getUrl());
            initSwicherforJs();
            this.mWebView.loadDataWithBaseURL("about:blank", source, "text/html", "utf-8", null);
        }else {
            JNILog.e("url is Empty!");
        }
    }


    public WebViewProxy initWebClient(){
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                //页面开始加载时
                super.onPageStarted(view, url, favicon);
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                //页面加载结束时
                super.onPageFinished(view, url);
            }
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                // 这里进行无网络或错误处理，具体可以根据errorCode的值进行判断，
            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                /**
                 * 网页跳转：
                 * 1.在当前的webview跳转到新连接
                 * view.loadUrl(url);
                 * 2.调用系统浏览器跳转到新网页
                 * Intent i = new Intent(Intent.ACTION_VIEW);
                 * i.setData(Uri.parse(url));
                 * startActivity(i);
                 */
                return true;
            }
        });

        return this;
    }

    /**
     * 辅助WebView处理Javascript的对话框、网站图标、网站Title、加载进度等
     */
    public WebViewProxy initChromWebClientForWebsitMsg(){
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // 获得网页的加载进度 newProgress为当前加载百分比
                super.onProgressChanged(view, newProgress);
            }
            @Override
            public void onReceivedTitle(WebView view, String title) {
                // 获取网页的title，客户端可以在这里动态修改页面的title
                // 另外，当加载错误时title为“找不到该网页”
                super.onReceivedTitle(view, title);
            }
        });

        return this;
    }

    /**
     *  支持与JS交互addJavascriptInterface
     */
    public WebViewProxy initSwicherforJs(){
        JNILog.e("------------------------> init js config");
        if (EmptyAndSizeUtils.isNotEmpty(mWebView)) {
            WebSettings mSettings = mWebView.getSettings();

            mWebView.clearCache(true);
            mWebView.clearHistory();
            mSettings.setJavaScriptEnabled(true);
            mSettings.setDomStorageEnabled(true);
            mSettings.setAllowFileAccess(true);
            mSettings.setLoadsImagesAutomatically(true);

            //mSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            //mSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
            //mSettings.setDomStorageEnabled(true);//设置适应HTML5的一些方法
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }*/
            //mSettings.setUserAgentString("User-Agent:Android");
            // mSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 不加载缓存


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mSettings.setAllowFileAccessFromFileURLs(true);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mSettings.setAllowUniversalAccessFromFileURLs(true);
            }
        }else {
            JNILog.e("-------------------> cann't init js,webView is NullPoint");
        }
        return this;
    }

    /**
     * 使用 交互,使用自动生成别名
     */
    @SuppressLint("JavascriptInterface")
    public void  setJsSwitcherEntity(Object entity){
        initSwicherforJs();
        if (null!= mWebView&&EmptyAndSizeUtils.isNotEmpty(entity)) {
            String bridgeName = UtilsManager.getInstance().getBridgeTagFactory().getRandomTagName(100);
            JNILog.e("桥接名字："+bridgeName);
            mWebView.addJavascriptInterface(entity, bridgeName);
            JNILog.e("桥架设置完成："+bridgeName);
        }else {
            JNILog.e("桥架设置失败"+" entity = " + entity);
        }
    }

    /**
     * 使用 交互
     *   使用提供的别名注册
     */
    @SuppressLint("JavascriptInterface")
    public void  setJsSwitcherEntity(Object entity,String bridgeAlias){
        initSwicherforJs();
        if (null!= mWebView&&EmptyAndSizeUtils.isNotEmpty(entity)) {
            if (EmptyAndSizeUtils.isEmpty(bridgeAlias)) {
                JNILog.e("----------------->没有提供注册桥接别名，使用随机生成");
                bridgeAlias = UtilsManager.getInstance().getBridgeTagFactory().getRandomTagName(100);
            }
            JNILog.e("桥接名字："+bridgeAlias);
            mWebView.addJavascriptInterface(entity, bridgeAlias);
            JNILog.e("桥架设置完成："+bridgeAlias);
        }else {
            JNILog.e("桥架设置失败"+" entity = " + entity);
        }
    }

    /**
     * 使用 交互
     */
    @SuppressLint("JavascriptInterface")
    public void setJsSwitcherEntityByUtils(Object entity,String utilsBridgeName){
        initSwicherforJs();
        // injs 是在js 中通过 window.injs.runOnAndroidJavaScript(); 调用原生的方法名
        // name.方法名
        if (null!= mWebView&&EmptyAndSizeUtils.isNotEmpty(entity)) {
            JNILog.e("工具类  桥架设置完成"+ entity.toString());
           // mWebView.addJavascriptInterface(entity, "injs");
            mWebView.addJavascriptInterface(entity, utilsBridgeName);
        }else {
            JNILog.e("工具类  桥架设置失败");
        }
    }

    /**
     * 使用 交互
     */
    @SuppressLint("JavascriptInterface")
    public void setJsSwitcherEntity(String bridgeName,Object entity){
        initSwicherforJs();
        // injs 是在js 中通过 window.injs.runOnAndroidJavaScript(); 调用原生的方法名
        // name.方法名
        if (null!= mWebView) {
            //BaseJNI.callJsByArgs("vip",bridgeName);
            mWebView.addJavascriptInterface(entity, bridgeName);
            JNILog.e("绑定的方法：\nbridgeName ="+bridgeName + " \nentity = "+ entity);
        }else {
            JNILog.e("绑定失败：\nbridgeName = "+bridgeName + " \nentity = "+ entity);
        }

    }
    //JSInterface对象：
   public class JSInterface {
       @JavascriptInterface
       public void methodA() {

       }

       @JavascriptInterface
       public void methodA(String webMessage) {

       }
   }

    /**
     * java调用js
     */
    public void javaCallJs(){
    /**
             * js中的方法
              function java_call_Js(param) {
                var x = document.getElementById('test');
                x.innerHTML = "Hello , New text form java ! ------------>" + param;
             }

     调用js有参无返回值函数
     注意对于字符串作为参数值需要进行转义双引号。

     String call = "javascript:alertMessage(\"" + "content" + "\")";
     webView.loadUrl(call);

     Java调用JS：无参数
     String call = "javascript:sayHello()";
     webView.loadUrl(call);

    */
        mWebView.loadUrl(String.format("javascript:java_call_Js(" + "param "+ ")"));
    }


    public void JsCallJava(){
        /**
         *    HTML code:
         *      <a onClick="window.wst.methodA()">点击调用java代码</a><br/>
         *      <a onClick="window.wst.methodA('hello world')" >点击调用java代码并传递参数</a>
         *
         *      wst:为
         *          view.addJavascriptInterface(new JSInterface(), "jsInterface")给对象JSInterface起的别名,
         *          methodA为JSInterface对象方法
         */
    }


    /**
     *
     *  js通过alert(""调用)WebChromeClient类来处理
     */
    public void setWebViewWebClientForPopWindow(AlertWindowManager.AlertWindowInterface caller){
        JNILog.e("--------------> start initPage caller:  "+(null!= mWebView));
        if (null!= mWebView) {
            JNILog.e("--------------> start init webview config "+"caller 状态:"+(caller ==null));
            String  msg = mWebView.getOriginalUrl()+"\n"+mWebView.getUrl();
            JNILog.e("--------------> 缓存信息： "+msg);
            mWebView.setWebChromeClient(new MyWebViewClient(caller));
            mWebView.setWebViewClient(new MyWebViewClientState(caller));
        }else {
            JNILog.e("-------------->  webview is null ");
        }
    }

    private class MyWebViewClientState extends WebViewClient{
        private AlertWindowManager.AlertWindowInterface caller;
        public MyWebViewClientState( AlertWindowManager.AlertWindowInterface caller){
            this.caller=caller;
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            JNILog.e("////////////////////////////////>  onPageFinished");
            JNILog.e("onPageFinished");
            if (isCanCalled()){
                JNILog.e("can called");
                caller.onPageFinished(view,url);
            }

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            JNILog.e("////////////////////////////////>  onPageStarted");
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            mWebView.loadUrl(url);//在2.3上面不加这句话，可以加载出页面，在4.0上面必须要加入，不然出现白屏
            JNILog.e("////////////////////////////////>  shouldOverrideUrlLoading");
            return true;
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
            if (isCanCalled()){
                caller.onReceivedSslError(view,handler,error);
            }
            super.onReceivedSslError(view, handler, error);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            JNILog.e("////////////////////////////////>  onReceivedError");
            if (isCanCalled()){
                caller.onReceivedError(view,request,error);
            }

        }

        private boolean isCanCalled(){
            return null != caller;
        }


    }
    /**
     * 解决webview中无法弹出alert的问题
     */
    private class MyWebViewClient extends WebChromeClient {
        private AlertWindowManager.AlertWindowInterface caller;

        public MyWebViewClient(AlertWindowManager.AlertWindowInterface caller){
            this.caller = caller;
        }

        private boolean isCanCalled(){
           return null != caller;
        }
        @Override
        public boolean onJsAlert(WebView view, String url, String message,
                                 final JsResult result) {
            JNILog.e("onJsAlert  "+"message :"+message);
            if (isCanCalled()){
                boolean isConfirm = caller.onJsAlert(view,message);
               /* if (isConfirm) {
                    result.confirm();
                }else {
                    result.cancel();
                }*/
            }else {
                Toast.makeText(mContext, ""+message, Toast.LENGTH_SHORT).show();
            }
            result.confirm();
            return true;
        }


        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
            JNILog.e("onJsPrompt  "+"message :"+message);
            if (isCanCalled()){
                boolean isConfirm = caller.onJsPrompt(view,message);
                if (isConfirm) {
                    result.confirm();
                }else {
                    result.cancel();
                }
            }
            return true /*super.onJsPrompt(view, url, message, defaultValue, result)*/;
        }

        @Override
        public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
            JNILog.e("onJsConfirm  "+"message :"+message);
            if (isCanCalled()){
                boolean isConfirm = caller.onConfirm(view,message);
                if (isConfirm) {
                    result.confirm();
                }else {
                    result.cancel();
                }
            }
            return /*super.onJsConfirm(view, url, message, result)*/true;
        }


    }

}
