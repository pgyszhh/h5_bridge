package com.shuiyinhuo.component.mixdev.activity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shuiyinhuo.component.mixdev.caller.BackListenerInterface;
import com.shuiyinhuo.component.mixdev.caller.WidgetBtnConfigProxyInterface;
import com.shuiyinhuo.component.mixdev.caller.WidgetInterface;
import com.shuiyinhuo.component.mixdev.jinbean.pro.WindowButtonBaseConfig;
import com.shuiyinhuo.component.mixdev.locationmanager.UtilsManager;
import com.shuiyinhuo.component.mixdev.proxy.ProxyTranparentActity;
import com.shuiyinhuo.component.mixdev.utils.comm.EmptyAndSizeUtils;
import com.shuiyinhuo.component.mixdev.utils.comm.ScreenUtils;
import com.shuiyinhuo.component.mixdev.utils.comm.ViewFinder;
import com.shuiyinhuo.component.mixdev.utils.widget.CircleRadiusRelativeLayout;
import com.shuiyinhuo.component.mixdev.utils.widget.Loading;
import com.shuiyinhuo.component.mixdev.proxy.ProxyWidgetForWebView;
import com.shuiyinhuo.component.mixdev.entity.WindowBtnConfig;

import java.lang.reflect.Method;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/9/4 0004
 * @ Description：透明Activity，当做dialog、PopupWindow 用
 * =====================================
 */
public abstract class ProxyBaseActivityOfDialogWindow extends ProxyTranparentActity {
    /**
     * 可见窗口占设备的窗口的比例
     */
    private float windowPercent = 0.7f;
    private CircleRadiusRelativeLayout mContentView;
    private View mWindowContentView = null;
    /**
     * 基础宽高比例
     */
    private  float basePercent=1.3f;
    /**
     * 默认的透明度
     */
    private int mTransparentLeven = 7;
    //默认的窗体圆角半径
    private int radiusValues = 10;
    //窗体背景颜色
    private String mWindowBgColor = "#FFFFFF";

    /**
     * 底部按钮参数
     *  left：左按钮
     *  right:右边按钮
     */
    private WindowBtnConfig left=new WindowBtnConfig();
    private WindowBtnConfig right=new WindowBtnConfig();
    /**
     * 按钮高度
     */
    private int btnHight=-1;
    /**
     * 线条颜色
     */
    private String lineColor;
    /**
     * 线条宽度
     */
    private int lineWidthSize;
    /**
     * 默认的按钮中间线条宽度
     */
    private int lineWidth = 1;
    /**
     * 默认的按钮中间的颜色
     */
    private String mLineColor = "#696969";

    /**
     * 默认为宽口高度的1/10
     */
    private int newHeight = -1;
    private ViewGroup BtnContentView;
    /**
     * 按钮组数量标记
     */
    private WidgetBtnConfigProxyInterface buttonConfig;


    @Override
    protected void prepareLayout() {
        this.buttonConfig=setButtonConfig();
    }



    public WebView getDefaultWebView() {
        if (mWindowContentView instanceof WebView) {
            return (WebView) mWindowContentView;
        }
        return null;
    }




