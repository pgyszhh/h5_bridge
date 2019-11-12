package com.shuiyinhuo.component.mixdev.caller;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/8/17 0017
 * @ Description：调用js拿去返回值接口
 * =====================================
 */
public interface OnJSCarryValuesBackInterface {
    void onSucc(String Alias,String backValue);
    void onfailed(String Alias,String error);

}
