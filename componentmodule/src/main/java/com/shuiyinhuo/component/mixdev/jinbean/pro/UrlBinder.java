package com.shuiyinhuo.component.mixdev.jinbean.pro;

import android.app.Activity;
import android.content.Context;
import android.os.SystemClock;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;

import com.shuiyinhuo.component.mixdev.activity.ProxyWebViewActivity;
import com.shuiyinhuo.component.mixdev.caller.AccompanyThreadInterface;
import com.shuiyinhuo.component.mixdev.caller.WebPageStateInterface;
import com.shuiyinhuo.component.mixdev.jinbean.WebConfig;
import com.shuiyinhuo.component.mixdev.jinbean.threads.AccompanyThread;
import com.shuiyinhuo.component.mixdev.locationmanager.AlertWindowManager;
import com.shuiyinhuo.component.mixdev.locationmanager.UtilsManager;
import com.shuiyinhuo.component.mixdev.utils.comm.EmptyAndSizeUtils;
import com.shuiyinhuo.component.mixdev.utils.comm.JNILog;
import com.shuiyinhuo.component.mixdev.utils.comm.WebViewProxy;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/8/20 0020
 * @ Description：WebView与page绑定实体
 * =====================================
 */
public class UrlBinder extends WebConfig implements AccompanyThreadInterface {
    private WebViewProxy mWebViewProxy;
    private String url;
    private int wbId = -1;
    private WebView mPageContainer;
    private Object mTag = -1;
    private ViewGroup layout;
    /**
     * 当前Webview索引
     */
    private int index=-1;

    /**
     * 原生与 JS 交互 实体基类
     *
     *
     * 与当前Webview绑定的通信桥接，通过当前的
     * 桥接可完成与绑定的桥接的相互通信
     */
    private AndroidToJsSignalCommunicationProxy mCommunicationProxy;

    /**
     * 当前Webview的别名，在调用的时候使用查询的依据
     * 默认是将Js与Webview桥接名作为别名
     */

    private  String mAlias = "";
    /**
     *  当前桥接的名字，与Webview绑定
     */
    private String mBridgeName = "";
    private  boolean isPageInflaterSucc=false;
    private  boolean isPageInflaterError=false;
    private  boolean isNetWorkQueryCompleted=true;
    private boolean isOutime=false;


    /**
     * 网络请求是否完成，默认是完成的，即不需要发起网络请求
     */
    private WebPageStateInterface mStateInterface;
    /**
     * 默认 60 秒超时
     */
    private long timeout=60;
    private   Future<Object> mFuture;


    public void setStateInterface(WebPageStateInterface stateInterface) {
        mStateInterface = stateInterface;
    }

    public WebPageStateInterface getStateInterface() {
        return mStateInterface;
    }

    public UrlBinder(String url, int wbId) {
        this.url = url;
        this.wbId = wbId;
    }


    public UrlBinder(String url, WebView pageContainer) {
        this.url = url;
        if (EmptyAndSizeUtils.isNotEmpty(pageContainer)) {
            mPageContainer = pageContainer;
            mWebViewProxy = new WebViewProxy(mPageContainer.getContext(), mPageContainer);
            mPageContainer.setVerticalScrollBarEnabled(false);
            mCommunicationProxy = new AndroidToJsSignalCommunicationProxy(pageContainer.getContext());
            mCommunicationProxy.setWebActivity(pageContainer.getContext());
            mBridgeName = UtilsManager.getInstance().getBridgeTagFactory().getRandomTagName(this.getIndex());
            JNILog.e("Net","设置桥接器....  "+mBridgeName+"   索引"+ getIndex());
            setDefaultAlias(mBridgeName);
        }
    }


    public Object getTag() {
        return mTag;
    }

    public void setTag(Object tag) {
        mTag = tag;
    }

    public void setUrl(String url) {
        if (EmptyAndSizeUtils.isEmpty(this.url)) {
            this.url = url;
        }
    }

    public AndroidToJsSignalCommunicationProxy getCommunicationProxy() {
        return mCommunicationProxy;
    }


    public boolean isUsefulWebView() {
        return wbId != -1 || EmptyAndSizeUtils.isNotEmpty(mPageContainer);
    }


    public boolean isUrlUseful() {
        return EmptyAndSizeUtils.isNotEmpty(this.url);
    }

    public String getUrl() {
        return getRelUrl();
    }

    public int getWbId() {
        return wbId;
    }

