package com.shuiyinhuo.component.mixdev.entity;

import com.shuiyinhuo.component.mixdev.abs.ProxyOnclickListener;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/9/5 0005
 * @ Description：组件按钮配置器
 * =====================================
 */
public class WindowBtnConfig {
    /**
     * 按钮文字
     */
    private String mButtonText="";
    /**
     * 按钮背景颜色
     */
    private String mBackgroundColor="#FFFFFF";

    /**
     * 按钮字体颜色
     */
    private String mTextColor="#000000";
    /**
     * 文字大小
     */
    private int mTextSize=12;
    /**
     * 按钮监听事件
     */
    private ProxyOnclickListener mOnclickListener;


    public String getButtonText() {
        return mButtonText;
    }

    public void setButtonText(String buttonText) {
        mButtonText = buttonText;
    }

    public String getBackgroundColor() {
        return mBackgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        mBackgroundColor = backgroundColor;
    }

    public String getTextColor() {
        return mTextColor;
    }

    public void setTextColor(String textColor) {
        mTextColor = textColor;
    }

    public int getTextSize() {
        return mTextSize;
    }

    public void setTextSize(int textSize) {
        mTextSize = textSize;
    }

    public ProxyOnclickListener getOnclickListener() {
        return mOnclickListener;
    }

    public void setOnclickListener(ProxyOnclickListener onclickListener) {
        mOnclickListener = onclickListener;
    }
}
