package com.shuiyinhuo.component.mixdev.utils.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.OnCompositionLoadedListener;
import com.shuiyinhuo.component.mixdev.caller.WebPageStateInterface;
import com.shuiyinhuo.component.mixdev.utils.comm.EmptyAndSizeUtils;
import com.shuiyinhuo.component.mixdev.utils.comm.JNILog;
import com.shuiyinhuo.component.mixdev.utils.comm.ScreenUtils;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/8/8 0008
 * @ Description：加载loading
 * =====================================
 */
public class Loading extends Dialog {
    private LottieAnimationView lottie;
    private Context mContext;
    private ShapeForker mForker;
    private RoundLightBarView mBarView;
    private WebPageStateInterface mStateInterface;


    public Loading(Context context) {
        this(context, -1);
    }

    public Loading(Context context, int themeResId) {
        this(context, false, null);
    }

    protected Loading(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mContext = context;
        init();
    }

    public void registStateInterface(WebPageStateInterface stateInterface) {
        mStateInterface = stateInterface;
    }

// calling_pulsar  jumping-ball


    public void bindAnimation() {
        LottieComposition.Factory.fromAssetFileName(mContext, "web_config/loading/" + mForker.getAnimationName() + ".json", new OnCompositionLoadedListener() {
            @Override
            public void onCompositionLoaded(@Nullable LottieComposition composition) {
                //相当于将组建于json绑定
                lottie.setComposition(composition);
            }
        });
    }

    public void init() {
        /**设置对话框背景透明*/
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //setContentView(R.layout.loading);
        setContentView(generateView());
        //lottie = (LottieAnimationView) findViewById(R.id.lot);
        setAnimationType(mForker);
        lottie.loop(true);
        lottie.playAnimation();
        setCanceledOnTouchOutside(false);
        bindAnimation();
    }
    /** ball_4  loading  many_shuibowen  music_pectrum sin
     优秀 quxianqiu  循环滚动球体_青色    ball_4  循环滚动球体_绿色   水波纹球体loading  球踢与矩形跳loading
    瓷瓶 jumping_ball  many_shape_types
     */
    public void setAnimationType(ShapeForker forker) {
        forker.setAnimationType(ShapeForker.LOADING_TYPE.ball_4 , true, new String[]{"00000000", "ffffff"});
    }

    @Override
    public void onBackPressed() {
        JNILog.e("------------->onBackPressed");
        if (EmptyAndSizeUtils.isNotEmpty(mStateInterface)){
            if (mStateInterface.isCanBack()){
                mStateInterface.dismissLoading();
                mStateInterface.dismissWindow();
            }else {
                return;
            }
        }
        super.onBackPressed();
    }

    /**
     * 为加载进度个对话框设置不同的提示消息
     *
     * @param message 给用户展示的提示信息
     * @return build模式设计，可以链式调用
     */
    public Loading setMessage(String message) {
        //tv_text.setText(message);
        return this;
    }


    public View generateView() {
        FrameLayout mContent = new FrameLayout(mContext);
        FrameLayout.LayoutParams mParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mParams.gravity = Gravity.CENTER;
        mContent.setLayoutParams(mParams);

        mBarView = new RoundLightBarView(mContext);
        FrameLayout.LayoutParams mRoundParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mRoundParams.gravity = Gravity.CENTER;
        mBarView.setLayoutParams(mRoundParams);


        mForker = new ShapeForker(mContext);
        int size = (int) (ScreenUtils.getScreenWidth(mContext) * 1f / 4);
        FrameLayout.LayoutParams shapeParams = new FrameLayout.LayoutParams(size, size);
        shapeParams.bottomMargin = 10;
        shapeParams.topMargin = 10;
        shapeParams.leftMargin = 10;
        shapeParams.rightMargin = 10;
        mForker.setPadding(20, 20, 20, 20);
        mForker.setLayoutParams(shapeParams);

        lottie = new LottieAnimationView(mContext);

        mBarView.removeAllViews();
        mContent.removeAllViews();

        mForker.addView(lottie);
        mBarView.addView(mForker);
        mContent.addView(mBarView);
        return mContent;
    }



    public static class ShapeForker extends FrameLayout {
        public enum LOADING_TYPE {
            animation_ball,
            animation_ball1,
            ball_4,
            dna,
            double_ball,
            girl_jumping_loader,
            loading,
            loading_flip,
            many_shape_types,
            many_shuibowen,
            music_pectrum,
            quxianqiu,
            sin,
            交叉圈,
            循环滚动球体_绿色,
            循环滚动球体_青色,
            水波纹球体loading,
            球踢与矩形跳loading
        }

