package com.shuiyinhuo.component.mixdev.entity;

import android.content.Context;

import com.shuiyinhuo.component.mixdev.utils.comm.ScreenUtils;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/9/8 0008
 * @ Description：基础透明dialog设置
 * =====================================
 */
public class BaseDialogConfig {
    private Context mContext;
    private int windowWidth=-1;
    private int windowHeight=-1;
    public enum WindowPosition{TOP,BOTTOM,CENTER}
    //默认的位置
    private WindowPosition location= WindowPosition.CENTER;
    private String backgroundColor="#FFFFFF";
    //x轴是不是满屏,默认是满屏
    private boolean isFullWidth=true;
    //y轴高度占宽度的比例，确定窗口的形状
    private float yPercentOfX=0.6f;

    public BaseDialogConfig(Context context) {
        mContext = context;
        windowWidth = ScreenUtils.getScreenWidth(mContext) ;
        windowHeight = (int) (windowWidth* 7f / 8*0.6f);
    }

    public int getWindowWidth() {
        if (isFullWidth()){
            windowWidth = ScreenUtils.getScreenWidth(mContext);
            windowHeight = (int) (windowWidth*0.6f);
            return windowWidth;
        }else {
            return (int) (windowWidth* 7f / 8);
        }
    }

    public void setWindowWidth(int windowWidth) {
        this.windowWidth = windowWidth;
    }

    public int getWindowHeight() {
        return (int) (getWindowWidth()*getyPercentOfX());
    }

    public void setWindowHeight(int windowHeight) {
        this.windowHeight = windowHeight;
    }

    public WindowPosition getLocation() {
        return location;
    }

    public void setLocation(WindowPosition location) {
        this.location = location;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public boolean isFullWidth() {
        return isFullWidth;
    }

    public void setFullWidth(boolean fullWidth) {
        isFullWidth = fullWidth;
    }

    private float getyPercentOfX() {
        return yPercentOfX;
    }

    public void setyPercentOfX(float yPercentOfX) {
        this.yPercentOfX = yPercentOfX;
    }
}
