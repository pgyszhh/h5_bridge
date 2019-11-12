package com.shuiyinhuo.component.mixdev.jinbean;

import android.webkit.WebView;

import com.shuiyinhuo.component.mixdev.caller.OnJSCarryValuesBackInterface;
import com.shuiyinhuo.component.mixdev.jinbean.pro.BaseSignal;
import com.shuiyinhuo.component.mixdev.locationmanager.UtilsManager;
import com.shuiyinhuo.component.mixdev.utils.comm.EmptyAndSizeUtils;
import com.shuiyinhuo.component.mixdev.utils.comm.JNILog;
import com.shuiyinhuo.component.mixdev.utils.comm.ToastManager;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/8/6 0006
 * @ Description：网页数据回调接口代理
 * =====================================
 */
public  abstract class WebJNISignalProxy extends BaseSignal {

    public WebJNISignalProxy() {
        super();
    }
    abstract  public void   commDispatcher(String Alias,String tagName, String data);
    abstract  public boolean   checkForJsCall(String Alias,String tagName,String _json);
    abstract  public void   submit(String Alias,String tagName,String _json);



}
