package com.shuiyinhuo.component.mixdev.proxy;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.shuiyinhuo.component.mixdev.caller.BackListenerInterface;
import com.shuiyinhuo.component.mixdev.caller.WidgetInterface;
import com.shuiyinhuo.component.mixdev.jinbean.em.WindowTransParencyLevel;
import com.shuiyinhuo.component.mixdev.locationmanager.ComponentUtilsManager;
import com.shuiyinhuo.component.mixdev.locationmanager.UtilsManager;
import com.shuiyinhuo.component.mixdev.utils.comm.ColorUtils;
import com.shuiyinhuo.component.mixdev.utils.comm.EmptyAndSizeUtils;
import com.shuiyinhuo.component.mixdev.utils.comm.JNILog;
import com.shuiyinhuo.component.mixdev.utils.comm.ViewFinder;
import com.shuiyinhuo.component.mixdev.utils.widget.Loading;

import java.lang.reflect.Method;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/9/4 0004
 * @ Description：透明Activity，作为一些特殊组件使用
 * =====================================
 */
public abstract class ProxyTranparentActity extends FragmentActivity {
    protected Context mContext;
    /**
     * view寻址器
     */
    protected ViewFinder mFinder;
    /**
     * 渲染器
     */
    protected LayoutInflater mInflater;
    /**
     * 颜色渲染器
     */
    private ColorUtils mColorInflater;
    /**
     * 返回回调
     */
    private BackListenerInterface backPressListener;
    /**
     * 组件代理
     */
    protected ProxyWidgetForWebView mWidgetProxy;
    /**
     * 加载Loading
     */
    private Loading mLoading;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mInflater = LayoutInflater.from(mContext);
        mColorInflater = ComponentUtilsManager.getInstance().getColorUtils();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去掉Activity上面的状态栏
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //setStateColor();
        //setTheme(android.R.style.Theme_DeviceDefault_DialogWhenLarge);
        UtilsManager.getInstance().baseRegister(mContext);
        mFinder = UtilsManager.getInstance().getFinder();
        mWidgetProxy = ProxyWidgetForWebView.getInstance();
        translucentActivity(ProxyTranparentActity.this);
        prepareLayout();
        if (EmptyAndSizeUtils.isNotEmpty(generatorMainContentView())) {
            setContentView(generatorMainContentView());
        } else {
            if (getResId() != -1) {
                setContentView(getResId());
            } else {
                throw new NullPointerException("Please supply ResId !");
            }
        }
        initView();
        initEvents();
    }





    /**
     * 设置Activity背景颜色
     *
     * @param activity
     */
    private void translucentActivity(Activity activity) {
        try {
            int color = setWindowTranparentLevel(mColorInflater);
            activity.getWindow().setBackgroundDrawable(new ColorDrawable(color));
            //  activity.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#80000000")));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                activity.getWindow().getDecorView().setBackgroundColor(color);
            } else {
                activity.getWindow().getDecorView().setBackgroundColor(color);
            }
            Method activityOptions = Activity.class.getDeclaredMethod("getActivityOptions");
            activityOptions.setAccessible(true);
            Object options = activityOptions.invoke(activity);

            Class<?>[] classes = Activity.class.getDeclaredClasses();
            Class<?> aClass = null;
            for (Class clazz : classes) {
                if (clazz.getSimpleName().contains("TranslucentConversionListener")) {
                    aClass = clazz;
                }
            }
            Method method = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                method = Activity.class.getDeclaredMethod("convertToTranslucent",
                        aClass, ActivityOptions.class);
            }
            method.setAccessible(true);
            method.invoke(activity, null, options);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    /**
     *  设置窗口的透明度
     *
     */
    protected int setWindowTranparentLevel(ColorUtils mColorInflater){
        return Color.TRANSPARENT;
    }

    private int getStatusBarHeight() {
        int result = 0;
        //获取状态栏高度的资源id
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (EmptyAndSizeUtils.isNotEmpty(backPressListener)) {
                this.backPressListener.onKeyDownBack(this);
                if (EmptyAndSizeUtils.isNotEmpty(this.backPressListener)) {
                    if (this.backPressListener.isCanDismissWindow()) {
                        super.onKeyDown(keyCode, event);
                    } else {
                        return false;
                    }

                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    /**
     * 当提供的wiew或者没有提供wiew（产生WebView） 为Webview
     */
    public void registJavaScript(WidgetInterface mWidgetInterface) {
        if (EmptyAndSizeUtils.isNotEmpty(mWidgetProxy)) {
            mWidgetProxy.register(this, mWidgetInterface);
        }
    }

    /**
     * 初始化状态栏为透明背景
     */
    public void setStateTransparentColor() {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else {
            ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
            View statusBarView = new View(this);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    getStatusBarHeight());
            statusBarView.setBackgroundColor(Color.RED);
            decorView.addView(statusBarView, lp);

        }
    }


    /**
     * 设置返回监听
     */
    public void setBackPressListener(BackListenerInterface backPressListener) {
        this.backPressListener = backPressListener;
    }

    /**
     * 吐司提示
     *
     * @param msg
     */
    public void showToast(String msg) {
        mWidgetProxy.getToastManager().showToast(msg);
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

    @Override
    protected void onResume() {
        super.onResume();
        JNILog.e("*********************************>Activity ："+this.getClass().getName());
    }

    protected void prepareLayout() {

    }

    public View generatorMainContentView() {
        return null;
    }

    ;

    /**
     * 初始化view，当提供了静态布局时使用
     *
     * @param
     */
    public abstract void initView();

    /**
     * 初始化事件， 当提供了静态布局时使用
     */
    public abstract void initEvents();

    abstract public int getResId();
}
