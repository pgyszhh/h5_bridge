package com.shuiyinhuo.component.mixdev.utils.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.shuiyinhuo.component.mixdev.utils.comm.ScreenUtils;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/8/8 0008
 * @ Description：自定义头部返回箭头
 * =====================================
 */
public class BackArrowView extends View {
    private Context mContext;
    private String mDefaultArrowColor = "#FFFAFA";
    private String mDefaultBgColor = "#00000000";
    private int mWidth = 60, mHeight = 40;
    private float w = 10, h = 18;
    private float x_dis = -1, y_dis = -1;
    private Paint mPainter = null;
    private boolean isCalc = false;
    private boolean isHiddenText = true;

    public BackArrowView(Context context) {
        this(context, null);
    }

    public BackArrowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }


    public int getNavWidth() {
        return mWidth;
    }

    public void setNavWidth(int width) {
        mWidth = width;
    }

    public int getNavHeight() {
        return mHeight;
    }

    public void setNavHeight(int height) {
        mHeight = height;
    }

    private void init() {
        mPainter = new Paint();
    }

    public boolean isHiddenText() {
        return isHiddenText;
    }

    public void setHiddenText(boolean hiddenText) {
        isHiddenText = hiddenText;
        requestLayout();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!isCalc) {
            calc();
        }


        float of = 1*1f/2;
        x_dis = (getWidth() - w) * 1f / 2;
        y_dis = (getHeight() - h) * 1f / 2;


        float _x_1 = getWidth() - x_dis*(1+ (of==0?0:of));
        float _y_1 = y_dis;

        float _x_2 = x_dis* (of ==0?1:of);
        float _y_2 = getHeight() * 1f / 2;

        float _x_3 = getWidth() - x_dis*(1+(of==0?0:of));
        float _y_3 = getHeight() - y_dis;

        mPainter.setColor(Color.parseColor(mDefaultArrowColor));
        mPainter.setStrokeWidth(4);
        mPainter.setStyle(Paint.Style.STROKE);
        Path mPath = new Path();

        mPath.moveTo(_x_1, _y_1);
        mPath.lineTo(_x_2, _y_2);
        mPath.lineTo(_x_3, _y_3);

        canvas.drawPath(mPath, mPainter);

        if (!isHiddenText) {
            mPainter.setStrokeWidth(2);
            mPainter.setStyle(Paint.Style.FILL);
            mPainter.setTextSize(ScreenUtils.dip2px(mContext, 16));//设置画笔的大小
            Paint.FontMetrics fontMetrics = mPainter.getFontMetrics();
            float baseline = (fontMetrics.bottom - fontMetrics.top) / 4 + getHeight() * 1f / 2;


            canvas.drawText("返回", _x_3 + w / 3, baseline, mPainter);
        }

    }

    private void calc() {
        isCalc = true;
        setBackgroundColor(Color.parseColor(mDefaultBgColor));
        float p = w * 1f / h;
        /**
         *   w1       w2
         *   ------=  ----
         *   h1        h2
         */
        //Toast.makeText(mContext, "w = "+w+"  宽度："+getWidth(), Toast.LENGTH_SHORT).show();

        if (w >= getWidth()) {
            w = getWidth() * 3f / 4;
        }
        if (w != 10) {
            h = w * 1f / p;
        }

        w = ScreenUtils.dip2px(mContext, w);
        h = ScreenUtils.dip2px(mContext, h);
    }
}
