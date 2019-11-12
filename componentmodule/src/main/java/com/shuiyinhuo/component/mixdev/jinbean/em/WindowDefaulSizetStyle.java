package com.shuiyinhuo.component.mixdev.jinbean.em;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/9/6 0006
 * @ Description：窗口默认样式枚举类
 * <p>
 * <p>
 * 自定义枚举类，一定要有构造方法和对应的值，
 * 其中的每个枚举类型都代表一个当前枚举类
 * =====================================
 */
public enum WindowDefaulSizetStyle {
    STYLE_MINI("mini", 0.45f),
    STYLE_NORMAL("normal", 0.6f),
    STYLE_BIG("big", 1f),
    STYLE_BIGGER("bigger", 1.3f);

    private String key = "";
    private float value = -1;

    WindowDefaulSizetStyle(String key, float value) {
        this.key = key;
        this.value = value;
    }

    public String getKey(){
        return this.key;
    }
    public float getValue(){
        return this.value;
    };


    public float getValue(String key) {
        for (WindowDefaulSizetStyle style : WindowDefaulSizetStyle.values()) {
            if (style.getKey() ==key){
                return  style.getValue();
            }
        }
        return -1;
    }
}
