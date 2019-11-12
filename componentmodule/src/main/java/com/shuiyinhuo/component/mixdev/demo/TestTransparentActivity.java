package com.shuiyinhuo.component.mixdev.demo;

import com.shuiyinhuo.component.R;
import com.shuiyinhuo.component.mixdev.activity.ProxyBaseActivityOfDialogWindow;
import com.shuiyinhuo.component.mixdev.jinbean.em.WindowTransParencyLevel;
import com.shuiyinhuo.component.mixdev.proxy.ProxyTranparentActity;
import com.shuiyinhuo.component.mixdev.utils.comm.ColorUtils;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/9/7 0007
 * @ Description：测试 透明Activity
 * =====================================
 */
public class TestTransparentActivity extends ProxyTranparentActity {
    @Override
    public void initView() {
        setStateTransparentColor();
        //setWindowTranparentLevel(70);


    }

    @Override
    protected int setWindowTranparentLevel(ColorUtils mColorInflater) {
        return mColorInflater.parase_ColorByGeneratorColor(WindowTransParencyLevel.TRANS_PARENCY_LEVEL_DEFAULT,false);
    }

    @Override
    public void initEvents() {

    }

    @Override
    public int getResId() {
        return R.layout.loading_test;
    }
}
