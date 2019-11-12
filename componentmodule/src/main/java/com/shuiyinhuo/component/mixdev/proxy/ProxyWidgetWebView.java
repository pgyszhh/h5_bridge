package com.shuiyinhuo.component.mixdev.proxy;

import android.app.Activity;
import android.content.Context;
import android.net.http.SslError;
import android.os.SystemClock;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.widget.Toast;

import com.shuiyinhuo.component.mixdev.caller.OnJSCarryValuesBackInterface;
import com.shuiyinhuo.component.mixdev.caller.WebPageStateInterface;
import com.shuiyinhuo.component.mixdev.caller.WidgetBackupInterface;
import com.shuiyinhuo.component.mixdev.caller.WidgetInterface;
import com.shuiyinhuo.component.mixdev.cnf.comm.HtmlUtilsController;
import com.shuiyinhuo.component.mixdev.cnf.comm.StaticConfig;
import com.shuiyinhuo.component.mixdev.dispatcher.dispatch.MessageDisapther;
import com.shuiyinhuo.component.mixdev.dispatcher.observe.FunctionHunter;
import com.shuiyinhuo.component.mixdev.dispatcher.observe.INetStater;
import com.shuiyinhuo.component.mixdev.jinbean.LayoutConfig;
import com.shuiyinhuo.component.mixdev.jinbean.pro.BaseJNI;
import com.shuiyinhuo.component.mixdev.jinbean.pro.BridgeBinderAlias;
import com.shuiyinhuo.component.mixdev.jinbean.pro.UrlBinder;
import com.shuiyinhuo.component.mixdev.jinbean.pro.WidgetConfig;
import com.shuiyinhuo.component.mixdev.locationmanager.AlertWindowManager;
import com.shuiyinhuo.component.mixdev.locationmanager.JavaToJsManager;
import com.shuiyinhuo.component.mixdev.locationmanager.UtilsManager;
import com.shuiyinhuo.component.mixdev.utils.comm.EmptyAndSizeUtils;
import com.shuiyinhuo.component.mixdev.utils.comm.JNILog;
import com.shuiyinhuo.component.mixdev.utils.comm.ToastManager;
import com.shuiyinhuo.component.mixdev.entity.LoadingObserver;
import com.shuiyinhuo.component.mixdev.utils.widget.MsgWindow;


/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/8/6 0006
 * @ Description：通用组件代理
 * =====================================
 */
public   class ProxyWidgetWebView implements WebPageStateInterface {
    private Context mContext;
    private JavaToJsManager mToJsManager;
    protected LayoutInflater mInflater;
    protected ToastManager mToastManager;

    /**
     * 默认的Webview别名
     */
    private String mDefaultAlias="JS_component";

    /**
     * 全局对象
     */
    protected INetStater mGlobalINetStater;
    protected FunctionHunter mGlobalFunHunter;
    /**
     * 网页事件回调
     */
    private AlertWindowManager.AlertWindowInterface mWindowInterface;


    private LayoutConfig mConfig;
    private MsgWindow mWindow;
    private WidgetInterface mWidgetInterface;
    private LoadingObserver mLoadingObserver;
    private WidgetBackupInterface mBackupInterface;



    public void registerWebView(Activity context, WidgetInterface mWidgetInterface){
        mContext = context;
        UtilsManager.getInstance().baseRegister(mContext);
        mLoadingObserver = new LoadingObserver(this);
       /// this.mContentView=mContentView;
        mToastManager = UtilsManager.getInstance().getToastManager();
        this.mWidgetInterface=mWidgetInterface;
        initBaseConfig();
        mConfig.getJudger().setCanBinder(true);
        initLayout();
        initWebView();
        iniDatas();
    }

    public void registComponentListener(WidgetBackupInterface mBackupInterface){
        this.mBackupInterface=mBackupInterface;
    }

    private void initBaseConfig() {
        UtilsManager.getInstance().baseRegister(mContext);
        mInflater = LayoutInflater.from(mContext);
        initDefaultConfig();
        mConfig = new LayoutConfig();
        mToJsManager.getMessageDisapther().registerStateInterface(ProxyWidgetWebView.this);
        initLayout();
        mToastManager.registStateInterface(this);
        mGlobalINetStater=new INetStater(mToJsManager.getMessageDisapther());
        mGlobalFunHunter=mToJsManager.getFunctionHunter();
        if (EmptyAndSizeUtils.isNotEmpty(this.mWidgetInterface)){
            this.mWidgetInterface.JsHunting(mToJsManager.getFunctionHunter());
        }

    }

    public ToastManager getToastManager(){
        return mToastManager;
    }

