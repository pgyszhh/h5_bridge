package com.shuiyinhuo.component.mixdev.utils.html;

import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.RelativeSizeSpan;

import com.shuiyinhuo.component.mixdev.utils.comm.EmptyAndSizeUtils;
import com.shuiyinhuo.component.mixdev.utils.comm.JNILog;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/8/18 0018
 * @ Description：Html相关工具类,格式化html字符串
 * =====================================
 */
public class HtmlUtils {
    private static String baseTextColorTempate = "<font color = 'RColor'>RText</font>";
    private static String baseTextColorAndHeaderTempate = "<font color = 'RColor'><big><b>RText</b></big></font>";
    private static String baseTextColorAndBoldTempate = "<font color = 'RColor'><b>RText</b></font>";
    private static String baseTextColorTempate2 = "<font size='RSizepx' color = 'RColor'>RText</font>";



    public static Spanned getFormatUseHtml(String sorce) {
        if (EmptyAndSizeUtils.isNotEmpty(sorce)) {
            return Html.fromHtml(sorce);
        }
        return null;
    }

    /**
     * 得到一个指定颜色的字符串
     *
     * @return
     */
    public static Spanned getAppointColorOfText(String sorce, String color) {
        if (EmptyAndSizeUtils.isNotEmpty(checkLegitimate(color))) {
            color =checkLegitimate(color);
            String newFormatStr = baseTextColorTempate.replaceAll("RText", sorce).replaceAll("RColor", color);
            return Html.fromHtml(newFormatStr);
        }
        return null;
    }


    /**
     * 得到一个指定颜色的字符串
     *
     * @return
     */
    public static Spanned getAppointColorAndbigBoldOfText(String sorce, String color) {
        if (EmptyAndSizeUtils.isNotEmpty(checkLegitimate(color))) {
            color =checkLegitimate(color);
            String newFormatStr = baseTextColorAndHeaderTempate.replaceAll("RText", sorce).replaceAll("RColor", color);
            return Html.fromHtml(newFormatStr);
        }
        return null;
    }

    /**
     * 得到一个指定颜色的字符串
     *
     * @return
     */
    public static Spanned getAppointColorAndBoldOfText(String sorce, String color) {
        if (EmptyAndSizeUtils.isNotEmpty(checkLegitimate(color))) {
            color =checkLegitimate(color);
            String newFormatStr = baseTextColorAndBoldTempate.replaceAll("RText", sorce).replaceAll("RColor", color);
            return Html.fromHtml(newFormatStr);
        }
        return null;
    }

    /**
     * 得到一个指定颜色的字符串
     *
     * @return
     */
    public static Spanned getAppointColorAndSizeOfText(String sorce, String color, int sizOfPx) {
        if (EmptyAndSizeUtils.isNotEmpty(checkLegitimate(color))) {
            color=checkLegitimate(color);
            String newFormatStr = baseTextColorTempate2.replaceAll("RText", sorce).replaceAll("RColor", color).replaceAll("RSize",sizOfPx+"");
            JNILog.e("格式化字符串："+newFormatStr);
            Spanned mSpanned = Html.fromHtml(newFormatStr);
            Spannable span = new SpannableString(mSpanned);
            SpannableStringBuilder style=new SpannableStringBuilder(span);

            int start=0;
            int end=sorce.length();
            //设置字体大小
            style.setSpan(new AbsoluteSizeSpan(sizOfPx),start,end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            //span.setSpan(new RelativeSizeSpan(1.5f), 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            span.setSpan(new RelativeSizeSpan(1f), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            //span.setSpan(new ForegroundColorSpan(Color.RED), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return span;
        }
        return null;
    }



    public static String checkLegitimate(String color) {
        if (EmptyAndSizeUtils.isNotEmpty(color)) {

        }
        String sColor = null;
        if (color.contains("#")) {
            if (color.startsWith("#")) {
                sColor = color;
            } else {
                sColor = null;
            }

        } else {
            sColor = "#" + color;
        }
        return sColor;
    }
}
