package com.shuiyinhuo.component.mixdev.jinbean;

import android.view.View;
import android.webkit.WebView;

import com.shuiyinhuo.component.mixdev.jinbean.pro.Judger;
import com.shuiyinhuo.component.mixdev.utils.comm.EmptyAndSizeUtils;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/8/6 0006
 * @ Description：布局配置参数
 * =====================================
 */
public class LayoutConfig {
    private Judger mJudger =new Judger();
    public void  bindMainLayout(View mainLayout){
        mJudger.bindMainLayout(mainLayout);
    }

    public void  bindMainLayout(int layoutId){
        mJudger.bindMainLayout(layoutId);
    }

    /**
     * 绑定 url 到 Webview
     * <p>
     * 可以同时设置多个WebViewId
     */

    public void bindUrlToWebView(int wbId, String url,String aliasKey) {
        if (mJudger.isCanBinder()) {
            mJudger.bindUrlToWebView(wbId, url,aliasKey);
        }
    }

    /**
     * 只提供一个Webview id,将来默认加载 主页提供的地址
     *
     *
     */

    public void bindWebViewId(int wbId,String url) {
        if (mJudger.isCanBinder()) {
            mJudger.bindWebViewId(wbId,url,null);
        }
    }

    /**
     * 只提供一个Webview,将来默认加载 主页提供的地址
     */
    public void bindWebView(WebView web,String url) {
        if (mJudger.isCanBinder()) {
            mJudger.bindWebView(web,url,null);
        }
    }

    /**
     * 绑定 url 到 Webview
     * <p>
     * 可以同时设置多个WebView 与 url，彼此绑定
     */
    public void bindUrlToWebView(WebView web, String url,String aliasKey) {
        if (mJudger.isCanBinder()) {
            mJudger.bindUrlToWebView(web, url,aliasKey);
        }

    }

    /**
     * 测试别名是否设置
     * @return
     */
    private String testAlias(String ...args){
        if (EmptyAndSizeUtils.isEmpty(args)&& args.length!=0){
            return args[0];
        }else {
                return "";
        }
    }



    public Judger getJudger() {
        return mJudger;
    }

}
