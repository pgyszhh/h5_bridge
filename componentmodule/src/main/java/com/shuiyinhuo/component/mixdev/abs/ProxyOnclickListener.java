package com.shuiyinhuo.component.mixdev.abs;

import android.view.View;

import com.shuiyinhuo.component.mixdev.utils.comm.EmptyAndSizeUtils;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/9/4 0004
 * @ Description：
 * =====================================
 */
public abstract class ProxyOnclickListener implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        int id= EmptyAndSizeUtils.isEmpty(v)?-1:v.getId();
        onClick(v,id);
    }

    public abstract void onClick(View view,int id);
}
