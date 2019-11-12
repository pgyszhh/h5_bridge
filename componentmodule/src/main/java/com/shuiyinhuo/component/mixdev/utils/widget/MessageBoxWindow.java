package com.shuiyinhuo.component.mixdev.utils.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.shuiyinhuo.component.mixdev.utils.comm.EmptyAndSizeUtils;
import com.shuiyinhuo.component.mixdev.utils.comm.JNILog;
import com.shuiyinhuo.component.mixdev.utils.comm.ScreenUtils;

import java.util.HashSet;

/**
 * =====================================
 *
 * @ Author: szhh
 * @ Date : on 2017/10/14 0014
 * @ Description： 通用的消息框
 * =====================================
 */
public abstract class MessageBoxWindow extends PopupWindow implements View.OnClickListener, ViewTreeObserver.OnGlobalLayoutListener {
    /**

     <!--通用信息框动画-->
     <style name="sgcc_com_message_anim" parent="android:Animation">
     <item name="android:windowEnterAnimation">@anim/bottom_menu_enter</item>
     <item name="android:windowExitAnimation">@anim/bottom_menu_exit</item>
     </style>

     */
    private String TAG = "BaseWindow";
    protected Context mContext;
    private FrameLayout mMainContentView;
    private View mContentView;
    private LayoutInflater mInflater;
    private double[] mPercentsXY = {0.5, 0.5};
    private boolean isHalfColor = true;
    private boolean isOutSideClickedDismiss = false;
    private boolean isFixedSize = false;
    private int mColorLevel = -1;
    private String[] colorMode = {"#00", "#1A", "#33", "#4D", "#66", "#80", "#99", "#B3", "#CC", "#E6", "#F2", "#FF",};
    private int mTopPadding = 0, mButtomPadding = 0, mLeftPadding = 0, mRightPadding = 0;
    private String mBackgroundColor = "#FFFFFF";
    private int mCoverRadius = 20;
    private OnViewClickListener mListener = new OnViewClickListener();
    private int mMargin = 10;
    private int mWindowWithoutTitleHeight = 0;
    private ShapeBorder shapeBorder = null;
    private float transColor = 0.5f;
    private boolean isCenter=true;
    //透明度级别
    public static enum TransParentLevel {
        TRANSPARENT, LEVEL_1, LEVEL_2, LEVEL_3, LEVEL_4, LEVEL_5, LEVEL_6, LEVEL_7, LEVEL_8, LEVEL_9, LEVEL_10, NORMAL_LEVEL
    }
    private HashSet<View> mBindedEventsView=new HashSet<View>();

    public MessageBoxWindow(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
        init();
    }

    public void init() {
        mMainContentView = new FrameLayout(mContext);
        setContentView(mMainContentView);

        this.setWidth(getWindowWidth());
        this.setHeight(getWindowHeight());
        this.setOutsideTouchable(true);
        this.setTouchable(true);
        this.setFocusable(true);
        ColorDrawable colorDrawable = new ColorDrawable(getParseColorValue(getColorStringByTransferLevel(mColorLevel == -1 ? 5 : mColorLevel)));
        this.setBackgroundDrawable(colorDrawable);
        this.setAnimationStyle(android.R.anim.fade_in);
        initColorModeDatas();
    }

    private void addContentView() {
        shapeBorder = new ShapeBorder(mContext);
        shapeBorder.setBgColor(mBackgroundColor);
        shapeBorder.setRadius(mCoverRadius);
        mMainContentView.addView(shapeBorder);
        FrameLayout.LayoutParams coverShape = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        coverShape.gravity = Gravity.CENTER;
        coverShape.topMargin = mMargin;
        coverShape.bottomMargin = mMargin;
        coverShape.leftMargin = mMargin;
        coverShape.rightMargin = mMargin;
        shapeBorder.setPadding(mLeftPadding, mTopPadding > mCoverRadius ? mTopPadding : mCoverRadius, mRightPadding, mButtomPadding > mCoverRadius ? mButtomPadding : mCoverRadius);
        shapeBorder.setLayoutParams(coverShape);

        if (EmptyAndSizeUtils.isEmpty(getContentViewLayout())) {
            mContentView = mInflater.inflate(getContentViewLayoutId(), null, false);
        }else {
            mContentView=getContentViewLayout();
        }
        shapeBorder.addView(mContentView);
        mContentView.getViewTreeObserver().addOnGlobalLayoutListener(this);
        findViewByIdFromWindowContentView(mContentView);
    }

