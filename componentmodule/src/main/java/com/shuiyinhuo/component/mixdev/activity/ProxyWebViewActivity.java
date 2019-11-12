package com.shuiyinhuo.component.mixdev.activity;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.shuiyinhuo.component.R;
import com.shuiyinhuo.component.mixdev.caller.WebPageStateInterface;
import com.shuiyinhuo.component.mixdev.cnf.comm.HtmlUtilsController;
import com.shuiyinhuo.component.mixdev.cnf.comm.StaticConfig;
import com.shuiyinhuo.component.mixdev.dispatcher.dispatch.MessageDisapther;
import com.shuiyinhuo.component.mixdev.dispatcher.observe.FunctionHunter;
import com.shuiyinhuo.component.mixdev.dispatcher.observe.INetStater;
import com.shuiyinhuo.component.mixdev.jinbean.WebJNISignalProxy;
import com.shuiyinhuo.component.mixdev.jinbean.pro.BaseJNI;
import com.shuiyinhuo.component.mixdev.jinbean.LayoutConfig;
import com.shuiyinhuo.component.mixdev.caller.OnJSCarryValuesBackInterface;
import com.shuiyinhuo.component.mixdev.jinbean.pro.BridgeBinderAlias;
import com.shuiyinhuo.component.mixdev.jinbean.pro.Judger;
import com.shuiyinhuo.component.mixdev.jinbean.pro.UrlBinder;
import com.shuiyinhuo.component.mixdev.locationmanager.AlertWindowManager;
import com.shuiyinhuo.component.mixdev.locationmanager.JavaToJsManager;
import com.shuiyinhuo.component.mixdev.locationmanager.UtilsManager;
import com.shuiyinhuo.component.mixdev.proxy.ProxyAnimatorListener;
import com.shuiyinhuo.component.mixdev.utils.comm.EmptyAndSizeUtils;
import com.shuiyinhuo.component.mixdev.utils.comm.JNILog;
import com.shuiyinhuo.component.mixdev.utils.comm.ScreenUtils;
import com.shuiyinhuo.component.mixdev.utils.comm.ToastManager;
import com.shuiyinhuo.component.mixdev.utils.pkg.domain.AnimationUtils;
import com.shuiyinhuo.component.mixdev.utils.widget.Loading;
import com.shuiyinhuo.component.mixdev.entity.LoadingObserver;
import com.shuiyinhuo.component.mixdev.utils.widget.MsgWindow;
import com.shuiyinhuo.component.mixdev.entity.NavBarProxy;


/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/8/6 0006
 * @ Description：
 * =====================================
 */
public abstract class ProxyWebViewActivity extends FragmentActivity implements WebPageStateInterface {
    public Context mContext;
    private JavaToJsManager mToJsManager;
    protected LayoutInflater mInflater;
    private LinearLayout mMainContentView;
    private View navBar;
    private ViewGroup mContentView;
    protected ToastManager mToastManager;
    private LoadingObserver mLoadingObserver;
    /**
     * 默认的Webview别名
     */
    private String mDefaultAlias = "JS_component";

    /**
     * 全局对象
     */
    protected INetStater mGlobalINetStater;
    protected FunctionHunter mGlobalFunHunter;
    /**
     * 网页事件回调
     */
    private AlertWindowManager.AlertWindowInterface mWindowInterface;
    /**
     * 是否需要改变状态栏颜色
     */
    private boolean isSetStatusBarColor = false;
    /**
     * 状态栏高度
     */
    private static int sStatusBarHeight = -1;

    private NavBarProxy mNavBarProxy;

    /**
     * 状态栏高度
     */
    static View statusView = null;
    private View lowerState;

