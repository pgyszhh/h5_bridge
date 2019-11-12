package com.shuiyinhuo.component.mixdev.jinbean.threads;

import android.app.Activity;
import android.os.SystemClock;

import com.shuiyinhuo.component.mixdev.jinbean.pro.UrlBinder;
import com.shuiyinhuo.component.mixdev.utils.comm.EmptyAndSizeUtils;
import com.shuiyinhuo.component.mixdev.utils.comm.JNILog;

import java.util.concurrent.Callable;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/8/26 0026
 * @ Description：
 * =====================================
 */
public class MyWorker implements Callable {
    private UrlBinder mUrlBinder;

    public MyWorker(UrlBinder urlBinder) {
        mUrlBinder = urlBinder;
    }


    @Override
    public Object call() throws Exception {

        //线程执行体
        JNILog.e("Net ", "--------------> start hunting NetWork ...  ");
        while (!mUrlBinder.calcIsOk()) {
            if (mUrlBinder.isOutime()) {
                JNILog.e("Net ", "------------> 超时了..........");
                break;
            }
            mUrlBinder.showMsg();
            SystemClock.sleep(500);
        }
        ;
        if (!mUrlBinder.isOutime()) {
            if (EmptyAndSizeUtils.isNotEmpty(mUrlBinder.getStateInterface())) {
                ((Activity) mUrlBinder.getPageContainer().getContext()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mUrlBinder.getStateInterface().synchronizationLoadDatasToWebView(mUrlBinder.getAlias());
                    }
                });
            } else {
                JNILog.e("Net ", "--------------> hunting NetWork Completed, And pre to load datas to Page,But call Interface  ...  ");
            }
            JNILog.e("Net ", "--------------> hunting NetWork Completed, And pre to load datas to Page ...  ");

        }
        //返回值
        return null;
    }
}
