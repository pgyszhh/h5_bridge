package com.shuiyinhuo.component.mixdev.utils.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.shuiyinhuo.component.mixdev.utils.comm.DensityUtil;

import java.io.IOException;
import java.io.InputStream;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/8/11 0011
 * @ Description：
 * =====================================
 */
public class RoundLightBarView extends FrameLayout {
    private int mTotalWidth, mTotalHeight;
    private int mCenterX, mCenterY;
    //底色画笔
    private Paint mCirclePaint;

    //底色实心圆画笔
    private Paint mBgPaint;
    //进度条画笔
    private Paint mProgressPaint;
    //圆点画笔
    private Paint mbitmapPaint;
    private Matrix mMatrix; // 矩阵,用于对图片进行一些操作
    private float[] pos;   // 当前点的实际位置
    private float[] tan;   // 当前点的tangent值,用于计算图片所需旋转的角度


    private int mCircleR;

    /**
     * 颜色建表
     */
    //进度条颜色
    private String mProcessStartColor  ="#0FB9B1";//7cc9d0
    // 中间主段颜色
    private String mProcessCenterColor ="#CCE7FC";//3bbaea
    //进度条颜色
    private String mProcessEndColor ="#DAFBCC";//7ac9d3

    /**
     * 背景圆颜色
     */
    private String mCirleColor="#99000000";//99193E75

    /**
     * 进度条底色基圆颜色
     */
    private String mBaseCirleColor="#00000000";
    /**
     * white_round.png
     */
    private String fileName="white_round.png";

    private Context mContext;
    //距离外围的边距
    private float interval;

    private int startAngle = 1;

    //球
    private Bitmap mLititleBitmap; // 圆点图片

    public RoundLightBarView(Context context) {
        this(context,null);
    }


    public RoundLightBarView(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }
    public RoundLightBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;


