package com.shuiyinhuo.component.mixdev.proxy;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shuiyinhuo.component.mixdev.caller.DialogWindowDefaultSytleInterface;
import com.shuiyinhuo.component.mixdev.caller.WidgetBtnConfigProxyInterface;
import com.shuiyinhuo.component.mixdev.caller.WidgetInterface;
import com.shuiyinhuo.component.mixdev.caller.WindowWidgetListenerMonitor;
import com.shuiyinhuo.component.mixdev.entity.DefaultStyleConfig;
import com.shuiyinhuo.component.mixdev.entity.WindowBtnConfig;
import com.shuiyinhuo.component.mixdev.jinbean.em.WindowDefaulSizetStyle;
import com.shuiyinhuo.component.mixdev.jinbean.pro.WindowButtonBaseConfig;
import com.shuiyinhuo.component.mixdev.locationmanager.UtilsManager;
import com.shuiyinhuo.component.mixdev.utils.comm.EmptyAndSizeUtils;
import com.shuiyinhuo.component.mixdev.utils.comm.JNILog;
import com.shuiyinhuo.component.mixdev.utils.comm.ScreenUtils;
import com.shuiyinhuo.component.mixdev.utils.widget.CircleRadiusRelativeLayout;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/9/6 0006
 * @ Description：带有样式的dialog
 * =====================================
 */
public abstract class ProxyTransparentWindow extends ProxyBaseTransparentWindow {
    //窗口声明控制监控
    private WindowWidgetListenerMonitor mMonitor;
    //窗体底部样式配置选项
    private WindowButtonBaseConfig baseConfig;
    //默认样式
    private DialogWindowDefaultSytleInterface defaultSytleInterface;


    //可见窗口占设备的窗口的比例 x轴
    private float windowPercentX = 0.75f;
    //可见窗口占设备的窗口的比例 y轴
    private float windowPercentY = -1;
    //样式控制器
    private CircleRadiusRelativeLayout mContentView;
    //基础宽高比例
    private float basePercent = 1.5f;//0.8
    //默认的窗体圆角半径
    private int radiusValues = 10;
    //窗体背景颜色
    private String mWindowBgColor = "#FFFFFF";
    //状态栏与底部按钮高度比例
    int baseNumber = 6;
    //默认的窗口基础大小配置
    private WindowDefaulSizetStyle mDefaultStyle = WindowDefaulSizetStyle.STYLE_NORMAL;

    /**
     * 底部按钮参数
     * left：左按钮
     * right:右边按钮
     */
    private WindowBtnConfig left = new WindowBtnConfig();
    private WindowBtnConfig right = new WindowBtnConfig();
    //按钮高度
    private int btnHight = -1;
    //线条颜色
    private String lineColor;
    //线条宽度
    private int lineWidthSize;
    //默认的按钮中间线条宽度
    private int lineWidth = 1;
    //默认的按钮中间的颜色
    private String mLineColor = "#696969";

    //默认为宽口高度的1/10
    private int newHeight = -1;
    private ViewGroup BtnContentView;
    //按钮组数量标记
    private WidgetBtnConfigProxyInterface buttonConfig;
    //设置默认样式主窗口高度
    private int carryBtnMainHeight = -1;
    //底部按钮最终的高度
    private int relHeight = -1;


    public ProxyTransparentWindow(Context context) {
        this(context, -1);
    }

    public ProxyTransparentWindow(Context context, int themeResId) {
        this(context, false, null);
    }

    protected ProxyTransparentWindow(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }

    private void init() {
        /**设置对话框背景透明*/
        mContentView = new CircleRadiusRelativeLayout(mContext);
        baseConfig = new WindowButtonBaseConfig();
    }



    private WindowWidgetListenerMonitor getMonitor() {
        return mMonitor;
    }

    public void setWindowStateMonitor(WindowWidgetListenerMonitor monitor) {
        mMonitor = monitor;
    }