    /**
     * 构建主window界面
     * @return
     */
    public ViewGroup generatorMainContentView() {
        RelativeLayout mFrameLayout = new RelativeLayout(mContext);
        FrameLayout.LayoutParams mParams = new FrameLayout.LayoutParams(ScreenUtils.getScreenWidth(mContext), ScreenUtils.getScreenHeight(mContext));
        //mFrameLayout.setBackgroundColor(Color.YELLOW);
        mFrameLayout.setGravity(Gravity.CENTER);
        mFrameLayout.setLayoutParams(mParams);
        RelativeLayout father = new RelativeLayout(mContext);
        mContentView = new CircleRadiusRelativeLayout(mContext);
        mContentView.setInterval(radiusValues);
        mContentView.setBackgroundColor(mWindowBgColor);
        int width = (int) ((ScreenUtils.getScreenWidth(mContext) * windowPercent));

        int height = (int) (width * basePercent);
        FrameLayout.LayoutParams contentParams = new FrameLayout.LayoutParams(width, height);
        mContentView.setLayoutParams(contentParams);
        contentParams.gravity = Gravity.CENTER;
        //mContentView.setBackgroundColor(Color.parseColor("#1E90FF"));

        RelativeLayout.LayoutParams fatherParams = new RelativeLayout.LayoutParams(width, height);
        //father.setBackgroundColor(Color.parseColor("#D8BFD8"));
        father.setBackgroundColor(Color.parseColor("#00000000"));
        fatherParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        father.setLayoutParams(fatherParams);
        father.setGravity(Gravity.CENTER);

        father.addView(mContentView);
        mWindowContentView = null;
        if (getResId() <= 0) {
            mWindowContentView = getContentView();
            if (EmptyAndSizeUtils.isEmpty(mWindowContentView)) {
                mWindowContentView = new WebView(mContext);
            }
        } else {
            mWindowContentView = mInflater.inflate(getResId(), null, false);
        }

        if (EmptyAndSizeUtils.isNotEmpty(buttonConfig)){
            buttonConfig.setLeftButtonConfig(this.left);
            buttonConfig.setRightButtonConfig(this.right);
            if (EmptyAndSizeUtils.isEmpty(left.getButtonText())){
                this.left=null;
            }
            if (EmptyAndSizeUtils.isEmpty(this.right.getButtonText())){
                this.right=null;
            }
            WindowButtonBaseConfig baseConfig=new WindowButtonBaseConfig();
            buttonConfig.setWindBaseConfig(baseConfig);
            this.btnHight=baseConfig.getBtnHight();
            this.lineColor=baseConfig.getLineColor();
            this.lineWidthSize=baseConfig.getLineWidthSize();
            mContentView.setButtonMidLineColor(mLineColor);
            mContentView.setLineWidth(lineWidth);
            if (EmptyAndSizeUtils.isNotEmpty(left)){
                mContentView.setLeftButtonColor(left.getBackgroundColor());
            }
            if (EmptyAndSizeUtils.isNotEmpty(right)){
                mContentView.setRightButtonColor(right.getBackgroundColor());
            }
            View btnView=  generatorWithDoubleButtonLayout(this.left, this.right,mContentView.getInterval());
            initCarryBtnLayout(mWindowContentView,width,height,mContentView.getInterval());
            BtnContentView.addView(mWindowContentView);
            father.addView(resetViewWindowParams(btnView, mContentView.getInterval()));
        }else {

            father.addView(resetViewWindowParams(mWindowContentView, mContentView.getInterval()));
        }

        //father.addView(resetViewWindowParams(mWindowContentView, mContentView.getInterval()));
        //father.addView(mWindowContentView);
        mFrameLayout.removeAllViews();
        mFrameLayout.addView(father);
        /*mFrameLayout.addView(mContentView);*/
        return mFrameLayout;
    }

    /**
     * 重置窗口参数
     * @param view
     * @param dis
     * @return
     */
    private View resetViewWindowParams(View view, int dis) {
        RelativeLayout.LayoutParams mLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        int width = (int) ((ScreenUtils.getScreenWidth(mContext) * windowPercent));
        int height = (int) (width * basePercent);
        mLayoutParams.width = width;
        mLayoutParams.height = (int) (height - dis * 4f);
        mLayoutParams.leftMargin = 0;
        mLayoutParams.topMargin = (int) (dis * 2f);
        view.setBackgroundColor(Color.parseColor(checkColorValue(mWindowBgColor)));
        view.setLayoutParams(mLayoutParams);
        return view;
    }