        interval = DensityUtil.px2dip(mContext, 50);
        //初始化bitmap
        initBitmap();
        //初始化画笔
        initPaint();
        setBackgroundColor(Color.parseColor("#000000ff"));
    }



    private void initBitmap() {
        mMatrix = new Matrix();
        pos = new float[2];
        tan = new float[2];
        String path ="web_config/loading/"+fileName;

            InputStream mStream = null;
            try {
                mStream = mContext.getAssets().open(path);
                mLititleBitmap = BitmapFactory.decodeStream(mStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    private void initPaint() {
        //画黑底的深色圆画笔
        mCirclePaint = new Paint();
        //抗锯齿
        mCirclePaint.setAntiAlias(true);
        // 防抖动
        mCirclePaint.setDither(true);
        // 开启图像过滤，对位图进行滤波处理。
        mCirclePaint.setFilterBitmap(true);
        mCirclePaint.setColor(Color.parseColor(mBaseCirleColor));
        //空心圆
        mCirclePaint.setStyle(Paint.Style.FILL);
        //圆半径
        mCircleR = DensityUtil.px2dip(mContext, 20);
        mCirclePaint.setStrokeWidth(mCircleR);


        //画黑底的深色圆画笔
        mBgPaint = new Paint();
        //抗锯齿
        mBgPaint.setAntiAlias(true);
        // 防抖动
        mBgPaint.setDither(true);
        // 开启图像过滤，对位图进行滤波处理。
        mBgPaint.setFilterBitmap(true);
        mBgPaint.setColor(Color.parseColor(mCirleColor));
        //空心圆
        mBgPaint.setStyle(Paint.Style.FILL);
        //圆半径
        mCircleR = DensityUtil.px2dip(mContext, 20);
        mBgPaint.setStrokeWidth(mCircleR);




        //画彩色圆弧的画笔
        mProgressPaint = new Paint();
        //抗锯齿
        mProgressPaint.setAntiAlias(true);
        // 防抖动
        mProgressPaint.setDither(true);
        // 开启图像过滤，对位图进行滤波处理。
        mProgressPaint.setFilterBitmap(true);
        mProgressPaint.setColor(Color.BLUE);
        //空心圆
        mProgressPaint.setStyle(Paint.Style.STROKE);
        //设置笔刷样式为原型
        mProgressPaint.setStrokeCap(Paint.Cap.ROUND);
        //设置圆弧粗
        mProgressPaint.setStrokeWidth(mCircleR);
        //将绘制的内容显示在第一次绘制内容之上
        mProgressPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));


        //圆点画笔
        mbitmapPaint = new Paint();
        mbitmapPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        mbitmapPaint.setStyle(Paint.Style.FILL);
        mbitmapPaint.setAntiAlias(true);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //canvas去锯齿
        canvas.setDrawFilter(
                new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));

        drawBaseShape(canvas);
        //画底色圆
        canvas.drawCircle(mCenterX, mCenterY, mCenterX - mCircleR - interval, mCirclePaint);
        //画进度条
        int colorSweep[] = {Color.TRANSPARENT, Color.parseColor(mProcessCenterColor), Color.parseColor(mProcessEndColor), Color.parseColor(mProcessStartColor)};

        //设置渐变色
        sweepGradient = new SweepGradient(mCenterX, mCenterY, colorSweep, null);
        //按照圆心旋转
        Matrix matrix = new Matrix();
        matrix.setRotate(startAngle, mCenterX, mCenterY);
        sweepGradient.setLocalMatrix(matrix);

        mProgressPaint.setShader(sweepGradient);

        canvas.drawArc(
                new RectF(0 + mCircleR + interval, 0 + mCircleR + interval,
                        mTotalWidth - mCircleR - interval, mTotalHeight - mCircleR - interval),
                2 + startAngle, 350, false, mProgressPaint);

        startAngle++;
        if (startAngle == 360) {
            startAngle = 1;
        }

        //绘制白色小星星
        Path orbit = new Path();
        //通过Path类画一个90度（180—270）的内切圆弧路径
        orbit.addArc(
                new RectF(0 + mCircleR + interval, 0 + mCircleR + interval,
                        mTotalWidth - mCircleR - interval, mTotalHeight - mCircleR - interval)
                , 2 + startAngle, 350);
        // 创建 PathMeasure
        PathMeasure measure = new PathMeasure(orbit, false);
        measure.getPosTan(measure.getLength() * 1, pos, tan);
        mMatrix.reset();
        mMatrix.postScale(2, 2);
        mMatrix.postTranslate(pos[0] - mLititleBitmap.getWidth(), pos[1] - mLititleBitmap.getHeight());  // 将图片绘制中心调整到与当前点重合
        canvas.drawBitmap(mLititleBitmap, mMatrix, mbitmapPaint);//绘制球
        mbitmapPaint.setColor(Color.WHITE);
        //绘制实心小圆圈
        canvas.drawCircle(pos[0], pos[1], 5, mbitmapPaint);

        //启动绘制
        postInvalidateDelayed(5);
    }

    private void drawBaseShape(Canvas canvas){

        float circle=interval*2;
        float cirleRf=circle*2;
        //背景
        RectF cirle_lt=new RectF(0,0,cirleRf,cirleRf);
        RectF cirle_rt=new RectF(getWidth()-cirleRf,0,getWidth(),cirleRf);
        RectF cirle_lb=new RectF(0,getHeight()-cirleRf,cirleRf,getHeight());
        RectF cirle_rb=new RectF(getWidth()-cirleRf,getHeight()-cirleRf,getWidth(),getHeight());
      // canvas.drawCircle(circle,circle,circle,  mBgPaint);
      // canvas.drawCircle(getWidth()-circle,circle,circle,  mBgPaint);
       //canvas.drawCircle(circle,getHeight()-circle,circle,  mBgPaint);
      // canvas.drawCircle(getWidth()-circle,getHeight()-circle,circle,  mBgPaint);

        canvas.drawArc(cirle_lt,180,90,true, mBgPaint);
        canvas.drawArc(cirle_rt,-90,90,true, mBgPaint);
        canvas.drawArc(cirle_lb,90,90,true, mBgPaint);
        canvas.drawArc(cirle_rb,0,90,true, mBgPaint);

        RectF mRectF_Top=new RectF(circle,0,getWidth()-circle,circle);
        RectF mRectF_Center=new RectF(0,circle,getWidth(),getHeight()-circle);
        RectF mRectF_Bottom=new RectF(circle,getHeight()-circle,getWidth()-circle,getHeight());
       canvas.drawRect(mRectF_Top,mBgPaint);
       canvas.drawRect(mRectF_Center,mBgPaint);
       canvas.drawRect(mRectF_Bottom,mBgPaint);


    }

    SweepGradient sweepGradient;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTotalWidth = w;
        mTotalHeight = h;
        mCenterX = mTotalWidth / 2;
        mCenterY = mTotalHeight / 2;


    }

}