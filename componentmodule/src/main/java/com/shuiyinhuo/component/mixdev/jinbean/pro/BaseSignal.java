package com.shuiyinhuo.component.mixdev.jinbean.pro;

import android.webkit.WebView;

import com.shuiyinhuo.component.mixdev.caller.OnJSCarryValuesBackInterface;
import com.shuiyinhuo.component.mixdev.locationmanager.UtilsManager;
import com.shuiyinhuo.component.mixdev.utils.comm.EmptyAndSizeUtils;
import com.shuiyinhuo.component.mixdev.utils.comm.JNILog;
import com.shuiyinhuo.component.mixdev.utils.comm.ToastManager;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/8/25 0025
 * @ Description：
 * =====================================
 */
public class BaseSignal {
    protected ToastManager mToastManager;
    private OnJSCarryValuesBackInterface backInterface;
    public BaseSignal() {
        mToastManager= UtilsManager.getInstance().getToastManager();
    }

    public OnJSCarryValuesBackInterface getBackInterface() {
        return backInterface;
    }

    public void setBackInterface(OnJSCarryValuesBackInterface sbackInterface) {
        backInterface = sbackInterface;
    }

    /*用于加载网页数据*/
    public void loadDatas(String alias) {
        JNILog.e("页面加载完成，可加载数据");
    }

    public void showMsgWindow(String Alias,String tagName, String content) {
        mToastManager.showAlert(tagName, content);
    }


    public void startUploadImage(String Alias,String targetName, String index) {
        mToastManager.showAlert(targetName, index);
    }

    public String collectionValuePassToJs(String Alias,String targetName, String _json) {
        mToastManager.showAlert(targetName, "collectionValuePassToJs:\n\n" + "标签名：targetName = " + targetName + "\n数据： " + _json);
        return null;
    }


    /**
     * 该方法如果被复写，就不会调用 callJsWithValuesBackFromJs
     * 方法传进来的 OnJSCarryValuesBackInterface 接口方法，
     * 需要自行处理：
     * 方式有两种：
     * 1、不用复写该方法，使用OnJSCarryValuesBackInterface 接口的回调
     * 2、符写该方法，和values自行判断
     *
     * @param
     * @param _json
     * @param error
     */
    protected void whenBackValuesCalledByJs(String Alias,String _json, String error) {
        if (EmptyAndSizeUtils.isNotEmpty(getBackInterface())) {
            OnJSCarryValuesBackInterface mBackInterface = getBackInterface();
            if (EmptyAndSizeUtils.isNotEmpty(error)) {
                JNILog.e("方法调用错误：" + error);
                mBackInterface.onfailed(Alias,error);
            } else {
                JNILog.e("方法调用成功过：");
                mBackInterface.onSucc(Alias,_json);
            }
        } else {
            UtilsManager.getInstance().getToastManager().showAlert("\n   data =" + _json + "\n  error =" + error);
        }
    }



    public boolean onConfirm(String Alias,WebView view, String message) {
        mToastManager.showAlert("onConfirm:\n\n" + "message = " + message);
        return false;
    }


    public boolean onJsPrompt(String Alias,WebView view, String message) {
        mToastManager.showAlert("onJsPrompt:\n\n" + "message = " + message);
        return false;
    }


    public boolean onJsAlert(String Alias,WebView view, String message) {
        String msg = "onJsAlert:\n\n" + "message = " + message;
        JNILog.e("参数：\n " + msg);
        mToastManager.showAlert(msg);
        return false;
    }

    public void closeWindow(String alias){
        mToastManager.showToast("关闭窗口");
    }
}