    /**
     * 构造双按钮布局
     *
     * @return
     */
    private View generatorWithDoubleButtonLayout(WindowBtnConfig left, WindowBtnConfig right,int dis) {
        int width = (int) ((ScreenUtils.getScreenWidth(mContext) * windowPercent));
        int height = (int) (width * basePercent);

        /**
         * 默认的按钮高度
         */
        if (EmptyAndSizeUtils.isNotEmpty(btnHight)) {
            if (btnHight!=-1){
                if (btnHight >= 500) {
                    newHeight = 200;
                } else if (btnHight <30) {
                    newHeight = 30;
                }else {
                    newHeight = btnHight;
                }
            }else {
                newHeight= (int) (height*1f/10);
            }

        }else {
            newHeight= (int) (height*1f/10);
        }


        if (EmptyAndSizeUtils.isNotEmpty(lineColor)) {
            mLineColor = lineColor;
        }

        if (EmptyAndSizeUtils.isNotEmpty(lineWidthSize)) {
            if (lineWidthSize !=-1) {
                if (lineWidthSize >= 20) {
                    lineWidth = 20;
                } else if (lineWidthSize <= 0) {
                    lineWidth = 0;
                } else {
                    lineWidth = lineWidthSize;
                }
            }

        }

        /**
         * 父布局
         */
        LinearLayout mmParent = new LinearLayout(mContext);
        mmParent.setOrientation(LinearLayout.VERTICAL);
        RelativeLayout.LayoutParams mLayoutParams = new RelativeLayout.LayoutParams(width, height);
        mmParent.setLayoutParams(mLayoutParams);




        /**
         * 底部按钮布局
         */
        LinearLayout mmButtonParent = new LinearLayout(mContext);
        mmButtonParent.setOrientation(LinearLayout.HORIZONTAL);
        RelativeLayout.LayoutParams mButtonParentParams = new RelativeLayout.LayoutParams(width, newHeight);
        mmButtonParent.setLayoutParams(mButtonParentParams);

        /**
         * 按钮组
         */
        int btnWinth = EmptyAndSizeUtils.isNotEmpty(left) && EmptyAndSizeUtils.isNotEmpty(right) ? (int) ((width * 1f) / 2) : width;


        TextView mLeftButton = new TextView(mContext);
        mLeftButton.setClickable(true);
        mLeftButton.setGravity(Gravity.CENTER);
        RelativeLayout.LayoutParams mLeftButtonParams = new RelativeLayout.LayoutParams(btnWinth, newHeight - lineWidth);
        mLeftButton.setLayoutParams(mLeftButtonParams);
        if (EmptyAndSizeUtils.isNotEmpty(left)) {
            test_color(mLeftButton, EmptyAndSizeUtils.isEmpty(left.getBackgroundColor()) ? "#DCDCDC" : left.getBackgroundColor());
            mLeftButton.setTextColor(Color.parseColor(checkColorValue(EmptyAndSizeUtils.isEmpty(left.getTextColor()) ? "##000000" : left.getTextColor())));
            mLeftButton.setText(EmptyAndSizeUtils.isEmpty(left.getButtonText()) ? "确认" : left.getButtonText());
            if (EmptyAndSizeUtils.isNotEmpty(left.getOnclickListener())) {
                mLeftButton.setOnClickListener(left.getOnclickListener());
            }
            mLeftButton.setTextSize(left.getTextSize());
        }

        View maindLine = new View(mContext);
        maindLine.setBackgroundColor(Color.parseColor(checkColorValue(mLineColor)));
        test_color(maindLine, mLineColor);
        RelativeLayout.LayoutParams maindLineParams = new RelativeLayout.LayoutParams(width, lineWidth);
        maindLine.setLayoutParams(maindLineParams);


        View mdLine = new View(mContext);
        RelativeLayout.LayoutParams mdLineParams = new RelativeLayout.LayoutParams(lineWidth, newHeight - lineWidth);
        mdLine.setLayoutParams(mdLineParams);
        test_color(mdLine, mLineColor);

        TextView mRightButton = new TextView(mContext);
        mRightButton.setGravity(Gravity.CENTER);
        mRightButton.setClickable(true);
        RelativeLayout.LayoutParams mRightButtonParams = new RelativeLayout.LayoutParams(btnWinth, newHeight - lineWidth);
        mRightButton.setLayoutParams(mRightButtonParams);

        if (EmptyAndSizeUtils.isNotEmpty(right)) {
            test_color(mRightButton, EmptyAndSizeUtils.isEmpty(right.getBackgroundColor()) ? "#DCDCDC" : right.getBackgroundColor());
            mRightButton.setTextColor(Color.parseColor(checkColorValue(EmptyAndSizeUtils.isEmpty(right.getTextColor()) ? "##000000" : right.getTextColor())));
            mRightButton.setText(EmptyAndSizeUtils.isEmpty(right.getButtonText()) ? "取消" : right.getButtonText());
            if (EmptyAndSizeUtils.isNotEmpty(right.getOnclickListener())) {
                mRightButton.setOnClickListener(right.getOnclickListener());
            }
            mRightButton.setTextSize(right.getTextSize());
        }

        mmButtonParent.removeAllViews();
        mmParent.removeAllViews();

        if (EmptyAndSizeUtils.isNotEmpty(left) && EmptyAndSizeUtils.isNotEmpty(right)) {
            mmButtonParent.addView(mLeftButton);
            mmButtonParent.addView(mdLine);
            mmButtonParent.addView(mRightButton);
        } else if (EmptyAndSizeUtils.isNotEmpty(left) && EmptyAndSizeUtils.isEmpty(right)) {
            mmButtonParent.addView(mLeftButton);
        } else if (EmptyAndSizeUtils.isNotEmpty(right) && EmptyAndSizeUtils.isEmpty(left)) {
            mmButtonParent.addView(mRightButton);
        }

        BtnContentView =new LinearLayout(mContext);
        initCarryBtnLayout(BtnContentView, width, height,dis);
        mmParent.addView(BtnContentView);
        mmParent.addView(maindLine);
        mmParent.addView(mmButtonParent);
        return mmParent;
    }

