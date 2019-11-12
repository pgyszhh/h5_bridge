package com.shuiyinhuo.component.mixdev.utils.widget;
import android.content.Context;
import android.graphics.Color;
import android.text.Spanned;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shuiyinhuo.component.mixdev.caller.WebPageStateInterface;
import com.shuiyinhuo.component.mixdev.cnf.comm.HtmlUtilsController;
import com.shuiyinhuo.component.mixdev.locationmanager.UtilsManager;
import com.shuiyinhuo.component.mixdev.utils.comm.EmptyAndSizeUtils;
import com.shuiyinhuo.component.mixdev.utils.comm.JNILog;
import com.shuiyinhuo.component.mixdev.utils.comm.ScreenUtils;

import java.util.HashSet;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/8/23 0023
 * @ Description：消息框
 * =====================================
 */
public class MsgWindow extends MessageBoxWindow {
    private String  mContent;
    private Spanned mContents;
    private String mTitle;
    private Spanned  mTitles ;
    private TextView mContentLayout;
    private TextView buttom;
    private TextView mLine;
    private TextView mTitleLayout;
    private WebPageStateInterface mStateInterface;

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public void setContent(Spanned content) {
        mContents = content;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setTitle(Spanned title) {
        mTitles = title;
    }

    public MsgWindow(Context context,WebPageStateInterface mStateInterface){
        super(context);
        this.mStateInterface=mStateInterface;
    }
    public MsgWindow(String content,String title,Context context) {
        super(context);
        this.mContent=content;
        this.mTitle=title;
    }

    @Override
    public View getContentViewLayout() {
        LinearLayout mainContent = new LinearLayout(mContext);
        //mainContent.setBackgroundColor(Color.parseColor("#00868B"));
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(getWidth(), getHeight());
        mainContent.setOrientation(LinearLayout.VERTICAL);
        mainContent.setLayoutParams(mParams);

        LinearLayout.LayoutParams comm_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mContentLayout = new TextView(mContext);
        //mContentLayout.setPadding(0,ScreenUtils.px2dip(mContext,15),0,ScreenUtils.px2dip(mContext,25));
        //mContentLayout.setBackgroundColor(Color.parseColor("#00868B"));
        mContentLayout.setTextSize(15);
        mContentLayout.setLayoutParams(comm_layout);
        mContentLayout.setPadding(ScreenUtils.dip2px(mContext,15),ScreenUtils.dip2px(mContext,10),ScreenUtils.dip2px(mContext,15),ScreenUtils.dip2px(mContext,15));


        mTitleLayout = new TextView(mContext);
        LinearLayout.LayoutParams comm_title_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //mTitleLayout.setBackgroundColor(Color.parseColor("#66CDAA"));
        comm_title_layout.weight=1;
        mTitleLayout.setTextSize(17);
        comm_title_layout.gravity=Gravity.CENTER;
        mTitleLayout.setPadding(0,15,0,0);
        mTitleLayout.setLayoutParams(comm_title_layout);


        buttom = new TextView(mContext);
        buttom.setTextSize(18);
        LinearLayout.LayoutParams comm_title_layout2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        comm_title_layout2.weight=1;
        comm_title_layout2.gravity=Gravity.CENTER;
        int padding_des =18;
        int padding_tb =5;
        buttom.setPadding(padding_des,padding_tb*8,padding_des,padding_tb);
        buttom.setLayoutParams(comm_title_layout2);

        LinearLayout.LayoutParams line_params = new LinearLayout.LayoutParams(getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
        mLine= new TextView(mContext);
        line_params.height=1;
        mLine.setBackgroundColor(Color.parseColor("#696969"));
        mLine.setLayoutParams(line_params);

        mainContent.addView(mTitleLayout);
        mainContent.addView(mContentLayout);
        mainContent.addView(mLine);
        mainContent.addView(buttom);
        return mainContent;
    }

    @Override
    public int getContentViewLayoutId() {
        return 0;
    }

    @Override
    public void findViewByIdFromWindowContentView(View contentView) {

    }

    @Override
    public void addViewsToContainerForClickListener(HashSet<View> container) {
        container.add(buttom);
    }

    @Override
    public void bindDatas() {
        HtmlUtilsController  mController= UtilsManager.getInstance().getHtmlUtilsController();
        int state = EmptyAndSizeUtils.isNotEmpty(mTitle) ? View.VISIBLE : View.GONE;
        mTitleLayout.setVisibility(state);
        if (EmptyAndSizeUtils.isNotEmpty(mTitle)) {
            mTitleLayout.setText(mController.formatAppointColorAndBoldOfText(mTitle,"#000000"));
        }else {
            if (EmptyAndSizeUtils.isNotEmpty(mTitles)) {
                mTitleLayout.setText(mTitles);
            }else {
                mTitleLayout.setText(" ");
            }
        }


       if (EmptyAndSizeUtils.isNotEmpty(mContent)) {
           mContentLayout.setText(mController.formatAppointColorOfText(mContent,"#000000"));
       }else {
           mContentLayout.setText(mContents);
       }
        buttom.setText(mController.formatAppointColorOfText("      确定        ", "#000000"));
        buttom.setClickable(true);
    }

    @Override
    public void onClickListener(int id, View view) {
        if (EmptyAndSizeUtils.isNotEmpty(this.mStateInterface)){
            this.mStateInterface.dismissWindow();
        }else {
            dismiss();
            JNILog.e("----------------> window interface is NUll ");
        }
    }

    @Override
    public void showWindow(MessageBoxWindow window, View anchor, int... offsetXY) {
        setWindowOutSideClickedDismissWindow(false);
        setWindowSizeMode(true);
        setShowWindowCententViewSizeByPercnetXY(0.8,0);
        super.showWindow(window, anchor, offsetXY);
    }
}
