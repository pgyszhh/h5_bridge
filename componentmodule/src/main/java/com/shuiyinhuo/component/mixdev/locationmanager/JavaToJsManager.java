package com.shuiyinhuo.component.mixdev.locationmanager;

import android.content.Context;

import com.shuiyinhuo.component.mixdev.dispatcher.dispatch.MessageDisapther;
import com.shuiyinhuo.component.mixdev.dispatcher.observe.FunctionHunter;
import com.shuiyinhuo.component.mixdev.jinbean.WebConfig;
import com.shuiyinhuo.component.mixdev.jinbean.WebJNISignalProxy;
import com.shuiyinhuo.component.mixdev.single.SingleDataProxy;
import com.shuiyinhuo.component.mixdev.utils.comm.EmptyAndSizeUtils;


/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/8/6 0006
 * @ Description：
 * =====================================
 */
public class JavaToJsManager {

    /**
     * 通用弹窗方法封装类
     */
    private WebJNISignalProxy mWebDataProxy;
    /**
     * 网页配置信息，如地址等
     */
    private WebConfig mWebConfig;
    private FunctionHunter mFunctionHunter;
    private MessageDisapther mMessageDisapther;

    //是否延迟加载数据
    public static boolean IS_DELAY_LOAD = false;
    //延迟加载的秒数
    public static int DELAY_SECONDS = 100;

    public JavaToJsManager() {
        mWebConfig = new WebConfig() ;
    }


    public void initDefaultConfig(){
        setWebDataProxy(new SingleDataProxy());
    }

    public FunctionHunter getFunctionHunter() {
        return mFunctionHunter;
    }

    public void setFunctionHunter(FunctionHunter functionHunter) {
        mFunctionHunter = functionHunter;
    }

    public WebConfig getWebConfig() {
        return mWebConfig;
    }

    public MessageDisapther getMessageDisapther() {
        return mMessageDisapther;
    }

    public void setMessageDisapther(MessageDisapther messageDisapther) {
        mMessageDisapther = messageDisapther;
    }

    public WebJNISignalProxy getWebDataProxy() {
        return mWebDataProxy;
    }

    public void setWebDataProxy(WebJNISignalProxy webDataProxy) {
        if (EmptyAndSizeUtils.isEmpty(webDataProxy)){
            initDefaultConfig();
        }else {
            mWebDataProxy = webDataProxy;
        }
        mMessageDisapther=new MessageDisapther(mWebDataProxy);
        mFunctionHunter=new FunctionHunter(mMessageDisapther);
    }


}




