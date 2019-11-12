package com.shuiyinhuo.component.mixdev.utils.comm;

import android.view.ContextThemeWrapper;
import android.view.ViewGroup;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/9/8 0008
 * @ Description：动态给组件设置样式
 * =====================================
 */
public class WidgetStyleUtils {
    public void setStyleToView(ViewGroup view,int styleId){
        //oncreate中
        ContextThemeWrapper  contextThemeWrapper = new ContextThemeWrapper(view.getContext(),styleId);
//另一个button的onclick事件
        contextThemeWrapper.setTheme(styleId);
     /*   view.sty
        view.postInvalidate();
        view.set*/
    }
}
