package com.shuiyinhuo.component.mixdev.single;

import android.webkit.WebView;

import com.shuiyinhuo.component.mixdev.jinbean.WebJNISignalProxy;


/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/8/6 0006
 * @ Description：
 * =====================================
 */
public class SingleDataProxy extends WebJNISignalProxy {
    public SingleDataProxy() {
    }

    @Override
    public void commDispatcher(String Alias, String tagName, String data) {

    }

    @Override
    public boolean checkForJsCall(String Alias, String tagName, String _json) {
        return false;
    }

    @Override
    public void submit(String Alias, String tagName, String _json) {

    }


}