    private LayoutConfig mConfig;
    private Loading mLoading;
    private MsgWindow mWindow;
    private View mView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //JNILog.e("------------------------->判断状态："+ EmptyAndSizeUtils.isEmpty(savedInstanceState));
        //overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
        initBaseConfig();
        initLayout();
        initWebView();
        mView = getWindow().getDecorView();
        if (isActivityPlayWithAnimation()) {
            AnimationUtils.playObjectAnimatorFadeInLeft(mView, new ProxyAnimatorListener() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    initViews();
                    iniDatas();
                    initEvent();
                }
            }, 400);
        } else {
            initViews();
            iniDatas();
            initEvent();
        }

        //  mView.setAnimation(mTranslateAnimationRelationParentLeftFadeIn);
    }

    private void initBaseConfig() {
        mContext = this;
        UtilsManager.getInstance().baseRegister(mContext);
        mInflater = LayoutInflater.from(mContext);
        navBar = generatorNavBar();
        initAppThem();
        if (isSetStatusBarColor) {
            setColor(this, mNavBarProxy.getNavThemColor());
        }
        mInflater = LayoutInflater.from(mContext);
        mMainContentView = generatorMainContent();
        initDefaultConfig();
        mConfig = new LayoutConfig();
        mLoadingObserver = new LoadingObserver(this);
        mToJsManager.getMessageDisapther().registerStateInterface(this);
        Judger mJudger = mConfig.getJudger();
        mJudger.setCanBinder(false);
        initLayout();
        mJudger.setCanBinder(true);
        if (mJudger.isExitStaticLayoutRes()) {
            JNILog.e("init static layout");
            if (mJudger.isCanInflater()) {
                try {
                    mContentView = (ViewGroup) mInflater.inflate(mJudger.getLayoutId(), mMainContentView, false);
                } catch (Exception e) {
                    throw new NullPointerException("请提供静态布局");
                }
            } else if (EmptyAndSizeUtils.isNotEmpty(mJudger.getMainLayout())) {
                mContentView = (ViewGroup) mJudger.getMainLayout();
            } else {
                throw new NullPointerException("请提供静态布局");
            }
            if (mJudger.isUseful()) {
                mJudger.getFirstConfig().setUrl(getPageNameOrUrl());
            }
        } else {
            mContentView = (ViewGroup) generatorWebView();
            JNILog.e("init dynamic layout");
        }
        mToastManager.registStateInterface(this);
        setContentView(addView(navBar, mContentView));
        if (EmptyAndSizeUtils.isNotEmpty(lowerState)) {
            lowerState.setVisibility(View.VISIBLE);
        }

        mGlobalINetStater = new INetStater(mToJsManager.getMessageDisapther());

        mGlobalFunHunter = mToJsManager.getFunctionHunter();
        JsHunting(mToJsManager.getFunctionHunter());
    }


    /**
     * 初始化网页交互配置
     */
    private void initDefaultConfig() {
        mToJsManager = new JavaToJsManager();
        mToJsManager.setWebDataProxy(getWebDataProxy());
    }

    private void initLayout() {
        setLayoutResConfig(mConfig);
    }


    protected void setCustomerThemEnable(boolean enable) {
        this.isSetStatusBarColor = enable;
    }

    private void resetAppThem() {
        if (isSetStatusBarColor) {
            addView(navBar, mContentView);
            setColor(this, mNavBarProxy.getNavThemColor());
        }

    }

    private View addView(View navBar, View content) {
        mMainContentView.removeAllViews();
        if (sStatusBarHeight > 0) {
            lowerState = generatorFixedState();
            lowerState.setVisibility(View.VISIBLE);
            mMainContentView.addView(lowerState);
        }
        mMainContentView.addView(navBar);
        mMainContentView.addView(content);
        return mMainContentView;
    }

    public void setAppThem(String color) {
        mNavBarProxy.setNavBgColor(color);

    }

    public void setAppThem(int color) {
        mNavBarProxy.setNavBgColorId(color);
    }


    @Override
    protected void onResume() {
        JNILog.e("*********************************>Activity ：" + this.getClass().getName());
        super.onResume();
        if (!isSetStatusBarColor) {
            if (EmptyAndSizeUtils.isNotEmpty(lowerState)) {
                lowerState.setVisibility(View.GONE);
            }
        }
    }


    /**
     * 生成一个和状态栏大小相同的矩形条 * * @param activity 需要设置的activity * @param color 状态栏颜色值 * @return 状态栏矩形条
     */
    private static View createStatusView(Activity activity, int color) {
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        sStatusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);
        // 绘制一个和状态栏一样高的矩形
        statusView = new View(activity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                sStatusBarHeight);
        statusView.setLayoutParams(params);
        statusView.setBackgroundColor(color);
        return statusView;
    }


    /**
     * 设置状态栏颜色 * * @param activity 需要设置的activity * @param color 状态栏颜色值
     */
    public static void setColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置状态栏透明
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 生成一个状态栏大小的矩形
            View statusView = createStatusView(activity, color);
            if (sStatusBarHeight != -1) {
                // 添加 statusView 到布局中
                ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
                decorView.addView(statusView);
            }

        }
    }

    /**
     * 设置标题栏的颜色
     *
     * @param color
     */
    public void setNavBarBgColor(String color) {
        mNavBarProxy.setNavBgColor(color);
        resetAppThem();
    }

    /**
     * 设置标题栏的颜色
     *
     * @param color
     */
    public void setNavBarBgColor(int color) {
        mNavBarProxy.setNavBgColorId(color);
        resetAppThem();
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        mNavBarProxy.setTitle(title);
    }

    /**
     * 设置标题颜色
     *
     * @param color
     */
    public void setTitleColor(String color) {
        mNavBarProxy.setTextColor(color);
    }

    /**
     * 设置标题颜色
     *
     * @param isHidden
     */
    public void setBackTextState(boolean isHidden) {
        mNavBarProxy.setHiddenBackText(isHidden);
    }


    /**
     * 添加默认的导航条，默认的只有一个返回
     */
    protected void initNavBar() {

    }


    /*    public void init() {
     *//*  if (EmptyAndSizeUtils.isNotEmpty(mToJsManager)) {
            AndroidToJsSignalCommunicationProxy mHunter = mToJsManager.getCommunicationProxy();
            if (EmptyAndSizeUtils.isNotEmpty(mHunter)) {
                mToJsManager.getCommunicationProxy().setDataProxy(mToJsManager.getWebDataProxy()).setCatchThread(new CatchThread());
            } else {
                JNILog.e("jni 实体为空");
            }
        }*//*
    }*/


    private void iniDatas() {
        if (mConfig.getJudger().isCanInflater()) {
            JNILog.e("================================》 use static layout。。。。。。。");
        } else {
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
               /* AndroidToJsSignalCommunicationProxy mAndroid = mToJsManager.getCommunicationProxy();
                if (EmptyAndSizeUtils.isEmpty(mAndroid)) {
                    mToJsManager.setCommunicationProxy(new SingleJNIEntity(mContext));
                }
                mAndroid.setWebActivity(this);*/
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

        initDatas();

    }


    private void showCozyMsg(final String msg) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(1000);
                runOnUiThread(new Runnable() {
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
        if (EmptyAndSizeUtils.isNotEmpty(mConfig.getJudger())) {
            UrlBinder mFirstConfig = mConfig.getJudger().getFirstConfig();
            if (EmptyAndSizeUtils.isNotEmpty(mFirstConfig)) {
                mDefaultAlias = mFirstConfig.getAlias();
            } else {
                mFirstConfig = null;
            }

        }
        if (mConfig.getJudger().isUseful()) {
            JNILog.e("------------------> start init all WebView binder Info .....");
            mConfig.getJudger().initConfig(getPageNameOrUrl(), mContentView, mToJsManager, mLoadingObserver);
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
                    mToJsManager.getWebDataProxy().onConfirm(mToJsManager.getMessageDisapther().getAliasFromWebView(view), view, message);
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
                    mToJsManager.getWebDataProxy().onJsPrompt(mToJsManager.getMessageDisapther().getAliasFromWebView(view), view, message);
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
                    mToJsManager.getWebDataProxy().onJsAlert(mToJsManager.getMessageDisapther().getAliasFromWebView(view), view, message);
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
                UtilsManager.getInstance().getConfigForJavaToJs()._register(mToJsManager, mToJsManager.getFunctionHunter(), mToastManager, view);
                //_registerCallJs(view,"vip", bridgeName);
                if (EmptyAndSizeUtils.isNotEmpty(mLoadingObserver)) {
                    mLoadingObserver.checkState(view);
                }
                String alias = mToJsManager.getMessageDisapther().getAliasFromWebView(view);
                ProxyWebViewActivity.this.onOneOfPagesFinished(alias, view, message);
                mToJsManager.getMessageDisapther().updateState(view, MessageDisapther.SUCC_CODE);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                JNILog.e("onReceivedError  start dismissLoading");
                if (EmptyAndSizeUtils.isNotEmpty(mLoadingObserver)) {
                    mLoadingObserver.checkState(view);
                }
                mToJsManager.getMessageDisapther().updateState(view, MessageDisapther.ERROR_CODE);
                String alias = mToJsManager.getMessageDisapther().getAliasFromWebView(view);
                ProxyWebViewActivity.this.onOneOfPagesError(alias, view, request, error);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                JNILog.e("onReceivedSslError  start dismissLoading");
                if (EmptyAndSizeUtils.isNotEmpty(mLoadingObserver)) {
                    mLoadingObserver.checkState(view);
                }
                mToJsManager.getMessageDisapther().updateState(view, MessageDisapther.ERROR_CODE);
                String alias = mToJsManager.getMessageDisapther().getAliasFromWebView(view);
                ProxyWebViewActivity.this.onReceivedSslError(alias, view, handler, error);
            }
        };

    }


    public void onOneOfPagesFinished(String Alias, WebView view, String message) {
    }

    public void onOneOfPagesError(String Alias, WebView view, WebResourceRequest request, WebResourceError error) {
        StringBuffer sb = new StringBuffer("Webview错误信息：").append("<br/>");
        sb.append("别名： " + Alias).append("<br/>");
        sb.append("接收错误方法：onOneOfPagesError").append("<br/>");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sb.append("<font color='red'>地址: ").append(request.getUrl().toString()).append("</font><br/>");
            sb.append("方法: ").append(request.getMethod()).append("<br/>");

        } else {
            sb.append("错误信息： ").append(request.toString()).append("<br/>");
            sb.append("方法: ").append(request.toString());
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            sb.append("描述: ").append(error.getDescription());
        } else {
            sb.append("描述: ").append(error.toString());
        }
        mToastManager.showAlert("存在网页加载失败", UtilsManager.getInstance().getHtmlUtilsController().formatHtmlForSpanned(sb.toString()));
    }

    public void onReceivedSslError(String alias, WebView view, SslErrorHandler handler, SslError error) {
        showToast("存在网页加载失败 onReceivedSslError   " + alias);
    }

    protected boolean isTagName(String tagName, String value) {
        if (EmptyAndSizeUtils.isNotEmpty(tagName)) {
            return TextUtils.equals(tagName, value);
        } else {
            showToast("tagName 为空，比较失败");
        }
        return false;
    }

    private boolean isCanCalled() {
        return (mConfig.getJudger().isUseful()) || EmptyAndSizeUtils.isNotEmpty(mToJsManager.getWebDataProxy());
    }

    public <T> T viewFinder(int id) {
        try {
            return (T) this.findViewById(id);
        } catch (Exception e) {
            return null;
        }
    }

    private LinearLayout generatorMainContent() {
        LinearLayout mContentVW = new LinearLayout(mContext);
        FrameLayout.LayoutParams mVWLayoutParams = new FrameLayout.LayoutParams(ScreenUtils.getScreenWidth(mContext), ScreenUtils.getScreenHeight(mContext));
        mContentVW.setLayoutParams(mVWLayoutParams);
        //mContentVW.setBackgroundColor(Color.parseColor("#668B8B"));
        mContentVW.setOrientation(LinearLayout.VERTICAL);
        mToastManager = UtilsManager.getInstance().getToastManager();
        return mContentVW;
    }

    private View generatorFixedState() {
        FrameLayout state = new FrameLayout(mContext);
        state.setVisibility(View.GONE);
        FrameLayout.LayoutParams mVWLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, sStatusBarHeight);
        int colorRes = -1;
        if (mNavBarProxy.getNavBgColorId() != -1) {
            colorRes = mNavBarProxy.getNavBgColorId();
        } else {
            colorRes = Color.parseColor(mNavBarProxy.getNavBgColor());
        }
        state.setBackgroundColor(colorRes);
        state.setLayoutParams(mVWLayoutParams);
        return state;
    }

    private View generatorWebView() {
        RelativeLayout mLayout = new RelativeLayout(mContext);
        FrameLayout.LayoutParams mLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mLayout.setLayoutParams(mLayoutParams);

        WebView mContainer = new WebView(mContext);
        RelativeLayout.LayoutParams webParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        webParams.addRule(RelativeLayout.CENTER_IN_PARENT);

        /**
         * 绑定默认的Webview
         */
        if (EmptyAndSizeUtils.isEmpty(getPageNameOrUrl())) {
            Toast.makeText(mContext, "Empty url", Toast.LENGTH_SHORT).show();
        }
        mConfig.bindUrlToWebView(mContainer, getPageNameOrUrl(), mDefaultAlias);

        mLayout.removeAllViews();
        mContainer.setLayoutParams(webParams);
        mLayout.addView(mContainer);
        return mLayout;
    }


    private View generatorNavBar() {
        mNavBarProxy = new NavBarProxy(mContext, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBackListener()) {
                    mContext = null;
                    // mConfig.getJudger().finish();
                    // mContentView.removeAllViews();
                    finish();
                    //System.exit(0);
                }
            }
        });
        return mNavBarProxy.getNavBar();
    }

    @Override
    public void onBackPressed() {
        JNILog.e("-------------->ProxyWebViewActivity onBackPressed 返回");
        dismissLoading();
        dismissWindow();
        if (!onBackListener()) {
            return;
        }
        super.onBackPressed();
    }

    private boolean onBackListener() {
        BaseJNI.isCanSignalCommunication = false;
        return true;
    }

    @Override
    protected void onDestroy() {
        BaseJNI.isCanSignalCommunication = false;
        if (isActivityPlayWithAnimation()) {
            overridePendingTransition(-1, android.R.anim.slide_out_right);
        }
        super.onDestroy();
    }

    protected boolean isActivityPlayWithAnimation() {
        return false;
    }

    @Override
    public void showLoading() {
        if (isLoadingDefaultStyle()) {
            dismissLoading();
            mLoading = new Loading(mContext);
            mLoading.registStateInterface(this);
            mLoading.show();
        }

    }

    @Override
    public void dismissLoading() {
        if (EmptyAndSizeUtils.isNotEmpty(mLoading) && mLoading.isShowing()) {
            mLoading.dismiss();
            mLoading = null;
        }
    }

    @Override
    public void showWindow(final Object title, final Object content) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dismissWindow();
                mWindow = new MsgWindow(mContext, ProxyWebViewActivity.this);
                JNILog.e(" 进入  window  title = " + title + "\n" + "content = " + content);
                if (EmptyAndSizeUtils.isNotEmpty(mWindow)) {
                    if (content instanceof Spanned) {
                        Spanned centent = (Spanned) content;
                        mWindow.setContent(centent);
                    } else if (content instanceof String) {
                        String centent = (String) content;
                        mWindow.setContent(centent);

                    }

                    if (title instanceof Spanned) {
                        Spanned mTitle = (Spanned) title;
                        mWindow.setTitle(mTitle);
                    } else if (title instanceof String) {
                        String mTitle = (String) title;
                        mWindow.setTitle(mTitle);

                    }
                    if (EmptyAndSizeUtils.isNotEmpty(navBar)) {
                        mWindow.showWindow(mWindow, navBar);
                    } else {
                        JNILog.e("-----------------> anchar is NullPoint");
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

    protected boolean isLoadingDefaultStyle() {
        return false;
    }

    @Override
    public boolean isCanBack() {
        return onBackListener();
    }


    /**
     * 使用静态布局需要复写的方法
     *
     * @param config
     */
    public abstract void setLayoutResConfig(LayoutConfig config);

    /**
     * @param jniBinderProxy 绑定 与js 交互的方法，需要时复写
     */
    public void bindJNIEntity(BridgeBinderAlias jniBinderProxy) {
    }


    /**
     * 默认的提交数据 复写方法
     *
     * @param data
     */
    public void defaultSubmit(String tagName, String data) {
        showWindow("默认提交", "回调标签：tagName =" + tagName + "\n\n 数据：data = " + data);
        //Toast.makeText(mContext, data, Toast.LENGTH_SHORT).show();
    }

    /**
     * 默认的检测方法 复写方法
     *
     * @param data
     */
    public boolean defaultCheck(String tagName, String data) {
        showWindow("默认js调用check方法", "如需使用，请复写defaultCheck 方法；\n\n回调标签：tagName =" + tagName + "\n 数据：data = " + data);
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
    public void callJsWithBackValues(String alias, OnJSCarryValuesBackInterface backValueCall, String methodName, String... data) {
        if (EmptyAndSizeUtils.isNotEmpty(mToJsManager)) {
            mToJsManager.getFunctionHunter().callJsWithBackValues(alias, backValueCall, methodName, data);
        }
    }

    /**
     * 无参数方法
     * 调用js中的带返回值的方法
     *
     * @param methodName js中的方法名
     * @return
     */
    public void callJsWithBackValues(String alias, OnJSCarryValuesBackInterface backValueCall, String methodName) {
        if (EmptyAndSizeUtils.isNotEmpty(mToJsManager)) {
            mToJsManager.getFunctionHunter().callJsWithBackValues(alias, backValueCall, methodName);
        }
    }


    /**
     * 不带参数方法
     * 掉用js中的函数
     *
     * @methodName js 中定义的方法名
     */
    public void callJs(String alias, String methodName) {
        if (!EmptyAndSizeUtils.isEmpty(mToJsManager)) {
            mToJsManager.getFunctionHunter().callJs(alias, methodName);
        }
    }


    /**
     * 不带参数方法
     * 掉用js中的函数
     *
     * @methodName js 中定义的方法名
     */
    public void callJs(String alias, String methodName, String _jsonDatas) {
        if (!EmptyAndSizeUtils.isEmpty(mToJsManager)) {
            mToJsManager.getFunctionHunter().callJsByArgs(alias, methodName, _jsonDatas);
        }
    }


    /**
     * 不带参数方法
     * 掉用js中的函数
     *
     * @methodName js 中定义的方法名
     */
    public void callJsByDefault(String alias, String _jsonDatas) {
        if (!EmptyAndSizeUtils.isEmpty(mToJsManager)) {
            mToJsManager.getFunctionHunter().callJsByArgs(alias, _jsonDatas);
        }
    }

    public void showToast(String msg) {
        msg = EmptyAndSizeUtils.isEmpty(msg) ? "" : msg;
        mToastManager.showToast(msg);
    }

    /**
     * 获取当前默认的别名，这个别名是：
     * <p>
     * 动态布局时：生成布局
     * ----------------------------------
     * 使用动态创建是webview别名
     * ----------------------------------
     * 使用静态布局：提供的布局
     * ----------------------------------
     * 第一个Webview的别名，如果只有一
     * 个Webview，就是这个Webview的别名
     * ----------------------------------
     * <p>
     * 注意：如果没有可用的Webview，就返回空
     */
    public String getDefaultAlias() {
        return mDefaultAlias;
    }

    ;

    public void closeActivity(String Alias) {
        mToastManager.showToast(Alias + "关闭Activity");
        this.finish();
    }


    public abstract void initViews();

    public abstract void initEvent();

    public abstract void initAppThem();

    public abstract void initDatas();

    public abstract String getPageNameOrUrl();

    public abstract WebJNISignalProxy getWebDataProxy();

    public abstract void monitorNetWork(INetStater iNetStater);

    public abstract void JsHunting(FunctionHunter funHunter);

}