    public WebView getDefaultWebView() {
        if (mWindowContentView instanceof WebView) {
            return (WebView) mWindowContentView;
        }
        return null;
    }

    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        showToast("返回");
        if (keyCode==KeyEvent.KEYCODE_BACK){
            showToast("返回");
        }
        return super.onKeyDown(keyCode, event);
    }*/






    @Override
    public void onBackPressed() {
        JNILog.e("------------->onBackPressed");
        if (EmptyAndSizeUtils.isNotEmpty(getMonitor())) {
            getMonitor().onBackPressed(this);
            if (getMonitor().isCanBack()) {
                super.onBackPressed();
            } else {
                return;
            }
        }
        super.onBackPressed();
    }


    /**
     * 构建主window界面 获取窗口布局
     *
     * @return
     */
    @Override
    protected View getContentView() {
        RelativeLayout mFrameLayout = new RelativeLayout(mContext);
        //FrameLayout.LayoutParams mParams = new FrameLayout.LayoutParams(ScreenUtils.getScreenWidth(mContext), ScreenUtils.getScreenHeight(mContext));
        FrameLayout.LayoutParams mParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        //mFrameLayout.setBackgroundColor(Color.YELLOW);
        mFrameLayout.setGravity(Gravity.CENTER);
        mFrameLayout.setLayoutParams(mParams);
        RelativeLayout father = new RelativeLayout(mContext);
        father.setOnClickListener(null);
        mContentView.setInterval(radiusValues);
        mContentView.setBackgroundColor(mWindowBgColor);
        int width = getWindowWidth();
        if (EmptyAndSizeUtils.isEmpty(buttonConfig) && EmptyAndSizeUtils.isNotEmpty(this.defaultSytleInterface)) {
            basePercent = 0.6f;
            baseConfig.setWindowDefaultStyle(null);
        } else if (EmptyAndSizeUtils.isEmpty(this.defaultSytleInterface) && EmptyAndSizeUtils.isNotEmpty(buttonConfig)) {
            buttonConfig.setWindBaseConfig(baseConfig);
            WindowDefaulSizetStyle mWindowDefaultStyle = baseConfig.getWindowDefaultStyle();
            basePercent = mWindowDefaultStyle.getValue();
        } else if (EmptyAndSizeUtils.isNotEmpty(buttonConfig) && EmptyAndSizeUtils.isNotEmpty(this.defaultSytleInterface)) {
            basePercent = 0.7f;
            baseConfig.setWindowDefaultStyle(null);
        } else {
            baseConfig.setWindowDefaultStyle(null);
            if (EmptyAndSizeUtils.isNotEmpty(this.mDefaultStyle)) {
                basePercent = calcBtnHeight(this.mDefaultStyle,true);
            }
        }

        int height = getWindowHeight();
        if (windowPercentY == -1) {
            height = (int) (width * basePercent);
        }


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

        if (EmptyAndSizeUtils.isEmpty(this.defaultSytleInterface)) {
            if (getWindowResId() <= 0) {
                mWindowContentView = getContentComonentView();
                if (EmptyAndSizeUtils.isEmpty(mWindowContentView)) {
                    mWindowContentView = new WebView(mContext);
                }
            } else {
                mWindowContentView = mInflater.inflate(getWindowResId(), null, false);
            }
        }

        if (EmptyAndSizeUtils.isNotEmpty(buttonConfig)) {
            buttonConfig.setLeftButtonConfig(this.left);
            buttonConfig.setRightButtonConfig(this.right);
            if (EmptyAndSizeUtils.isEmpty(left.getButtonText())) {
                this.left = null;
            }
            if (EmptyAndSizeUtils.isEmpty(this.right.getButtonText())) {
                this.right = null;
            }
            /**
             * 按钮基础配置
             */

            this.btnHight = baseConfig.getBtnHight();
            this.lineColor = baseConfig.getLineColor();
            this.lineWidthSize = baseConfig.getLineWidthSize();
            mContentView.setButtonMidLineColor(mLineColor);
            mContentView.setLineWidth(lineWidth);
            if (EmptyAndSizeUtils.isNotEmpty(left)) {
                mContentView.setLeftButtonColor(left.getBackgroundColor());
            }
            if (EmptyAndSizeUtils.isNotEmpty(right)) {
                mContentView.setRightButtonColor(right.getBackgroundColor());
            }
            View btnView = generatorWithDoubleButtonLayout(this.left, this.right, mContentView.getInterval());
            initCarryBtnLayout(mWindowContentView, width, height, mContentView.getInterval());
            if (EmptyAndSizeUtils.isNotEmpty(this.defaultSytleInterface)) {
                mWindowContentView = generatorDefaultWindowLayout(width, carryBtnMainHeight, mContentView.getInterval(), this.defaultSytleInterface, false);
            }
            BtnContentView.addView(mWindowContentView);
            father.addView(reCalcViewWindowParams(btnView, mContentView.getInterval()));
        } else {
            if (EmptyAndSizeUtils.isNotEmpty(this.defaultSytleInterface)) {
                newHeight = (int) (height * 1f / baseNumber);
                mWindowContentView = generatorDefaultWindowLayout(width, height, mContentView.getInterval(), this.defaultSytleInterface, true);
            }
            father.addView(reCalcViewWindowParams(mWindowContentView, mContentView.getInterval()));
        }

        mFrameLayout.removeAllViews();
        mFrameLayout.addView(father);
        return mFrameLayout;
    }


    private int getWindowWidth() {
        return (int) ((ScreenUtils.getScreenWidth(mContext) * windowPercentX));
    }

    private int getWindowHeight() {
        return (int) ((ScreenUtils.getScreenHeight(mContext) * windowPercentY));
    }

    /**
     * 重置窗口参数
     *
     * @param view
     * @param dis
     * @return
     */
    private View reCalcViewWindowParams(View view, int dis) {
        RelativeLayout.LayoutParams mLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        int width = getWindowWidth()/*(int) ((ScreenUtils.getScreenWidth(mContext) * windowPercent))*/;
        int height = getWindowHeight();
        if (windowPercentY == -1) {
            height = (int) (width * basePercent);
        }

        mLayoutParams.width = width;
        mLayoutParams.height = (int) (height - dis * 4f);
        mLayoutParams.leftMargin = 0;
        mLayoutParams.topMargin = (int) (dis * 2f);
        view.setBackgroundColor(Color.parseColor(mColorInflater.checkColorValue(mWindowBgColor)));
        view.setLayoutParams(mLayoutParams);
        return view;
    }

    private View generatorDefaultWindowLayout(int width, int height, int interval, DialogWindowDefaultSytleInterface defaultSytleInterface, boolean isSetBooter) {
        DefaultStyleConfig defaultStyleConfig = new DefaultStyleConfig();
        defaultSytleInterface.bindParamsToWindow(defaultStyleConfig);
        int titleBarHeight = -1;
        int padding = (int) (newHeight * 0.2);
        //有标题的主窗口布局
        LinearLayout mLayout = new LinearLayout(mContext);
        mLayout.setOrientation(LinearLayout.VERTICAL);
        if (defaultStyleConfig.getTitleBarHeight() != -1) {
            titleBarHeight = defaultStyleConfig.getTitleBarHeight();
        } else {
            titleBarHeight = (int) (newHeight * 1.2);
        }


        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
        mLayout.setLayoutParams(layoutParams);

        //标题栏
        TextView mTitle = new TextView(mContext);
        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(width, titleBarHeight);
        mTitle.setGravity(Gravity.CENTER);
        //mTitle.setPadding(padding, interval / 2, padding, 0);
        mTitle.setLines(1);
        mTitle.setMarqueeRepeatLimit(-1);
        mTitle.setEllipsize(TextUtils.TruncateAt.MARQUEE);//设置旋转
        mTitle.setFocusable(true);
        mTitle.setMarqueeRepeatLimit(Integer.MAX_VALUE);
        mTitle.setSelected(true);
        mTitle.setSingleLine();


        mTitle.setLayoutParams(titleParams);

        //标题下线条颜色
        ImageView headerLine = new ImageView(mContext);
        LinearLayout.LayoutParams headerLineParams = new LinearLayout.LayoutParams(width, lineWidth);
        headerLine.setLayoutParams(headerLineParams);
        mColorInflater.setViewBgColor(headerLine, mLineColor);

        //内容部分
        TextView mContent = new TextView(mContext);
        int contentHeight = height - lineWidth - titleBarHeight;
        if (!defaultStyleConfig.isShowTitleBarUnderLine()) {
            contentHeight = contentHeight + lineWidth;
        }
        if (EmptyAndSizeUtils.isEmpty(defaultStyleConfig.getTitle())) {
            contentHeight = contentHeight + titleBarHeight;
        }
        defaultStyleConfig.isShowTitleBarUnderLine();
        LinearLayout.LayoutParams mContetParams = new LinearLayout.LayoutParams(width, contentHeight);
        mContent.setLayoutParams(mContetParams);
        //mContent.setGravity(Gravity.CENTER_VERTICAL);
        mContent.setPadding(padding * 2, padding * 3, padding * 2, padding);

        if (EmptyAndSizeUtils.isNotEmpty(defaultStyleConfig.getTitle())) {
            if (defaultStyleConfig.isTitleBold()) {
                mTitle.setText(UtilsManager.getInstance().getHtmlUtilsController().formatAppointColorAndBoldOfText(defaultStyleConfig.getTitle(), defaultStyleConfig.getTitleColor()));
            } else {
                mTitle.setText(defaultStyleConfig.getTitle());
            }
            //mTitle.setTypeface(Typeface.DEFAULT_BOLD);
            if (EmptyAndSizeUtils.isNotEmpty(defaultStyleConfig.getTitleColor())) {
                mColorInflater.setViewBgColor(mTitle, defaultStyleConfig.getTitleBarColor());
                mContentView.setHeaderBgColor(defaultStyleConfig.getTitleBarColor());
            }
            if (EmptyAndSizeUtils.isNotEmpty(defaultStyleConfig.getTitleColor())) {
                mTitle.setTextColor(mColorInflater.inflaterColorFromStr(defaultStyleConfig.getTitleColor()));
            }
            mTitle.setTextSize(defaultStyleConfig.getTitleSize());
        }

        if (EmptyAndSizeUtils.isNotEmpty(defaultStyleConfig.isContentNotEmpty())) {
            if (EmptyAndSizeUtils.isNotEmpty(defaultStyleConfig.getContent())) {
                mContent.setText(defaultStyleConfig.getContent());
            } else {
                mContent.setText(defaultStyleConfig.getSpannedContent());
            }
            mContent.setTextSize(defaultStyleConfig.getContentSize());
            if (EmptyAndSizeUtils.isNotEmpty(defaultStyleConfig.getContentColor())) {
                mContent.setTextColor(mColorInflater.inflaterColorFromStr(defaultStyleConfig.getContentColor()));
            }
            if (EmptyAndSizeUtils.isNotEmpty(defaultStyleConfig.getContentBgColor())) {
                mColorInflater.setViewBgColor(mContent, defaultStyleConfig.getContentBgColor());
                if (isSetBooter) {
                    mContentView.setLeftButtonColor(defaultStyleConfig.getContentBgColor());
                }
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mContent.setLineSpacing(3f, 1.1f);
            }
        }


        mLayout.removeAllViews();
        if (EmptyAndSizeUtils.isNotEmpty(defaultStyleConfig.getTitle()) && EmptyAndSizeUtils.isNotEmpty(defaultStyleConfig.getContentBgColor())) {
            mLayout.addView(mTitle);
            if (defaultStyleConfig.isShowTitleBarUnderLine()) {
                mLayout.addView(headerLine);
            }
        } else {
            if (EmptyAndSizeUtils.isNotEmpty(defaultStyleConfig.getContentBgColor())) {
                mContentView.setHeaderBgColor(defaultStyleConfig.getContentBgColor());
            }
        }
        mLayout.addView(mContent);
        return mLayout;
    }

    /**
     * 根据默认样式计算按钮的尺寸,获取基数因子
     *
     * @param
     * @return
     */
    private float calcBtnHeight(WindowDefaulSizetStyle mStyle, boolean isRevers) {
        float baseFactor = 1.2f;
        //WindowDefaulSizetStyle mStyle = baseConfig.getWindowDefaultStyle();
        if (EmptyAndSizeUtils.isNotEmpty(mStyle)) {
            if (isRevers) {
                baseFactor = mStyle.getValue();
                return baseFactor;
            }
            //计算默认因子
            switch (mStyle) {
                case STYLE_MINI:
                    baseFactor = 1.2f + mStyle.getValue();//1.65
                    break;
                case STYLE_NORMAL:
                    baseFactor = 0.8f + mStyle.getValue();//1.4
                    break;
                case STYLE_BIG:
                    baseFactor = mStyle.getValue() - 0.2f;//
                    break;
                case STYLE_BIGGER:
                    baseFactor = mStyle.getValue() - 0.6f;
                    break;
            }
        }
        return baseFactor;
    }

    /**
     * 构造双按钮布局
     *
     * @return
     */
    private View generatorWithDoubleButtonLayout(WindowBtnConfig left, WindowBtnConfig right, int dis) {
        int width = (int) ((ScreenUtils.getScreenWidth(mContext) * windowPercentX));
        int height = getWindowHeight();
        if (windowPercentY == -1) {
            height = (int) (width * basePercent);
        }
        /**
         * 默认的按钮高度
         */
        if (EmptyAndSizeUtils.isNotEmpty(btnHight)) {
            if (btnHight != -1) {
                if (btnHight >= 500) {
                    newHeight = 200;
                } else if (btnHight < 30) {
                    newHeight = 30;
                } else {
                    newHeight = btnHight;
                }
            } else {
                newHeight = (int) (height * 1f / baseNumber);
            }

        } else {
            newHeight = (int) (height * 1f / baseNumber);
        }


        if (EmptyAndSizeUtils.isNotEmpty(lineColor)) {
            mLineColor = lineColor;
        }

        if (EmptyAndSizeUtils.isNotEmpty(lineWidthSize)) {
            if (lineWidthSize != -1) {
                if (lineWidthSize >= 20) {
                    lineWidth = 20;
                } else if (lineWidthSize <= 0) {
                    lineWidth = 0;
                } else {
                    lineWidth = lineWidthSize;
                }
            }

        }
        // int newHeights = (int) (newHeight * 1.2);
        relHeight = (int) (newHeight * calcBtnHeight(baseConfig.getWindowDefaultStyle(),false));


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
        RelativeLayout.LayoutParams mButtonParentParams = new RelativeLayout.LayoutParams(width, relHeight);
        mmButtonParent.setLayoutParams(mButtonParentParams);

        /**
         * 按钮组
         */
        int btnWinth = EmptyAndSizeUtils.isNotEmpty(left) && EmptyAndSizeUtils.isNotEmpty(right) ? (int) ((width * 1f) / 2) : width;


        TextView mLeftButton = new TextView(mContext);
        mLeftButton.setClickable(true);
        mLeftButton.setGravity(Gravity.CENTER);
        RelativeLayout.LayoutParams mLeftButtonParams = new RelativeLayout.LayoutParams(btnWinth, relHeight - lineWidth);
        mLeftButton.setLayoutParams(mLeftButtonParams);
        if (EmptyAndSizeUtils.isNotEmpty(left)) {
            mColorInflater.setViewBgColor(mLeftButton, EmptyAndSizeUtils.isEmpty(left.getBackgroundColor()) ? "#DCDCDC" : left.getBackgroundColor());
            mLeftButton.setTextColor(Color.parseColor(mColorInflater.checkColorValue(EmptyAndSizeUtils.isEmpty(left.getTextColor()) ? "##000000" : left.getTextColor())));
            mLeftButton.setText(EmptyAndSizeUtils.isEmpty(left.getButtonText()) ? "确认" : left.getButtonText());
            if (EmptyAndSizeUtils.isNotEmpty(left.getOnclickListener())) {
                mLeftButton.setOnClickListener(left.getOnclickListener());
            }
            mLeftButton.setTextSize(left.getTextSize());
        }

        View maindLine = new View(mContext);
        maindLine.setBackgroundColor(Color.parseColor(mColorInflater.checkColorValue(mLineColor)));
        mColorInflater.setViewBgColor(maindLine, mLineColor);
        RelativeLayout.LayoutParams maindLineParams = new RelativeLayout.LayoutParams(width, lineWidth);
        maindLine.setLayoutParams(maindLineParams);


        View mdLine = new View(mContext);
        RelativeLayout.LayoutParams mdLineParams = new RelativeLayout.LayoutParams(lineWidth, relHeight - lineWidth);
        mdLine.setLayoutParams(mdLineParams);
        mColorInflater.setViewBgColor(mdLine, mLineColor);

        TextView mRightButton = new TextView(mContext);
        mRightButton.setGravity(Gravity.CENTER);
        mRightButton.setClickable(true);
        RelativeLayout.LayoutParams mRightButtonParams = new RelativeLayout.LayoutParams(btnWinth, relHeight - lineWidth);
        mRightButton.setLayoutParams(mRightButtonParams);

        if (EmptyAndSizeUtils.isNotEmpty(right)) {
            mColorInflater.setViewBgColor(mRightButton, EmptyAndSizeUtils.isEmpty(right.getBackgroundColor()) ? "#DCDCDC" : right.getBackgroundColor());
            mRightButton.setTextColor(Color.parseColor(mColorInflater.checkColorValue(EmptyAndSizeUtils.isEmpty(right.getTextColor()) ? "##000000" : right.getTextColor())));
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

        BtnContentView = new LinearLayout(mContext);
        initCarryBtnLayout(BtnContentView, width, height, dis);
        mmParent.addView(BtnContentView);
        mmParent.addView(maindLine);
        mmParent.addView(mmButtonParent);
        return mmParent;
    }

    /**
     * 重置按钮参数
     *
     * @param view
     * @param width
     * @param height
     * @param dis
     */
    private void initCarryBtnLayout(View view, int width, int height, int dis) {
        carryBtnMainHeight = (int) (height - /*(newHeight * 1.2)*/relHeight - dis * 3);
        if (EmptyAndSizeUtils.isNotEmpty(view)) {
            RelativeLayout.LayoutParams tesmLayoutParams = new RelativeLayout.LayoutParams(width, carryBtnMainHeight);
            view.setLayoutParams(tesmLayoutParams);
        }
    }




    /**
     * 窗口基础参数设置
     *
     * @param
     * @param windowSizePercentOfParentX
     * @param windowBgColor
     */
    public void setWindowBaseParams(float windowSizePercentOfParentX, float windowSizePercentOfParentY, String windowBgColor) {
        if (windowSizePercentOfParentX >= 1) {
            windowPercentX = 1f;
        } else if (windowSizePercentOfParentX <= 0.4) {
            windowPercentX = 0.4f;
        } else {
            windowPercentX = windowSizePercentOfParentX;
        }
        if (windowSizePercentOfParentY >= 1) {
            windowPercentY = 1f;
        } else if (windowSizePercentOfParentY <= 0.4) {
            windowPercentY = 0.4f;
        } else {
            windowPercentY = windowSizePercentOfParentY;
        }

        if (EmptyAndSizeUtils.isNotEmpty(windowBgColor)) {
            mWindowBgColor = windowBgColor;
        }
    }

    /**
     * 通过默认的样式控制窗口的大小
     *
     * @param mDefaultStyle
     */
    public void setWindowDefaultStyle(WindowDefaulSizetStyle mDefaultStyle) {
        this.mDefaultStyle = mDefaultStyle;
    }

    /**
     * 窗口基础参数设置
     */
    public void setWindowBaseParams(float windowSizePercentOfParentX, float windowSizePercentOfParentY) {
        if (windowSizePercentOfParentX >= 1) {
            windowPercentX = 1f;
        } else if (windowSizePercentOfParentX <= 0.4) {
            windowPercentX = 0.4f;
        } else {
            windowPercentX = windowSizePercentOfParentX;
        }
        if (windowSizePercentOfParentY >= 1) {
            windowPercentY = 1f;
        } else if (windowSizePercentOfParentY <= 0.3) {
            windowPercentY = 0.3f;
        } else {
            windowPercentY = windowSizePercentOfParentY;
        }
    }

    public void setWindowBgColor(String windowBgColor) {
        if (EmptyAndSizeUtils.isNotEmpty(windowBgColor)) {
            mWindowBgColor = windowBgColor;
        }
    }

    /**
     * 设置 窗体占父窗口的比例，调整窗体大小
     *
     * @param windowPercentX
     */
    public void setWindowPercentOfParent(float windowPercentX) {
        this.windowPercentX = windowPercentX;
    }

    /**
     * 设置底部按钮参数
     */
    public void setWindowBottomButton(String leftButtonBgColor, String rightButtonBgColor, String meddleLineColor) {
        if (EmptyAndSizeUtils.isNotEmpty(mContentView)) {
            mContentView.setLeftButtonColor(leftButtonBgColor);
            mContentView.setRightButtonColor(rightButtonBgColor);
            mContentView.setButtonMidLineColor(meddleLineColor);
        }
    }

    /**
     * 设置底部按钮参数
     */
    public void setWindowFooterBackgroundBorderColor(String leftButtonBgColor) {
        if (EmptyAndSizeUtils.isNotEmpty(mContentView)) {
            mContentView.setLeftButtonColor(leftButtonBgColor);
        }
    }

    /**
     * 设置头部边框颜色，当定义头部一体的组件时使用
     */
    public void setWindowHeaderBackgroundBorderColor(String headerBackgroundColor) {
        if (EmptyAndSizeUtils.isNotEmpty(headerBackgroundColor)) {
            mContentView.setHeaderBgColor(headerBackgroundColor);
        }
    }

    /**
     * 当提供的wiew或者没有提供wiew（产生WebView） 为Webview
     */
    public void registJavaScript(Activity activity, WidgetInterface mWidgetInterface) {
        if (EmptyAndSizeUtils.isNotEmpty(mWidgetProxy)) {
            mWidgetProxy.register(activity, mWidgetInterface);
        }
    }


    /**
     * 默认的有标题栏与内容的样式
     */
    public void setWindowWithDefaultTitleAndContent_Style(DialogWindowDefaultSytleInterface defaultSytleInterface) {
        this.defaultSytleInterface = defaultSytleInterface;
    }


    /**
     * 初始化底部双按钮
     * 设置按钮的基础配置
     *
     * @param config 按钮配置对象
     */
    public void setWindowBottomButtonSConfig(WidgetBtnConfigProxyInterface config) {
        this.buttonConfig = config;
    }

    /**
     * 提供的布局资源，需要渲染
     *
     * @return
     */
    abstract protected int getWindowResId();




    /**
     * 组件布局
     *
     * @return
     */
    public View getContentComonentView() {

        return null;
    }



    /**
     * 数据绑定初始化，当需要给组件设置数据是使用
     *
     * @return
     */
    public void bindWindowDatas() {

    }


}
