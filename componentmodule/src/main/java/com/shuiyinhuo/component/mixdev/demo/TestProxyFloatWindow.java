package com.shuiyinhuo.component.mixdev.demo;

import android.content.Context;
import android.view.View;

import com.shuiyinhuo.component.mixdev.abs.ProxyOnclickListener;
import com.shuiyinhuo.component.mixdev.caller.WidgetBtnConfigProxyInterface;
import com.shuiyinhuo.component.mixdev.entity.WindowBtnConfig;
import com.shuiyinhuo.component.mixdev.jinbean.em.WindowDefaulSizetStyle;
import com.shuiyinhuo.component.mixdev.jinbean.em.WindowTransParencyLevel;
import com.shuiyinhuo.component.mixdev.jinbean.pro.WindowButtonBaseConfig;
import com.shuiyinhuo.component.mixdev.proxy.ProxyTransparentWindow;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/9/4 0004
 * @ Description：透明Activity，当做dialog、PopupWindow 用
 * =====================================
 */
public  class TestProxyFloatWindow extends ProxyTransparentWindow {



    public TestProxyFloatWindow(Context context) {
        super(context);
    }

    public TestProxyFloatWindow(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected TestProxyFloatWindow(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onPreparedBeforeLayout() {
       setWindowDefaultStyle(WindowDefaulSizetStyle.STYLE_NORMAL);
       setWindowBottomButtonSConfig(new WidgetBtnConfigProxyInterface() {
           @Override
           public void setLeftButtonConfig(WindowBtnConfig leftButtonConfig) {
               leftButtonConfig.setButtonText("确认");
               leftButtonConfig.setOnclickListener(new ProxyOnclickListener() {
                   @Override
                   public void onClick(View view, int id) {
                       showToast("aaa");
                       dismissSelf();
                   }
               });
               leftButtonConfig.setBackgroundColor("#1E90FF");
           }

           @Override
           public void setRightButtonConfig(WindowBtnConfig rightButtonConfig) {

           }

           @Override
           public void setWindBaseConfig(WindowButtonBaseConfig baseConfig) {

           }
       });
    }

    @Override
    protected int getWindowResId() {
        return 0;
    }

    @Override
    public boolean setCanceledOnTouchOutside() {
        return true;
    }


    @Override
    protected void initWindowView(View view) {

    }

    @Override
    public int getResId() {
        return 0;
    }



}
