package com.shuiyinhuo.component.mixdev.dispatcher.observe;

import com.shuiyinhuo.component.mixdev.dispatcher.dispatch.MessageDisapther;
import com.shuiyinhuo.component.mixdev.utils.comm.EmptyAndSizeUtils;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/8/25 0025
 * @ Description：网络状态监测
 * =====================================
 */
public class INetStater {
    private MessageDisapther mDisapther;

    public INetStater(MessageDisapther disapther) {
        mDisapther = disapther;
    }



    /**
     * 告诉某个Webview
     * @param Alias
     */
    public void setStartNetWork(String Alias,int ...outime){
        if (EmptyAndSizeUtils.isNotEmpty(mDisapther)&&EmptyAndSizeUtils.isNotEmpty(Alias)){
            mDisapther.startNetWork(Alias,outime);
        }
    }

    public void setEndNetWork(String Alias){
        if (EmptyAndSizeUtils.isNotEmpty(mDisapther)&&EmptyAndSizeUtils.isNotEmpty(Alias)){
            mDisapther.endNetWork(Alias);
        }
    }
}