    /**
     * 初始化网页交互配置
     */
    private void initDefaultConfig() {
        mToJsManager = new JavaToJsManager();
        if (EmptyAndSizeUtils.isNotEmpty(this.mWidgetInterface)){
            mToJsManager.setWebDataProxy(this.mWidgetInterface.getWebDataProxy());
        }

    }

    private void initLayout() {
        if (EmptyAndSizeUtils.isNotEmpty(this.mWidgetInterface)){
            this.mWidgetInterface.setLayoutResConfig(new WidgetConfig(mConfig));
        }
    }



    private void iniDatas() {
        if (mConfig.getJudger().isCanInflater()) {
            JNILog.e("================================》 use static layout。。。。。。。");
        }else {
            JNILog.e("================================》 use dynamic layout。。。。。。。");
        }

        if (mConfig.getJudger().isUseful()) {
            if (!mConfig.getJudger().isWebViewUseful()) {
                if (StaticConfig.isShowLogin) {
                    final String msg = "<font color='#000000' ><big>没有可用的Viewview加载对应的url，要想与js交互：</big></font><br/><br/><font color='#2E8B57' >" +
                            "1、请在setLayoutResId 中绑定正确的url与对应的WebView;\n\n" +
                            "<br/><br/>2、请在setLayoutResId 中绑定正确的url与对应的WebView 对应的Id</font";
                    showCozyMsg(msg);
                }
            } else {
                bindJNIEntity(new BridgeBinderAlias(mConfig));
                mConfig.getJudger().dispatherWebView();
            }
            initWebPageCallBack();
            mConfig.getJudger().initLoading(this);
            monitorNetWork(mGlobalINetStater);
            mConfig.getJudger().startLoadUrlCarryCallBack(mWindowInterface);

            boolean mOnlyOne = mConfig.getJudger().isOnlyOne();
            if (mOnlyOne) {
                JNILog.e("====================> only one WebView");
            } else {
                JNILog.e("====================> many  WebView");

            }
        } else {
            JNILog.e("当前页面不需要使用Webview相关配置");
        }
    }

    public INetStater getGlobalINetStater() {
        return mGlobalINetStater;
    }

    public FunctionHunter getGlobalFunHunter() {
        return mGlobalFunHunter;
    }

