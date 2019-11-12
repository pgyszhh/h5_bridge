package com.shuiyinhuo.component.mixdev.utils.pkg.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期与星期格式相互转换
 */
public class DataTansformToWeek {


    /**
     * 日期转换为星期格式
     * @param time
     * @return
     */
    public String getWeekTime(String time) {//2017-03-22
        Date date = StringToDate(time);
        return getWeekOfDate(date);
    }


    private static Date StringToDate(String String) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date time = null;
        try {
            time = format.parse(String);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    public static String getWeekOfDate(Date date) {
        String[] weekOfDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekOfDays[w];
    }

}