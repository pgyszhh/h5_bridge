package com.shuiyinhuo.component.mixdev.utils.widget;

import android.app.Activity;
import android.os.AsyncTask;

import com.shuiyinhuo.component.mixdev.utils.comm.EmptyAndSizeUtils;


/**
 * =====================================
 *
 * @ Author: szhh
 * @ Date : on 2019/3/26 0026
 * @ Description：主线程调用方法
 * =====================================
 */
public class SGCCRunUiThreadUtils {
    private static SGCCRunUiThreadUtils mInstance;
    private  Activity mActivity;
    private OnUiThreadCallBack onUiThreadCallBack;
    private WorkThread mWorkThread;
    private static boolean isAlive=false;
    private static Object preDate=null;
    private  SGCCRunUiThreadUtils(){

    }

    public static SGCCRunUiThreadUtils getmInstance(){
        synchronized (SGCCRunUiThreadUtils.class) {
            if (EmptyAndSizeUtils.isEmpty(mInstance)) {
                return new SGCCRunUiThreadUtils();
            }
        }

        return mInstance;
    }

    public void start(Activity activity,OnUiThreadCallBack onUiThreadCallBack){
        mActivity=activity;
        this.onUiThreadCallBack=onUiThreadCallBack;
        if (EmptyAndSizeUtils.isNotEmpty(mActivity)&&EmptyAndSizeUtils.isNotEmpty(onUiThreadCallBack)){
            if (EmptyAndSizeUtils.isNotEmpty(mWorkThread)&&isAlive){
                if (mWorkThread.isAlive()){
                    try {
                        mWorkThread.destroy();
                    } catch (Exception e) {
                        mWorkThread=null;
                    }
                }
            }
            isAlive=false;
            mWorkThread=new WorkThread();
            mWorkThread.start();
        }

    }

    private class WorkThread extends Thread{
        public WorkThread(){

        }
        @Override
        public void run() {
            super.run();
            isAlive=true;

            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    preDate = onUiThreadCallBack.onPrePareUiRun();
                }
            });
            final Object o = onUiThreadCallBack.asyncTaskRun(preDate);
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onUiThreadCallBack.uiTaskRun(o,preDate);
                    }
                });
            isAlive=false;
            try {
                this.destroy();
            } catch (Exception e) {
                mWorkThread=null;
            }
        }
    }

    public interface OnUiThreadCallBack<T>{
        T onPrePareUiRun();
        T asyncTaskRun(T preData);
        void uiTaskRun(T result, T preData);
    }
}
