package com.shuiyinhuo.component.mixdev.jinbean.em;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/9/8 0008
 * @ Description：颜色透明度级别
 * =====================================
 */
public enum WindowTransParencyLevel {
    TRANS_PARENCY_LEVEL_DEFAULT(50),
    TRANS_PARENCY_LEVEL_0(0),
    TRANS_PARENCY_LEVEL_5(5),
    TRANS_PARENCY_LEVEL_10(10),
    TTRANS_PARENCY_LEVEL_15(15),
    TRANS_PARENCY_LEVEL_20(20),
    TRANS_PARENCY_LEVEL_25(25),
    TRANS_PARENCY_LEVEL_30(30),
    TRANS_PARENCY_LEVEL_35(35),
    TRANS_PARENCY_LEVEL_40(40),
    TRANS_PARENCY_LEVEL_45(45),
    TRANS_PARENCY_LEVEL_50(50),
    TRANS_PARENCY_LEVEL_55(55),
    TRANS_PARENCY_LEVEL_60(60),
    TRANS_PARENCY_LEVEL_65(65),
    TRANS_PARENCY_LEVEL_70(70),
    TRANS_PARENCY_LEVEL_75(75),
    TRANS_PARENCY_LEVEL_80(80),
    TRANS_PARENCY_LEVEL_85(85),
    TRANS_PARENCY_LEVEL_90(90),
    TRANS_PARENCY_LEVEL_95(95),
    TRANS_PARENCY_LEVEL_100(100);
    
    private int level=50;
    private WindowTransParencyLevel(int level){
        this.level=level;
    }
    
    public int getLevelValue(){
        return this.level;
    }
    
}
