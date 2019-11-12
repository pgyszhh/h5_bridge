package com.shuiyinhuo.component.mixdev.entity;

import android.text.Spanned;

import com.shuiyinhuo.component.mixdev.utils.comm.EmptyAndSizeUtils;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/9/6 0006
 * @ Description：窗口默认配置
 * =====================================
 */
public class DefaultStyleConfig {
    private String mTitle="";
    private String mContent="";
    private Spanned mSpannedContent=null;

    /**
     * 默认字体颜色
     */
    private String mTitleColor="#000000";
    private String mContentColor="#000000";
    /**
     * 标题栏颜色
     */
    private String mTitleBarColor="#FFFFFF";
    /**
     * 内容背景颜色
     */
    private String mContentBgColor="#FFFFFF";

    /**
     * 标题栏字体大小
     */
    private int titleSize=16;
    /**
     * 正文字体大小
     */
    private int mContentSize=15;
    /**
     * 默认的标题栏高度
     */
    private int mTitleBarHeight=-1;

    /**
     * 标题栏是否为加粗
     */
    private boolean isTitleBold=false;

    /**
     * 是否展示标题栏下的线
     */
    private boolean isShowTitleBarUnderLine=true;


    public String getTitle() {
        return mTitle;
    }

    public boolean isContentNotEmpty(){
        return EmptyAndSizeUtils.isNotEmpty(getContent())||EmptyAndSizeUtils.isNotEmpty(getSpannedContent());
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public Spanned getSpannedContent() {
        return mSpannedContent;
    }

    public void setSpannedContent(Spanned spannedContent) {
        mSpannedContent = spannedContent;
    }

    public String getTitleColor() {
        return mTitleColor;
    }

    public void setTitleColor(String titleColor) {
        mTitleColor = titleColor;
    }

    public String getContentColor() {
        return mContentColor;
    }

    public void setContentColor(String contentColor) {
        mContentColor = contentColor;
    }

    public String getTitleBarColor() {
        return mTitleBarColor;
    }

    public void setTitleBarColor(String titleBarColor) {
        mTitleBarColor = titleBarColor;
    }

    public int getTitleSize() {
        return titleSize;
    }

    public void setTitleSize(int titleSize) {
        this.titleSize = titleSize;
    }

    public int getContentSize() {
        return mContentSize;
    }

    public void setContentSize(int contentSize) {
        mContentSize = contentSize;
    }

    public int getTitleBarHeight() {
        return mTitleBarHeight;
    }

    public void setTitleBarHeight(int titleBarHeight) {
        mTitleBarHeight = titleBarHeight;
    }

    public String getContentBgColor() {
        return mContentBgColor;
    }

    public void setContentBgColor(String contentBgColor) {
        mContentBgColor = contentBgColor;
    }

    public boolean isTitleBold() {
        return isTitleBold;
    }

    public void setTitleBold(boolean titleBold) {
        isTitleBold = titleBold;
    }

    public boolean isShowTitleBarUnderLine() {
        return isShowTitleBarUnderLine;
    }

    public void setShowTitleBarUnderLine(boolean showTitleBarUnderLine) {
        isShowTitleBarUnderLine = showTitleBarUnderLine;
    }


}
