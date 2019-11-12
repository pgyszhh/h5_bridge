package com.shuiyinhuo.component.mixdev.jinbean.threads;

import com.shuiyinhuo.component.mixdev.caller.AccompanyThreadInterface;
import com.shuiyinhuo.component.mixdev.utils.comm.EmptyAndSizeUtils;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/8/26 0026
 * @ Description：伴随线程
 * =====================================
 */
public class AccompanyThread extends Thread{
    private AccompanyThreadInterface mThreadInterface;
    public AccompanyThread(AccompanyThreadInterface mThreadInterface) {
        this.mThreadInterface=mThreadInterface;
    }

    @Override
    public void run() {
        super.run();
        if (EmptyAndSizeUtils.isNotEmpty(mThreadInterface)){
            mThreadInterface.startFitting();
        }
    }
}
