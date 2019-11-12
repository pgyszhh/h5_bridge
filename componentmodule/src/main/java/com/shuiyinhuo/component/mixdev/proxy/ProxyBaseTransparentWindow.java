package com.shuiyinhuo.component.mixdev.proxy;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.shuiyinhuo.component.mixdev.abs.ProxyOnclickListener;
import com.shuiyinhuo.component.mixdev.entity.BaseDialogConfig;
import com.shuiyinhuo.component.mixdev.jinbean.em.ApproachAndDepartureType;
import com.shuiyinhuo.component.mixdev.jinbean.em.ScaleAnimationPositionType;
import com.shuiyinhuo.component.mixdev.jinbean.em.WindowTransParencyLevel;
import com.shuiyinhuo.component.mixdev.locationmanager.ComponentUtilsManager;
import com.shuiyinhuo.component.mixdev.locationmanager.UtilsManager;
import com.shuiyinhuo.component.mixdev.utils.comm.ColorUtils;
import com.shuiyinhuo.component.mixdev.utils.comm.EmptyAndSizeUtils;
import com.shuiyinhuo.component.mixdev.utils.comm.ScreenUtils;
import com.shuiyinhuo.component.mixdev.utils.comm.ViewFinder;
import com.shuiyinhuo.component.mixdev.utils.pkg.domain.AnimationUtils;
import com.shuiyinhuo.component.mixdev.utils.widget.FrameLayoutM;
import com.shuiyinhuo.component.mixdev.utils.widget.Loading;

import java.lang.reflect.Field;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/9/7 0007
 * @ Description：
 * =====================================
 */
public abstract class ProxyBaseTransparentWindow extends Dialog {
    protected Context mContext;
    /***------------------基础工具类-------------------------**/
    //组件代理
    protected ProxyWidgetForWebView mWidgetProxy;
    //颜色辅助类
    protected ColorUtils mColorInflater;
    //渲染器
    protected LayoutInflater mInflater;
    //view寻找器
    protected ViewFinder mFinder;
    //窗口主题父布局
    protected View mWindowContentView = null;
    //被渲染的父布局
    private View MVIEW = null;
    //被播放动画的view
    private  ViewGroup mAnimationView;
    //loading
    private Loading mLoading;
    //窗口基础设置，当需要非透明部分window时
    private BaseDialogConfig baseConfig;
    //窗口的背景透明度
    private int mWindowBackgroundColor = Color.TRANSPARENT;
    //是否自带动画
    private boolean isWithAnimation = true;
    /**
     * 动画监听
     */
    private OnOutTouchEvents mOnOutTouchEvents;


    public ProxyBaseTransparentWindow(Context context) {
        this(context, -1);

    }

    public ProxyBaseTransparentWindow(Context context, int themeResId) {
        this(context, false, null);
    }

