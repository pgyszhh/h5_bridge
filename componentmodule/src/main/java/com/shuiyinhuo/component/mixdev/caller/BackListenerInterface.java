package com.shuiyinhuo.component.mixdev.caller;

import android.app.Activity;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/9/5 0005
 * @ Description：返回回到
 * =====================================
 */
public interface BackListenerInterface {
    /**
     * 返回回调监听，摁下返回键时回调
     */
    void onKeyDownBack(Activity activity);

    /**
     *  控制窗口的销毁，检测home键与返回键
     * 获取可返回状态
     *     true:返回销毁window
     *     不能通过返回键销毁window
     * @return
     */
    boolean isCanDismissWindow();
}
