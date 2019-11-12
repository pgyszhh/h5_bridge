package com.shuiyinhuo.component.mixdev.entity;

import android.webkit.WebView;

import com.shuiyinhuo.component.mixdev.caller.WebPageStateInterface;
import com.shuiyinhuo.component.mixdev.jinbean.pro.UrlBinder;
import com.shuiyinhuo.component.mixdev.utils.comm.EmptyAndSizeUtils;
import com.shuiyinhuo.component.mixdev.utils.comm.JNILog;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/8/22 0022
 * @ Description：所有页面观察者
 * =====================================
 */
public class LoadingObserver{
    private WebPageStateInterface mStateInterface;
    private ArrayList<UrlBinder> mBinderArrayList=new ArrayList<>();

    public LoadingObserver(WebPageStateInterface mStateInterface) {
        mBinderArrayList.clear();
        this.mStateInterface =mStateInterface;
    }

    public void attach(UrlBinder urlBinder){
        mBinderArrayList.add(urlBinder);
    }

    public void startObserve(){
        if (EmptyAndSizeUtils.isNotEmpty(mStateInterface)){
            mStateInterface.showLoading();
        }
    }

    public void checkState(WebView webView){
        if (EmptyAndSizeUtils.isNotEmpty(mBinderArrayList)){
            for (UrlBinder mBinder:mBinderArrayList){
                if (EmptyAndSizeUtils.isEmpty(mBinder)||mBinder.getPageContainer() == webView){
                        mBinderArrayList.remove(mBinder);
                        break;
                    }
                }
            }
        if (mBinderArrayList.size()==0){
            JNILog.e("--------------> page loading completed,loading will dismiss ..... ");
            if (EmptyAndSizeUtils.isNotEmpty(mStateInterface)){
                mStateInterface.dismissLoading();
            }else {
                JNILog.e("--------------> loading interface is NullPoint，please init");
            }
        }
    }

}
