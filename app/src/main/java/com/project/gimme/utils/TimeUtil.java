package com.project.gimme.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author DrGilbert
 * @date 2022/1/11 16:08
 */
public class TimeUtil {
    /**
     * 将时间化为HH:mm制
     *
     * @param date 要转化的时间
     * @return 转化好的时间
     */
    public static String changeToHourAndMinute(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(date);
    }
}
