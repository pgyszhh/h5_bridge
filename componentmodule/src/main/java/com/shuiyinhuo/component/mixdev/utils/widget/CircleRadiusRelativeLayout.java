package com.shuiyinhuo.component.mixdev.utils.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.shuiyinhuo.component.mixdev.locationmanager.UtilsManager;
import com.shuiyinhuo.component.mixdev.utils.comm.DensityUtil;
import com.shuiyinhuo.component.mixdev.utils.comm.EmptyAndSizeUtils;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/9/4 0004
 * @ Description：有圆角的矩形，作为组件添加到其他view上
 * =====================================
 */
public class CircleRadiusRelativeLayout extends RelativeLayout {
    private Paint mPaint;
    private int interval = -1;
    private Context mContext;
    private String mBackgroundColor = "#F4F4F6";
    /**
     * 底部按钮与页面同步颜色
     */
    private String mLeftButtonColor = "";

    /**
     * 底部按钮与页面同步颜色
     */
    private String mHeaderBgColor = "";
    /**
     * 底部按钮与页面同步颜色
     */
    private String mRightButtonColor = "";
    /**
     * 存在底部按钮时，中间的线颜色
     */
    private String mButtonMidLine = "#3FA5FA";
    //当有按钮时，中间线宽
    private int lineWidth = 2;

    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
    }

    public String getBackgroundColor() {
        return mBackgroundColor;
    }

    public void setLeftButtonColor(String buttonColor) {
        mLeftButtonColor = buttonColor;
    }

    public String getHeaderBgColor() {
        return mHeaderBgColor;
    }

    public void setHeaderBgColor(String headerBgColor) {
        mHeaderBgColor = headerBgColor;
    }

    public void setRightButtonColor(String buttonColor) {
        mRightButtonColor = buttonColor;
    }

    public String getButtonMidLine() {
        return mButtonMidLine;
    }

    public void setButtonMidLineColor(String buttonMidLineColor) {
        mButtonMidLine = buttonMidLineColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        mBackgroundColor = backgroundColor;
    }

    public CircleRadiusRelativeLayout(Context context) {
        this(context, null);
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public CircleRadiusRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public CircleRadiusRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        mPaint = new Paint();
        if (interval == -1) {
            interval = DensityUtil.px2dip(mContext, 50);
        }
        setBackgroundColor(1);

    }

    @Override
    public void setBackgroundColor(int color) {
        super.setBackgroundColor(Color.parseColor("#00000000"));
        //super.setBackgroundColor(color);//00000000
    }

    public int getInterval() {
        return interval;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        float circle = interval * 2;
        float cirleRf = circle * 2;
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor(getBackgroundColor()));
        //背景
        RectF cirle_lt = new RectF(0, 0, cirleRf, cirleRf);
        RectF cirle_rt = new RectF(getWidth() - cirleRf, 0, getWidth(), cirleRf);

        RectF cirle_lb = new RectF(0, getHeight() - cirleRf, cirleRf, getHeight());
        RectF cirle_rb = new RectF(getWidth() - cirleRf, getHeight() - cirleRf, getWidth(), getHeight());

        // canvas.drawCircle(circle,circle,circle,  mBgPaint);
        // canvas.drawCircle(getWidth()-circle,circle,circle,  mBgPaint);
        //canvas.drawCircle(circle,getHeight()-circle,circle,  mBgPaint);
        // canvas.drawCircle(getWidth()-circle,getHeight()-circle,circle,  mBgPaint);

        if (EmptyAndSizeUtils.isNotEmpty(getHeaderBgColor())){
            mPaint.setColor(Color.parseColor(getHeaderBgColor()));
        }
        canvas.drawArc(cirle_lt, 180, 90, true, mPaint);
        canvas.drawArc(cirle_rt, -90, 90, true, mPaint);
        mPaint.setColor(Color.parseColor(getBackgroundColor()));
        if (EmptyAndSizeUtils.isNotEmpty(mLeftButtonColor) && EmptyAndSizeUtils.isNotEmpty(mRightButtonColor)) {
            if (EmptyAndSizeUtils.isNotEmpty(mLeftButtonColor)) {
                mPaint.setColor(Color.parseColor(mLeftButtonColor));
            }
            canvas.drawArc(cirle_lb, 90, 90, true, mPaint);
            if (EmptyAndSizeUtils.isNotEmpty(mRightButtonColor)) {
                mPaint.setColor(Color.parseColor(mRightButtonColor));
            }
            canvas.drawArc(cirle_rb, 0, 90, true, mPaint);
        } else if (EmptyAndSizeUtils.isNotEmpty(mLeftButtonColor) || EmptyAndSizeUtils.isNotEmpty(mRightButtonColor)){
            if (EmptyAndSizeUtils.isNotEmpty(mRightButtonColor)) {
                mPaint.setColor(Color.parseColor(mRightButtonColor));
            } else {
                mPaint.setColor(Color.parseColor(mLeftButtonColor));
            }
            canvas.drawArc(cirle_lb, 90, 90, true, mPaint);
            canvas.drawArc(cirle_rb, 0, 90, true, mPaint);
        }else {
            mPaint.setColor(Color.parseColor(getBackgroundColor()));
            canvas.drawArc(cirle_lb, 90, 90, true, mPaint);
            canvas.drawArc(cirle_rb, 0, 90, true, mPaint);
        }


        if (EmptyAndSizeUtils.isNotEmpty(getBackgroundColor())) {
            mPaint.setColor(Color.parseColor(getBackgroundColor()));
        }
        RectF mRectF_Top = new RectF(circle, 0, getWidth() - circle, circle);
        RectF mRectF_Center = new RectF(0, circle, getWidth(), getHeight() - circle);
        RectF mRectF_Bottom = new RectF(circle, getHeight() - circle, getWidth() - circle, getHeight());
        canvas.drawRect(mRectF_Top, mPaint);
        canvas.drawRect(mRectF_Center, mPaint);


        int sx = (int) (getWidth() * 1f / 2);
        int sy = (int) (getHeight() - circle * 2);
        int ex = sx;
        int ey = getHeight();


        canvas.drawRect(mRectF_Bottom, mPaint);
        if (EmptyAndSizeUtils.isNotEmpty(mLeftButtonColor) && EmptyAndSizeUtils.isNotEmpty(mRightButtonColor)) {
            mPaint.setStrokeWidth(lineWidth);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(Color.parseColor(getButtonMidLine()));
            RectF mRectFLine = new RectF(circle, sy, ex + lineWidth, getHeight());
            canvas.drawRect(mRectFLine, mPaint);

            if (EmptyAndSizeUtils.isNotEmpty(mLeftButtonColor)) {
                mPaint.setColor(Color.parseColor(mLeftButtonColor));
                RectF mRectFL = new RectF(circle, sy, ex, getHeight());
                canvas.drawRect(mRectFL, mPaint);
            }


            if (EmptyAndSizeUtils.isNotEmpty(mRightButtonColor)) {
                mPaint.setColor(Color.parseColor(mRightButtonColor));
                RectF mRectFR = new RectF(ex + lineWidth, sy, getWidth() - circle, getHeight());
                canvas.drawRect(mRectFR, mPaint);
            }
            mPaint.setColor(Color.parseColor(getBackgroundColor()));
        }


        /**
         * 只有一个按钮时
         */
        boolean test =(EmptyAndSizeUtils.isNotEmpty(mRightButtonColor)&& EmptyAndSizeUtils.isEmpty(mLeftButtonColor))||
                (EmptyAndSizeUtils.isEmpty(mRightButtonColor) && EmptyAndSizeUtils.isNotEmpty(mLeftButtonColor));
        if (test) {
            RectF mRectFR = new RectF(circle, sy, getWidth() - circle, getHeight());
            if (EmptyAndSizeUtils.isNotEmpty(mRightButtonColor)) {
                mPaint.setColor(Color.parseColor(mRightButtonColor));
            } else {
                mPaint.setColor(Color.parseColor(mLeftButtonColor));
            }
            canvas.drawRect(mRectFR, mPaint);
            mPaint.setColor(Color.parseColor(getBackgroundColor()));
        }else {
            mPaint.setColor(Color.parseColor(getBackgroundColor()));
        }

        if (EmptyAndSizeUtils.isNotEmpty(getHeaderBgColor())){
            mPaint.setColor(Color.parseColor(getHeaderBgColor()));
            RectF mRectFR = new RectF(circle, 0, getWidth() - circle, circle);
            canvas.drawRect(mRectFR, mPaint);
        }

    }


}
