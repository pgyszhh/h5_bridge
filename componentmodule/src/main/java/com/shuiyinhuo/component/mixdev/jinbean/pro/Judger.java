package com.shuiyinhuo.component.mixdev.jinbean.pro;

import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.shuiyinhuo.component.mixdev.caller.WebPageStateInterface;
import com.shuiyinhuo.component.mixdev.dispatcher.dispatch.MessageDisapther;
import com.shuiyinhuo.component.mixdev.dispatcher.observe.ThreadTrusteeshiper;
import com.shuiyinhuo.component.mixdev.locationmanager.AlertWindowManager;
import com.shuiyinhuo.component.mixdev.locationmanager.JavaToJsManager;
import com.shuiyinhuo.component.mixdev.utils.comm.EmptyAndSizeUtils;
import com.shuiyinhuo.component.mixdev.utils.comm.JNILog;
import com.shuiyinhuo.component.mixdev.entity.LoadingObserver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/8/20 0020
 * @ Description：
 * =====================================
 */
public class Judger {
    /**
     * 主布局 id
     */
    private int layoutId = -1;

    private View mMainLayout;
    /**
     * WebView id
     */
    private HashSet<UrlBinder> mBinders = new HashSet<>();
    private LoadingObserver mLoadingObserver;
    protected ThreadTrusteeshiper mWebLooper;


    private boolean isCanBinder = false;


    public Judger() {
        isCanBinder = false;
    }

    public boolean isCanBinder() {
        return isCanBinder;
    }

    public void setCanBinder(boolean canBinder) {
        isCanBinder = canBinder;
    }

    private JavaToJsManager mJavaToJsManager;


    public int getLayoutId() {
        return layoutId;
    }


    public View getMainLayout() {
        return mMainLayout;
    }

    public void bindMainLayout(View mainLayout) {
        if (EmptyAndSizeUtils.isNotEmpty(mainLayout)) {
            this.mMainLayout = mMainLayout;
        } else {
            JNILog.e("请绑定主布局View");
        }
    }

    public void bindMainLayout(int layoutId) {
        if (layoutId != -1) {
            this.layoutId = layoutId;
        } else {
            JNILog.e("请提供正确的主布局资源Id");
        }
    }

    /**
     * 绑定 url 到 Webview
     * <p>
     * 可以同时设置多个WebViewId
     */

    public void bindUrlToWebView(int wbId, String url, String aliasKey) {
        if (wbId != -1 && EmptyAndSizeUtils.isNotEmpty(url)) {
            UrlBinder mUrlBinder = new UrlBinder(url, wbId);
            mUrlBinder.setAlias(aliasKey);
            mUrlBinder.setIndex(mBinders.size());
            mBinders.add(mUrlBinder);
        }
    }


    /**
     * 绑定 url 到 Webview
     * <p>
     * 可以同时设置多个WebView 与 url，彼此绑定
     */
    public void bindUrlToWebView(WebView web, String url, String aliasKey) {
        if (EmptyAndSizeUtils.isNotEmpty(web) && EmptyAndSizeUtils.isNotEmpty(url)) {
            UrlBinder mUrlBinder = new UrlBinder(url, web);
            mUrlBinder.setAlias(aliasKey);
            mUrlBinder.setIndex(mBinders.size());
            mBinders.add(mUrlBinder);
        }

    }


    /**
     * 绑定 url 到 Webview
     * <p>
     * 可以同时设置多个WebView 与 url，彼此绑定
     */
    public void bindWebView(WebView web,String url, String aliasKey) {
        if (EmptyAndSizeUtils.isNotEmpty(web)) {
            UrlBinder mUrlBinder = new UrlBinder(url, web);
            mUrlBinder.setAlias(aliasKey);
            mUrlBinder.setIndex(mBinders.size());
            mBinders.add(mUrlBinder);
        }

    }


    /**
     * 绑定 url 到 Webview
     * <p>
     * 可以同时设置多个WebViewId
     */

