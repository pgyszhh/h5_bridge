package com.shuiyinhuo.component.mixdev.utils.comm;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.Spanned;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.shuiyinhuo.component.mixdev.activity.ProxyWebViewActivity;
import com.shuiyinhuo.component.mixdev.caller.WebPageStateInterface;
import com.shuiyinhuo.component.mixdev.locationmanager.UtilsManager;
import com.shuiyinhuo.component.mixdev.utils.widget.MsgWindow;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/8/21 0021
 * @ Description：
 * =====================================
 */
public class ToastManager {
    private  Context mContext;
    private WebPageStateInterface mStateInterface;



    public ToastManager(Context context) {
        mContext = context;
    }

    public void registStateInterface(WebPageStateInterface stateInterface) {
        mStateInterface = stateInterface;
    }

    private WebPageStateInterface getStateInterface() {
        return mStateInterface;
    }

    public void showToast(String msg) {
        if (EmptyAndSizeUtils.isNotEmpty(mContext)) {
            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
        }
    }

    public void showAlert(Context context, Object content) {
        JNILog.e("showAlert\n" + "context" + context );
        if (EmptyAndSizeUtils.isNotEmpty(getStateInterface())) {
            getStateInterface().showWindow(null, content);
        } else {
            UtilsManager.getInstance().getToastManager().showToast(content.toString());
        }
    }




    public void showAlert(String title, String content) {
        JNILog.e("showAlert:\n" + "   content = " + content);
        if (EmptyAndSizeUtils.isNotEmpty(getStateInterface())) {
           getStateInterface().showWindow(title,content);
        } else {
            UtilsManager.getInstance().getToastManager().showToast(content);
        }
    }

    public void showAlert(String title, Spanned content) {
        JNILog.e("showAlert:\n" + "   content = " + content);
        if (EmptyAndSizeUtils.isNotEmpty(getStateInterface())) {
           getStateInterface().showWindow(title,content);
        } else {
            UtilsManager.getInstance().getToastManager().showToast(content.toString());
        }
    }

    public void showAlert(String content) {
        showAlert("",content);
    }

    public void showAlert(Spanned content) {
        JNILog.e("showAlert:\n" + "   content = " + content);

        if (EmptyAndSizeUtils.isNotEmpty(getStateInterface())) {
           getStateInterface().showWindow(null,content);
        } else {
            UtilsManager.getInstance().getToastManager().showToast(content.toString());
        }
    }




}
