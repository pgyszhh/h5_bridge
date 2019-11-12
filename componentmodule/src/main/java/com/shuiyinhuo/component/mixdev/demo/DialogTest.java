package com.shuiyinhuo.component.mixdev.demo;

import android.content.Context;
import android.view.View;

import com.shuiyinhuo.component.R;
import com.shuiyinhuo.component.mixdev.entity.BaseDialogConfig;
import com.shuiyinhuo.component.mixdev.jinbean.em.WindowTransParencyLevel;
import com.shuiyinhuo.component.mixdev.proxy.ProxyBaseTransparentWindow;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/9/7 0007
 * @ Description：测试透明Dialog效果实例
 * =====================================
 */
public class DialogTest extends ProxyBaseTransparentWindow {

    public DialogTest(Context context) {
        super(context);
    }

    @Override
    protected void onPreparedBeforeLayout() {
        BaseDialogConfig baseConfig =new BaseDialogConfig(mContext);
        baseConfig.setLocation(BaseDialogConfig.WindowPosition.CENTER);
        baseConfig.setFullWidth(false);
        setWindowBaseConfig(baseConfig);
        setWindowTranparentLevel(WindowTransParencyLevel.TRANS_PARENCY_LEVEL_40);
    }

    @Override
    public boolean setCanceledOnTouchOutside() {
        return true;
    }

    @Override
    protected void initWindowView(View view) {

    }

    @Override
    public int getResId() {
        return R.layout.loading_test;
    }
}