    public WebView getPageContainer() {
        return mPageContainer;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isPageInflaterSucc() {
        return isPageInflaterSucc;
    }

    public void setPageInflaterSucc(boolean pageInflaterSucc) {
        isPageInflaterSucc = pageInflaterSucc;
    }

    public boolean isPageInflaterError() {
        return isPageInflaterError;
    }

    public boolean isNetWorkQueryCompleted() {
        return isNetWorkQueryCompleted;
    }

    public long getTimeout() {
       return this.timeout;
    }

    public void setNetWorkQueryCompleted(boolean netWorkQueryCompleted) {
        isNetWorkQueryCompleted = netWorkQueryCompleted;
        JNILog.e("=================================================> "+getAlias()+"  的状态："+getAlias() +"   "+ isNetWorkQueryCompleted);
    }

    public void setPageInflaterError(boolean pageInflaterError) {
        isPageInflaterError = pageInflaterError;
    }

    public void finish() {
        if (EmptyAndSizeUtils.isNotEmpty(mPageContainer)) {
            mPageContainer.clearHistory();
            mPageContainer.clearCache(true);
            mPageContainer.pauseTimers();
            mPageContainer.destroy();
            if (EmptyAndSizeUtils.isNotEmpty(mPageContainer) && EmptyAndSizeUtils.isNotEmpty(layout)) {
                JNILog.e("----------------->被移除");
                layout.removeView(mPageContainer);
            }
            mPageContainer = null;
        }

    }


    public  String getAlias() {
        return mAlias;
    }

    public  void setAlias(String alias) {
        if (EmptyAndSizeUtils.isNotEmpty(alias)) {
            mAlias = alias;
        }
    }

    public boolean isOutime() {
        return isOutime;
    }

    public void setOutime(boolean outime) {
        isOutime = outime;
    }

    /**
     * 初始化默认的别名
     * @param alias
     */
    private  void setDefaultAlias(String alias) {
        if (EmptyAndSizeUtils.isEmpty(this.mAlias)) {
            if (EmptyAndSizeUtils.isNotEmpty(alias)) {
                mAlias = alias;
            }
        }
    }

    public String getBridgeName() {
        return mBridgeName;
    }


    /**
     * 初始化当前Webview，如果没有渲染执
     * 行渲染找到相应的Webview，并且创建相应的Webview代理器
     *
     * @param layout
     */
    public void initConfig(ViewGroup layout) {
        this.layout = layout;
        if (EmptyAndSizeUtils.isEmpty(mPageContainer)) {
            if (isUsefulWebView()) {
                JNILog.e("----------------------> 当前WebView 状态：" + EmptyAndSizeUtils.isEmpty(mPageContainer));
                mPageContainer = UtilsManager.getInstance().getFinder().finder(layout, this.wbId);
                if (EmptyAndSizeUtils.isNotEmpty(mPageContainer)) {
                    mWebViewProxy = new WebViewProxy(layout.getContext(), mPageContainer);
                    JNILog.e("-----------------------> WebViewProxy 为空，正在创建...." + mPageContainer.getUrl());
                    mPageContainer.setVerticalScrollBarEnabled(false);
                    mCommunicationProxy = new AndroidToJsSignalCommunicationProxy(mPageContainer.getContext());
                    mBridgeName = UtilsManager.getInstance().getBridgeTagFactory().getRandomTagName(getIndex());
                    JNILog.e("Net","设置桥接器....  "+mBridgeName+"   索引"+ getIndex());
                    setDefaultAlias(mBridgeName);
                    JNILog.e("============================> findView ..... ");
                }
            } else {
                JNILog.e("当前需要绑定WebView 的id 不能用");
            }
        } else {
            JNILog.e("不需要重新 find WebView");
        }
    }

    /**
     * 动态绑定别名，需要是才会用
     *
     * @param bridgeName
     * @param entity
     */
    public void bindJNIEntity(String bridgeName, AndroidToJsSignalCommunicationProxy entity) {
        if (EmptyAndSizeUtils.isNotEmpty(mWebViewProxy)) {
            JNILog.e("开始绑定通信桥梁");
            mWebViewProxy.setJsSwitcherEntity(bridgeName, entity);
        } else {
            JNILog.e("绑定通信桥梁失败");
        }
    }

    public WebViewProxy getWebViewProxy() {
        return mWebViewProxy;
    }



    /**
     * 注册通信桥接
     *
     * @param jniEntity
     */
    public void setJsSwitcherEntity(AndroidToJsSignalCommunicationProxy jniEntity) {
        if (EmptyAndSizeUtils.isNotEmpty(jniEntity) && EmptyAndSizeUtils.isNotEmpty(mWebViewProxy)) {
            JNILog.e("==========================================>bind entity ..... ");
            jniEntity.setAlias(this.getAlias());
            mWebViewProxy.setJsSwitcherEntity(getCommunicationProxy(),getBridgeName());

        }
    }

    /**
     * 开始加载网页，并且通知等待器等待该网页的接在完成
     *
     * @param
     * @param mWindowInterface
     */
    public void startLoading(AlertWindowManager.AlertWindowInterface mWindowInterface) {
        if (EmptyAndSizeUtils.isEmpty(getUrl())) {
            if (EmptyAndSizeUtils.isNotEmpty(mPageContainer)) {
                Context mContext = mPageContainer.getContext();
                Toast.makeText(mContext, "", Toast.LENGTH_SHORT).show();
            } else {
                JNILog.e("------------------------> 当前Webview为空，不能初始化 ：" + mPageContainer);
            }
            JNILog.e("------------------------> 网页 Url 不可用,无法加载....." + getUrl());
            UtilsManager.getInstance().getToastManager().showToast("请配置合法的url地址");
            return;
        }
        JNILog.e("----->加载中....\n\n" + url + "  " + (mWebViewProxy == null));
        mCommunicationProxy.setWebView(getPageContainer());
        mCommunicationProxy.setWebActivity(getPageContainer().getContext());
        mWebViewProxy.setWebViewWebClientForPopWindow(mWindowInterface);
        mWebViewProxy.loadUrl(getUrl());

    }

    @Override
    public String setPageUrl() {
        return this.url;
    }

    @Override
    public String toString() {
        return "UrlBinder{" +
                "url='" + url + '\'' +
                ", mTag=" + mTag +
                ", index=" + index +
                ", mAlias='" + mAlias + '\'' +
                ", mBridgeName='" + mBridgeName + '\'' +
                '}';
    }


    public void startNetWork(int ...timeout){
        if (EmptyAndSizeUtils.isNotEmpty(timeout)&&timeout.length!=0) {
            this.timeout = timeout[0];
        }
        setNetWorkQueryCompleted(false);
    }

    public void endNetWork(){
        setNetWorkQueryCompleted(true);
    }



    /**
     *  计算当前WebView加载网络状态
     * @return
     */
    public  boolean calcIsOk(){
        JNILog.e("Net ","=================================================> 状态 2 ："+getAlias() +"   "+ isNetWorkQueryCompleted);
        return  (isPageInflaterSucc()||isPageInflaterError())&&isNetWorkQueryCompleted();
    }

    /**
     * 发起网络请求 ...
     */
    public void startSynNetWork(ExecutorService netWorkTrusteeship){
        JNILog.e("Net ","----------------------------------> 开始加载...... "+getAlias());
        showMsg();
        AccompanyThread mAccompanyThread=new AccompanyThread(this);
        netWorkTrusteeship.execute(mAccompanyThread);
    }

    public void initFuture(Future<Object> future){
        mFuture =future;
    }

    public void showMsg(){
        JNILog.e("Net",getAlias()+" 状态信息："+"  isPageInflaterSucc  =  "+isPageInflaterSucc()+"  isPageInflaterError = "+isPageInflaterError()+"  isNetWorkQueryCompleted = " +isNetWorkQueryCompleted());
    }

    @Override
    public void startFitting() {
            try {
                //阻塞等待线程返回
                //返回值类型为Callable定义的类型
                //backValue = futureTask.get(2000, TimeUnit.MILLISECONDS);
                mFuture.get(getTimeout() * 1000, TimeUnit.MILLISECONDS);
                //executor.shutdown();
                //这种是不阻塞，等待5秒钟
                //future.get(5, TimeUnit.SECONDS);
            } catch (InterruptedException | ExecutionException e) {
                setOutime(true);
                e.printStackTrace();
                JNILog.e("Net ","方法执行异常：1" + e.getMessage());
                //executor.shutdown();
                if (EmptyAndSizeUtils.isNotEmpty(getStateInterface())) {
                    ((Activity) getPageContainer().getContext()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getStateInterface().onPageLoadError(getPageContainer(), getAlias());
                        }
                    });

                }
            } catch (TimeoutException e) {
                setOutime(true);
                JNILog.e("Net ","----------> 超时....... " + e.getMessage());
                //executor.shutdown();
                if (EmptyAndSizeUtils.isNotEmpty(getStateInterface())) {
                    ((Activity) getPageContainer().getContext()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getStateInterface().onPageLoadError(getPageContainer(), getAlias());
                        }
                    });

                }
            }
        }
}