    private void showCozyMsg(final String msg) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(1000);
                ((Activity)mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (EmptyAndSizeUtils.isNotEmpty(mContext)) {
                            HtmlUtilsController mUtilsController = UtilsManager.getInstance().getHtmlUtilsController();
                            Spanned nS = mUtilsController.formatHtmlForSpanned(msg);
                            mToastManager.showAlert(mContext, nS);
                        }
                    }
                });
            }
        }).start();

    }


    private void initWebView() {
        if (EmptyAndSizeUtils.isNotEmpty(mConfig.getJudger())){
            UrlBinder mFirstConfig = mConfig.getJudger().getFirstConfig();
            if (EmptyAndSizeUtils.isNotEmpty(mFirstConfig)){
                mDefaultAlias=mFirstConfig.getAlias();
            }else {
                mFirstConfig=null;
            }

        }
        if (mConfig.getJudger().isUseful()) {
            JNILog.e("------------------> start init all WebView binder Info .....");
            mConfig.getJudger().initConfig(null, null, mToJsManager, mLoadingObserver);
        }
    }

    /**
     * 初始化页面事件回调监听
     */
    private void initWebPageCallBack() {
        mWindowInterface = new AlertWindowManager.AlertWindowInterface() {
            @Override
            public boolean onConfirm(WebView view, String message) {
                if (isCanCalled()) {
                    mToJsManager.getWebDataProxy().onConfirm(mToJsManager.getMessageDisapther().getAliasFromWebView(view),view, message);
                } else {
                    if (EmptyAndSizeUtils.isNotEmpty(mToJsManager.getWebDataProxy())) {
                        mToastManager.showAlert(message);
                    } else {
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                    }

                }
                return false;
            }

            @Override
            public boolean onJsPrompt(WebView view, String message) {
                if (isCanCalled()) {
                    mToJsManager.getWebDataProxy().onJsPrompt(mToJsManager.getMessageDisapther().getAliasFromWebView(view),view, message);
                } else {
                    if (EmptyAndSizeUtils.isNotEmpty(mToJsManager.getWebDataProxy())) {
                        mToastManager.showAlert(message);
                    } else {
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                    }

                }
                return false;
            }

            @Override
            public boolean onJsAlert(WebView view, String message) {
                if (isCanCalled()) {
                    mToJsManager.getWebDataProxy().onJsAlert(mToJsManager.getMessageDisapther().getAliasFromWebView(view),view, message);
                } else {
                    if (EmptyAndSizeUtils.isNotEmpty(mToJsManager.getWebDataProxy())) {
                        mToastManager.showAlert(message);
                    } else {
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String message) {
                UtilsManager.getInstance().getConfigForJavaToJs()._register(mToJsManager,mToJsManager.getFunctionHunter(), mToastManager,view);
                //_registerCallJs(view,"vip", bridgeName);
                if (EmptyAndSizeUtils.isNotEmpty(mLoadingObserver)) {
                    mLoadingObserver.checkState(view);
                }
                String alias= mToJsManager.getMessageDisapther().getAliasFromWebView(view);
                ProxyWidgetWebView.this.onOneOfPagesFinished(alias,view, message);
                mToJsManager.getMessageDisapther().updateState(view,MessageDisapther.SUCC_CODE);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                JNILog.e("onReceivedError  start dismissLoading");
                mToJsManager.getMessageDisapther().updateState(view,MessageDisapther.ERROR_CODE);
                String alias= mToJsManager.getMessageDisapther().getAliasFromWebView(view);
                ProxyWidgetWebView.this.onOneOfPagesError(alias,view, request, error);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                JNILog.e("onReceivedSslError  start dismissLoading");
                mToJsManager.getMessageDisapther().updateState(view,MessageDisapther.ERROR_CODE);
                String alias= mToJsManager.getMessageDisapther().getAliasFromWebView(view);
                ProxyWidgetWebView.this.onReceivedSslError(alias,view,handler,error);
            }
        };
    }


    public void onOneOfPagesFinished(String Alias,WebView view, String message) {
        if (EmptyAndSizeUtils.isNotEmpty( this.mBackupInterface)){
            this.mBackupInterface.onOneOfPagesFinished(Alias,view,message);
        }
    }

    public void onOneOfPagesError(String Alias,WebView view, WebResourceRequest request, WebResourceError error) {
        String msg="存在网页加载失败  onOneOfPagesError   "+Alias;
        if (EmptyAndSizeUtils.isNotEmpty( this.mBackupInterface)){
            this.mBackupInterface.onOneOfPagesError(Alias,view,request,error,msg);
        }else {
            showToast(msg);
        }
    }

    public void onReceivedSslError(String alias,WebView view, SslErrorHandler handler, SslError error) {
        String msg="存在网页加载失败 onReceivedSslError   "+alias;
        if (EmptyAndSizeUtils.isNotEmpty( this.mBackupInterface)){
            this.mBackupInterface.onReceivedSslError(alias,view,handler,error,msg);
        }else {
            showToast(msg);
        }
    }



    private boolean isCanCalled() {
        return (mConfig.getJudger().isUseful()) || EmptyAndSizeUtils.isNotEmpty(mToJsManager.getWebDataProxy());
    }

    private boolean onBackListener() {
        BaseJNI.isCanSignalCommunication = false;
        return true;
    }


    @Override
    public void showLoading() {
        if (EmptyAndSizeUtils.isNotEmpty( this.mWidgetInterface)){
            this.mWidgetInterface.showLoading();
        }
    }

    @Override
    public void dismissLoading() {
        if (EmptyAndSizeUtils.isNotEmpty( this.mWidgetInterface)){
            this.mWidgetInterface.dismissLoading();
        }
    }

    @Override
    public void showWindow(final Object title, final Object content) {
        ((Activity)mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dismissWindow();
                mWindow = new MsgWindow(mContext, ProxyWidgetWebView.this);
                JNILog.e(" 进入  window  " + "content = " + content);
                if (EmptyAndSizeUtils.isNotEmpty(mWindow)) {
                    if (content instanceof Spanned) {
                        Spanned   centent = (Spanned) content;
                        mWindow.setContent(centent);
                    } else if (content instanceof String) {
                        String centent = (String) content;
                        mWindow.setContent(centent);

                    }

                    if (title instanceof Spanned) {
                         Spanned mTitle =(Spanned)   title;
                         mWindow.setTitle(mTitle);
                    } else if (content instanceof String) {
                        String mTitle = (String) title;
                        mWindow.setTitle(mTitle);

                    }

                }

            }
        });
    }



    @Override
    public void dismissWindow() {
        if (EmptyAndSizeUtils.isNotEmpty(mWindow) && mWindow.isShowing()) {
            mWindow.dismiss();
            mWindow = null;
        }
    }


    @Override
    public boolean isCanBack() {
        return onBackListener();
    }

    @Override
    public void onPageLoadError(WebView webView, String Alias) {
        if (EmptyAndSizeUtils.isNotEmpty(this.mWidgetInterface)){
            this.mWidgetInterface.onPageLoadError(mGlobalFunHunter,webView,Alias);
        }
    }

    @Override
    public void monitorNetWork(INetStater iNetStater) {
        if (EmptyAndSizeUtils.isNotEmpty(this.mWidgetInterface)){
            this.mWidgetInterface.monitorNetWork(iNetStater);
        }
    }

    @Override
    public void synchronizationLoadDatasToWebView(String Alias) {
        if (EmptyAndSizeUtils.isNotEmpty(this.mWidgetInterface)){
            this.mWidgetInterface.synchronizationLoadDatasToWebView(Alias);
        }
    }




    /**
     * @param jniBinderProxy 绑定 与js 交互的方法，需要时复写
     */
    private void bindJNIEntity(BridgeBinderAlias jniBinderProxy) {
       if (EmptyAndSizeUtils.isNotEmpty( this.mBackupInterface)){
           this.mBackupInterface.bindJNIEntity(jniBinderProxy);
       }
    }


    /**
     * 默认的提交数据 复写方法
     *
     * @param data
     */
    public void defaultSubmit(String tagName, String data) {
        showWindow("默认提交","回调标签：tagName ="+tagName+"\n\n 数据：data = "+data);
        //Toast.makeText(mContext, data, Toast.LENGTH_SHORT).show();
    }

    /**
     * 默认的检测方法 复写方法
     *
     * @param data
     */
    public boolean defaultCheck(String tagName, String data) {
        showWindow("默认js调用check方法","如需使用，请复写defaultCheck 方法；\n\n回调标签：tagName ="+tagName+"\n 数据：data = "+data);
        //Toast.makeText(mContext, data, Toast.LENGTH_SHORT).show();
        return false;
    }

    ;

    /**
     * 带参数方法
     * 调用js中的带返回值的方法
     *
     * @param methodName js中的方法名
     * @param data       参数
     * @return
     */
    public void callJsWithBackValues(String alias,OnJSCarryValuesBackInterface backValueCall, String methodName, String... data) {
        if (EmptyAndSizeUtils.isNotEmpty(mToJsManager)) {
            mToJsManager.getFunctionHunter().callJsWithBackValues(alias,backValueCall, methodName, data);
        }
    }

    /**
     * 无参数方法
     * 调用js中的带返回值的方法
     *
     * @param methodName js中的方法名
     * @return
     */
    public void callJsWithBackValues(String alias,OnJSCarryValuesBackInterface backValueCall, String methodName) {
        if (EmptyAndSizeUtils.isNotEmpty(mToJsManager)) {
            mToJsManager.getFunctionHunter().callJsWithBackValues(alias,backValueCall, methodName);
        }
    }


    /**
     * 不带参数方法
     * 掉用js中的函数
     *
     * @methodName js 中定义的方法名
     */
    public void callJs(String alias,String methodName) {
        if (!EmptyAndSizeUtils.isEmpty(mToJsManager)) {
            mToJsManager.getFunctionHunter().callJs(alias,methodName);
        }
    }



    /**
     * 不带参数方法
     * 掉用js中的函数
     *
     * @methodName js 中定义的方法名
     */
    public void callJs(String alias,String methodName, String _jsonDatas) {
        if (!EmptyAndSizeUtils.isEmpty(mToJsManager)) {
            mToJsManager.getFunctionHunter().callJsByArgs(alias,methodName, _jsonDatas);
        }
    }


    /**
     * 不带参数方法
     * 掉用js中的函数
     *
     * @methodName js 中定义的方法名
     */
    public void callJsByDefault(String alias,String _jsonDatas) {
        if (!EmptyAndSizeUtils.isEmpty(mToJsManager)) {
            mToJsManager.getFunctionHunter().callJsByArgs(alias,_jsonDatas);
        }
    }

    public void showToast(String msg) {
        msg = EmptyAndSizeUtils.isEmpty(msg) ? "" : msg;
        mToastManager.showToast(msg);
    }

    /**
     * 获取当前默认的别名，这个别名是：
     *
     *     动态布局时：生成布局
     *         ----------------------------------
     *          使用动态创建是webview别名
     *         ----------------------------------
     *     使用静态布局：提供的布局
     *         ----------------------------------
     *          第一个Webview的别名，如果只有一
     *         个Webview，就是这个Webview的别名
     *        ----------------------------------
     *
     *    注意：如果没有可用的Webview，就返回空
     */
    public String getDefaultAlias(){
        return mDefaultAlias;
    };







}
