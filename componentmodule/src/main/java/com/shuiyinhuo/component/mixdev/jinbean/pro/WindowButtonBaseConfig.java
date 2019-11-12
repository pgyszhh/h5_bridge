package com.shuiyinhuo.component.mixdev.jinbean.pro;

import com.shuiyinhuo.component.mixdev.jinbean.em.WindowDefaulSizetStyle;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/9/5 0005
 * @ Description：按钮布局基础配置
 * =====================================
 */
public class WindowButtonBaseConfig {
    private int btnHight=-1;
    private String lineColor;
    private int lineWidthSize=-1;
    /**
     * 默认窗口大小样式的设置
     */
    public WindowDefaulSizetStyle mWindowDefaultStyle= WindowDefaulSizetStyle.STYLE_NORMAL;

    public int getBtnHight() {
        return btnHight;
    }

    public void setBtnHight(int btnHight) {
        this.btnHight = btnHight;
    }

    public String getLineColor() {
        return lineColor;
    }

    public void setLineColor(String lineColor) {
        this.lineColor = lineColor;
    }

    public int getLineWidthSize() {
        return lineWidthSize;
    }

    public void setLineWidthSize(int lineWidthSize) {
        this.lineWidthSize = lineWidthSize;
    }

    public WindowDefaulSizetStyle getWindowDefaultStyle() {
        return mWindowDefaultStyle;
    }

    public void setWindowDefaultStyle(WindowDefaulSizetStyle windowDefaultStyle) {
        mWindowDefaultStyle = windowDefaultStyle;
    }
}
