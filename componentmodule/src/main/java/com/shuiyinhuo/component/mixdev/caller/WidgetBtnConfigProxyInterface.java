package com.shuiyinhuo.component.mixdev.caller;

import com.shuiyinhuo.component.mixdev.jinbean.pro.WindowButtonBaseConfig;
import com.shuiyinhuo.component.mixdev.entity.WindowBtnConfig;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/9/5 0005
 * @ Description：组件按钮配置代理
 * =====================================
 */
public interface WidgetBtnConfigProxyInterface {
    void   setLeftButtonConfig(WindowBtnConfig leftButtonConfig);
    void   setRightButtonConfig(WindowBtnConfig rightButtonConfig);
    void setWindBaseConfig(WindowButtonBaseConfig baseConfig);

}
