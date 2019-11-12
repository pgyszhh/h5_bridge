package com.shuiyinhuo.component.mixdev.jinbean.pro;

import com.shuiyinhuo.component.mixdev.jinbean.LayoutConfig;
import com.shuiyinhuo.component.mixdev.utils.comm.EmptyAndSizeUtils;
import com.shuiyinhuo.component.mixdev.utils.comm.JNILog;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/8/20 0020
 * @ Description：别名绑定辅助类
 * =====================================
 */
public class BridgeBinderAlias {
    private LayoutConfig layoutConfig;

    public BridgeBinderAlias( LayoutConfig layoutConfig) {
        this.layoutConfig = layoutConfig;
        if (EmptyAndSizeUtils.isNotEmpty(layoutConfig)) {
            layoutConfig.getJudger().setJsSwitcherEntity();
        }
    }

    public void bindJNIEntity(String bridgeName, AndroidToJsSignalCommunicationProxy entity) {
        try {
            JNILog.e("开始绑定通信桥梁");
            this.layoutConfig.getJudger().bindJNIEntity(bridgeName, entity);
        } catch (Exception e) {
            JNILog.e("绑定通信桥梁失败：" + e.getMessage());
        }

    }

}
