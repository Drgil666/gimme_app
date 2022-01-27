package com.project.gimme.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author DrGilbert
 * @date 2022/1/11 16:08
 */
public class NumberUtil {
    private static final String HOUR_AND_MINUTE = "HH:mm";
    private static final String YEAR_AND_MONTH_DAY = "yy-MM-dd";
    private static final Long B_SIZE = 8L;
    private static final Long KB_SIZE = 1024 * 8L;
    private static final Long MB_SIZE = 1024 * 1024 * 8L;
    private static final Long GB_SIZE = 1024 * 1024 * 1024 * 8L;

    /**
     * 将时间化为HH:mm制
     *
     * @param date 要转化的时间
     * @return 转化好的时间
     */
    public static String changeToHourAndMinute(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(HOUR_AND_MINUTE);
        return format.format(date);
    }

    public static String changeToYearAndMonthAndDay(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(YEAR_AND_MONTH_DAY);
        return format.format(date);
    }

    public static String changeToFileSize(Long size) {
        if (size >= GB_SIZE) {
            return new DecimalFormat("#.00").format(size * 1.0 / GB_SIZE) + "GB";
        } else if (size >= MB_SIZE) {
            return new DecimalFormat("#.00").format(size * 1.0 / MB_SIZE) + "MB";
        } else if (size >= KB_SIZE) {
            return new DecimalFormat("#.00").format(size * 1.0 / KB_SIZE) + "KB";
        } else {
            return new DecimalFormat("#.00").format(size * 1.0 / B_SIZE) + "Byte";
        }
    }
}
