package com.shuiyinhuo.component.mixdev.entity;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shuiyinhuo.component.mixdev.utils.comm.EmptyAndSizeUtils;
import com.shuiyinhuo.component.mixdev.utils.comm.ScreenUtils;
import com.shuiyinhuo.component.mixdev.utils.widget.BackArrowView;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/8/8 0008
 * @ Description：导航栏属性代理
 * =====================================
 */
public class NavBarProxy {
    private BackArrowView mArrowView;
    private TextView mTitleView;
    private LinearLayout NavBar;

    private String mTitle ="标题";
    private String mNavBgColor= "#528B8B";
    private int mNavBgColorId= -1;
    private String mTextColor="#FFFFFF";
    public boolean isHiddenBackText = true;
    private Context mContext;
    private View.OnClickListener mBackListener;
    //导航主题颜色
    private int NavThemColor = -1;

    public NavBarProxy(Context mContext,View.OnClickListener backListener) {
        this.mContext = mContext;
        this.mBackListener = backListener;
        initNavBar();
        init();
    }

    private void initNavBar(){
        NavBar = new LinearLayout(mContext);
        FrameLayout.LayoutParams mLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtils.dip2px(mContext,40));
        NavBar.setLayoutParams(mLayoutParams);

        NavBar.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout.LayoutParams space =new LinearLayout.LayoutParams(ScreenUtils.dip2px(mContext,isHiddenBackText()?60:80), ViewGroup.LayoutParams.WRAP_CONTENT);
        mArrowView = new BackArrowView(mContext);

        mArrowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBackListener.onClick(v);
            }
        });
        mArrowView.setLayoutParams(space);
        LinearLayout.LayoutParams webParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        webParams.weight=1;
        mTitleView = new TextView(mContext);
        mTitleView.setTextSize(18);
        mTitleView.setGravity(Gravity.CENTER);
        mTitleView.setLayoutParams(webParams);


        View right = new View(mContext);
        right.setLayoutParams(space);
        NavBar.removeAllViews();
        NavBar.addView(mArrowView);
        NavBar.addView(mTitleView);
        NavBar.addView(right);
    }

    public void init(){
        mTitleView.setTextColor(Color.parseColor(mTextColor));
        mTitleView.setText(mTitle);
        mArrowView.setHiddenText(isHiddenBackText);
        if (mNavBgColorId != -1){
            setNavThemColor(mNavBgColorId);
            NavBar.setBackgroundColor(mNavBgColorId);
        }else {
            if (EmptyAndSizeUtils.isEmpty(mNavBgColor)){
                mNavBgColor ="#008B8B";
                NavBar.setBackgroundColor(Color.parseColor(mNavBgColor));
                setNavThemColor(Color.parseColor(mNavBgColor));
            }else {
                NavBar.setBackgroundColor(Color.parseColor(mNavBgColor));
                setNavThemColor(Color.parseColor(mNavBgColor));
            }
        }
    }

    public int getNavThemColor() {
        return NavThemColor;
    }

    public void setNavThemColor(int navThemColor) {
        NavThemColor = navThemColor;
    }

    public void setTitle(String title) {
        mTitle = title;
        mTitleView.setText(mTitle);
    }

    public String getNavBgColor() {
        return mNavBgColor;
    }

    public void setNavBgColor(String navBgColor) {
        mNavBgColor = navBgColor;
        init();
    }

    public int getNavBgColorId() {
        return mNavBgColorId;
    }

    public void setNavBgColorId(int navBgColorId) {
        mNavBgColorId = navBgColorId;
        mNavBgColor ="";
        init();
    }

    public String getTextColor() {
        return mTextColor;
    }

    public void setTextColor(String textColor) {
        mTextColor = textColor;
        init();
    }

    public boolean isHiddenBackText() {
        return isHiddenBackText;
    }

    public void setHiddenBackText(boolean isHidden) {
        isHiddenBackText = isHidden;
        mArrowView.setHiddenText(isHiddenBackText);
    }

    public BackArrowView getArrowView() {
        return mArrowView;
    }


    public TextView getTitleView() {
        return mTitleView;
    }


    public String getTitle() {
        return mTitle;
    }

    public View getNavBar() {
        return NavBar;
    }


}