        private Paint mPaint;
        private int mWidth = 2;
        private Context mContext;
        private LOADING_TYPE mTYPE = LOADING_TYPE.球踢与矩形跳loading;
        /**
         * quxianqiu   quxianqiu  jumping_ball  jumping_ball_green
         * many_shape_types  球踢与矩形跳loading  水波纹球体loading  循环滚动球体_绿色  交叉圈 循环滚动球体_青色
         */
        private String[] mAnimationSrc = {
                "animation_ball",
                "animation_ball1",
                "ball_4",
                "dna",
                "double_ball",
                "girl_jumping_loader",
                "loading",
                "loading_flip",
                "many_shape_types",
                "many_shuibowen",
                "music_pectrum",
                "quxianqiu",
                "sin",
                "交叉圈",
                "循环滚动球体_绿色",
                "循环滚动球体_青色",
                "水波纹球体loading",
                "球踢与矩形跳loading"
        };

        /**
         * loading 类型设置
         */
        private TypeClassess mClassess;

        public ShapeForker(Context context) {
            this(context, null);
        }

        public ShapeForker(Context context, AttributeSet attrs) {
            this(context, attrs, -1);
        }

        public ShapeForker(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init(context);
        }


        public void setAnimationType(LOADING_TYPE type, boolean isTransparent) {
            wrapperType(type, isTransparent, null);
        }


        public void setAnimationType(LOADING_TYPE type) {
            wrapperType(type, true, null);
        }

        public void setAnimationType(LOADING_TYPE type, boolean isTransparent, String[] color) {
            wrapperType(type, isTransparent, color);
        }

        private void init(Context context) {
            mContext = context;
            mPaint = new Paint();
            setBackgroundColor(Color.parseColor("#00000000"));
        }

        public String getAnimationName() {
            return mAnimationSrc[mTYPE.ordinal()];
        }


        private void wrapperType(LOADING_TYPE type, boolean isTransparent, String[] colors) {
            mTYPE = type;
            mClassess = new TypeClassess();
            String bg_color = "#FFFFFF";
            String border_color = "#FFFFFF";
            if (EmptyAndSizeUtils.isNotEmpty(colors)) {
                if (colors.length >= 2) {
                    bg_color = colors[0];
                    border_color = colors[1];
                } else {
                    bg_color = colors[0];
                    border_color = colors[0];
                }
            }
            switch (type) {
                case 球踢与矩形跳loading: {
                    bg_color = "#00000000";
                    border_color = "#0FB9B1";
                }
                break;
            }
            mClassess.setBgColor(addColorTag(bg_color));
            mClassess.setBoderColor(addColorTag(border_color));
            mClassess.setIs_BG_TRANSPARENT(isTransparent);
            requestLayout();
        }

        private String addColorTag(String orgColor) {
            if (!orgColor.contains("#")) {
                orgColor = "#" + orgColor;
            }
            return orgColor;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            if (EmptyAndSizeUtils.isNotEmpty(mPaint)) {
                mPaint.setStyle(Paint.Style.STROKE);
                mPaint.setStrokeWidth(mWidth);
                mPaint.setColor(android.graphics.Color.parseColor(mClassess.getBoderColor()));
                float cx = getWidth() * 1f / 2;
                float cy = getHeight() * 1f / 2;
                float radius = cx * 3f / 4;
                canvas.drawCircle(cx, cy, radius, mPaint);
                mPaint.setStyle(Paint.Style.FILL);
                mPaint.setAntiAlias(true);
                mPaint.setColor(Color.parseColor(mClassess.getBgColor()));
                // canvas.drawBitmap(cirle.png);
                canvas.drawCircle(cx, cy, radius - mWidth, mPaint);
            }
        }

        private class TypeClassess {
            private String boderColor = "";
            private String bgColor = "";
            private boolean is_BG_TRANSPARENT = false;
            private String mTransparentColor = "#00000000";
            private String mAnimationSrc = "";


            public TypeClassess() {
            }

            public String getBoderColor() {
                return is_BG_TRANSPARENT ? mTransparentColor : boderColor;
            }

            public void setBoderColor(String boderColor) {
                this.boderColor = boderColor;
            }

            public String getBgColor() {
                return is_BG_TRANSPARENT ? mTransparentColor : bgColor;
            }

            public void setBgColor(String bgColor) {
                this.bgColor = bgColor;
            }

            public boolean isIs_BG_TRANSPARENT() {
                return is_BG_TRANSPARENT;
            }

            public void setIs_BG_TRANSPARENT(boolean is_BG_TRANSPARENT) {
                this.is_BG_TRANSPARENT = is_BG_TRANSPARENT;
            }

            public String getAnimationSrc() {
                return mAnimationSrc;
            }

            public void setAnimationSrc(String animationSrc) {
                mAnimationSrc = animationSrc;
            }
        }

    }



}