    protected ProxyBaseTransparentWindow(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        //super(context,/*android.R.style.Theme_NoTitleBar_Fullscreen*/);
        super(context,/*android.R.style.Theme_NoTitleBar_Fullscreen*/-1);
       /* }else {
            super(context, cancelable, cancelListener);
        }*/
        mContext = context;
        UtilsManager.getInstance().baseRegister(mContext);
        mInflater = LayoutInflater.from(mContext);
        mWidgetProxy = ProxyWidgetForWebView.getInstance();
        mFinder = UtilsManager.getInstance().getFinder();
        mColorInflater = ComponentUtilsManager.getInstance().getColorUtils();
        mWindowBackgroundColor = mColorInflater.parase_ColorByGeneratorColor(WindowTransParencyLevel.TRANS_PARENCY_LEVEL_DEFAULT, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        onPreparedBeforeLayout();
        setCanceledOnTouchOutside(setCanceledOnTouchOutside());
        //setCanceledOnTouchOutside(true);
        //getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //清除Dialog底背景模糊和黑暗度

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND | WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        getWindow().setBackgroundDrawable(new ColorDrawable(mColorInflater.parase_ColorByGeneratorColor(WindowTransParencyLevel.TRANS_PARENCY_LEVEL_100,false)));
        //getWindow().setBackgroundDrawable(new ColorDrawable(mColorInflater.inflaterColorFromStr(generatorColor())));
        setStateColor(window, mContext, mWindowBackgroundColor);
        //添加测试布局


        MVIEW = getContentView();
        ViewGroup mContent = testLayout();
        if (EmptyAndSizeUtils.isNotEmpty(MVIEW)) {
            //setContentView(MVIEW);
            initViewParams(MVIEW);
            mContent.addView(MVIEW);
        } else {
            if (getResId() != 0 && getResId() != -1) {
                MVIEW = mInflater.inflate(getResId(), mContent, false);
                //setContentView(MVIEW);
                initViewParams(MVIEW);
                mAnimationView.addView(MVIEW);
            } else {
                throw new NullPointerException("Please supply Can Inflate ResId !");
            }
        }

        setContentView(mContent);
        if (EmptyAndSizeUtils.isEmpty(mAnimationView)){
            mAnimationView= (ViewGroup) MVIEW;
        }
       // mContent.setAnimation(AnimationUtils.getScaleAnimationByPosition(ScaleAnimationPositionType.TYPE_BOTTOM,ApproachAndDepartureType.TYPE_APPROACH,300));
        if (isWithAnimation) {
           AnimationUtils.playObjectAnimatorFadeInCenter(mAnimationView,null);
        }
        if (EmptyAndSizeUtils.isNotEmpty(MVIEW)) {
            initWindowView(MVIEW);
            initWindowEvents();
        }
    }

    private void initViewParams(View view) {
        int width = -1;
        int height = -1;
        if (EmptyAndSizeUtils.isNotEmpty(baseConfig)) {
            width = baseConfig.getWindowWidth();
            height = baseConfig.getWindowHeight();
            RelativeLayout.LayoutParams mLayoutParams = new RelativeLayout.LayoutParams(width, height);
            view.setLayoutParams(mLayoutParams);
            //view.setOnClickListener(null);
        }

    }

    /**
     * 获取可播放动画的view
     * @return
     */
    public View getPlayAnimationView(){
        return mAnimationView;
    }

    /**
     * 设置窗口是否带有动画
     * @param isWithAnimation
     */
    public void setWindowWithAnimation(boolean isWithAnimation){
        this.isWithAnimation=isWithAnimation;
    }

    /**
     * 设置状态栏颜色 * * @param activity 需要设置的activity * @param color 状态栏颜色值
     */
    public static void setStateColor(Window window, Context context, int color) {
        if (window != null) {
            window.setGravity(Gravity.BOTTOM);  //设置dialog显示的位置
            window.setWindowAnimations(android.R.style.Animation_Dialog); //添加动画
            //解决 状态栏变色的bug
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(Color.TRANSPARENT);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    try {
                        Class decorViewClazz = Class.forName("com.android.internal.policy.DecorView");
                        Field field = decorViewClazz.getDeclaredField("mSemiTransparentStatusBarColor");
                        field.setAccessible(true);
                        field.setInt(window.getDecorView(), Color.TRANSPARENT);  //去掉高版本蒙层改为透明
                    } catch (Exception e) {
                    }
                }
            }
        }
    }


    /**
     * 设置窗口的透明度
     */
    public void setWindowTranparentLevel(WindowTransParencyLevel level) {
        // return Color.TRANSPARENT;
        mWindowBackgroundColor = mColorInflater.parase_ColorByGeneratorColor(level, false);
        //return mColorInflater.inflaterColorFromStr("#3CB371");
    }

    /**
     * 控制背景透明度母布局
     *
     * @return
     */
    private ViewGroup testLayout() {

        //透明度控制
        RelativeLayout window = new RelativeLayout(mContext);
        FrameLayout.LayoutParams windowParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        window.removeAllViews();
        int location = Gravity.CENTER;
        if (EmptyAndSizeUtils.isNotEmpty(baseConfig)) {
            /*baseConfig =new BaseDialogConfig(mContext);
            setWindowBaseConfig(baseConfig);*/
            BaseDialogConfig.WindowPosition mLocation = baseConfig.getLocation();
            switch (mLocation) {
                case TOP:
                    location = Gravity.CENTER_HORIZONTAL;
                    break;
                case CENTER:
                    location = Gravity.CENTER;
                    break;
                case BOTTOM:
                    location = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
                    break;
            }

            window.setGravity(Gravity.CENTER);
            int mContentWidth = baseConfig.getWindowWidth();
            int mContentHeight = baseConfig.getWindowHeight();
            //二级显示布局
            FrameLayoutM mContent = new FrameLayoutM(mContext);
            mContent.setGravity(RelativeLayout.CENTER_IN_PARENT);
            FrameLayout.LayoutParams mContentParams = new FrameLayout.LayoutParams(mContentWidth, mContentHeight);
           /* mContentParams.width = mContentWidth;
            mContentParams.height = mContentHeight;*/
            mContent.setFillet(true);
            mContent.setRadius(10);
            //可是框背景控制
            if (EmptyAndSizeUtils.isNotEmpty(baseConfig.getBackgroundColor())) {
                mContent.setBackColor(mColorInflater.inflaterColorFromStr(baseConfig.getBackgroundColor()));
            }
            mContent.setLayoutParams(mContentParams);

            int xPadding = (int) ((ScreenUtils.getScreenWidth(mContext) - mContentWidth) * 1f / 2);
            int yPadding = (int) ((ScreenUtils.getScreenHeight(mContext) - mContentHeight) * 1f / 2);
            //window.setPadding(xPadding,yPadding,xPadding,yPadding);

            mContent.setOnClickListener(null);
            window.addView(mContent);
            mAnimationView=mContent;
        }
        window.setLayoutParams(windowParams);
        window.setGravity(location);
        window.setBackgroundColor(mWindowBackgroundColor);
        //window.setBackgroundColor(mColorInflater.inflaterColorFromStr("#D8BFD8"));
         mOnOutTouchEvents = new OnOutTouchEvents();
        window.setOnClickListener(mOnOutTouchEvents);
        return window;
    }



    /**
     * 起始准备
     */
    abstract protected void onPreparedBeforeLayout();

    private class OnOutTouchEvents extends ProxyOnclickListener {
        @Override
        public void onClick(View view, int id) {
            if (setCanceledOnTouchOutside()) {
                //view.setAnimation(AnimationUtils.getHiddenAlphaAnimation(2000));
                if (isWithAnimation) {
                    playAnimation();
                }else {
                    killSelf();
                }
            } else {
                //killSelf();
                if (EmptyAndSizeUtils.isEmpty(view)){
                    if (isWithAnimation) {
                        playAnimation();
                    }else {
                        killSelf();
                    }
                }
            }
        }
    }

    /**
     * 自杀方法
     */
    private void killSelf(){
        super.dismiss();
    }

    private void playAnimation(){
        AnimationUtils.playObjectAnimatorDown(mAnimationView, new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                killSelf();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                killSelf();
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                killSelf();
            }
        });
    }

    /**
     * 不能调用改方法销毁自己
     */
    @Override
    public void dismiss() {
        View mDecorView = getWindow().getDecorView();
        if (EmptyAndSizeUtils.isNotEmpty(mOnOutTouchEvents)) {
            mOnOutTouchEvents.onClick(mDecorView);
        }
    }


    /**
     * 销毁自己
     */
    public void dismissSelf() {
        if (EmptyAndSizeUtils.isNotEmpty(mOnOutTouchEvents)) {
            mOnOutTouchEvents.onClick(null);
        }
    }

    public void showLoading() {
        dismissLoading();
        mLoading = new Loading(mContext);
        mLoading.show();
    }

    public void dismissLoading() {
        if (EmptyAndSizeUtils.isNotEmpty(mLoading) && mLoading.isShowing()) {
            mLoading.dismiss();
            mLoading = null;
        }
    }

    /**
     * 吐司提示
     *
     * @param msg
     */
    public void showToast(String msg) {
        mWidgetProxy.getToastManager().showToast(msg);
    }

    /**
     * 窗口基础设置
     */
    public void setWindowBaseConfig(BaseDialogConfig baseConfig) {
        this.baseConfig = baseConfig;
    }

    /**
     * 事件初始化，当需要给按钮设置监听时使用
     */
    public void initWindowEvents() {

    }

    /**
     * 设置窗口点击外部是否可以被取消
     *
     * @return
     */
    abstract public boolean setCanceledOnTouchOutside();

    /**
     * 初始化view，当提供了静态布局时使用
     *
     * @param view
     */
    abstract protected void initWindowView(View view);

    /**
     * 通过提供的view去填充主布局
     *
     * @return
     */
    protected View getContentView() {

        return null;
    }

    abstract public int getResId();
}
