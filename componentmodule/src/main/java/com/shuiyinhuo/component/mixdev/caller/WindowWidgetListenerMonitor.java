package com.shuiyinhuo.component.mixdev.caller;

import com.shuiyinhuo.component.mixdev.proxy.ProxyTransparentWindow;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/9/6 0006
 * @ Description：透明窗口事件监测接口
 * =====================================
 */
public interface WindowWidgetListenerMonitor {
    boolean isCanBack();
    void onBackPressed(ProxyTransparentWindow window);
}