    private MessageBoxWindow initSize() {
        getFrameWorkContentView(mMainContentView);
        addContentView();
        int mWidth = getWindowWidth(mPercentsXY[0]);
        int mHeight = getWindowHeight(mPercentsXY[1]);
        JNILog.e(TAG, "mWidth:" + mWidth);
        JNILog.e(TAG, "mHeight:" + mHeight);
        mMainContentView.setBackgroundColor(getParseColorValue(mColorLevel == -1 ? isHalfColor ? "#e0000000" : "#00000000" : getColorStringByTransferLevel(mColorLevel)));
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(mWidth == 0 ? ViewGroup.LayoutParams.WRAP_CONTENT : !isFixedSize ? ViewGroup.LayoutParams.WRAP_CONTENT : mWidth, mHeight == 0 ? ViewGroup.LayoutParams.WRAP_CONTENT : !isFixedSize ? ViewGroup.LayoutParams.WRAP_CONTENT : mHeight);
        layoutParams.gravity = Gravity.CENTER;
        mContentView.setLayoutParams(layoutParams);

        dispathDatasAndEvents();
        return this;
    }

    ;

    private int getParseColorValue(String color) {
        return Color.parseColor("#00000000");
    }

    private void getFrameWorkContentView(FrameLayout frameLayout) {
        JNILog.e(TAG, "between size :mWindowWithoutTitleHeight   " + mWindowWithoutTitleHeight + "   ?getWindowHeight():" + getWindowHeight());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(getWindowWidth(), mWindowWithoutTitleHeight == 0 ? getWindowHeight() : mWindowWithoutTitleHeight);
        frameLayout.setLayoutParams(layoutParams);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            frameLayout.setBackground(getTransColorDrawable());
        } else {
            frameLayout.setBackgroundColor(getParseColorValue((mColorLevel <= 11 || mColorLevel > -1) ? getColorStringByTransferLevel(mColorLevel) : isHalfColor ? "#e0000000" : "#00000000"));
        }
    }

    ;

    private DisplayMetrics getDisplayMetrics() {
        return mContext.getResources().getDisplayMetrics();
    }

    private int getWindowWidth() {
        return getDisplayMetrics().widthPixels;
    }

    private int getWindowWidth(double percent) {
        return (int) (getDisplayMetrics().widthPixels * percent);
    }

    private int getWindowHeight(double percent) {
        return (int) (getDisplayMetrics().heightPixels * percent);
    }

    private int getWindowHeight() {
        return getDisplayMetrics().heightPixels;
    }

    /**
     * 0.1  0.2   0.3   0.4  0.5  0.6  0.7  0.8   0.9   1
     * 0    1      2     3    4     5    6    7    8    9
     *
     * @param mColorLevel
     * @return
     */
    private String getColorStringByTransferLevel(int mColorLevel) {
        return colorMode[mColorLevel] + "000000";
    }

    private Drawable getTransColorDrawable() {
        return new ColorDrawable(getParseColorValue((mColorLevel != -1) ? getColorStringByTransferLevel(mColorLevel) : isHalfColor ? "#e0000000" : "#00000000"));
    }


    private void initColorModeDatas() {
        JNILog.e(TAG, "level datas:" + colorMode.toString());
    }

    private void bindListenerToViews(){
        mBindedEventsView.clear();
       addViewsToContainerForClickListener(mBindedEventsView);
        if (this.mBindedEventsView!=null&&this.mBindedEventsView.size()!=0){
            for (View view:mBindedEventsView){
                view.setClickable(true);
                view.setOnClickListener(mListener);
            }
        }
    }
    public void setViewClickListener(View view) {
        if (view != null) {
            view.setClickable(true);
            view.setOnClickListener(mListener);
        }

    }

    public <T>T getView(View mContentView,int id){
        return (T) mContentView.findViewById(id);
    }

    public <T>T getView(int id){
        return (T)mMainContentView.findViewById(id);
    }

    public abstract int getContentViewLayoutId();
    public View getContentViewLayout(){
        return null;
    };

    public abstract void findViewByIdFromWindowContentView(View contentView);

    public abstract void addViewsToContainerForClickListener(HashSet<View> container);

    public abstract void bindDatas();

    public abstract void onClickListener(int id, View view);


    public MessageBoxWindow setWindowOutSideClickedDismissWindow(boolean isDismiss) {
        this.isOutSideClickedDismiss = isDismiss;
        mMainContentView.setClickable(isDismiss);
        if (isOutSideClickedDismiss) {
            mMainContentView.setTag(8000);
            mMainContentView.setOnClickListener(this);
        } else {
            mMainContentView.setOnClickListener(null);
        }
        return this;
    }

    public MessageBoxWindow setWindowTransferMode(boolean isHalfColor) {
        this.isHalfColor = isHalfColor;
        return this;
    }

    public MessageBoxWindow setWindowPadding(int topPadding, int leftPadding, int buttomPadding, int rightPadding) {
        this.mTopPadding = ScreenUtils.dip2px(mContext, topPadding);
        this.mButtomPadding = dip2px(mContext, buttomPadding);
        this.mLeftPadding =dip2px(mContext, leftPadding);
        this.mRightPadding = dip2px(mContext, rightPadding);
        return this;
    }

    public MessageBoxWindow setWindowMargin(int margin) {
        this.mMargin = dip2px(mContext, margin);
        return this;
    }

    public MessageBoxWindow setWindowCoverRadius(int coverRadius) {
        this.mCoverRadius = dip2px(mContext, coverRadius);
        return this;
    }

    public MessageBoxWindow setWindowBackgroundColor(String bgColor) {
        this.mBackgroundColor = bgColor;
        return this;
    }

    /**
     *  设置窗口的内容是否为固定比例，true时：
     *  需要调用setShowWindowCententViewSizeByPercnetXY(double XPercent, double YPercent)
     *  传入X Y方向占设备对应宽高的比例
     */
    public MessageBoxWindow setWindowSizeMode(boolean isWindowFixedSize) {
        this.isFixedSize = isWindowFixedSize;
        return this;
    }

    public MessageBoxWindow setWindowTransferModeByLevenLevel(TransParentLevel modeLevel) {
        switch (modeLevel) {
            case TRANSPARENT: {
                this.mColorLevel = 0;
            }
            break;
            case LEVEL_1: {
                this.mColorLevel = 1;
            }
            break;
            case LEVEL_2: {
                this.mColorLevel = 2;
            }
            break;
            case LEVEL_3: {
                this.mColorLevel = 3;
            }
            break;
            case LEVEL_4: {
                this.mColorLevel = 4;
            }
            break;
            case LEVEL_5: {
                this.mColorLevel = 5;
            }
            break;
            case LEVEL_6: {
                this.mColorLevel = 6;
            }
            break;
            case LEVEL_7: {
                this.mColorLevel = 7;
            }
            break;
            case LEVEL_8: {
                this.mColorLevel = 8;
            }
            break;
            case LEVEL_9: {
                this.mColorLevel = 9;
            }
            break;
            case LEVEL_10: {
                this.mColorLevel = 10;
            }
            break;
            case NORMAL_LEVEL: {
                this.mColorLevel = 11;
            }
            break;
        }
        if (mColorLevel == 0) {
            transColor = 0.5f;
        } else if (mColorLevel == 11) {
            transColor = 0.0f;
        } else {
            transColor = mColorLevel * 1f / 10;
        }

        return this;
    }

    public MessageBoxWindow setWindowMainHeight(int height) {
        this.mWindowWithoutTitleHeight = height;
        return this;
    }
    // value: 0.1    1
    public MessageBoxWindow setShowWindowCententViewSizeByPercnetXY(double XPercent, double YPercent) {
        this.mPercentsXY[0] = XPercent;
        this.mPercentsXY[1] = YPercent;
        return this;
    }

    @Override
    public void dismiss() {
        if (this.isShowing()) {
            switchBackground(1f);
        }
        super.dismiss();
    }

    private void switchBackground(float bgAlpha) {
        Activity activity = getActivity();
        if (activity != null) {
            backgroundAlpha(activity, bgAlpha);
        }
    }

    private Activity getActivity() {
        Activity activity = null;
        try {
            activity = (Activity) mContext;
        } catch (Exception e) {
        }
        return activity;
    }

    private void backgroundAlpha(Activity activity, float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        activity.getWindow().setAttributes(lp);
    }

    public void destoryWindow(MessageBoxWindow window) {
        if (window != null && window.isShowing()) {
            window.dismiss();
        }
        window = null;
        System.gc();
    }

    public void showWindow(MessageBoxWindow window, View anchor, int... offsetXY) {
        destoryWindow(window);
        initSize();
        int x = 0;
        int y = 0;
        if (offsetXY != null && offsetXY.length != 0) {
            if (offsetXY.length == 1) {
                x = offsetXY[0];
            }
            if (offsetXY.length == 2) {
                x = offsetXY[0];
                y = offsetXY[1];
            }
        }
        if (offsetXY == null || offsetXY.length == 0) {
            this.showAtLocation(anchor, Gravity.CENTER, 0, 0);
        } else {
            this.showAsDropDown(anchor, x, y);
        }
        switchBackground(transColor);

    }

    @Override
    public void onClick(View view) {
        if (view == mMainContentView) {
            this.dismiss();
        }
    }

    private class OnViewClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            onClickListener(view.getId(), view);
        }
    }

    private void dispathDatasAndEvents() {
        bindDatas();
        bindListenerToViews();

    }

    public MessageBoxWindow setTextToView(TextView t, String data) {
        if (t != null) {
            t.setText(!TextUtils.isEmpty(data) ? data : "");
        }
        return this;
    }

    @Override
    public void onGlobalLayout() {
        if (mContentView != null) {
            if (mContentView.getWidth() != 0 && mContentView.getHeight() != 0) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    mMainContentView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    mMainContentView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
                JNILog.e(TAG, " remove onGlobalLayout" + "mContentView.getWidth() :" + mContentView.getWidth() + "  mContentView.getHeight() :" + mContentView.getHeight());
                shapeBorder.mRequestLayout();
                int y = (int) mContentView.getY();
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) shapeBorder.getLayoutParams();
                int halfHeight = (int) (getWindowHeight() * 1f / 2);
                int height = shapeBorder.getHeight();
                int resultMargin = (int) (halfHeight - height * 1f / 2);
                layoutParams.bottomMargin = y;
                shapeBorder.setLayoutParams(layoutParams);

                int tp= (int) (((getWindowHeight()-shapeBorder.getHeight())*1.0f/2)*2/3);
                JNILog.e(TAG,"高度："+tp);
                if (!isCenter) {
                    mMainContentView.setPadding(0,0,0,tp);
                }

                JNILog.e(TAG, "(resultMargin:" + resultMargin + "\n shapeBorder height:" + height + "\n WindowHeight y :" + y);
                shapeBorder.setLayoutParams(layoutParams);
            }
        }
    }

    public void setWindowCenter(boolean isCenter){
        this.isCenter=isCenter;
    }

    private class ShapeBorder extends FrameLayout {
        private String TAG = "ShapeBorder";
        private String mMainColor = "#00000000";
        private String mShapeColor = "#FFFFFF";
        private Paint mPaint;
        private int mStrokeWidth = 1;
        private boolean isStyleModeFill = true;
        private int mRadius = 20;

        private int[] leftTopCircle = new int[2];
        private int[] rightButtomCircle = new int[2];

        private int mWidth = 0;
        private int mHeight = 0;

        public ShapeBorder(Context context) {
            this(context, null);
        }

        public ShapeBorder(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
        }

        public ShapeBorder(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            this.setBackgroundColor(Color.parseColor(mMainColor));
            init();
        }

        private void init() {
            mPaint = new Paint();
            mPaint.setStrokeWidth(mStrokeWidth);
            mPaint.setColor(Color.parseColor(TextUtils.isEmpty(mShapeColor) ? mShapeColor = "#FFFFFF" : !mShapeColor.contains("#") ? "#" + mShapeColor : mShapeColor));
            mPaint.setStyle(isStyleModeFill ? Paint.Style.FILL : Paint.Style.STROKE);
            mPaint.setAntiAlias(true);
            mWidth = mWidth == 0 ? this.getWidth() : mWidth;
            mHeight = mHeight == 0 ? this.getHeight() : mHeight;

            leftTopCircle[0] = mRadius;
            leftTopCircle[1] = mRadius;
            rightButtomCircle[0] = mWidth - mRadius;
            rightButtomCircle[1] = mHeight - mRadius;
            requestLayout();
            JNILog.e(TAG, "start relayout...");
        }

        private void printsMsg() {
            JNILog.e(TAG, " leftTopCircle[0]:" + leftTopCircle[0]);
            JNILog.e(TAG, " leftTopCircle[1]:" + leftTopCircle[1]);
            JNILog.e(TAG, " rightButtomCircle[0]:" + rightButtomCircle[0]);
            JNILog.e(TAG, " rightButtomCircle[1]:" + rightButtomCircle[1]);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            printsMsg();
            //lefttop_circle
            onDrawSelfCircle(canvas, mPaint, leftTopCircle[0], leftTopCircle[1], mRadius);
            //leftBottom_circle
            onDrawSelfCircle(canvas, mPaint, leftTopCircle[0], rightButtomCircle[1], mRadius);
            //rightTop_circle
            onDrawSelfCircle(canvas, mPaint, rightButtomCircle[0], leftTopCircle[1], mRadius);
            //rightBottom_circle
            onDrawSelfCircle(canvas, mPaint, rightButtomCircle[0], rightButtomCircle[1], mRadius);
            // topOval
            onSelfdrawOval(canvas, mPaint, leftTopCircle[0], 0, mWidth - leftTopCircle[0], leftTopCircle[1]);
            //center Oval
            onSelfdrawOval(canvas, mPaint, 0, leftTopCircle[1], mWidth, rightButtomCircle[1]);
            // bottom Oval
            onSelfdrawOval(canvas, mPaint, leftTopCircle[0], rightButtomCircle[1], mWidth - leftTopCircle[0], mHeight);

        }

        private void onSelfdrawOval(Canvas canvas, Paint paint, float left, float top, float right, float bottom) {
            RectF rf = new RectF(left, top, right, bottom);
            canvas.drawRect(rf, paint);
        }

        public void onDrawSelfCircle(Canvas canvas, Paint paint, float cx, float cy, float radius) {
            canvas.drawCircle(cx, cy, radius, paint);
        }


        public void setBgColor(String mShapeColor) {
            this.mShapeColor = mShapeColor;
        }
        public void setRadius(int mRadius) {
            this.mRadius = mRadius;
        }
        public void setWidth(int width) {
            mWidth = width;
        }
        public void setHeight(int height) {
            mHeight = height;
        }
        public void mRequestLayout() {
            JNILog.e(TAG, "start mRequestLayout");
            init();
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    private   int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
