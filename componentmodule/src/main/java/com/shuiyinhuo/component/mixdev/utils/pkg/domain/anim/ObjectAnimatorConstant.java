package com.shuiyinhuo.component.mixdev.utils.pkg.domain.anim;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/9/9 0009
 * @ Description：属性动画的属性常量
 * =====================================
 */
public enum ObjectAnimatorConstant {
    TRANSLATIONX("translationX", 400),

    TRANSLATIONY("translationY", 400),

    ROTATION("rotation", 400),

    SCALEX("scaleX", 400),

    SCALEY("scaleY", 400),

    ALPHA("alpha", 400);

    private int durationMillis = 400;
    private String proper = "translationX";


    ObjectAnimatorConstant(String proper, int durationMillis) {
        this.durationMillis = durationMillis;
        this.proper = proper;
    }

    public int getDurationMillis() {
        return durationMillis;
    }

    public void setDurationMillis(int durationMillis) {
        this.durationMillis = durationMillis;
    }

    public String getProper() {
        return proper;
    }

    public void setProper(String proper) {
        this.proper = proper;
    }


}