    /**
     * 重置按钮参数
     * @param view
     * @param width
     * @param height
     * @param dis
     */
    private void initCarryBtnLayout(View view,int width,int height,int dis){
        if (EmptyAndSizeUtils.isNotEmpty(view)) {
            RelativeLayout.LayoutParams tesmLayoutParams = new RelativeLayout.LayoutParams(width, height - newHeight-dis*3);
            view.setLayoutParams(tesmLayoutParams);
        }
    }

    /**
     * 测试背景颜色
     * @param v
     * @param color
     */
    private void test_color(View v, String color) {
        v.setBackgroundColor(Color.parseColor(checkColorValue(color)));
    }

    /**
     * 颜色监测
     *
     * @param color
     * @return
     */
    private String checkColorValue(String color) {
        if (EmptyAndSizeUtils.isNotEmpty(color)) {
            color = color.startsWith("#") ? color : "#" + color;
        } else {
            color = "#E6E6FA";
        }
        return color;
    }





    /**
     * 窗口基础参数设置
     * @param windowTransLevel
     * @param windowSizePercentOfParent
     * @param windowBgColor
     */
    public void setBaseParams(int windowTransLevel, float windowSizePercentOfParent, String windowBgColor) {
        if (windowTransLevel > 10) {
            mTransparentLeven = 10;
        } else if (windowTransLevel <= 0) {
            mTransparentLeven = 0;
        } else {
            mTransparentLeven = windowTransLevel;
        }

        if (windowSizePercentOfParent >= 1) {
            windowPercent = 1f;
        } else if (windowSizePercentOfParent <= 0.4) {
            windowPercent = 0.4f;
        } else {
            windowPercent = windowSizePercentOfParent;
        }

        if (EmptyAndSizeUtils.isNotEmpty(windowBgColor)) {
            mWindowBgColor = windowBgColor;
        }
    }

    /**
     * 设置 窗体占父窗口的比例，调整窗体大小
     *
     * @param windowPercent
     */
    public void setWindowPercentOfParent(float windowPercent) {
        this.windowPercent = windowPercent;
    }

    /**
     * 设置底部按钮参数
     */
    public void setBottomButton(String leftButtonBgColor,String rightButtonBgColor, String meddleLineColor) {
        if (EmptyAndSizeUtils.isNotEmpty(mContentView)) {
            mContentView.setLeftButtonColor(leftButtonBgColor);
            mContentView.setRightButtonColor(rightButtonBgColor);
            mContentView.setButtonMidLineColor(meddleLineColor);
        }
    }

    /**
     * 设置底部按钮参数
     */
    public void setFooterBackgroundBorderColor(String leftButtonBgColor) {
        if (EmptyAndSizeUtils.isNotEmpty(mContentView)) {
            mContentView.setLeftButtonColor(leftButtonBgColor);
        }
    }

    /**
     * 设置头部边框颜色，当定义头部一体的组件时使用
     */
    public void setHeaderBackgroundBorderColor(String headerBackgroundColor){
        if (EmptyAndSizeUtils.isNotEmpty(headerBackgroundColor)){
            mContentView.setHeaderBgColor(headerBackgroundColor);
        }
    }

    /**
     *  提供的布局资源，需要渲染
     * @return
     */
    public abstract int getResId();



    /**
     * 通过提供的view去填充主布局
     * @return
     */
    public View getContentView() {

        return null;
    }

    /**
     * 初始化底部双按钮
     */
    public WidgetBtnConfigProxyInterface setButtonConfig(){
        return null;
    }


}
