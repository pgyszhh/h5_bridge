package com.shuiyinhuo.component.mixdev.utils.comm;

import android.graphics.Color;
import android.view.View;

import com.shuiyinhuo.component.mixdev.jinbean.em.WindowTransParencyLevel;
import com.shuiyinhuo.component.mixdev.utils.pkg.domain.Colors;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/9/6 0006
 * @ Description：颜色工具类
 * =====================================
 */
public class ColorUtils {
    //默认的白色
    private String  baseColor="#FFFFFF";
    private ColorAlph mColorAlph;
    public ColorUtils()
    {
        mColorAlph=new ColorAlph();
    }
    /**
     * 颜色监测
     *
     * @param color
     * @return
     */
    public String checkColorValue(String color) {
        if (EmptyAndSizeUtils.isNotEmpty(color)) {
            color = color.startsWith("#") ? color : "#" + color;
        } else {
            color = "#E6E6FA";
        }
        return color;
    }


    /**
     *  颜色渲染：将字符串颜色渲染成颜色值
     * @return
     */
    public int inflaterColorFromStr(String color){
        if (EmptyAndSizeUtils.isNotEmpty(color)){
            return Color.parseColor(checkColorValue(color));
        }
        return baseColor();
    }

    /**
     * 测试背景颜色
     * @param v
     * @param color
     */
    public void setViewBgColor(View v, String color) {
        if (EmptyAndSizeUtils.isNotEmpty(v)) {
            v.setBackgroundColor(Color.parseColor(checkColorValue(color)));
        }
    }


    private int baseColor(){
        return Color.parseColor(checkColorValue(baseColor));
    }

    /**
     * 构造颜色：黑色底部
     *
     * @return
     */
    public String generatorColor_Black(WindowTransParencyLevel level) {
        return "#$000000".replaceAll("[$]", mColorAlph.getAlph(level.getLevelValue()) );
    }

    /**
     * 构造颜色,白色底部
     *
     * @return
     */
    public String generatorColor_White(WindowTransParencyLevel level) {
        return "#$FFFFFF".replaceAll("[$]", mColorAlph.getAlph(level.getLevelValue()) );
    }

    public int parase_ColorByGeneratorColor(WindowTransParencyLevel level,boolean isWhite){
        return inflaterColorFromStr(isWhite?generatorColor_White(level):generatorColor_Black(level));
    }


}