    public void bindWebViewId(int wbId,String url, String aliasKey) {
        if (!isExist(wbId)) {
            if (wbId != -1) {
                UrlBinder mUrlBinder = new UrlBinder(url, wbId);
                mUrlBinder.setAlias(aliasKey);
                mUrlBinder.setIndex(mBinders.size());
                mBinders.add(mUrlBinder);
            }
        }

    }

    public boolean isExist(int _id) {
        if (isUseful()) {
            for (UrlBinder mBinder : mBinders) {
                if (mBinder.getWbId() == _id) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 判断是否只有一个 webview
     *
     * @return
     */

    public boolean isOnlyOne() {
        return mBinders.size() == 1;
    }

    public void finish() {
        JNILog.e("-------------------->销毁....");
        if (isUseful()) {
            Iterator<UrlBinder> mIterator = mBinders.iterator();
            while (mIterator.hasNext()) {
                UrlBinder mNext = mIterator.next();
                mNext.finish();
            }
        }

    }

    public HashSet<UrlBinder> getBinders() {
        return mBinders;
    }


    public UrlBinder getFirstConfig() {
        if (isUseful()) {
            Iterator<UrlBinder> mIterator = mBinders.iterator();
            while (mIterator.hasNext()) {
                UrlBinder mNext = mIterator.next();
                if (mNext.getIndex() != -1) {
                    if (mNext.getIndex() == 0) {
                        return mNext;
                    }
                }

            }
            JNILog.e("未找到");
            return null;
        } else {
            JNILog.e("没有需要加载的Weview 与 url");
            return null;
        }
    }


    public void initConfig(String defaultUrl, ViewGroup layout, JavaToJsManager toJsManager, LoadingObserver mLoadingObserver) {
        this.mLoadingObserver = mLoadingObserver;
        JNILog.e("----------------------》 start init UrlBinders ");
        if (isUseful()) {
            if (isOnlyOne() && EmptyAndSizeUtils.isNotEmpty(defaultUrl)) {
                getFirstConfig().setUrl(defaultUrl);
            }
            JNILog.e("-----------------------> 开始初始化组件....");
            this.mJavaToJsManager = toJsManager;
            Iterator<UrlBinder> mIterator = mBinders.iterator();
            while (mIterator.hasNext()) {
                UrlBinder mBinder = mIterator.next();
                mBinder.initConfig(layout);
                mBinder.getCommunicationProxy().setDataProxy(toJsManager.getWebDataProxy());
                if (EmptyAndSizeUtils.isNotEmpty(mLoadingObserver)) {
                    mLoadingObserver.attach(mBinder);
                }
            }

            if (isUseful()) {
                if (EmptyAndSizeUtils.isNotEmpty(mLoadingObserver)) {
                    JNILog.e("-------------------------> start show loading processing ..... ");
                    mLoadingObserver.startObserve();
                }
            } else {
                JNILog.e("-------------------------> need not show loading process,beacse None WebView can use !");
            }
        } else {
            JNILog.e("----------------------》 cann't init UrlBinders ");
        }
    }


    /**
     * 判断ids 是否可用
     *
     * @return
     */
    public boolean isUseful() {
        return mBinders.size() != 0;
    }

    public boolean isWebViewUseful() {
        boolean isAllEmpty = false;
        if (isUseful()) {
            Iterator<UrlBinder> mIterator = mBinders.iterator();
            while (mIterator.hasNext()) {
                UrlBinder mBinder = mIterator.next();
                if (EmptyAndSizeUtils.isNotEmpty(mBinder.getPageContainer())) {
                    isAllEmpty = true;
                    break;
                }
            }
        }
        return isAllEmpty;
    }


    public void bindJNIEntity(String bridgeName, AndroidToJsSignalCommunicationProxy entity) {
        if (isUseful()) {
            Iterator<UrlBinder> mIterator = mBinders.iterator();
            while (mIterator.hasNext()) {
                UrlBinder mUrlBinder = mIterator.next();
                if (EmptyAndSizeUtils.isNotEmpty(mUrlBinder)) {
                    mUrlBinder.bindJNIEntity(bridgeName, entity);
                }
            }
        }
    }


    public void setJsSwitcherEntity() {
        if (isUseful()) {
            JNILog.e("=================================> start set message change bridge  size:=" + mBinders.size());
            Iterator<UrlBinder> mIterator = mBinders.iterator();
            if (EmptyAndSizeUtils.isNotEmpty(mJavaToJsManager)) {
                while (mIterator.hasNext()) {
                    UrlBinder mUrlBinder = mIterator.next();
                    if (EmptyAndSizeUtils.isNotEmpty(mUrlBinder)) {
                        JNILog.e("=================================> bind ... ... ");
                        mUrlBinder.setJsSwitcherEntity(mUrlBinder.getCommunicationProxy());
                    }
                }
            } else {
                JNILog.e("静态初始化通信桥接失败：setJsSwitcherEntity ，请先初始化 JavaToJsManager");
            }
        }
    }


    public void initLoading(WebPageStateInterface stateInterface) {
        Iterator<UrlBinder> mIterator = mBinders.iterator();
        UrlBinder mBinder = null;
        WebBundleStorager mInstance = WebBundleStorager.getInstance();
        mInstance.clearAll();
        while (mIterator.hasNext()) {
            mBinder = mIterator.next();
            mBinder.setStateInterface(stateInterface);
            mInstance.addBinder(mBinder);
        }


    }

    /**
     * 正式加载网页
     */
    public void startLoadUrlCarryCallBack(AlertWindowManager.AlertWindowInterface mWindowInterface) {

        JNILog.e("Net ","----------------->准备开始加载..............\n\n");

        if (EmptyAndSizeUtils.isNotEmpty(mBinders)) {
            MessageDisapther.webTagName = -1;
            Iterator<UrlBinder> mIterator = mBinders.iterator();
            UrlBinder mBinder = null;

            this.mWebLooper = new ThreadTrusteeshiper();
            while (mIterator.hasNext()) {
                try {
                    mBinder = mIterator.next();
                   /* if (MessageDisapther.webTagName == -1){
                        MessageDisapther.webTagName=mBinder.getPageContainer().hashCode();
                    }*/
                    this.mWebLooper.loopMessage(mBinder);
                    mBinder.getPageContainer().setTag(MessageDisapther.webTagName, mBinder.getAlias());
                    mBinder.startLoading(mWindowInterface);
                } catch (Exception e) {
                    if (EmptyAndSizeUtils.isNotEmpty(mBinder)) {
                        this.mLoadingObserver.checkState(mBinder.getPageContainer());
                    }
                    JNILog.e("网页加载失败：" + e.getMessage());
                }
            }

        } else {
            JNILog.e("网页加载失败，没有可加载的WebView或url");
        }

    }


    public boolean isCanInflater() {
        return layoutId != -1;
    }

    public boolean isExitStaticLayoutRes() {
        return layoutId != -1 || EmptyAndSizeUtils.isNotEmpty(mMainLayout);
    }


    /**
     * 分发WebView，分发后就可以调用
     */
    public void dispatherWebView() {
        ArrayList<WebView> mAllWebViews = getAllWebViews();
        if (EmptyAndSizeUtils.isNotEmpty(mAllWebViews) && EmptyAndSizeUtils.isNotEmpty(mJavaToJsManager)) {
            // mJavaToJsManager.getMessageDisapther().setWebViews(mAllWebViews);
        }
    }


    private ArrayList<WebView> getAllWebViews() {
        ArrayList<WebView> mArrayList = new ArrayList<>();
        mArrayList.clear();
        if (isUseful()) {
            Iterator<UrlBinder> mIterator = mBinders.iterator();
            while (mIterator.hasNext()) {
                UrlBinder mUrlBinder = mIterator.next();
                if (EmptyAndSizeUtils.isNotEmpty(mUrlBinder) && EmptyAndSizeUtils.isNotEmpty(mUrlBinder.getPageContainer())) {
                    mArrayList.add(mUrlBinder.getPageContainer());
                }
            }

        }
        return mArrayList;
    }
}

