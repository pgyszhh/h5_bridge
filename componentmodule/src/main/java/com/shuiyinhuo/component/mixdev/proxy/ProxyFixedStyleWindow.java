package com.shuiyinhuo.component.mixdev.proxy;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.shuiyinhuo.component.mixdev.proxy.ProxyTransparentWindow;
import com.shuiyinhuo.component.mixdev.utils.comm.EmptyAndSizeUtils;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/9/4 0004
 * @ Description：带有固定样式的Dialog,如果要自定义，请通过继承 ProxyTransparentWindow
 * =====================================
 */
public  class ProxyFixedStyleWindow extends ProxyTransparentWindow {
    private ViewGroup view;
    private int resId=-1;
    public ProxyFixedStyleWindow(Context context) {
        super(context);
    }

    public ProxyFixedStyleWindow(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected ProxyFixedStyleWindow(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onPreparedBeforeLayout() {

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
        return this.resId;
    }

    @Override
    protected View getContentView() {
        if (EmptyAndSizeUtils.isEmpty(view)) {
            return super.getContentView();
        }else {
            return view;
        }
    }

    public void setContentView(ViewGroup view){
        this.view=view;
    }
    public void setContentView(int resId){
        this.resId=resId;
    }

}
