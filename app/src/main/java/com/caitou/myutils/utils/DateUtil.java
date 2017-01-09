package com.caitou.myutils.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @className: DateUtil
 * @classDescription: 时间工具类
 * @Author: Guangzhao Cai
 * @createTime: 2016-08-11.
 */
public class DateUtil {
    private static SimpleDateFormat sdf = null;

    // 获取系统时间
    public static String getCurrentDate() {
        long time = System.currentTimeMillis();
        System.out.println("time = " + time);
        Date date = new Date(time);
        sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        return sdf.format(date);
    }

    // 时间戳转换成字符串
    public static String getDate(long time) {
        String strTime = time + "";
        time = Long.parseLong(strTime) * 1000;
        sdf = new SimpleDateFormat("yyyy年MM月dd日");
        return sdf.format(time);
    }

    // 将字符串转换成时间戳
    public static long stringToDate(String time) {
        sdf = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date();

        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    // 提取时间戳中的时间
    public static String getTime(String strTime) {

        long time = Long.parseLong(strTime) * 1000;
        sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(time);
    }
}
